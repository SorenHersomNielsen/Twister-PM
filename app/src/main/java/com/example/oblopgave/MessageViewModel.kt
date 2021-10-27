package com.example.oblopgave

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MessageViewModel : ViewModel() {
    private val repository = TwisterMessageRespository()
    val MessageLiveData: LiveData<List<Message>> = repository.booksLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    val updateMessageLiveData: LiveData<String> = repository.updateMessageLiveData

    init {
        reload()
    }

    fun reload() {
        repository.GetAllMessage()
    }

    operator fun get(index: Int): Message? {
        return MessageLiveData.value?.get(index)
    }

    fun add(message: Message) {
        repository.add(message)
    }

    fun delete(id: Int) {
        repository.delete(id)
    }
}