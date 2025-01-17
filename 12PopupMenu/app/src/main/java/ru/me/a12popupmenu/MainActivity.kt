package ru.me.a12popupmenu

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Включение edge-to-edge интерфейса
        enableEdgeToEdge()

        // Установка разметки активности
        setContentView(R.layout.activity_main)

        // Инициализация элементов интерфейса
        val imageView: ImageView = findViewById(R.id.imageView)
        val textView: TextView = findViewById(R.id.textView)
        val button: Button = findViewById(R.id.button)

        // Создание первого PopupMenu для ImageView
        val popupMenu = androidx.appcompat.widget.PopupMenu(this, imageView)
        popupMenu.inflate(R.menu.popupmenu) // Инфлейт меню из ресурсов
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu1 -> {
                    textView.text = "Вы выбрали PopupMenu 1"
                    true
                }
                R.id.menu2 -> {
                    textView.text = "Вы выбрали PopupMenu 2"
                    true
                }
                R.id.menu3 -> {
                    textView.text = "Вы выбрали PopupMenu 3"
                    true
                }
                else -> false
            }
        }

        // Создание второго PopupMenu для Button
        val popupMenu2 = PopupMenu(this, button)
        popupMenu2.inflate(R.menu.popup_menu) // Инфлейт меню из ресурсов
        popupMenu2.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.red -> {
                    textView.background = ColorDrawable(Color.RED) // Устанавливаем фон TextView в красный
                    textView.text = "Вы выбрали красный цвет"
                }
                R.id.yellow -> {
                    textView.background = ColorDrawable(Color.YELLOW) // Устанавливаем фон TextView в жёлтый
                    textView.text = "Вы выбрали жёлтый цвет"
                }
                R.id.green -> {
                    textView.background = ColorDrawable(Color.GREEN) // Устанавливаем фон TextView в зелёный
                    textView.text = "Вы выбрали зелёный цвет"
                }
            }
            false // Возвращаем false, так как не передаём событие дальше
        }

        // Для API 29+ (Android 10+) принудительно показываем иконки в меню
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu2.setForceShowIcon(true)
        }

        // Обработчик клика для Button (показывает popupMenu2)
        button.setOnClickListener {
            popupMenu2.show()
        }

        // Обработчик клика для ImageView (показывает popupMenu)
        imageView.setOnClickListener {
            popupMenu.show()
        }

        // Настраиваем отступы для корневого макета с учётом системных баров
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
