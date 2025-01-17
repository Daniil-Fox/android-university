package ru.me.a31basicviews

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import ru.me.a31basicviews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Переменная для конфигурации AppBar (управление навигацией по верхнему меню)
    private lateinit var appBarConfiguration: AppBarConfiguration
    // Переменная для привязки макета Activity
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Вызов метода суперкласса для базовой инициализации

        // Инициализация привязки макета с использованием View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) // Установка корневого элемента макета

        // Установка Toolbar как Action Bar
        setSupportActionBar(binding.toolbar)

        // Получение контроллера навигации
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // Инициализация AppBarConfiguration для навигации
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration) // Связывание ActionBar с NavController

        // Установка обработчика события на плавающую кнопку (FAB)
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG) // Показ Snackbar с примерным текстом
                .setAction("Action", null) // Настройка действия кнопки в Snackbar (пустое действие)
                .setAnchorView(R.id.fab) // Указание якоря для Snackbar (FAB)
                .show() // Отображение Snackbar
        }
    }

    // Переопределение метода для создания меню
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu) // Подключение меню из ресурса menu_main.xml
        return true // Возвращаем true для отображения меню
    }

    // Обработка выбора пунктов меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true // Если выбран пункт "Settings", возвращаем true
            else -> super.onOptionsItemSelected(item) // Для остальных пунктов вызываем супер метод
        }
    }

    // Обработка кнопки "Назад/Вверх" в ActionBar
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main) // Получение NavController
        return navController.navigateUp(appBarConfiguration) // Выполнение навигации "Вверх"
                || super.onSupportNavigateUp() // Если невозможно, вызываем стандартную обработку
    }
}
