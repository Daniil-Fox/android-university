package ru.me.switchingbetweenscreens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

// Класс AboutActivity отображает экран "О приложении" или "About".
class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Вызов базового метода суперкласса.

        // Устанавливаем макет активности.
        setContentView(R.layout.activity_about)
    }
}