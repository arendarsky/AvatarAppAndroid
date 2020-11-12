package com.avatar.ava.presentation.main.fragments.battles.viewholder

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avatar.ava.DataModule
import com.avatar.ava.R
import com.avatar.ava.presentation.main.fragments.battles.semifinalists.SemifinalistsAdapter
import com.avatar.ava.presentation.main.fragments.battles.item.BattleItem
import com.avatar.ava.presentation.main.fragments.battles.item.ParticipantItem
import com.avatar.ava.presentation.main.fragments.battles.listener.RecyclerClickListener
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.Util
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class BattlesViewHolder(
        parent: ViewGroup,
        private val listener: RecyclerClickListener,
) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
                R.layout.item_battle,
                parent,
                false
        )
) {
    var time: TextView = itemView.findViewById(R.id.fragment_semifinalists_time)
    var recyclerView: RecyclerView = itemView.findViewById(R.id.fragment_semifinalists_list)
    var video: PlayerView = itemView.findViewById(R.id.rating_item_video)
    var name: TextView = itemView.findViewById(R.id.rating_item_name)
    var description: TextView = itemView.findViewById(R.id.rating_item_description)
    var ava: ImageView = itemView.findViewById(R.id.rating_item_ava)
    var likes: TextView = itemView.findViewById(R.id.rating_item_likes)
    var winnersNumber: TextView = itemView.findViewById(R.id.hint_winners_number)

    var buttonLike: View = itemView.findViewById(R.id.button_like)
    var likeFullImage: View = itemView.findViewById(R.id.like_full)
    var likeImage: View = itemView.findViewById(R.id.like)

    private lateinit var mediaSource: MediaSource
    private lateinit var factory: DataSource.Factory

    private val adapter: SemifinalistsAdapter = SemifinalistsAdapter(listener)

    private val simpleDateFormat = SimpleDateFormat("dd : hh : mm")

    fun bind(item: BattleItem, player: SimpleExoPlayer?) {
        initParticipantsAdapter(item)
        setBattleEndTime()

        setProfileData(item.selectedParticipant)

        item.selectedParticipant.semifinalist.videoName?.let { initPlayer(it, player) }

        winnersNumber.text = String.format(
            itemView.context.getString(R.string.winners_number_format),
            item.winnersNumber
        )

        buttonLike.setOnClickListener {
            listener.voteClicked(item.id, item.selectedParticipant)
        }

        ava.setOnClickListener {
            listener.detailClicked(item.selectedParticipant.id)
        }
    }

    fun selectParticipant(selectedBattleId: Int, selectedParticipant: ParticipantItem, player: SimpleExoPlayer?) {
        setProfileData(selectedParticipant)
        selectedParticipant.semifinalist.videoName?.let { initPlayer(it, player) }

        buttonLike.setOnClickListener {
            listener.voteClicked(selectedBattleId, selectedParticipant)
        }

        ava.setOnClickListener {
            listener.detailClicked(selectedParticipant.id)
        }
    }

    fun vote(item: BattleItem) {
        initParticipantsAdapter(item)
        likes.text = item.selectedParticipant.semifinalist.votesNumber.toString()

        if (item.selectedParticipant.semifinalist.isLikedByUser) {
            likeFullImage.isVisible = true
            likeImage.isVisible = false
        } else {
            likeFullImage.isVisible = false
            likeImage.isVisible = true
        }
    }

    private fun setProfileData(participant: ParticipantItem) {
        if (participant.profilePhoto != null) Glide.with(itemView)
                .load(DataModule.SERVER_NAME + "/api/profile/photo/get/" + participant.profilePhoto)
                .circleCrop()
                .into(ava) else Glide.with(itemView)
                .load(R.drawable.empty_profile)
                .centerCrop()
                .into(ava)
        name.text = participant.name
        description.text = participant.description
        likes.text = participant.semifinalist.votesNumber.toString()

        if (participant.semifinalist.isLikedByUser) {
            likeFullImage.isVisible = true
            likeImage.isVisible = false
        } else {
            likeFullImage.isVisible = false
            likeImage.isVisible = true
        }
    }

    private fun initParticipantsAdapter(battleItem: BattleItem) {
        recyclerView.layoutManager = LinearLayoutManager(
                itemView.context,
                LinearLayoutManager.HORIZONTAL,
                false
        )
        recyclerView.adapter = adapter
        adapter.setItems(battleItem)
    }

    private fun setBattleEndTime() {
        try {
            Log.d("SFLog", "here")
            val date1 = simpleDateFormat.parse("21 : 14 : 10")
            val calendar = Calendar.getInstance()
            val date2 = simpleDateFormat.parse(calendar[Calendar.DAY_OF_MONTH].toString() + " : " + calendar[Calendar.HOUR] + " : " + calendar[Calendar.MINUTE])
            printDifference(date2, date1)
        } catch (e: ParseException) {
            Log.d("SFLog", "error")
            e.printStackTrace()
        }
    }

    private fun printDifference(startDate: Date, endDate: Date) {
        //milliseconds
        var different = endDate.time - startDate.time
        Log.d("SFLOG", "startDate : $startDate")
        Log.d("SFLOG", "endDate : $endDate")
        Log.d("SFLOG", "different : $different")
        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24
        val elapsedDays = different / daysInMilli
        different = different % daysInMilli
        val elapsedHours = different / hoursInMilli
        different = different % hoursInMilli
        val elapsedMinutes = different / minutesInMilli
        different = different % minutesInMilli
        val elapsedSeconds = different / secondsInMilli
        Log.d("SFLOG", "$elapsedDays $elapsedHours $elapsedMinutes $elapsedSeconds")
        time.text = "$elapsedDays : $elapsedHours : $elapsedMinutes"
    }

    private fun initPlayer(videoName: String, player: SimpleExoPlayer?) {
        factory = DefaultDataSourceFactory(itemView.context,
                Util.getUserAgent(itemView.context, "XCE FACTOR"))
        mediaSource = ProgressiveMediaSource.Factory(factory)
                .createMediaSource(Uri.parse(DataModule.SERVER_NAME + "/api/video/" + videoName))
        video.player = player
        player?.prepare(mediaSource)
        player?.playWhenReady = false
    }
}