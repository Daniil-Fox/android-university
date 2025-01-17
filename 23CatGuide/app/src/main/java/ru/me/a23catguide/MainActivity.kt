package ru.me.a23catguide

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Массив строк с названиями, которые будут отображаться в списке
    private val titles = arrayOf(
        "00. Начало",
        "01. Чем кормить кота",
        "02. Как гладить кота",
        "03. Как спит кот",
        "04. Как играть с котом",
        "05. Как разговаривать с котом",
        "06. Интересные факты из жизни котов",
        "07. Как назвать кота"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Включаем поддержку интерфейса edge-to-edge
        enableEdgeToEdge()

        // Устанавливаем разметку для MainActivity
        setContentView(R.layout.activity_main)

        // Находим ListView из разметки
        val listView: ListView = findViewById(R.id.listView)

        // Устанавливаем адаптер для ListView, чтобы связать данные из массива `titles` с элементами списка
        listView.adapter = ArrayAdapter<Any?>(
            this,
            android.R.layout.simple_list_item_1, // Стандартный layout элемента списка
            titles // Данные для отображения
        )

        // Включаем фильтрацию текста в ListView (например, для поиска)
        listView.isTextFilterEnabled = true

        // Устанавливаем обработчик нажатий на элементы списка
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ -> // Лямбда, принимающая параметры (адаптер, view, позиция, id)
                val intent = Intent() // Создаём новый intent
                intent.setClass(this@MainActivity, DetailActivity::class.java) // Указываем класс целевой активности
                intent.putExtra("title", position) // Передаём в intent позицию элемента списка

                startActivity(intent) // Запускаем DetailActivity
            }

        // Настройка системных отступов для корректного отображения интерфейса
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Устанавливаем отступы на основе системных баров
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
