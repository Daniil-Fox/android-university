package ru.me.a16lifecycle

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Метод, вызываемый при создании активности
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Включаем поддержку интерфейса edge-to-edge
        enableEdgeToEdge()

        // Устанавливаем разметку активности
        setContentView(R.layout.activity_main)

        // Логируем вызов метода onCreate
        Log.i("MainActivity", "onCreate() called")

        // Устанавливаем отступы для основного макета, чтобы учитывать системные бары
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Метод, вызываемый при запуске активности (активность становится видимой)
    override fun onStart() {
        super.onStart()
        Log.i("MainActivity", "onStart() called")
    }

    // Метод, вызываемый при перезапуске активности (после вызова onStop)
    override fun onRestart() {
        super.onRestart()
        Log.i("MainActivity", "onRestart() called")
    }

    // Метод, вызываемый после onStart (активность готова к взаимодействию с пользователем)
    override fun onResume() {
        super.onResume()
        Log.i("MainActivity", "onResume() called")
    }

    // Метод, вызываемый перед переходом в неактивное состояние (например, при переключении на другую активность)
    override fun onPause() {
        super.onPause()
        Log.i("MainActivity", "onPause() called")
    }

    // Метод, вызываемый, когда активность становится невидимой
    override fun onStop() {
        super.onStop()
        Log.i("MainActivity", "onStop() called")
    }

    // Метод, вызываемый перед уничтожением активности
    override fun onDestroy() {
        super.onDestroy()
        Log.i("MainActivity", "onDestroy() called")
    }
}
