package Comment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.oblopgave.SecondFragment
import com.example.oblopgave.databinding.FragmentSecondBinding
import com.example.oblopgave.databinding.FragmentThirdBinding
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
        Log.d("apple", secondBinding.messageId.toString())
            getAllComment(secondBinding.messageId)
    }

    fun getAllComment(messageId: Int){
        //Log.d("apple", "getallcomment: " + messageId.toString().toInt())
        commentservice.getAllComment(messageId).enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if (response.isSuccessful) {
                    //Log.d("apple", response.body().toString())
                    CommentLiveData.postValue(response.body())
                    errorCommentLiveData.postValue("")
                    //Log.d("apple", "get all comment success")

                } else {
                    //Log.d("apple","get all comment fail" )
                    val comment = response.code().toString() + " " + response.message()
                    errorCommentLiveData.postValue(comment)
                    //Log.d("apple", comment)
                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                errorCommentLiveData.postValue(t.message)
                //Log.d("apple", t.message!!)
            }
        })
    }

    fun saveComment(messageId: Int, comment: Comment){
        commentservice.saveComment(messageId, comment).enqueue(object : Callback<Comment> {
            override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                if (response.isSuccessful) {
                    //Log.d("apple", "Added: " + response.body())
                    PostDeleteCommentLiveData.postValue("Added: " + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorCommentLiveData.postValue(message)
                    //Log.d("apple", message)
                }
            }

            override fun onFailure(call: Call<Comment>, t: Throwable) {
                errorCommentLiveData.postValue(t.message)
                //Log.d("apple", t.message!!)
            }
        })
    }
}