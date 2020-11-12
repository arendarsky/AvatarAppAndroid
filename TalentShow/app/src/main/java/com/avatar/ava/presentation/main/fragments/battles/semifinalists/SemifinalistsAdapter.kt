package com.avatar.ava.presentation.main.fragments.battles.semifinalists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.avatar.ava.DataModule
import com.avatar.ava.R
import com.avatar.ava.presentation.main.fragments.battles.semifinalists.SemifinalistsAdapter.SemifinalistsViewHolder
import com.avatar.ava.presentation.main.fragments.battles.item.BattleItem
import com.avatar.ava.presentation.main.fragments.battles.item.ParticipantItem
import com.avatar.ava.presentation.main.fragments.battles.listener.RecyclerClickListener
import com.bumptech.glide.Glide

class SemifinalistsAdapter(private val clickListener: RecyclerClickListener)
    : RecyclerView.Adapter<SemifinalistsViewHolder>() {
    private var data: List<ParticipantItem> = listOf()
    private var battleId: Int? = null
    private var totalVotes: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SemifinalistsViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.rating_recycler_semifinalists_item, parent, false)
        return SemifinalistsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SemifinalistsViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun setItems(battle: BattleItem) {
        this.battleId = battle.id
        this.totalVotes = battle.battleParticipants.sumBy { it.semifinalist.votesNumber }
        data = battle.battleParticipants
        notifyDataSetChanged()
    }

    inner class SemifinalistsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userAva: ImageView = itemView.findViewById(R.id.rating_recycler_semifinalists_ava_item)
        private val userName: TextView = itemView.findViewById(R.id.rating_recycler_semifinalists_name_item)
        private val userLikes: TextView = itemView.findViewById(R.id.rating_recycler_semifinalists_likes_item)
        private val bg: ImageView = itemView.findViewById(R.id.rating_recycler_semifinalists_ava_item_bg)
        private val layout: ConstraintLayout = itemView.findViewById(R.id.rating_recycler_semifinalists_layout)

        fun bind(battleParticipant: ParticipantItem) {
            layout.setOnClickListener {
                battleId?.let { clickListener.selectClicked(it, battleParticipant) }
            }

            val participantVotes = battleParticipant.semifinalist.votesNumber
            totalVotes?.let {
                val percentage = (participantVotes.toDouble() / it.toDouble()) * 100
                val percent = String.format(itemView.context.getString(R.string.percentage_format), percentage.toInt())
                userLikes.text = percent
            }
            userName.text = battleParticipant.name

            if (battleParticipant.semifinalist.isLikedByUser) {
                bg.visibility = View.VISIBLE
            } else {
                bg.visibility = View.INVISIBLE
            }

            if (battleParticipant.profilePhoto != null) Glide.with(itemView.context)
                    .load(DataModule.SERVER_NAME + "/api/profile/photo/get/" + battleParticipant.profilePhoto)
                    .circleCrop()
                    .into(userAva) else Glide.with(itemView.context)
                    .load(R.drawable.empty_profile)
                    .centerCrop()
                    .into(userAva)
        }

    }
}