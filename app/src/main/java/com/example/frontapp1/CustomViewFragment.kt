package com.example.frontapp1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.frontapp1.databinding.FragmentCustomViewBinding
import com.example.frontapp1.databinding.FragmentImageBinding
import com.example.frontapp1.model.StudentsService

/**
 * A simple [Fragment] subclass.
 * Use the [CustomViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CustomViewFragment : Fragment() {
    private var _binding: FragmentCustomViewBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

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
        _binding = FragmentCustomViewBinding.inflate(inflater, container, false)

        binding.studentButtons.buttonWrite.setOnClickListener() {
            val firstName = binding.setStudent.editTextFirstName.text.toString()
            val secondName = binding.setStudent.editTextSecondName.text.toString()
            val groupNum = binding.setStudent.editTextGroupNum.text.toString()

            if (firstName.isNotBlank() && secondName.isNotBlank() && groupNum.isNotBlank()) {
                studentsService.addStudent(firstName, secondName, groupNum)

                binding.getStudent.textViewFirstName.text = firstName
                binding.getStudent.textViewSecondName.text = secondName
                binding.getStudent.textViewGroupNum.text = groupNum
                binding.setStudent.editTextFirstName.text = null
                binding.setStudent.editTextSecondName.text = null
                binding.setStudent.editTextGroupNum.text = null
            }
            else {
                Toast.makeText(requireContext(), "The fields cannot be empty!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.studentButtons.buttonClear.setOnClickListener() {
            binding.getStudent.textViewFirstName.text = null
            binding.getStudent.textViewSecondName.text = null
            binding.getStudent.textViewGroupNum.text = null
            binding.setStudent.editTextFirstName.text = null
            binding.setStudent.editTextSecondName.text = null
            binding.setStudent.editTextGroupNum.text = null
        }

        return binding.root
    }
}