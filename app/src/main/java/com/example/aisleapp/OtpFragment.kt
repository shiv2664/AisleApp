package com.example.aisleapp

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.aisleapp.api.ApiClient
import com.example.aisleapp.api.ApiInterface
import com.example.aisleapp.databinding.FragmentOtpBinding
import com.example.aisleapp.model.OtpModel
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OtpFragment : Fragment() {

    private lateinit var binding: FragmentOtpBinding
    private var countDownTimer: CountDownTimer? = null
    var phoneNumber:String=""
    private lateinit var apiInterface: ApiInterface


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentOtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apiInterface = ApiClient.apiClient?.create(ApiInterface::class.java)!!

        phoneNumber= arguments?.getString("phoneNumber").toString()
        binding.phoneNumber.text = phoneNumber

        val timerDurationInMillis = (1 * 60 * 1000).toLong()

        countDownTimer = object : CountDownTimer(timerDurationInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateTimerText(millisUntilFinished)
            }

            override fun onFinish() {
                binding.timerTextView.text = "Resend?"
            }
        }.start()

        binding.continueButton.setOnClickListener {

            try {
                val imm =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken, 0)
            } catch (e: Exception) {
                // TODO: handle exception
            }

            loadJSON1()
        }

    }

    private fun updateTimerText(millisUntilFinished: Long) {
        val minutes = (millisUntilFinished / 1000).toInt() / 60
        val seconds = (millisUntilFinished / 1000).toInt() % 60
        val timerText = String.format("%02d:%02d", minutes, seconds)
        binding.timerTextView.text = timerText
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cancel the timer when the activity is destroyed to avoid potential memory leaks
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
        }
    }


    private fun loadJSON1() {

        val otpModel= OtpModel(phoneNumber, binding.otpNumber.text.toString().trim())

        val call: Call<JsonElement?>? =  apiInterface.postOtp(otpModel)
        call!!.enqueue(object : Callback<JsonElement?> {
            override fun onResponse(call: Call<JsonElement?>, response: Response<JsonElement?>) {
                if (response.isSuccessful && response.body() != null) {
                    Log.d("MyTag", "Successful1" + response.body()?.toString())

                    val token= response.body()!!.asJsonObject["token"].asString

                    val bundle = Bundle()
                    bundle.putString("token", token)

                    if (activity != null && activity is MainActivity) {
                        val parentActivity: MainActivity? = activity as MainActivity?
                        parentActivity?.addFragment(FeedFragment(),bundle)
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