package Message
import retrofit2.Call
import retrofit2.http.*

interface TwisterMessageService {
    @GET("messages")
    fun getAllMessage(): Call<List<Message>>

    @POST("messages")
    fun saveMessage(@Body message: Message): Call<Message>

    @DELETE("messages/{id}")
    fun deleteMessage(@Path("id") id: Int): Call<Message>

}