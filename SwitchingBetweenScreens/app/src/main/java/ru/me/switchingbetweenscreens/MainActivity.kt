package ru.me.switchingbetweenscreens

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.me.switchingbetweenscreens.SecondActivity.Companion.THIEF

/ Основной класс MainActivity, который наследуется от AppCompatActivity.
// Этот класс управляет главным экраном приложения.
class MainActivity : AppCompatActivity() {

    // Переменные для доступа к элементам интерфейса.
    // Используется lateinit, чтобы отложить инициализацию этих переменных (они будут проинициализированы в onCreate).
    private lateinit var editAddress: EditText // Поле для ввода адреса
    private lateinit var editGift: EditText    // Поле для ввода подарка
    private lateinit var textRobber: TextView // Поле для отображения текста с именем "грабителя"

    companion object {
        // Константа для кода запроса, когда мы вызываем startActivityForResult(). Используется для идентификации результата.
        const val REQUEST_CHOOSE_THIEF = 0
    }

    // Метод, вызываемый при создании активности.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Вызов метода суперкласса для инициализации активности.
        enableEdgeToEdge() // Включение полноэкранного режима для устройств с вырезами.
        setContentView(R.layout.activity_main) // Подключение макета с файла activity_main.xml.

        // Сопоставление элементов интерфейса с соответствующими виджетами из макета.
        editAddress = findViewById(R.id.editAddress) // Поле ввода для адреса
        editGift = findViewById(R.id.editGift)       // Поле ввода для подарка
        textRobber = findViewById(R.id.robber)       // Текстовое поле для отображения результата

        // Инициализация кнопок из макета.
        val imageButton: ImageButton = findViewById(R.id.imageButton) // Кнопка для перехода в AboutActivity
        val buttonToSecond: Button = findViewById(R.id.button)        // Кнопка для перехода во вторую активность

        // Слушатель нажатий на imageButton. Открывает AboutActivity ("О приложении").
        imageButton.setOnClickListener {
            val intent = Intent(this@MainActivity, AboutActivity::class.java) // Создаём Intent для перехода к AboutActivity
            startActivity(intent) // Запускаем AboutActivity.
        }

        // Слушатель нажатий на buttonToSecond. Передаёт данные в SecondActivity и ожидает результата.
        buttonToSecond.setOnClickListener {
            val intentSecond = Intent(this@MainActivity, SecondActivity::class.java) // Создаём Intent для перехода к SecondActivity

            // Передаём введённые данные в SecondActivity через Intent.
            intentSecond.putExtra("username", editAddress.text.toString()) // Передача значения из editAddress
            intentSecond.putExtra("gift", editGift.text.toString())       // Передача значения из editGift

            // Запускаем SecondActivity и ожидаем результат с кодом REQUEST_CHOOSE_THIEF.
            startActivityForResult(intentSecond, REQUEST_CHOOSE_THIEF)
        }

        // Адаптация пользовательского интерфейса для устройств с вырезами/панелями системы, где требуется учесть системные отступы.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            // Получаем отступы для системных панелей (status bar, navigation bar).
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Устанавливаем padding для корневого View, чтобы интерфейс отображался корректно.
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets // Возвращаем insets для использования в дальнейшем.
        }
    }

    // Метод для обработки результата, возвращённого из SecondActivity.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Проверяем, что результат вернулся для нашего запроса REQUEST_CHOOSE_THIEF.
        if (requestCode == REQUEST_CHOOSE_THIEF) {

            // Если результат успешен (Activity.RESULT_OK), получаем данные.
            if (resultCode == Activity.RESULT_OK) {
                val thiefName = data?.getStringExtra(THIEF) // Получаем имя грабителя из Intent.
                textRobber.text = thiefName // Устанавливаем имя грабителя в TextView.
            } else {
                textRobber.text = "" // Если результат неудачен или отменён, очищаем текстовое поле.
            }
        }
    }
}