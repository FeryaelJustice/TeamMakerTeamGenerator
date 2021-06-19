package com.teammaker.teamgenerator.ui.result.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.teammaker.teamgenerator.R
import com.teammaker.teamgenerator.databinding.ItemMatchResultBinding
import com.teammaker.teamgenerator.domain.model.MatchTeam

class MatchResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemMatchResultBinding.bind(view)

    fun render(match: Pair<MatchTeam, MatchTeam>) {
        //binding.tvMatchResult.text = "Play ${match.first.team[0].name} vs ${match.second.team[0].name}"
        binding.tvMatchResult.text = itemView.context.getString(
            R.string.match,
            match.first.team[0].name,
            match.second.team[0].name
        )
    }
}