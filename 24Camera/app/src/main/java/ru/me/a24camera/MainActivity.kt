package ru.me.a24camera

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File


class MainActivity : AppCompatActivity() {

    // Константа для запроса на фотографирование
    private val REQUEST_TAKE_PHOTO = 1

    // Переменные для миниатюры изображения, полного изображения и URI сохраненного файла
    private lateinit var miniImage: ImageView
    private lateinit var fullImage: ImageView
    private var TAKE_PICTURE_REQUEST: Int = 1
    private lateinit var outputFileUri: Uri

    // Метод, вызываемый при создании активности
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Включаем поддержку интерфейса edge-to-edge
        enableEdgeToEdge()

        // Устанавливаем разметку активности
        setContentView(R.layout.activity_main)

        // Инициализация ImageView для отображения изображений
        miniImage = findViewById(R.id.miniImage)
        fullImage = findViewById(R.id.fullImage)

        // Инициализация кнопок
        val button_full_image: Button = findViewById(R.id.button_full_image) // Кнопка для сохранения полного изображения
        val button_bitmap: Button = findViewById(R.id.button_bitmap) // Кнопка для получения миниатюры

        // Обработка нажатия кнопки для получения полного изображения
        button_full_image.setOnClickListener {
            try {
                saveFullImage() // Метод для сохранения полного изображения
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace() // Обработка исключения, если действие фотоаппарата не найдено
            }
        }

        // Обработка нажатия кнопки для получения миниатюры изображения
        button_bitmap.setOnClickListener {
            try {
                getThumbnailPicture() // Метод для получения миниатюры изображения
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace() // Обработка исключения
            }
        }

        // Обработка системных отступов для корректного отображения интерфейса
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Устанавливаем отступы с учётом системных баров
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Метод для обработки результатов действий (например, получения изображения)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            TAKE_PICTURE_REQUEST -> { // Если запрос сделан для получения изображения
                if (resultCode == Activity.RESULT_OK && data !== null) { // Если результат успешен и есть данные
                    if (data.hasExtra("data")) { // Если данные содержат миниатюру
                        miniImage.setImageBitmap(data.extras?.get("data") as Bitmap) // Устанавливается миниатюра изображения
                    }
                }
                if (resultCode == Activity.RESULT_OK && data == null) { // Если данные отсутствуют (сохранение полного изображения)
                    fullImage.setImageDrawable(null) // Очистка предыдущего изображения
                    fullImage.setImageURI(outputFileUri) // Отображение сохранённого изображения по URI
                }
            }
            else -> {
                // Обработка неправильного кода запроса
                Toast.makeText(this, "Wrong request code", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Метод для получения миниатюры изображения
    private fun getThumbnailPicture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE) // Интент для вызова камеры
        startActivityForResult(intent, TAKE_PICTURE_REQUEST) // Запуск действия с ожиданием результата
    }

    // Метод для сохранения полного изображения
    private fun saveFullImage() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE) // Интент для вызова камеры
        val file = File(
            // Директория для сохранения изображения
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "test.jpg" // Имя файла
        )
        // Получение URI файла с использованием FileProvider (устаревшее Uri.fromFile заменено)
        outputFileUri = FileProvider.getUriForFile(
            this@MainActivity,
            "ru.me.a24camera.provider", // Укажите подпись вашего приложения + ".provider"
            file
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri) // Указываем, куда сохранить изображение
        startActivityForResult(intent, TAKE_PICTURE_REQUEST) // Запуск действия с ожиданием результата
    }
}
