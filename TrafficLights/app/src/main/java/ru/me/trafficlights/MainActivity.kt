package ru.me.trafficlights

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Метод, вызываемый при создании активности.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Вызов метода суперкласса onCreate для инициализации активности.

        // Включение поддержки режима Edge-to-Edge (навигация, статус-бар) для полноэкранного отображения.
        enableEdgeToEdge()

        // Задаем макет активности из файла `activity_main`.
        setContentView(R.layout.activity_main)

        // Привязка главного контейнера (ConstraintLayout) через его ID.
        val rootLayout: ConstraintLayout = findViewById(R.id.root_layout)

        // Привязка TextView, который будет отображать текущий выбранный цвет.
        val textView: TextView = findViewById(R.id.textView)

        // Привязка кнопки "Красный" через ID из XML-файла.
        val redButton: Button = findViewById(R.id.button_red)

        // Привязка кнопки "Желтый".
        val yellowButton: Button = findViewById(R.id.button_yellow)

        // Привязка кнопки "Зеленый".
        val greenButton: Button = findViewById(R.id.button_green)

        // Слушатель нажатия для кнопки "Красный".
        redButton.setOnClickListener {
            // Изменяем текст отображаемого элемента TextView на "Красный".
            textView.text = "Красный"

            // Изменяем цвет фона контейнера на красный, используя метод `resources.getColor`.
            rootLayout.setBackgroundColor(resources.getColor(R.color.redColor, null))
        }

        // Слушатель нажатия для кнопки "Желтый".
        yellowButton.setOnClickListener {
            // Устанавливаем текст через ссылку на строковой ресурс `R.string.yellow`.
            textView.setText(R.string.yellow)

            // Меняем цвет фона на желтый с использованием `ContextCompat.getColor` для лучшей кросс-версийной совместимости.
            rootLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.yellowColor))
        }

        // Слушатель нажатия для кнопки "Зеленый".
        greenButton.setOnClickListener {
            // Устанавливаем текст, используя метод `resources.getText` для получения строки из ресурсов.
            textView.text = resources.getText(R.string.green)

            // Меняем цвет фона на зеленый через объект `Color` (предустановленный SDK цвет).
            rootLayout.setBackgroundColor(Color.GREEN)
        }

        // Обработчик системных вставок (например, status bar и navigation bar) для корректного отображения интерфейса.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root_layout)) { v, insets ->
            // Получаем размеры вставок системных панелей (отступов).
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            // Устанавливаем отступы (padding) для контейнера на основе размеров systemBars.
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)

            // Возвращаем неизмененные вставки для дальнейшей обработки.
            insets
        }
    }
}