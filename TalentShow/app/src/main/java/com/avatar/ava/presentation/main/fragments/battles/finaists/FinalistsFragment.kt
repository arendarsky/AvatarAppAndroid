package com.avatar.ava.presentation.main.fragments.battles.finaists

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.avatar.ava.App
import com.avatar.ava.R
import com.avatar.ava.domain.entities.finalists.FinalDTO
import com.avatar.ava.domain.entities.finalists.Finalist
import com.avatar.ava.presentation.main.MainScreenPostman
import com.avatar.ava.presentation.main.fragments.battles.adapter.FinalistsAdapter
import com.avatar.ava.presentation.main.fragments.battles.adapter.setMiddleDivider
import com.avatar.ava.presentation.main.fragments.battles.finaists.webview.FinalWebViewClient
import kotlinx.android.synthetic.main.error_message.*
import kotlinx.android.synthetic.main.fragment_finalists.*
import toothpick.Toothpick
import java.text.SimpleDateFormat
import javax.inject.Inject

class FinalistsFragment : MvpAppCompatFragment(), FinalistsView {

    @Inject
    lateinit var appContext: Context


    @InjectPresenter
    lateinit var battlesPresenter: FinalistsPresenter

    private lateinit var adapter: FinalistsAdapter

    private val simpleDateFormat = SimpleDateFormat("dd : hh : mm")

    @ProvidePresenter
    fun getPresenter(): FinalistsPresenter {
        return Toothpick.openScope(App::class.java).getInstance(FinalistsPresenter::class.java)
    }

    override fun onAttach(context: Context) {
        Toothpick.inject(this, Toothpick.openScope(App::class.java))
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = FinalistsAdapter(object : FinalistListener {
            override fun voteClicked(finalist: Finalist) {
                battlesPresenter.vote(finalist)
            }

            override fun detailClicked(finalistId: Int) {
                (activity as MainScreenPostman).openPublicProfile(finalistId)
            }

        })
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?)
            : View? {
        return inflater.inflate(R.layout.fragment_finalists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        recycler_battles.layoutManager = linearLayoutManager
        recycler_battles.adapter = adapter
        recycler_battles.setMiddleDivider(
            adapter.itemCount,
            R.drawable.divider_horizontal
        )

        retry_loading_button.setOnClickListener {
            battlesPresenter.loadBattle()
        }
    }

    override fun disableUI(text: String) {
        recycler_battles.isVisible = false
        error_message.isVisible = true
        loading_error_text_view.text = text
    }

    override fun enableUI() {
        recycler_battles.isVisible = true
        error_message.isVisible = false
    }

    override fun vote(finalist: Finalist) {
        adapter.vote(finalist)
    }

    override fun setBattle(battle: FinalDTO) {
        setFinalEndTime(battle.secondsUntilEnd)

        hint_winners_number.text = String.format(
            getString(R.string.winners_number_format),
            battle.winnersNumber
        )
        adapter.setItems(battle)
        video_player.apply {
            settings.javaScriptEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.domStorageEnabled = true
            webViewClient = FinalWebViewClient()
            webChromeClient = WebChromeClient()
            loadUrl(battle.videoUrl)
        }
    }

    private fun setFinalEndTime(secondsUntilEnd: Int) {
        val days = secondsUntilEnd / 86400
        val hours = (secondsUntilEnd - 86400 * days) / 3600
        val minutes = (secondsUntilEnd - 86400 * days - 3600 * hours) / 60

        final_time.text = String.format(
                getString(R.string.date_format),
                days,
                hours,
                minutes
        )
    }

    override fun onPause() {
        video_player.clearCache(true)
        super.onPause()
    }

    interface FinalistListener {
        fun voteClicked(finalist: Finalist)
        fun detailClicked(finalistId: Int)
    }
}