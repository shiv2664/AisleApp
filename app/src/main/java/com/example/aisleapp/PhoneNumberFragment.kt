package com.example.aisleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.aisleapp.api.ApiClient
import com.example.aisleapp.api.ApiInterface
import com.example.aisleapp.databinding.FragmentPhoneNumberBinding


class PhoneNumberFragment : Fragment() {

    private lateinit var binding:FragmentPhoneNumberBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentPhoneNumberBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiInterface: ApiInterface? = ApiClient.apiClient?.create(ApiInterface::class.java)

        binding.continueButton.setOnClickListener {

            if (activity != null && activity is MainActivity) {
                val parentActivity: MainActivity? = activity as MainActivity?
                parentActivity?.addFragment(OtpFragment())
            }

        }


    }
}