package ru.me.a26preferencesframework

import android.os.Build
import android.os.Bundle
import android.preference.PreferenceFragment

class MyPrefsFragment : PreferenceFragment() {
    // Метод, вызываемый при создании фрагмента
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Вызов метода суперкласса

        // Проверяем версию API устройства
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            // Для версий ниже Honeycomb добавляем настройки напрямую, используя XML
            addPreferencesFromResource(R.xml.settings)
        } else {
            // Для более новых версий заменяем текущий фрагмент на MyPrefsFragment
            fragmentManager.beginTransaction()
                .replace(android.R.id.content, MyPrefsFragment()) // Замена текущего контента
                .commit() // Применение изменений
        }
    }
}
