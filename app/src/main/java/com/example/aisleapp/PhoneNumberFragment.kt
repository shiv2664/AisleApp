package com.example.aisleapp

import android.R.attr.data
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.aisleapp.api.ApiClient
import com.example.aisleapp.api.ApiInterface
import com.example.aisleapp.databinding.FragmentPhoneNumberBinding
import com.example.aisleapp.model.OtpModel
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PhoneNumberFragment : Fragment() {

    private lateinit var binding: FragmentPhoneNumberBinding
    private lateinit var apiInterface: ApiInterface
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhoneNumberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apiInterface = ApiClient.apiClient?.create(ApiInterface::class.java)!!

        val number="9876543212"

        binding.countryCode.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
                if (binding.countryCode.text.toString().length == 3) //size as per your requirement
                {
                    binding.phoneNumber.requestFocus()
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
            }
        })

        binding.continueButton.setOnClickListener {

            loadJSON1()

        }

    }

//    apiInterface.postNumber("+91"+(binding.phoneNumber.text.toString().trim()))
    private fun loadJSON1() {

    val otpModel=OtpModel("+919876543212", otp = "1234")
    val token="32c7794d2e6a1f7316ef35aa1eb34541"

        val call: Call<JsonElement?>? =  apiInterface.postNumber("+91"+(binding.phoneNumber.text.toString().trim()))
        call!!.enqueue(object : Callback<JsonElement?> {
            override fun onResponse(call: Call<JsonElement?>, response: Response<JsonElement?>) {
                if (response.isSuccessful && response.body() != null) {
                    Log.d("MyTag", "Successful1" + response.body()?.toString())

                    val bundle = Bundle()
                    bundle.putString("phoneNumber", "+91"+(binding.phoneNumber.text.toString().trim()))

                    if (activity != null && activity is MainActivity) {
                        val parentActivity: MainActivity? = activity as MainActivity?
                        parentActivity?.addFragment(OtpFragment(),bundle)
                    }

                } else {
                    val errorCode: String = when (response.code()) {
                        404 -> "404 not found"
                        500 -> "500 server broken"
                        else -> "unknown error"
                    }
                    Log.d("MyTag", errorCode)
                }
            }

            override fun onFailure(call: Call<JsonElement?>, t: Throwable) {

            }
        })
    }

}