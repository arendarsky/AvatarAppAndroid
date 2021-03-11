package com.avatar.ava.presentation.main.fragments.battles

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.avatar.ava.App
import com.avatar.ava.R
import com.avatar.ava.presentation.main.fragments.battles.adapter.BattleTab
import com.avatar.ava.presentation.main.fragments.battles.adapter.PagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_battles.*
import toothpick.Toothpick
import javax.inject.Inject

class BattlesFragment : MvpAppCompatFragment(), BattlesView {

    @Inject
    lateinit var appContext: Context

    @InjectPresenter
    lateinit var battlesPresenter: BattlesPresenter

    private lateinit var pagerAdapter: PagerAdapter

    @ProvidePresenter
    fun getPresenter(): BattlesPresenter {
        return Toothpick.openScope(App::class.java).getInstance(BattlesPresenter::class.java)
    }

    override fun onAttach(context: Context) {
        Toothpick.inject(this, Toothpick.openScope(App::class.java))
        super.onAttach(context)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?)
    : View? {
        return inflater.inflate(R.layout.fragment_battles, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pagerAdapter = PagerAdapter(childFragmentManager)
        pager.adapter = pagerAdapter
        tabs.setupWithViewPager(pager)
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(p0: TabLayout.Tab?) {}

            override fun onTabUnselected(p0: TabLayout.Tab?) {}

            override fun onTabReselected(p0: TabLayout.Tab?) {}

        })
        super.onViewCreated(view, savedInstanceState)
    }

    override fun addFinalTab() {
        pagerAdapter.addTab(BattleTab.FINALISTS)
    }

    override fun addSemifinalTab() {
        pagerAdapter.addTab(BattleTab.SEMIFINALISTS)
    }
}