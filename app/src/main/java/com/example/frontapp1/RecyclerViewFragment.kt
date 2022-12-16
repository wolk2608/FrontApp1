package com.example.frontapp1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frontapp1.databinding.FragmentRecyclerViewBinding
import com.example.frontapp1.model.Student
import com.example.frontapp1.model.StudentsListener
import com.example.frontapp1.model.StudentsService

/**
 * A simple [Fragment] subclass.
 * Use the [RecyclerViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecyclerViewFragment : Fragment() {
    private var _binding: FragmentRecyclerViewBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: StudentsAdapter

    private val studentsService: StudentsService
        get() = (requireContext().applicationContext as App).studentsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecyclerViewBinding.inflate(inflater, container, false)

        adapter = StudentsAdapter(object : StudentActionListener {
            override fun onStudentMove(student: Student, moveBy: Int) {
                studentsService.moveStudent(student, moveBy)
            }

            override fun onStudentDelete(student: Student) {
                studentsService.deleteStudent(student)
            }

            override fun onStudentDetails(student: Student) {
                Toast.makeText(requireContext(), "Student: ${student.firstName} ${student.secondName}", Toast.LENGTH_SHORT).show()
            }
        })
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        studentsService.addListener(studentsListener)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        studentsService.removeListener(studentsListener)
    }

    private val studentsListener: StudentsListener = { adapter.students = it}
}