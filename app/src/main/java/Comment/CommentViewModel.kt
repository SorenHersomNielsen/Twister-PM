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

    fun add(comment: Comment)  {
        Log.d("apple", messageId.toString() + " " + comment )
            repository.saveComment(messageId, comment)
    }

    fun delete(messageId: Int, commentId: Int){
        repository.deleteComment(messageId,commentId)
    }
}