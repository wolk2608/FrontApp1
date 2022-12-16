package com.example.frontapp1.model

import java.util.*
import kotlin.collections.ArrayList

typealias StudentsListener = (students: List<Student>) -> Unit

class StudentsService {

    private var students = mutableListOf<Student>()
    private var currentId = 0

    private val listeners :MutableSet<StudentsListener> = mutableSetOf<StudentsListener>()

    init {
        students.add(0,Student(0, "Evgeniy", "Cherkasov", "4936", "https://sun9-north.userapi.com/sun9-83/s/v1/ig2/Sw3KXe3CHAdEh9x5IjNU5GPuqTrrF_-3kNl_d450cwefgZBSnsh3grxBAYZiftHtyQTSIroyNnh_RSpVtpy_mAit.jpg?size=900x920&quality=96&type=album"))
        students.add(1,Student(1, "Alexander", "Krutov", "4936"))
        students.add(2,Student(2, "Rakhim", "Zinatov", "4936"))
        students.add(3,Student(3, "Michael", "Nesterenko", "4936"))
        students.add(4,Student(4, "Ivan", "Potapov", "1234"))
        currentId = 5
    }

    fun getStudents(): List<Student> {
        return students
    }

    fun addStudent(firstName: String, secondName: String, groupNum: String) {
        students.add(Student(currentId, firstName, secondName, groupNum))
        currentId++
    }

    fun addStudent(firstName: String, secondName: String, groupNum: String, photo: String) {
        students.add(Student(currentId, firstName, secondName, groupNum, photo))
        currentId++
    }

    fun deleteStudent(student: Student) {
        val indexToDelete :Int = students.indexOfFirst { it.id == student.id }
        if (indexToDelete != -1) {
            students = ArrayList(students)
            students.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    fun moveStudent(student: Student, moveBy: Int) {
        val oldIndex :Int = students.indexOfFirst { it.id == student.id }
        if (oldIndex == -1) return
        val newIndex :Int = oldIndex + moveBy
        if (newIndex < 0 || newIndex >= students.size) return
        students = ArrayList(students)
        Collections.swap(students, oldIndex, newIndex)
        notifyChanges()
    }

    fun addListener(listener: StudentsListener) {
        listeners.add(listener)
        listener.invoke(students)
    }

    fun removeListener(listener: StudentsListener) {
        listeners.remove(listener)
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(students) }
    }

}