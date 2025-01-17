package ru.me.a18animation

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val sunImageView: ImageView = findViewById(R.id.sun)
        val clockImageView: ImageView = findViewById(R.id.clock)
        val hourImageView: ImageView = findViewById(R.id.hour_hand)

        val activ : ConstraintLayout = findViewById(R.id.main)

        activ.setOnClickListener {
            // Анимация для восхода солнца
            val sunRiseAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.sun_rise)
            // Подключаем анимацию к нужному View
            sunImageView.startAnimation(sunRiseAnimation)

            // анимация для вращения часов
            val clockTurnAnimation = AnimationUtils.loadAnimation(this, R.anim.clock_turn)
            clockImageView.startAnimation(clockTurnAnimation)

            // анимация для стрелки
            val hourTurnAnimation = AnimationUtils.loadAnimation(this, R.anim.hour_turn)
            // присоединяем анимацию
            hourImageView.startAnimation(hourTurnAnimation)
        }
    }
}