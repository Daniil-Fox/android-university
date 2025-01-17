package ru.me.toast

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Метод, вызываемый при создании активности.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Вызов базового метода суперкласса.
        enableEdgeToEdge() // Включение Edge-to-Edge режима для полноэкранного отображения.
        setContentView(R.layout.activity_main) // Установка макета активности.

        // Привязываем кнопку из разметки по ее ID.
        val button: Button = findViewById(R.id.button)

        // Установка слушателя нажатия для кнопки.
        button.setOnClickListener {
            /***
             * Пример 1: Создание "ручного" кастомного тоста.
             */

            // Создаем тост с текстом через ресурс строки.
            val toast = Toast.makeText(applicationContext, R.string.cat_food, Toast.LENGTH_LONG)

            // Устанавливаем позицию отображения тоста (по центру экрана).
            toast.setGravity(Gravity.CENTER, 0, 0)

            // Создаем горизонтальный LinearLayout для кастомного тоста.
            val linearLayout = LinearLayout(applicationContext).apply {
                orientation = LinearLayout.HORIZONTAL // Располагаем элементы горизонтально.
            }

            // Создаем TextView для отображения текста.
            val textView = TextView(applicationContext).apply {
                text = resources.getString(R.string.cat_food) // Устанавливаем текст, используя строковый ресурс.
            }

            // Создаем ImageView для отображения картинки.
            val catImage = ImageView(applicationContext).apply {
                setImageResource(R.drawable.pinkhellokitty) // Устанавливаем изображение из ресурсов.
            }

            // Добавляем ImageView и TextView в LinearLayout.
            linearLayout.addView(catImage)
            linearLayout.addView(textView)

            // Создаем кастомный тост.
            val customToast = Toast(applicationContext).apply {
                setGravity(Gravity.BOTTOM, 0, 0) // Установка позиции в нижней части экрана.
                duration = Toast.LENGTH_LONG // Длительность LONG.
                view = linearLayout // Указываем созданный кастомный макет (LinearLayout) в качестве отображения.
            }

            // Отображаем кастомный тост.
            customToast.show()

            /***
             * Пример 2: Использование custom_toast.xml для кастомного тоста.
             */

            // Получаем LayoutInflater для инфлейта кастомной разметки.
            val inflater = layoutInflater

            // Получаем контейнер для тоста (например, ViewGroup используется в custom_toast.xml).
            val container = findViewById<ViewGroup>(R.id.custom_toast_container)

            // Инфлейтим кастомный макет из файла custom_toast.xml.
            val layout: View = inflater.inflate(R.layout.custom_toast, container)

            // Привязываем TextView из кастомной разметки.
            val text: TextView = layout.findViewById(R.id.text)
            text.text = "Пора покормить кота!" // Устанавливаем текст в TextView.

            // Создаем тост, используя кастомный макет.
            with(Toast(applicationContext)) {
                setGravity(Gravity.BOTTOM, 0, 0) // Устанавливаем позицию тоста.
                duration = Toast.LENGTH_LONG // Длительность LONG.
                view = layout // Устанавливаем кастомную разметку.
                show() // Отображаем тост.
            }
        }

        /***
         * Установка системных отступов для корневого контейнера.
         * Это нужно для корректного отображения контента на устройствах с вырезами (notch) или управлением жестами.
         */
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            // Получаем размеры системных панелей (status bar и navigation bar).
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            // Устанавливаем внутренние отступы для корневого контейнера.
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)

            // Возвращаем неизмененные вставки для дальнейшей обработки.
            insets
        }
    }
}