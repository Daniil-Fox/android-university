package ru.me.a25settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Переменная для счётчика воронов
    private var counter: Int = 0

    // Переменная для работы с SharedPreferences
    private lateinit var prefs: SharedPreferences

    // Ключ для сохранения/получения данных из SharedPreferences
    private val APP_PREFERENCES_COUNTER = "counter"

    // Переменная для текстового поля для отображения результата
    private lateinit var textView: TextView

    // Метод, вызываемый при создании экрана
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Включаем поддержку edge-to-edge UI
        enableEdgeToEdge()

        // Устанавливаем разметку активности
        setContentView(R.layout.activity_main)

        // Инициализируем SharedPreferences для сохранения данных
        prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)

        // Инициализируем текстовое поле
        textView = findViewById(R.id.textView)

        // Инициализация кнопок
        val button: Button = findViewById(R.id.button) // Кнопка для увеличения счётчика
        val button_null: Button = findViewById(R.id.button_null) // Кнопка для сброса счётчика

        // Обработка нажатия на кнопку увеличения счётчика
        button.setOnClickListener {
            textView.text = "Я насчитал ${++counter} ворон" // Увеличиваем счётчик и выводим его значение
        }

        // Обработка нажатия на кнопку сброса счётчика
        button_null.setOnClickListener {
            counter = 0 // Сбрасываем счётчик
            textView.text = "Я насчитал ${counter} ворон" // Отображаем сброшенное значение
        }

        // Установка обработчика для применения оконных отступов (system insets)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Устанавливаем отступы на основе отступов системных баров
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Метод вызывается при паузе активности (например, при сворачивании приложения)
    override fun onPause() {
        super.onPause()
        // Сохраняем текущее значение счётчика в SharedPreferences
        val editor = prefs.edit()
        editor.putInt(APP_PREFERENCES_COUNTER, counter).apply()
    }

    // Метод вызывается при возобновлении активности (например, при возвращении из свернутого состояния)
    override fun onResume() {
        super.onResume()
        // Проверяем, есть ли сохранённые данные в SharedPreferences
        if (prefs.contains(APP_PREFERENCES_COUNTER)) {
            // Если сохранённые данные существуют, загружаем значение счётчика
            counter = prefs.getInt(APP_PREFERENCES_COUNTER, 0)
            // Отображаем загруженное значение счётчика на экране
            textView.text = "Я насчитал $counter ворон"
        }
    }
}
