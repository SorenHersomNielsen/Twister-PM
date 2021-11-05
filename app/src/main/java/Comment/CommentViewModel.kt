package Comment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class CommentViewModel : ViewModel() {
    private val repository = CommentRepository()
    val commentLiveData: LiveData<List<Comment>> = repository.CommentLiveData
    val errorCommentLiveData: LiveData<String> = repository.errorCommentLiveData
    var messageId: Int = 0


    init {
        Log.d("apple", "comment init" + messageId)
        reload(messageId)
    }

    fun reload(messageId: Int) {
        Log.d("apple", "comment reload: " + messageId.toString().toInt())
        repository.getAllComment(messageId)
    }

    operator fun get(index: Int): Comment? {
        Log.d("apple", "comment get")
        return commentLiveData.value?.get(index)
    }
}