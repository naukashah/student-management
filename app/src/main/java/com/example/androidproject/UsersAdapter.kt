package com.example.androidproject

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import kotlinx.android.synthetic.main.user_row.view.*

class UsersAdapter(
    private val users: List<User>,
    private val listener: OnItemClickListener
): RecyclerView.Adapter<UsersAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        val firstName: TextView = itemView.firstName
        val studentId: TextView = itemView.student_id
        val university: TextView = itemView.university
        val term: TextView = itemView.term

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                 listener.onItemClick(position, studentId.text.toString(), firstName.text.toString(), university.text.toString(), term.text.toString())

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view= LayoutInflater.from(parent.context).inflate(R.layout.user_row,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.studentId.text  = user.id
        holder.firstName.text = user.name
        holder.university.text = user.university
        holder.term.text = user.term
    }


    interface OnItemClickListener{
        fun onItemClick(position: Int, userId: String, userName: String, university: String, term: String)
    }
}

/*class DefaultItemDecorator(private val horizontalSpacing: Int, private val verticalSpacing: Int) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.right = horizontalSpacing
        outRect.left = horizontalSpacing
                if (parent.getChildLayoutPosition(view) == 0){
                    outRect.top = verticalSpacing
    }
    outRect.bottom = verticalSpacing
}
}*/
