package com.example.capstone.di

import android.content.Context
import com.example.capstone.data.local.pref.UserPreference
import com.example.capstone.data.local.pref.dataStore
import com.example.capstone.data.remote.retrofit.ApiConfig
import com.example.capstone.data.repository.FridgeRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): FridgeRepository{
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user)
        return FridgeRepository.getInstance(apiService, pref)
    }
}