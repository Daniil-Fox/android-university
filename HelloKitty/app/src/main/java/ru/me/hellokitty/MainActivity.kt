package ru.me.hellokitty

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Ленивая инициализация (lateinit) переменной для TextView
    // Позволяет избежать null-значений, пока объект не будет инициализирован.
    private lateinit var helloTextView: TextView

    // Ленивая инициализация переменной для EditText — текстового поля.
    private lateinit var editText: EditText

    // Метод, вызываемый при создании активности.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Вызов базового метода onCreate (обязательная строка)

        // Включение режима Edge-to-Edge для полноэкранного интерфейса (например, скрыть статус-бар).
        enableEdgeToEdge()

        // Связывание макета XML-файла с активностью.
        setContentView(R.layout.activity_main)

        // Привязка TextView из макета (activity_main.xml) по его ID.
        helloTextView = findViewById(R.id.textView)

        // Привязка EditText из макета (activity_main.xml) по его ID.
        editText = findViewById(R.id.editTextText)

        // Привязка кнопки (ImageButton) из макета (activity_main.xml) по ее ID.
        var imageButton: ImageButton = findViewById(R.id.imageButton)

        // Установка слушателя нажатий для кнопки.
        imageButton.setOnClickListener {
            // Проверка: если поле ввода (EditText) пустое, изменяем текст на "Hello Kitty".
            if (editText.text.isEmpty()) {
                helloTextView.text = "Hello Kitty"
            } else {
                // Иначе, устанавливаем текст, комбинируя текст из поля ввода.
                helloTextView.text = "Hello, " + editText.text
            }
        }

        // Установка обработчика системных вставок (например, обработки отступов для статуса и навигационной панели).
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            // Извлечение системных вставок (краев экрана, занятых системой).
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            // Установка отступов (padding) для указанного View (например, основного лайаута активности).
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)

            // Возвращение insets без изменений.
            insets
        }
    }
}