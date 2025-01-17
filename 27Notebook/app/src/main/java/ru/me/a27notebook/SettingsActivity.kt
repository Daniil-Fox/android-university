package ru.me.a27notebook

import android.os.Bundle
import android.preference.PreferenceActivity

class SettingsActivity : PreferenceActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // загружаем предпочтения из ресурсов
        addPreferencesFromResource(R.xml.preferences)
    }
}