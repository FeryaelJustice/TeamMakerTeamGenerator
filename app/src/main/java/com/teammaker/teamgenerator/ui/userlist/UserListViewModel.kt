package com.teammaker.teamgenerator.ui.userlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teammaker.teamgenerator.domain.model.UserModel

class UserListViewModel : ViewModel() {

    // Mutable list of players introduced
    val usersLiveData = MutableLiveData<MutableList<UserModel>>()

    // Temp variable that copies the content of the previous variable each time the button of create teams is clicked
    val eventLiveData = MutableLiveData<MutableList<UserModel>>()

    fun onCreate() {
        usersLiveData.postValue(mutableListOf())
    }

    fun onUserAdded(newUser: String) {
        val temp: MutableList<UserModel> = usersLiveData.value ?: mutableListOf()
        temp.add(UserModel(newUser, (0..1).random()))
        usersLiveData.postValue(temp)
    }

    fun onGenerateTeamsButtonClicked() {
        eventLiveData.postValue(usersLiveData.value ?: mutableListOf())
    }
}