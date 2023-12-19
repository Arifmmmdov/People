package com.example.people

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.widget.Toast
import androidx.core.content.ContextCompat
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application()