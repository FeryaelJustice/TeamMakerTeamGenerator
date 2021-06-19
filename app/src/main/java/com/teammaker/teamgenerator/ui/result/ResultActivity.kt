package com.teammaker.teamgenerator.ui.result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.teammaker.teamgenerator.R
import com.teammaker.teamgenerator.databinding.ActivityResultBinding
import com.teammaker.teamgenerator.domain.model.MatchTeam
import com.teammaker.teamgenerator.domain.model.UserModel
import com.teammaker.teamgenerator.ui.result.adapter.MatchResultAdapter

class ResultActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_LIST = "extra_list"
        fun create(context: Context, users: ArrayList<UserModel>): Intent {
            return Intent(context, ResultActivity::class.java).apply {
                putParcelableArrayListExtra(EXTRA_LIST, users)
            }
        }
    }

    private lateinit var binding: ActivityResultBinding
    private val resultViewModel: ResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        resultViewModel.onCreate(
            intent.getParcelableArrayListExtra(EXTRA_LIST)
                ?: arrayListOf()
        )
        initUI()
    }

    private fun initUI() {
        initObservables()
    }

    private fun initObservables() {
        resultViewModel.matchTeamsLiveData.observe(this, {
            if (it.isNotEmpty()) {
                initRecyclerView(it)
            } else {
                // Error
                Toast.makeText(this, getString(R.string.error_nominplayers), Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun initRecyclerView(result: List<Pair<MatchTeam, MatchTeam>>) {
        binding.rvResult.setHasFixedSize(true)
        binding.rvResult.layoutManager = LinearLayoutManager(this)
        binding.rvResult.adapter = MatchResultAdapter(result)
    }
}