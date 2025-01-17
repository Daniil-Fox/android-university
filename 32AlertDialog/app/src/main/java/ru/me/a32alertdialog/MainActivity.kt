package ru.me.a32alertdialog

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Вызов метода суперкласса
        enableEdgeToEdge() // Включение Edge-to-Edge режима
        setContentView(R.layout.activity_main) // Установка макета активности

        // Привязка первой кнопки и показ MyDialogFragment при нажатии
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            val myDialogFragment = MyDialogFragment()
            val manager = supportFragmentManager // Получение FragmentManager
            val transaction: FragmentTransaction = manager.beginTransaction() // Начало транзакции
            myDialogFragment.show(transaction, "dialog") // Показ диалогового окна
        }

        // Привязка второй кнопки и показ MyDialogFragment2 при нажатии
        val button2: Button = findViewById(R.id.button2)
        button2.setOnClickListener {
            val myDialogFragment = MyDialogFragment2()
            val manager = supportFragmentManager // Получение FragmentManager
            val transaction: FragmentTransaction = manager.beginTransaction() // Начало транзакции
            myDialogFragment.show(transaction, "dialog2") // Показ диалогового окна
        }

        // Привязка третьей кнопки и показ MyDialogFragment3 при нажатии
        val button3: Button = findViewById(R.id.button3)
        button3.setOnClickListener {
            val myDialogFragment = MyDialogFragment3()
            val manager = supportFragmentManager // Получение FragmentManager
            val transaction: FragmentTransaction = manager.beginTransaction() // Начало транзакции
            myDialogFragment.show(transaction, "dialog3") // Показ диалогового окна
        }

        // Установка отступов для элементов интерфейса, чтобы учитывать системные панели
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()) // Получение размеров системных панелей
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom) // Установка отступов
            insets // Возврат insets для дальнейшей обработки
        }
    }

    // Метод оклика кнопки "Мяу"
    fun okClicked() {
        Toast.makeText(
            applicationContext, "Вы выбрали кнопку Мяу!", // Отображение Toast сообщения
            Toast.LENGTH_SHORT
        ).show()
    }

    // Метод отклика кнопки "Гав"
    fun cancelClicked() {
        Toast.makeText(
            applicationContext, "Вы выбрали кнопку Гав!", // Отображение Toast сообщения
            Toast.LENGTH_SHORT
        ).show()
    }
}
