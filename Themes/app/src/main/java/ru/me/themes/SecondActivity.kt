package ru.me.themes

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// Класс SecondActivity - вторая активность приложения.
class SecondActivity : AppCompatActivity() {
    // Метод, вызываемый при создании активности.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Вызов метода суперкласса для инициализации.

        enableEdgeToEdge() // Подключение режима Edge-to-Edge (если ранее определено в проекте).

        setContentView(R.layout.activity_second) // Задаём макет для SecondActivity.
    }
}