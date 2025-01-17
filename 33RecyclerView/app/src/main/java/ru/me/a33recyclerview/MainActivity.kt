package ru.me.a33recyclerview

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Инициализация активности
        enableEdgeToEdge() // Включение Edge-to-Edge режима
        setContentView(R.layout.activity_main) // Установка макета активности

        // Привязка RecyclerView из макета
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        // Установка менеджера компоновки для вертикального отображения
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Установка кастомного адаптера для RecyclerView
        recyclerView.adapter = CustomRecyclerAdapter(getCatList())

        // Переключение на горизонтальный менеджер компоновки (заменяет вертикальный)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Установка отступов для интерфейса на устройствах с вырезами/системными панелями
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()) // Получение размеров системных панелей
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom) // Применение отступов
            insets // Возврат insets для дальнейшей обработки
        }
    }

    // Создание списка строк от 0 до 30
    private fun fillList(): List<String> {
        val data = mutableListOf<String>()
        (0..30).forEach { i -> data.add("$i element") } // Добавление элементов в список
        return data // Возврат списка
    }

    // Получение списка строк из ресурсов массива (например, из strings.xml)
    private fun getCatList(): List<String> {
        return this.resources.getStringArray(R.array.cat_names).toList() // Конвертируем массив в список
    }
}