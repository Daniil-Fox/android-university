package ru.me.a20keyboard

import android.os.Bundle
import android.text.InputType
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), TextView.OnEditorActionListener {

    // Поля для EditText элементов
    private lateinit var editText1: EditText
    private lateinit var editText2: EditText
    private lateinit var editText3: EditText
    private lateinit var editText4: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Включаем поддержку интерфейса edge-to-edge
        enableEdgeToEdge()

        // Устанавливаем разметку для активности
        setContentView(R.layout.activity_main)

        // Инициализируем EditText элементы
        editText1 = findViewById(R.id.editTextText)
        editText2 = findViewById(R.id.editTextText2)
        editText3 = findViewById(R.id.editTextText3)
        editText4 = findViewById(R.id.editTextText4)

        // Устанавливаем разные типы ввода для EditText
        editText2.inputType = InputType.TYPE_CLASS_PHONE       // Телефонный ввод
        editText3.inputType = InputType.TYPE_CLASS_DATETIME    // Дата/время
        editText4.inputType = InputType.TYPE_CLASS_NUMBER      // Числовой ввод

        // Добавляем обработчики действия (EditorActionListener) для первого и второго полей
        editText1.setOnEditorActionListener(this)
        editText2.setOnEditorActionListener(this)

        // Устанавливаем отступы для главного элемента макета на основе размеров системных баров
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Переопределение метода для обработки нажатия кнопки "Назад"
    override fun onBackPressed() {
        // Создаём диалог подтверждения выхода при нажатии кнопки "Назад"
        AlertDialog.Builder(this).apply {
            setTitle("Подтверждение") // Заголовок диалога
            setMessage("Вы уверены, что хотите выйти из программы?") // Сообщение в диалоге

            // Добавляем кнопку "Да" с обработчиком
            setPositiveButton("Таки да") { _, _ ->
                super.onBackPressed() // Закрываем приложение
            }

            // Добавляем кнопку "Нет" с обработчиком
            setNegativeButton("Нет") { _, _ ->
                // Если пользователь отменяет выход
                Toast.makeText(this@MainActivity, "Спасибо, что остались!", Toast.LENGTH_LONG).show()
            }

            setCancelable(true) // Разрешаем возможность закрыть диалог без выбора
        }.create().show() // Создаём и показываем диалог
    }

    // Обработка действий при использовании кнопок клавиатуры
    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            // Обработка действия "Поиск"
            if (!editText1.text.toString().equals("cat", ignoreCase = true)) {
                // Если введённое значение не "cat"
                Toast.makeText(this, "Не буду ничего искать!", Toast.LENGTH_SHORT).show()
            } else {
                // Если введено "cat"
                Toast.makeText(this, "Отыщем кота!", Toast.LENGTH_SHORT).show()
            }
            return true // Указываем, что действие обработано
        }
        if (actionId == EditorInfo.IME_ACTION_GO) {
            // Обработка действия "Go"
            if (!editText2.text.toString().equals("cat", ignoreCase = true)) {
                // Если введённое значение не "cat"
                Toast.makeText(this, "Нет кота!", Toast.LENGTH_SHORT).show()
            } else {
                // Если введено "cat"
                Toast.makeText(this, "Ура, кот!", Toast.LENGTH_SHORT).show()
            }
            return true // Указываем, что действие обработано
        }
        return false // Если действие не обработано
    }
}
