package ru.me.a29yandexmap

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.mapview.MapView

class MainActivity : AppCompatActivity() {

    // Переменная для MapView из разметки
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Вызов метода суперкласса
        enableEdgeToEdge() // Включение Edge-to-Edge режима

        // Инициализация MapKitFactory (библиотека для работы с картами)
        MapKitFactory.initialize(this)

        setContentView(R.layout.activity_main) // Установка макета активности
        mapView = findViewById(R.id.mapview) // Привязка MapView из макета
    }

    override fun onStart() {
        super.onStart() // Вызов метода суперкласса
        MapKitFactory.getInstance().onStart() // Начало работы MapKitFactory
        mapView.onStart() // Начало работы MapView
    }

    override fun onStop() {
        mapView.onStop() // Остановка MapView
        MapKitFactory.getInstance().onStop() // Остановка MapKitFactory
        super.onStop() // Вызов метода суперкласса
    }
}
