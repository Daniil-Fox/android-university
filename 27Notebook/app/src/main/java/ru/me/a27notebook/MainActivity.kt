package ru.me.a27notebook

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File

class MainActivity : AppCompatActivity() {

    // Имя файла, с которым производится работа
    private val FILENAME = "sample.txt"

    // Поле EditText для ввода или редактирования текста
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Вызов метода суперкласса
        enableEdgeToEdge() // Включение Edge-to-Edge интерфейса
        setContentView(R.layout.activity_main) // Установка макета активности

        // Привязка EditText из макета
        editText = findViewById(R.id.editText)

        // Установка системных отступов, учитывающих элементы интерфейса устройства (например, вырезы)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Создание меню в верхней панели приложения
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu) // Привязка меню из файла ресурсов
        return true // Возвращаем true для отображения меню
    }

    // Обработка кликов по пунктам меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_open -> {
                // Если выбран пункт "Открыть файл", вызываем функцию openFile
                openFile(FILENAME)
                true
            }
            R.id.action_save -> {
                // Если выбран пункт "Сохранить файл", вызываем функцию saveFile
                saveFile(FILENAME)
                true
            }
            R.id.action_settings -> {
                // Если выбран пункт "Настройки", запускаем SettingsActivity
                val intent = Intent()
                intent.setClass(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true // Для остальных случаев возвращаем true
        }
    }

    // Метод для открытия файла
    private fun openFile(fileName: String) {
        // Чтение текста из файла (используется BufferedReader)
        val textFromFile = File(applicationContext.filesDir, fileName)
            .bufferedReader()
            .use { it.readText() }
        // Устанавливаем прочитанный текст в EditText
        editText.setText(textFromFile)
    }

    // Метод для сохранения текста в файл
    private fun saveFile(fileName: String) {
        // Открытие файла в режиме приватного сохранения и запись текста с EditText
        applicationContext.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(editText.text.toString().toByteArray()) // Конвертируем текст в байты и сохраняем
        }
        // Уведомление пользователя о том, что файл сохранён
        Toast.makeText(applicationContext, "Файл сохранён", Toast.LENGTH_SHORT).show()
    }

    // Метод, вызываемый при возобновлении активности
    override fun onResume() {
        super.onResume()

        // Получение настроек приложения через SharedPreferences
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        // Если пользователь включил опцию автоматического открытия файла, читаем содержимое
        if (prefs.getBoolean(getString(R.string.pref_openmode), false)) {
            openFile(FILENAME)
        }

        // Чтение размера шрифта из настроек (EditTextPreference) и установка его для EditText
        val fontSize = prefs.getString(getString(R.string.pref_size), "20")!!.toFloat()
        editText.textSize = fontSize

        // Чтение стилей текста из настроек (ListPreference)
        val regular = prefs.getString(getString(R.string.pref_style), "")
        var typeface = Typeface.NORMAL

        // Если в настройках выбран "Полужирный", добавляем стилю полужирность
        if (regular!!.contains("Полужирный")) typeface += Typeface.BOLD

        // Если в настройках выбран "Курсив", добавляем стилю курсив
        if (regular.contains("Курсив")) typeface += Typeface.ITALIC

        // Применяем настройки стиля текста для EditText
        editText.setTypeface(null, typeface)
    }
}
