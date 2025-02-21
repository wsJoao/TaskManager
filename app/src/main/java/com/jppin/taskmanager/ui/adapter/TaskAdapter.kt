package com.jppin.taskmanager.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jppin.taskmanager.R
import com.jppin.taskmanager.data.entities.Task
import com.jppin.taskmanager.databinding.ItemTaskLayoutBinding

class TaskAdapter(
    private var dataSet: List<Task>,
    private var isEditMode: Boolean,
    private val onDeleteClickListener: (Task) -> Unit,
    private val onEditClickListener: (Task) -> Unit) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemTaskLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.txtTask.text = task.title
            binding.txtDescription.text = task.description
            binding.imgPriority.setImageResource(R.drawable.ic_priority)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTaskLayoutBinding
            .inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
        holder.binding.imgPriority.setImageResource(getPriorityImage(position))

        val task = dataSet[position]
        val visibility = if (isEditMode) View.VISIBLE else View.GONE
        holder.binding.ibtnEdit.visibility = visibility
        holder.binding.ibtnDelete.visibility = visibility

        holder.binding.ibtnDelete.setOnClickListener {
            onDeleteClickListener(task)
        }
        holder.binding.ibtnEdit.setOnClickListener {
            onEditClickListener(task)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    private fun getPriorityImage(position: Int): Int {
        return when (dataSet[position].priority) {
            1 -> R.drawable.ic_priority_low
            2 -> R.drawable.ic_priority_medium
            else -> R.drawable.ic_priority_high
        }
    }

    fun updateEditMode(newState: Boolean) {
        isEditMode = newState
        notifyDataSetChanged()
    }

    fun submitList(newList: List<Task>) {
        dataSet = newList
        notifyDataSetChanged()
    }
}