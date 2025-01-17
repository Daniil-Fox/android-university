package ru.me.orientation

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Объявление переменных для интерфейса
    private lateinit var textView: TextView // Текстовое поле для отображения ориентации экрана
    private lateinit var textViewCrows: TextView // Текстовое поле для отображения количества "ворон"
    private lateinit var button: Button // Кнопка для увеличения количества ворон
    private var mCount: Int = 0 // Переменная подсчета количества ворон

    // Объект-компаньон для констант
    companion object {
        const val KEY_COUNT = "COUNT" // Ключ для сохранения данных при изменении конфигурации
    }

    // Метод, вызываемый при создании активности
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Вызов метода суперкласса для базовой инициализации
        setContentView(R.layout.activity_main) // Установка макета активности

        // Привязка компонентов интерфейса к переменным
        textView = findViewById(R.id.textView) // Поле для отображения ориентации экрана
        textViewCrows = findViewById(R.id.textView_crows) // Поле для отображения количества ворон
        button = findViewById(R.id.button) // Кнопка, по которой увеличивается число ворон

        // Устанавливаем текст ориентации экрана в textView, используя метод getScreenOrientation()
        textView.text = getScreenOrientation()

        // Устанавливаем слушатель нажатия на кнопку
        button.setOnClickListener {
            textViewCrows.text = "Я насчитал ${++mCount} ворон" // Увеличиваем счетчик и обновляем поле textViewCrows
        }

        // Проверяем, есть ли сохраненные данные из savedInstanceState
        if (savedInstanceState != null) {
            mCount = savedInstanceState.getInt(KEY_COUNT, 0) // Восстанавливаем сохраненный счетчик из Bundle
            textViewCrows.text = "Я насчитал $mCount ворон" // Обновляем текстовое поле на основе восстановленного значения
        }
    }

    // Метод, вызываемый при сохранении состояния активности
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState) // Вызов метода суперкласса для сохранения состояния
        outState.putInt(KEY_COUNT, mCount) // Сохраняем значение переменной mCount в Bundle
    }

    // Метод для определения текущей ориентации экрана
    private fun getScreenOrientation(): String {
        return when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> "Портретная ориентация" // Если экран в портретной ориентации
            Configuration.ORIENTATION_LANDSCAPE -> "Альбомная ориентация" // Если экран в альбомной ориентации
            else -> "Неопределённая ориентация" // Если ориентация не определена
        }
    }

    /*
    Метод onConfigurationChanged используется для обработки изменений конфигурации (например, изменения ориентации экрана).
    Закомментировано, так как ориентируется на использование savedInstanceState.

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig) // Вызов метода суперкласса

        // Проверяем ориентацию экрана
        val orientation = when(newConfig.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> "Portrait" // Портретная ориентация
            Configuration.ORIENTATION_LANDSCAPE -> "Landscape" // Альбомная ориентация
            Configuration.ORIENTATION_UNDEFINED -> "Undefined" // Ориентация не определена
            else -> "Error" // Ошибка
        }

        // Обновляем текст textView на новую ориентацию
        textView.text = "Orientation : $orientation"
    }
    */

    /*
    Метод getRotateOrientation() предназначен для определения угла поворота экрана.
    Этот метод был закомментирован, так как не используется.

    private fun getRotateOrientation(): String {
        return when (windowManager.defaultDisplay.rotation) {
            Surface.ROTATION_0 -> "Не поворачивали" // Экран в исходном положении
            Surface.ROTATION_90 -> "Повернули на 90 градусов по часовой стрелке" // Экран повернут на 90 градусов по часовой стрелке
            Surface.ROTATION_180 -> "Повернули на 180 градусов" // Экран повернут на 180 градусов
            Surface.ROTATION_270 -> "Повернули на 90 градусов против часовой стрелки" // Экран повернут на 90 градусов против часовой стрелки
            else -> "Не понятно" // Неизвестное состояние поворота
        }
    }
    */
}
