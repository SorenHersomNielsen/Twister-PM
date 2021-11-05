package Comment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oblopgave.R

class CommentAdapter<T>(private val items: List<T>, private val onItemClicked: (position: Int) -> Unit) :
    RecyclerView.Adapter<CommentAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
        return  items.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CommentAdapter.MyViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item, viewGroup, false)
        return CommentAdapter.MyViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(viewHolder: CommentAdapter.MyViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView.text = items[position].toString()
    }

    class MyViewHolder(itemView: View, private val onItemClicked: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val textView: TextView = itemView.findViewById(R.id.textview_list_item)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = bindingAdapterPosition
            onItemClicked(position)
        }
    }


}