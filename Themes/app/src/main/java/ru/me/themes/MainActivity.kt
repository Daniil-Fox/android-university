package ru.me.themes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Используем lateinit для отложенной инициализации кнопки (будет инициализирована в onCreate).
    private lateinit var button: Button

    // Метод, вызываемый при создании активности.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Вызов базового метода суперкласса.
        enableEdgeToEdge() // Активируем Edge-to-Edge режим для современного полноэкранного UI.
        setContentView(R.layout.activity_main) // Подключаем разметку `activity_main`.

        // Привязываем кнопку с ID `button` из разметки.
        button = findViewById(R.id.button)

        // Устанавливаем слушатель нажатий на кнопку.
        button.setOnClickListener {
            // Создаем Intent для перехода из MainActivity в SecondActivity.
            val intent = Intent(this@MainActivity, SecondActivity::class.java)

            // Запускаем SecondActivity через созданный Intent.
            startActivity(intent)
        }

        // Настраиваем правильные отступы для корневого View (на случай вырезов на экране, навигации жестами).
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            // Получаем отступы для status bar и navigation bar.
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            // Устанавливаем padding для корневого View с учетом системных панелей.
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)

            // Возвращаем неизмененные вставки для дальнейшей обработки.
            insets
        }
    }
}