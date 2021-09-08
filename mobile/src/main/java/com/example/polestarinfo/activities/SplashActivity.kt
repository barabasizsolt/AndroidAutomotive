package com.example.polestarinfo.activities

import android.annotation.SuppressLint
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

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

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
        var allGranted = true
        for(element in Constant.permissions){
            if(checkSelfPermission(element) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(Constant.permissions, 0)
                allGranted = false
                break
            }
        }
        if(allGranted) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var allGranted = true
        for(element in grantResults){
            if(element != PackageManager.PERMISSION_GRANTED){
                requestPermissions(Constant.permissions, 0)
                allGranted = false
                break
            }
        }
        if(allGranted){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onAnimationStart(p0: Animation?) {}
    override fun onAnimationRepeat(p0: Animation?) {}
}