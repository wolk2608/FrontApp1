package com.example.frontapp1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.frontapp1.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadFragment(ImageFragment())

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.imageFragment -> {
                    loadFragment(ImageFragment())
                    true
                }
                R.id.customViewFragment -> {
                    loadFragment(CustomViewFragment())
                    true
                }
                R.id.recyclerViewFragment -> {
                    loadFragment(RecyclerViewFragment())
                    true
                }
                else -> { false }
            }
        }
    }
    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }
}