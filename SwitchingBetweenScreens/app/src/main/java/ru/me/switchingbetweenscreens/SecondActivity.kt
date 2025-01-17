package ru.me.switchingbetweenscreens

import android.content.Intent
import android.os.Bundle
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {

    // Поздняя инициализация TextView для отображения информации
    private lateinit var textview_second_info: TextView

    // Объект-компаньон для хранения констант
    companion object {
        const val THIEF = "ru.me.switchingbetweenscreens.THIEF" // Константа для ключа результата: имя "грабителя"
    }

    // Метод, вызываемый при создании активности
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Вызов метода суперкласса для базовой инициализации
        enableEdgeToEdge() // Включение Edge-to-Edge режима
        setContentView(R.layout.activity_second) // Установка макета для активности

        // Привязка TextView из макета с идентификатором textViewSecond
        textview_second_info = findViewById(R.id.textViewSecond)
        // Привязка RadioGroup из макета
        val radioGroup: RadioGroup = findViewById(R.id.radioGroup)

        // Переменные для хранения информации от первой активности
        var user = "ЖЫвотное" // Значение по умолчанию для имени пользователя
        var gift = "дырку от бублика" // Значение по умолчанию для подарка

        // Получение данных из Intent. Если данные отсутствуют, используются значения по умолчанию.
        user = intent.extras?.getString("username") ?: "ЖЫвотное"
        gift = intent.extras?.getString("gift") ?: "дырку от бублика"

        // Установка текста в TextView с информацией о пользователе и подарке
        textview_second_info.text = "$user, вам передали $gift"

        // Установка слушателя для изменения состояния RadioGroup
        radioGroup.setOnCheckedChangeListener { _, optionId ->
            val answerIntent = Intent() // Создаем Intent для передачи результата
            // Обрабатываем выбор пользователя и записываем выбранное значение "грабителя"
            when (optionId) {
                R.id.radio_cat -> answerIntent.putExtra(THIEF, "Кот") // Если выбран "Кот"
                R.id.radio_dog -> answerIntent.putExtra(THIEF, "Собака") // Если выбрана "Собака"
                R.id.radio_crow -> answerIntent.putExtra(THIEF, "Ворона") // Если выбрана "Ворона"
            }
            setResult(RESULT_OK, answerIntent) // Устанавливаем результат как успешный и передаем данные
            finish() // Завершаем SecondActivity и возвращаемся в MainActivity
        }

        /*
        Обработка системных отступов для устройств с вырезами/жестами была закомментирована.
        Если она требуется, то нужно раскомментировать блок ниже:

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        */
    }
}