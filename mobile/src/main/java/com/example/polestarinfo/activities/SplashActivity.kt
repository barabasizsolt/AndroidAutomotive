package com.example.polestarinfo.activities

import android.annotation.SuppressLint
import android.car.Car
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.polestarinfo.R
import com.example.polestarinfo.cache.Cache
import com.example.polestarinfo.constants.Constant
import com.example.polestarinfo.databases.ScoreViewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity(), Animation.AnimationListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        supportActionBar?.setDisplayShowTitleEnabled(false)

        val scoreViewModel = ViewModelProvider(this).get(ScoreViewModel::class.java)
        scoreViewModel.readAllScore.observe(this, {scores ->
            Cache.setCache(scores)
        })

        val topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_anim)
        val logo = findViewById<ImageView>(R.id.logo)
        logo.startAnimation(topAnimation)

        val bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_anim)
        val icon = findViewById<ImageView>(R.id.icon)
        icon.startAnimation(bottomAnimation)

        topAnimation?.setAnimationListener(this)
    }

    override fun onAnimationEnd(p0: Animation?) {
        if(checkSelfPermission(Constant.permissions[0]) == PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(Constant.permissions[1]) == PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(Constant.permissions[2]) == PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(Constant.permissions[3]) == PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(Constant.permissions[4]) == PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(Constant.permissions[5]) == PackageManager.PERMISSION_GRANTED
        ) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            requestPermissions(Constant.permissions, 0)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (permissions[0] == Car.PERMISSION_SPEED && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
            permissions[1] == Car.PERMISSION_ENERGY && grantResults[1] == PackageManager.PERMISSION_GRANTED &&
            permissions[2] == Car.PERMISSION_POWERTRAIN && grantResults[2] == PackageManager.PERMISSION_GRANTED &&
            permissions[3] == Car.PERMISSION_CAR_INFO && grantResults[3] == PackageManager.PERMISSION_GRANTED &&
            permissions[4] == Car.PERMISSION_EXTERIOR_ENVIRONMENT && grantResults[4] == PackageManager.PERMISSION_GRANTED &&
            permissions[5] == Car.PERMISSION_ENERGY_PORTS && grantResults[5] == PackageManager.PERMISSION_GRANTED

        ) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onAnimationStart(p0: Animation?) {}
    override fun onAnimationRepeat(p0: Animation?) {}
}