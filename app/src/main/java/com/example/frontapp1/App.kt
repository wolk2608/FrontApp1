package com.example.frontapp1

import android.app.Application
import com.example.frontapp1.model.StudentsService

class App : Application() {

    val studentsService = StudentsService()
}