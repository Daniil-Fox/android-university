package ru.me.a13screen

import android.content.Intent
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Объявление кнопок и текстового поля
    private lateinit var button: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Включаем поддержку интерфейса edge-to-edge
        enableEdgeToEdge()

        // Устанавливаем разметку активности
        setContentView(R.layout.activity_main)

        // Инициализируем кнопки и текстовое поле
        button = findViewById(R.id.button)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)
        button4 = findViewById(R.id.button4)
        textView = findViewById(R.id.textView)

        // Обработчик нажатия на первую кнопку
        button.setOnClickListener {
            // Открываем экран настроек экрана из системного приложения
            val intent = Intent(Settings.ACTION_DISPLAY_SETTINGS)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent) // Запускаем действие, если доступно
            }
        }

        // Обработчик нажатия на вторую кнопку
        button2.setOnClickListener {
            // Определяем размеры экрана
            val display: Display = windowManager.defaultDisplay
            val point = Point()
            display.getSize(point)
            val screenWidth: Int = point.x // Ширина экрана
            val screenHeight: Int = point.y // Высота экрана

            // Формируем строку с информацией о размере экрана
            val width = Integer.toString(screenWidth)
            val height = Integer.toString(screenHeight)
            val info = "Ширина: $width; Высота: $height"

            // Отображаем информацию в текстовом поле
            textView.text = info
        }

        // Обработчик нажатия на третью кнопку
        button3.setOnClickListener {
            // Определяем текущую яркость экрана
            try {
                val curBrightnessValue = Settings.System.getInt(
                    contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS
                )
                textView.text = "Текущая яркость экрана: $curBrightnessValue"
            } catch (e: Settings.SettingNotFoundException) {
                e.printStackTrace() // Логируем исключение, если не удалось получить яркость
            }
        }

        // Обработчик нажатия на четвёртую кнопку
        button4.setOnClickListener {
            var screen = "" // Переменная для хранения информации о экране
            val metrics = DisplayMetrics()

            if (Build.VERSION.SDK_INT >= 30) {
                // Для API 30 и выше получаем метрики экрана
                display?.apply {
                    getRealMetrics(metrics)
                    screen = """
                    Width: ${metrics.widthPixels} pixels
                    Height: ${metrics.heightPixels} pixels 
                    The Logical Density: ${metrics.density} 
                    X Dimension: ${metrics.xdpi} dot/inch
                    Y Dimension: ${metrics.ydpi} dot/inch
                    The screen density expressed as dots-per-inch: ${metrics.densityDpi}
                    A scaling factor for fonts displayed on the display: ${metrics.scaledDensity}
                    """
                }
            } else {
                // Для API ниже 30 используем getMetrics, который устарел
                windowManager.defaultDisplay.getMetrics(metrics)
                screen = """
                    Width: ${metrics.widthPixels} pixels
                    Height: ${metrics.heightPixels} pixels 
                    The Logical Density: ${metrics.density} 
                    X Dimension: ${metrics.xdpi} dot/inch
                    Y Dimension: ${metrics.ydpi} dot/inch
                    The screen density expressed as dots-per-inch: ${metrics.densityDpi}
                    A scaling factor for fonts displayed on the display: ${metrics.scaledDensity}"""
            }

            // Отображаем информацию в текстовом поле
            val infoTextView = findViewById<TextView>(R.id.textView)
            infoTextView.text = screen
        }

        // Устанавливаем отступы для главного макета с учётом системных баров
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
