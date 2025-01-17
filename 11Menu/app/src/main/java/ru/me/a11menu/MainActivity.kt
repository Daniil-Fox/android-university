package ru.me.a11menu

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Поле для TextView
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Включаем поддержку интерфейса edge-to-edge
        enableEdgeToEdge()

        // Устанавливаем разметку активности
        setContentView(R.layout.activity_main)

        // Инициализируем TextView
        textView = findViewById(R.id.textView)

        // Устанавливаем отступы с учетом системных баров
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Создание меню в ToolBar/ActionBar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        // Подключаем XML разметку меню (res/menu/menu_main.xml)
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Обработка выбора пунктов меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Первый пункт меню
            R.id.action_cat1 -> {
                textView.text = "Вы выбрали кота!"
                return true
            }
            // Второй пункт меню
            R.id.action_cat2 -> {
                textView.text = "Вы выбрали кошку!"
                return true
            }
            // Третий пункт меню
            R.id.action_cat3 -> {
                textView.text = "Вы выбрали котёнка!"
                return true
            }
        }
        // Если выбран пункт, не обработанный в коде
        return super.onOptionsItemSelected(item)
    }
}
