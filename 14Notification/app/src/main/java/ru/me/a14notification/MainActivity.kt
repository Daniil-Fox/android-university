package ru.me.a14notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.media.SoundPool
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.IOException


class MainActivity : AppCompatActivity() {

    companion object {
        // Идентификатор уведомления
        const val NOTIFICATION_ID = 101
        const val CHANNEL_ID = "channelID" // Идентификатор канала уведомлений
    }

    private var counter = 101 // Счётчик для уведомлений
    private lateinit var soundPool: SoundPool // Звуковой пул для воспроизведения звуков
    private lateinit var assetManager: AssetManager // Для работы с ресурсами
    private var catSound: Int = 0 // Идентификатор звука
    private var streamID = 0 // Идентификатор потока звука

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Включаем поддержку интерфейса edge-to-edge
        enableEdgeToEdge()

        // Устанавливаем разметку активности
        setContentView(R.layout.activity_main)

        // Создаём канал уведомлений (для устройств с Android 8.0+)
        createNotificationChannel()

        // Создаём интент для уведомления
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        // Создаём PendingIntent для уведомления
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Создаём звуковые атрибуты для SoundPool
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME) // Указываем тип использования (игры)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION) // Тип контента
            .build()

        // Инициализируем SoundPool
        soundPool = SoundPool.Builder()
            .setAudioAttributes(attributes)
            .build()

        // Инициализируем AssetManager
        assetManager = assets

        // Загружаем звук для воспроизведения
        catSound = loadSound("glass_break.mp3")

        // Обрабатываем клик на кнопку
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            // Создаём уведомление с настройками
            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon) // Иконка уведомления
                .setContentTitle("Напоминание") // Заголовок уведомления
                .setContentText("Пора покормить кота") // Сообщение уведомления
                .setContentIntent(pendingIntent) // Действие при клике на уведомление
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        resources,
                        R.drawable.akashi1 // Картинка для большого значка
                    )
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Приоритет уведомления
                // Добавляем действия (опционально, пример кнопок)
                .addAction(R.drawable.icons_padlock, "Открыть", pendingIntent)
                .addAction(R.drawable.icons_replay, "Отказаться", pendingIntent)
                .addAction(R.drawable.icons_cat_footprint, "Другой вариант", pendingIntent)
                .setAutoCancel(true) // Уведомление исчезнет после клика

            // Отправляем уведомление и воспроизводим звук
            with(NotificationManagerCompat.from(this)) {
                notify(counter++, builder.build()) // Увеличиваем счётчик уведомлений
                playSound(catSound) // Воспроизводим звук
            }
        }

        // Устанавливаем отступы для основного макета активности, чтобы учитывать системные бары
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Метод для создания канала уведомлений (только для Android 8.0+)
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Notification Channel", // Имя канала
                NotificationManager.IMPORTANCE_DEFAULT // Уровень важности
            )
            val notificationManager = checkNotNull(
                getSystemService(NotificationManager::class.java)
            )
            notificationManager.createNotificationChannel(channel) // Регистрируем канал
        }
    }

    // Освобождаем ресурсы SoundPool при переходе активности в паузу
    override fun onPause() {
        super.onPause()
        soundPool.release() // Освобождаем ресурсы SoundPool
    }

    // Воспроизводим звук по идентификатору
    private fun playSound(sound: Int): Int {
        if (sound > 0) {
            streamID = soundPool.play(sound, 1F, 1F, 1, 0, 1F) // Воспроизведение звука
        }
        return streamID
    }

    // Загружаем звук из файлов ресурсов (assets)
    private fun loadSound(fileName: String): Int {
        val afd: AssetFileDescriptor = try {
            application.assets.openFd(fileName) // Открываем файл в каталогах assets
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("Meow", "Не могу загрузить файл $fileName") // Записываем ошибку в лог
            return -1 // Возвращаем -1, если файл не найден
        }
        return soundPool.load(afd, 1) // Загружаем звук в SoundPool
    }
}
