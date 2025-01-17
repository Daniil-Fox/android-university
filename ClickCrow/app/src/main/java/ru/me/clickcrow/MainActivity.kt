package ru.me.clickcrow

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Переменная для хранения количества "ворон"
    private var counter: Int = 0

    // Метод, вызываемый при создании активности
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Вызов метода суперкласса для базовой инициализации
        enableEdgeToEdge() // Включение режима Edge-to-Edge для корректного отображения интерфейса
        setContentView(R.layout.activity_main) // Установка макета активности

        // Привязка TextView из разметки, где будет отображаться результат
        val textView: TextView = findViewById(R.id.textView)

        // Привязка кнопки для отображения текста "Hello Kitty!"
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            // Устанавливаем текст "Hello Kitty!" в TextView при нажатии на кнопку
            textView.text = "Hello Kitty!"
        }

        // Привязка кнопки для увеличения счетчика ворон
        val button_counter: Button = findViewById(R.id.button_counter)
        button_counter.setOnClickListener {
            // Увеличиваем счетчик "ворон" на 1 и обновляем TextView с текущим значением
            textView.text = "Я насчитал ${++counter} ворон"
        }

        // Настройка системных отступов для интерфейса на устройствах с вырезами/панелями
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()) // Получение размеров системных панелей
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom) // Применение отступов для корневого вида
            insets // Возвращаем insets для дальнейшей обработки
        }
    }
}