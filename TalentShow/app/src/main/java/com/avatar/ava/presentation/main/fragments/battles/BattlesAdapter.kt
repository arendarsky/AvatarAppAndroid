package com.avatar.ava.presentation.main.fragments.battles

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avatar.ava.presentation.main.fragments.battles.item.BattleItem
import com.avatar.ava.presentation.main.fragments.battles.item.ParticipantItem
import com.avatar.ava.presentation.main.fragments.battles.listener.RecyclerClickListener

import com.avatar.ava.presentation.main.fragments.battles.viewholder.BattlesViewHolder
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer

class BattlesAdapter(
    private val listener: RecyclerClickListener,
    val context: Context
) : RecyclerView.Adapter<BattlesViewHolder>() {

    private var mapBattlesAudioPlayers: MutableMap<Int, SimpleExoPlayer> = mutableMapOf()
    private var battles: MutableList<BattleItem> = mutableListOf()

    fun setBattles(battles: List<BattleItem>) {
        this.battles = battles.toMutableList()
        this.battles.forEach {
            mapBattlesAudioPlayers.fillAudioPlayersMap(battleId = it.id)
        }
        notifyDataSetChanged()
    }

    fun stopAudioPlayers() {
        mapBattlesAudioPlayers.stopAudioPlayers()
    }

    fun selectParticipant(battleId: Int, battleParticipant: ParticipantItem) {
        mapBattlesAudioPlayers.pauseAudioPlayers()

        var updatedPosition: Int? = null
        battles = battles.mapIndexed { position, item ->
            if (item.id == battleId) {
                updatedPosition = position
                return@mapIndexed item.copy(selectedParticipant = battleParticipant)
            }
            return@mapIndexed item
        }.toMutableList()



        updatedPosition?.let {
            notifyItemChanged(it, battleParticipant)
        }
    }

    fun vote(battleId: Int, battleParticipant: ParticipantItem) {
        var updatedPosition: Int? = null
        var updatedBattle: BattleItem? = null
        battles = battles.mapIndexed { position, item ->
            if (item.id == battleId) {
                updatedPosition = position

                updatedBattle = item.copy(
                        selectedParticipant = battleParticipant,
                        battleParticipants = item.battleParticipants.map {
                            if (it.id == battleParticipant.id) {
                                return@map battleParticipant
                            } else {
                                return@map it
                            }
                        }.toMutableList()
                )

                return@mapIndexed item.copy(
                        battleParticipants = item.battleParticipants.map {
                            if (it.id == battleParticipant.id) {
                                return@map battleParticipant
                            } else {
                                return@map it
                            }
                        }.toMutableList()
                )
            }
            return@mapIndexed item
        }.toMutableList()

        updatedPosition?.let {
            notifyItemChanged(it, updatedBattle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BattlesViewHolder =
        BattlesViewHolder(parent, listener)

    override fun onBindViewHolder(holder: BattlesViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            payloads.forEach {
                when(it) {
                    is ParticipantItem -> {
                        val battle = battles[position]
                        holder.selectParticipant(battle.id, it, mapBattlesAudioPlayers[battle.id])
                    }
                    is BattleItem      -> {
                        holder.vote(it)
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: BattlesViewHolder, position: Int) {
        val battle = battles[position]
        holder.bind(battle, mapBattlesAudioPlayers[battle.id])
    }

    override fun getItemCount(): Int = battles.size

    private fun MutableMap<Int, SimpleExoPlayer>.fillAudioPlayersMap(battleId: Int) {
        this[battleId] = SimpleExoPlayer.Builder(context).build().apply {
            addListener(object : Player.EventListener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)
                    if (isPlaying) {
                        mapBattlesAudioPlayers.forEach {
                            if (it.value != this@apply) {
                                it.value.playWhenReady = false
                            }
                        }
                    }
                }
            })
        }
    }

    private fun MutableMap<Int, SimpleExoPlayer>.stopAudioPlayers() {
        this.forEach {
            it.value.stop()
        }
    }

    private fun MutableMap<Int, SimpleExoPlayer>.pauseAudioPlayers() {
        this.forEach {
            it.value.playWhenReady = false
        }
    }
}