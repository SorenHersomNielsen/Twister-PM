package Comment
import retrofit2.Call
import retrofit2.http.*

interface CommentService {

    @GET("{messageId}/comments")
    fun getAllComment(@Path("messageId") messageId: Int) : Call<List<Comment>>

    @POST("{messageid}/comments")
    fun saveComment(@Path("messageid") messageid: Int, @Body comment: Comment): Call<Comment>

    @DELETE("{messageId}/comments/{commentId}")
    fun deleteComment(@Path("messageId") meessageId: Int, @Path("commentId") commentId: Int)
}