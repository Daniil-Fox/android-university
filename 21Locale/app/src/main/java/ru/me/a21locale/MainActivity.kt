package ru.me.a21locale

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Включаем поддержку интерфейса edge-to-edge
        enableEdgeToEdge()

        // Устанавливаем разметку для активности
        setContentView(R.layout.activity_main)

        // Устанавливаем обработчик отступов для корневого элемента макета
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            // Получаем размеры системных баров (например, статус-бар и навигационная панель)
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            // Устанавливаем отступы для корневого элемента на основе размеров системных баров
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)

            insets // Возвращаем модифицированный объект `insets`
        }
    }
}
