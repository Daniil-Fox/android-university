package ru.me.a29yandexmap

import android.app.Application
import com.yandex.mapkit.MapKitFactory

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("e91a4397-3fc8-49b4-8b6d-3d6385125848")
    }
}