package com.teammaker.teamgenerator.ui.userlist

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.teammaker.teamgenerator.R
import com.teammaker.teamgenerator.core.extensions.afterTextChanged
import com.teammaker.teamgenerator.core.extensions.hideKeyboard
import com.teammaker.teamgenerator.databinding.ActivityUserlistBinding
import com.teammaker.teamgenerator.ui.result.ResultActivity
import com.teammaker.teamgenerator.ui.userlist.adapter.UsersAdapter


@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityUserlistBinding
private lateinit var userListViewModel: UserListViewModel

private var adapter: UsersAdapter = UsersAdapter(mutableListOf())

class UserListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userListViewModel = ViewModelProvider(
            this,
            defaultViewModelProviderFactory
        ).get(UserListViewModel::class.java)
        userListViewModel.onCreate()
        initUI()
    }

    private fun initUI() {
        initSubscription()
        initToolbar()
        initUserList()
        initListeners()
    }

    private fun initSubscription() {
        userListViewModel.usersLiveData.observe(this, {
            binding.btnGenerateTeam.isEnabled = it.isNotEmpty()
            adapter.userList = it
            adapter.notifyDataSetChanged()
        })
        userListViewModel.eventLiveData.observe(this, {
            //if (it.isNotEmpty()) {
            if (it.size > 1) {
                startActivity(ResultActivity.create(this, ArrayList(it)))
            } else {
                Toast.makeText(this, getString(R.string.error_nominplayers), Toast.LENGTH_SHORT)
                    .show();
            }
        })
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun initUserList() {
        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = adapter
    }

    private fun initListeners() {
        binding.btnAddUser.setOnClickListener {
            userListViewModel.onUserAdded(binding.etAddUser.text.toString())
            binding.etAddUser.text.clear()
            hideKeyboard()
        }
        binding.etAddUser.afterTextChanged {
            binding.btnAddUser.isEnabled = it.isNotEmpty()
        }
        binding.btnGenerateTeam.setOnClickListener {
            userListViewModel.onGenerateTeamsButtonClicked()
        }
    }
}