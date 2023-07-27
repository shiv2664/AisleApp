package com.example.aisleapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.aisleapp.api.ApiClient
import com.example.aisleapp.api.ApiInterface
import com.example.aisleapp.databinding.FragmentFeedBinding
import com.example.aisleapp.databinding.FragmentPhoneNumberBinding
import com.example.aisleapp.model.ModelDetails
import com.example.aisleapp.model.OtpModel
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding
    private lateinit var apiInterface: ApiInterface
    private lateinit var data:ModelDetails

    private var token=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentFeedBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        token= arguments?.getString("token").toString()
        Log.d("MyTag", "token is $token")

        apiInterface = ApiClient.apiClient?.create(ApiInterface::class.java)!!

        loadJSON1()


    }


    private fun loadJSON1() {

        val call: Call<ModelDetails?>? = apiInterface.getNotes(token)
        call!!.enqueue(object : Callback<ModelDetails?> {
            override fun onResponse(call: Call<ModelDetails?>, response: Response<ModelDetails?>) {
                if (response.isSuccessful && response.body() != null) {
                    Log.d("MyTag", "Successful1" + response.body()?.toString())

                    val data=response.body()

                    val imageUrl = data?.invites?.profiles?.get(0)?.photos?.get(0)?.photo

                    if (imageUrl!=null){

                        Glide.with(requireActivity())
                            .load(imageUrl)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .centerCrop()
                            .into(binding.img)
                    }

                    val title2=data?.likes?.profiles?.get(0)?.first_name
                    binding.title2.text = title2

                    val title3=data?.likes?.profiles?.get(1)?.first_name
                    binding.title3.text = title3


                    val imageUrl2 = data?.likes?.profiles?.get(0)?.avatar

                    if (imageUrl2!=null){

                        Glide.with(requireActivity())
                            .load(imageUrl2)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .centerCrop()
                            .into(binding.img2)
                    }

                    val imageUrl3 =  data?.likes?.profiles?.get(1)?.avatar

                    if (imageUrl3!=null){

                        Glide.with(requireActivity())
                            .load(imageUrl3)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .centerCrop()
                            .into(binding.img3)
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

            override fun onFailure(call: Call<ModelDetails?>, t: Throwable) {

            }
        })
    }


}