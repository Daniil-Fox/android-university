package ru.me.a28broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    companion object {
        // Константа для идентификации действия пользовательского широковещания
        val WHERE_MY_CAT_ACTION: String = "ru.me.a28broadcast.action.CAT"

        // Сообщение, которое будет отправлено в широковещание
        val ALARM_MESSAGE: String = "Срочно пришлите кота!"

        // Метод для получения текущего времени в формате yyyy-MM-dd HH:mm
        private fun getCurrentTimeStamp(): String {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
            val now = Date()
            return simpleDateFormat.format(now) // Возвращаем отформатированную строку
        }
    }

    // Поля для работы с кнопками, широковещательными приёмниками и текстовым представлением
    private lateinit var button: Button // Первая кнопка
    private lateinit var button2: Button // Вторая кнопка
    private lateinit var receiver: MessageReceiver // Приёмник пользовательского широковещания
    private lateinit var textView: TextView // Текстовое поле для отображения времени

    // Широковещательный приёмник для системного события TIME_TICK
    private val timeBroadcastReceiver = TimeBroadcastReceiver()

    // Ленивая инициализация анонимного широковещательного приёмника для обновления времени
    private val tickReceiver by lazy { makeBroadcastReceiver() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Вызов метода суперкласса
        enableEdgeToEdge() // Включение Edge-to-Edge интерфейса
        setContentView(R.layout.activity_main) // Установка макета активности

        // Привязка кнопок и текстового поля к элементам макета
        button = findViewById(R.id.button)
        button2 = findViewById(R.id.button2)
        textView = findViewById(R.id.textView)

        // Инициализация приёмника для кастомного действия WHERE_MY_CAT_ACTION
        receiver = MessageReceiver()
        IntentFilter(WHERE_MY_CAT_ACTION).also {
            registerReceiver(receiver, it, RECEIVER_EXPORTED) // Регистрация приёмника с фильтром
        }

        // Установка действия для первой кнопки: отправляем пользовательское широковещание
        button.setOnClickListener {
            val intent = Intent() // Создаём новый Intent
            intent.action = WHERE_MY_CAT_ACTION // Указываем действие
            intent.putExtra("ru.me.a28broadcast.Message", ALARM_MESSAGE) // Передаём сообщение
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES) // Указываем флаг для обработки остановленных приложений
            sendBroadcast(intent) // Отправляем широковещание
        }

        // Установка действия для второй кнопки: регистрируем приёмник для системного события TIME_TICK
        button2.setOnClickListener {
            registerReceiver(
                timeBroadcastReceiver, IntentFilter(
                    "android.intent.action.TIME_TICK" // Действие для системного события TIME_TICK
                )
            )
            // Уведомление для пользователя
            Toast.makeText(
                getApplicationContext(), "Приёмник включен",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Установка системных отступов для корректного отображения на устройствах с вырезами/панелями
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()) // Получение отступов системных панелей
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom) // Установка отступов
            insets // Возвращаем обработанные отступы
        }
    }

    // Остановка активности: отменяем регистрацию приёмников
    override fun onStop() {
        super.onStop() // Вызов метода суперкласса
        unregisterReceiver(receiver) // Отмена приёмника пользовательского широковещания
        unregisterReceiver(tickReceiver) // Отмена анонимного приёмника для TIME_TICK
    }

    // Возобновление активности: регистрация анонимного приёмника и обновление времени
    override fun onResume() {
        super.onResume() // Вызов метода суперкласса
        textView.text = getCurrentTimeStamp() // Обновляем текстовое поле с текущим временем
        registerReceiver(tickReceiver, IntentFilter(Intent.ACTION_TIME_TICK)) // Регистрируем приёмник для TIME_TICK
    }

    // Уничтожение активности: отменяем регистрацию системного приёмника и уведомляем пользователя
    override fun onDestroy() {
        super.onDestroy() // Вызов метода суперкласса
        unregisterReceiver(timeBroadcastReceiver) // Отмена системного приёмника для TIME_TICK
        Toast.makeText( // Уведомление для пользователя
            getApplicationContext(),
            "Приёмник выключён", Toast.LENGTH_SHORT
        ).show()
    }

    // Анонимный широковещательный приёмник для обработки TIME_TICK
    private fun makeBroadcastReceiver(): BroadcastReceiver {
        return object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent?) {
                if (intent?.action == Intent.ACTION_TIME_TICK) { // Проверка соответствия действия
                    textView.text = getCurrentTimeStamp() // Обновляем текстовое поле с текущим временем
                }
            }
        }
    }
}
