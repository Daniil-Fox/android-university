package ru.me.a26preferencesframework

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    // Переменная для TextView, где будет отображаться информация о настройках
    private lateinit var settingCheckBox: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Стандартный вызов базового метода жизненного цикла
        enableEdgeToEdge() // Включение Edge-to-Edge интерфейса
        setContentView(R.layout.activity_main) // Устанавливаем макет активности

        // Находим элементы из макета
        val button: Button = findViewById(R.id.button) // Кнопка для перехода в настройки
        val button2: Button = findViewById(R.id.button2) // Кнопка для перехода в настройки звука
        settingCheckBox = findViewById(R.id.textView) // Поле, где отображаются настройки

        // Слушатель для первой кнопки: открывает настройки
        button.setOnClickListener {
            showSettings()
        }

        // Слушатель для второй кнопки: открывает настройки рингтона
        button2.setOnClickListener {
            showSettings2()
        }

        // Устанавливаем системные отступы для корректного отображения на устройствах с вырезами/панелями
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Метод для открытия активности с общими настройками
    fun showSettings() {
        val intent = Intent(
            this@MainActivity, // Источник текущей активности
            MyPreferenceActivity::class.java // Целевая активность
        )
        startActivity(intent) // Запуск активности
    }

    // Метод для открытия активности с настройками рингтона
    fun showSettings2() {
        val intent = Intent(
            this@MainActivity, // Источник текущей активности
            RingtonePreferenceActivity::class.java // Целевая активность
        )
        startActivity(intent) // Запуск активности
    }

    // Метод для получения результата из другой активности
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data) // Вызов базового метода

        // Получение экземпляра SharedPreferences, где хранятся настройки
        val sharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(this)

        // Получаем значение настройки "height", которое по умолчанию равно 20
        val catHeight = sharedPreferences.getInt("height", 20)

        // Обновляем TextView с новой информацией о высоте кота
        settingCheckBox.setText(
            "Высота кота = " + catHeight // Устанавливаем текст
        )
    }
}
