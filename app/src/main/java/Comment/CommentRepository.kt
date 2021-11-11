package Comment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.oblopgave.SecondFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class CommentRepository {
    private val url = "https://anbo-restmessages.azurewebsites.net/api/Messages/"
    private val commentservice: CommentService


    val CommentLiveData: MutableLiveData<List<Comment>> = MutableLiveData<List<Comment>>()
    val errorCommentLiveData: MutableLiveData<String> = MutableLiveData()
    val PostDeleteCommentLiveData: MutableLiveData<String> = MutableLiveData()
    val secondBinding: SecondFragment = SecondFragment()




    init {
        Log.d("apple", "comment repo init")
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create()).build()
        commentservice = build.create(CommentService::class.java)
            getAllComment(secondBinding.messageId)
    }

    fun getAllComment(messageId: Int){

        commentservice.getAllComment(messageId).enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if (response.isSuccessful) {
                    CommentLiveData.postValue(response.body())
                    errorCommentLiveData.postValue("")

                } else {
                    val comment = response.code().toString() + " " + response.message()
                    errorCommentLiveData.postValue(comment)
                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                errorCommentLiveData.postValue(t.message)
            }
        })
    }

    fun saveComment(messageId: Int, comment: Comment){
        commentservice.saveComment(messageId, comment).enqueue(object : Callback<Comment> {
            override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                if (response.isSuccessful) {
                    PostDeleteCommentLiveData.postValue("Added: " + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorCommentLiveData.postValue(message)
                }
            }

            override fun onFailure(call: Call<Comment>, t: Throwable) {
                errorCommentLiveData.postValue(t.message)
            }
        })
    }

    fun deleteComment(messageId: Int, commentId: Int) {
        commentservice.deleteComment(messageId, commentId).enqueue(object : Callback<Comment> {
            override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                if (response.isSuccessful) {
                    Log.d("APPLE", "Updated: " + response.body())
                    PostDeleteCommentLiveData.postValue("Deleted: " + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorCommentLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<Comment>, t: Throwable) {
                errorCommentLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }
}