package com.example.mobcomfinals.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.mobcomfinals.R
import com.example.mobcomfinals.databinding.ActivityDashboardBinding
import com.example.mobcomfinals.databinding.ActivityHomePageBinding

class HomePageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomePageBinding
    private lateinit var authenticationViewModel: AuthenticationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityHomePageBinding.inflate(layoutInflater)
        authenticationViewModel = AuthenticationViewModel()
        authenticationViewModel.getStates().observe(this@HomePageActivity) {
            handleState(it)
        }
        authenticationViewModel.getUserProfile()

        setContentView(binding.root)
        with(binding){
            btnProfile.setOnClickListener {
            ProfileActivity.launch(this@HomePageActivity)
        }
            btnEnlistProperty.setOnClickListener {
                AddPropertyActivity.launch(this@HomePageActivity)
            }
            btnLookForRealEstate.setOnClickListener {
                CategoryChoiceActivity.launch(this@HomePageActivity)
            }

        }

    }

    private fun handleState(state : AuthenticationStates) {
        when(state) {
            is AuthenticationStates.Default -> {
                Glide.with(this)
                    .load(state.user?.profilePicture)
                    .centerCrop()
                    .into(binding.ivUserProfile)
                binding.tvWelcomeUser.text = "${state.user?.username}!"
            }
            AuthenticationStates.Error -> TODO()
            AuthenticationStates.LogOut -> {
                LoginActivity.launch(this@HomePageActivity)
                finish()
            }
            AuthenticationStates.UserDeleted -> {
                LoginActivity.launch(this@HomePageActivity)
                finish()
            }
            AuthenticationStates.VerificationEmailSent -> TODO()
            else -> {}
        }
    }

    companion object {
        fun launch(activity : Activity) = activity.startActivity(Intent(activity, HomePageActivity::class.java))
    }
}