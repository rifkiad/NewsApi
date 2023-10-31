package com.example.newsapi

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapi.ui.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    companion object {
        const val ANIMATION_TIME: Long = 6000 // Waktu animasi
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        initSplashScreen()
    }

    private fun initSplashScreen() {
        Handler(mainLooper).postDelayed({
            startMainActivity()
        }, ANIMATION_TIME)
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish() // Hapus activity ini dari tumpukan untuk mencegahnya muncul saat menutup MainActivity.
    }
}
