package com.example.frontapp1

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.frontapp1.databinding.StudentItemViewBinding
import com.example.frontapp1.model.Student

interface StudentActionListener {

    fun onStudentMove(student: Student, moveBy: Int)

    fun onStudentDelete(student: Student)

    fun onStudentDetails(student:Student)

}

class StudentsDiffCallback(
    private val oldList: List<Student>,
    private val newList: List<Student>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldValue = oldList[oldItemPosition]
        val newValue = newList[newItemPosition]
        return oldValue.id == newValue.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldValue = oldList[oldItemPosition]
        val newValue = newList[newItemPosition]
        return oldValue == newValue
    }
}

class StudentsAdapter(
    private  val actionListener: StudentActionListener
) : RecyclerView.Adapter<StudentsAdapter.StudentsViewHolder>(), View.OnClickListener {

    var students: List<Student> = emptyList()
    set(newValue) {
        val diffCallback = StudentsDiffCallback(field, newValue)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        field = newValue
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onClick(v: View) {
        val student :Student = v.tag as Student
        when (v.id) {
            R.id.imageViewListMore -> {
                showPopupMenu(v)
            }
            else -> {
                actionListener.onStudentDetails(student)
            }
        }


    }

    override fun getItemCount(): Int = students.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentsViewHolder {
        val inflater :LayoutInflater = LayoutInflater.from(parent.context)
        val binding :StudentItemViewBinding = StudentItemViewBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.imageViewListMore.setOnClickListener(this)

        return StudentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentsViewHolder, position: Int) {
        val student :Student = students[position]
        val name = student.firstName + " " + student.secondName
        val groupNum = "Group: " + student.groupNum
        with(holder.binding) {
            holder.itemView.tag = student
            imageViewListMore.tag = student

            textViewListName.text = name
            textViewListGroupNum.text = groupNum
            if (student.photo.isNotBlank()) {
                imageViewListAvatar.load(student.photo) {
                    transformations(CircleCropTransformation())
                }
            } else {
                imageViewListAvatar.load(R.drawable.ic_kit) {
                    transformations(CircleCropTransformation())
                }
            }

        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        val student :Student = view.tag as Student
        val position :Int = students.indexOfFirst { it.id == student.id }

        popupMenu.menu.add(0, ID_MODE_UP, Menu.NONE, "Move Up").apply {
            isEnabled = position > 0
        }
        popupMenu.menu.add(0, ID_MODE_DOWN, Menu.NONE, "Move Down").apply {
            isEnabled = position < students.size - 1
        }
        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, "Remove")

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_MODE_UP -> {
                    actionListener.onStudentMove(student, -1)
                }
                ID_MODE_DOWN -> {
                    actionListener.onStudentMove(student, 1)
                }
                ID_REMOVE -> {
                    actionListener.onStudentDelete(student)
                }
            }
            return@setOnMenuItemClickListener true
        }

        popupMenu.show()
    }

    class StudentsViewHolder (
        val binding: StudentItemViewBinding
    ) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private const val ID_MODE_UP = 1
        private const val ID_MODE_DOWN = 2
        private const val ID_REMOVE = 3
    }
}