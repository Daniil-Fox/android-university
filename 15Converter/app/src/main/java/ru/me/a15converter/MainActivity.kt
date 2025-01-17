package ru.me.a15converter

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Включаем поддержку интерфейса edge-to-edge
        enableEdgeToEdge()

        // Устанавливаем разметку активности
        setContentView(R.layout.activity_main)

        // Инициализация кнопки переключения в метры
        val meterRadioButton: RadioButton = findViewById(R.id.radio_button_meter)

        // Поле ввода для длины
        val inputEditText: EditText = findViewById(R.id.editText)

        // Кнопка для конвертации
        val button: Button = findViewById(R.id.button_converter)

        // Устанавливаем обработчик клика для кнопки конвертации
        button.setOnClickListener {
            // Проверяем, введён ли текст в поле ввода
            if (inputEditText.text.isEmpty()) {
                // Если поле пустое, показываем сообщение
                Toast.makeText(
                    applicationContext,
                    "Введите длину кота", // Сообщение при пустом поле
                    Toast.LENGTH_LONG
                ).show()
            } else {
                // Если поле не пустое, получаем введенное значение
                val inputValue = inputEditText.text.toString().toFloat()

                // Проверяем, какая радиокнопка выбрана
                if (meterRadioButton.isChecked) {
                    // Если выбрано "В метры", конвертируем из попугаев в метры
                    inputEditText.setText(convertParrotToMeter(inputValue).toString())
                } else {
                    // Если выбрано "В попугаи", конвертируем из метров в попугаи
                    inputEditText.setText(convertMeterToParrot(inputValue).toString())
                }
            }
        }

        // Устанавливаем отступы для основного макета, чтобы учитывать высоту системных баров
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Метод для конвертации из попугаев в метры
    private fun convertParrotToMeter(parrot: Float): Float = (parrot / 7.6).toFloat()

    // Метод для конвертации из метров в попугаи
    private fun convertMeterToParrot(meter: Float): Float = (meter * 7.6).toFloat()
}
