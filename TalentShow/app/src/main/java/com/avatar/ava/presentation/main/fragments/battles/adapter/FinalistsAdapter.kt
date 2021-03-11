package com.avatar.ava.presentation.main.fragments.battles.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.avatar.ava.R
import com.avatar.ava.di.DataModule
import com.avatar.ava.domain.entities.finalists.FinalDTO
import com.avatar.ava.domain.entities.finalists.Finalist
import com.avatar.ava.presentation.main.fragments.battles.finaists.FinalistsFragment
import com.bumptech.glide.Glide

class FinalistsAdapter(private val clickListener: FinalistsFragment.FinalistListener)
    : RecyclerView.Adapter<FinalistsAdapter.FinalistsViewHolder>() {
    private var data: List<Finalist> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinalistsViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.finalist_item, parent, false)
        return FinalistsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FinalistsViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun setItems(battle: FinalDTO) {
        data = battle.finalists
        notifyDataSetChanged()
    }

    fun vote(battleParticipant: Finalist) {
        var updatedPosition: Int? = null

        data = data.mapIndexed { position, participantItem ->
            if (battleParticipant.id == participantItem.id) {
                updatedPosition = position
                return@mapIndexed battleParticipant
            } else {
                return@mapIndexed participantItem
            }
        }

        updatedPosition?.let {
            notifyItemChanged(it, battleParticipant)
        }
    }

    inner class FinalistsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userAva: ImageView = itemView.findViewById(R.id.rating_recycler_semifinalists_ava_item)
        private val userName: TextView = itemView.findViewById(R.id.rating_recycler_semifinalists_name_item)
        private val description: TextView = itemView.findViewById(R.id.rating_item_description)
        private val bg: ImageView = itemView.findViewById(R.id.rating_recycler_semifinalists_ava_item_bg)
        private val layout: ConstraintLayout = itemView.findViewById(R.id.rating_recycler_semifinalists_layout)

        private val buttonLike: View = itemView.findViewById(R.id.button_like)
        private val likeFullImage: View = itemView.findViewById(R.id.like_full)
        private val likeImage: View = itemView.findViewById(R.id.like)

        fun bind(finalist: Finalist) {
            layout.setOnClickListener {
                clickListener.detailClicked(finalist.finalistInfo.id)
            }

            buttonLike.setOnClickListener {
                clickListener.voteClicked(finalist)
            }

            userName.text = finalist.finalistInfo.name
            description.text = finalist.finalistInfo.description

            if (finalist.isVoted) {
                bg.visibility = View.VISIBLE
                likeFullImage.visibility = View.VISIBLE
                likeImage.visibility = View.INVISIBLE
            } else {
                bg.visibility = View.INVISIBLE
                likeFullImage.visibility = View.INVISIBLE
                likeImage.visibility = View.VISIBLE
            }

            Glide.with(itemView.context)
                    .load(DataModule.SERVER_NAME + "/api/profile/photo/get/" + finalist.finalistInfo.profilePhoto)
                    .circleCrop()
                    .into(userAva)
        }

    }
}