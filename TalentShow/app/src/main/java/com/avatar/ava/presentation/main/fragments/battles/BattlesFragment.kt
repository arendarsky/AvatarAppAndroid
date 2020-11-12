package com.avatar.ava.presentation.main.fragments.battles

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.avatar.ava.App
import com.avatar.ava.R
import com.avatar.ava.presentation.main.MainScreenPostman
import com.avatar.ava.presentation.main.fragments.battles.item.BattleItem
import com.avatar.ava.presentation.main.fragments.battles.item.ParticipantItem
import com.avatar.ava.presentation.main.fragments.battles.listener.RecyclerClickListener
import kotlinx.android.synthetic.main.error_message.*
import kotlinx.android.synthetic.main.fragment_battles.*
import toothpick.Toothpick
import javax.inject.Inject

class BattlesFragment : MvpAppCompatFragment(), BattlesView {

    @Inject
    lateinit var appContext: Context

    @InjectPresenter
    lateinit var battlesPresenter: BattlesPresenter

    private lateinit var battlesAdapter: BattlesAdapter

    @ProvidePresenter
    fun getPresenter(): BattlesPresenter {
        return Toothpick.openScope(App::class.java).getInstance(BattlesPresenter::class.java)
    }

    override fun onAttach(context: Context) {
        Toothpick.inject(this, Toothpick.openScope(App::class.java))
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        battlesAdapter = BattlesAdapter(listener = object : RecyclerClickListener {
            override fun voteClicked(battleId: Int, battleParticipant: ParticipantItem) {
                battlesPresenter.vote(battleId, battleParticipant)
            }

            override fun selectClicked(battleId: Int, battleParticipant: ParticipantItem) {
                battlesAdapter.selectParticipant(battleId, battleParticipant)
            }

            override fun detailClicked(battleParticipantId: Int) {
                (activity as MainScreenPostman).openPublicProfile(battleParticipantId)
            }
        }, appContext)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?)
    : View? {
        return inflater.inflate(R.layout.fragment_battles, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(activity)
        val dividerItemDecoration = DividerItemDecoration(
                recycler_battles.context,
                linearLayoutManager.orientation
        ).apply {
            val divider = ContextCompat.getDrawable(appContext, R.drawable.recycler_view_divider)
            divider?.let {
                setDrawable(it)
            }
        }
        recycler_battles.addItemDecoration(dividerItemDecoration)
        recycler_battles.layoutManager = linearLayoutManager
        recycler_battles.adapter = battlesAdapter

        retry_loading_button.setOnClickListener {
            battlesPresenter.loadBattles()
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

    override fun vote(battleId: Int, battleParticipant: ParticipantItem) {
        battlesAdapter.vote(battleId, battleParticipant)
    }

    override fun setBattles(battles: List<BattleItem>) {
        battlesAdapter.setBattles(battles)
    }

    override fun onPause() {
        battlesAdapter.stopAudioPlayers()
        super.onPause()
    }
}