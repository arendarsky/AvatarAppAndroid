package com.avatar.ava.presentation.main.fragments.battles.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import com.avatar.ava.presentation.main.fragments.battles.finaists.FinalistsFragment
import com.avatar.ava.presentation.main.fragments.battles.semifinalists.SemifinalistsFragment

enum class BattleTab {
    FINALISTS,
    SEMIFINALISTS
}

class PagerAdapter(fragmentManager: FragmentManager)
    : FragmentPagerAdapter(fragmentManager, Lifecycle.State.CREATED.ordinal) {

    private val tabs: MutableList<BattleTab> = mutableListOf()

    fun addTab(tab: BattleTab) {
        if (!tabs.contains(tab)) {
            tabs.add(tab)
            notifyDataSetChanged()
        }
    }

    override fun getCount(): Int {
        return tabs.size
    }

    override fun getItem(position: Int): Fragment {
        return when (tabs[position]) {
            BattleTab.FINALISTS     -> {
                FinalistsFragment()
            }
            BattleTab.SEMIFINALISTS -> {
                SemifinalistsFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (tabs[position]) {
            BattleTab.FINALISTS     -> {
                "Финал"
            }
            BattleTab.SEMIFINALISTS -> {
                "Полуфинал"
            }
        }
    }
}