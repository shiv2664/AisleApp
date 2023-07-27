package com.example.aisleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.aisleapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =DataBindingUtil.setContentView(this,R.layout.activity_main)

        val bundle=Bundle()
        addFragment(PhoneNumberFragment(),bundle)






    }

    public fun addFragment(fragment: Fragment,bundle: Bundle) {
        fragment.arguments = bundle;
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment, "yourFragmentTag")
            .commit()

    }
}