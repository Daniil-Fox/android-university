package ru.me.a23catguide

import android.content.Context
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Включаем поддержку edge-to-edge UI
        enableEdgeToEdge()

        // Устанавливаем разметку для DetailActivity
        setContentView(R.layout.activity_detail)

        // Инициализируем WebView для отображения контента
        val webView: WebView = findViewById(R.id.webView)

        // Получаем интент, который запустил эту Activity
        val intent = intent

        // Получаем строку для имени ресурса из переданного значения "title"
        val resName = "n" + intent.getIntExtra("title", 0)

        // Получаем базовый контекст
        val context: Context = baseContext

        // Читаем текстовый файл из ресурсов по имени ресурса (raw)
        val text: String = readRawTextFile(
            context, resources.getIdentifier(
                resName,      // Имя ресурса
                "raw",        // Тип ресурса
                "ru.me.a23catguide" // Имя пакета
            )
        )

        // Загружаем полученный текст в WebView с указанием базового URL и типа файла
        webView.loadDataWithBaseURL(null, text, "text/html", "en_US", null)
    }

    // Метод для чтения текстового файла из raw-ресурсов
    private fun readRawTextFile(context: Context, resId: Int): String {
        // Открываем ресурс как InputStream
        val inputStream: InputStream = context.resources.openRawResource(resId)

        // Создаём ридер для чтения потока данных
        val inputReader = InputStreamReader(inputStream)
        val buffReader = BufferedReader(inputReader)

        // Переменная для хранения текущей строки
        var line: String?

        // Используем StringBuilder для сборки данных в строку
        val builder = StringBuilder()

        try {
            // Читаем файл построчно
            while (buffReader.readLine().also { line = it } != null) {
                builder.append(line) // Добавляем строку
                builder.append("\n") // Добавляем перевод строки
            }
        } catch (e: IOException) {
            // Возвращаем сообщение об ошибке, если произошло исключение
            return e.localizedMessage
        }

        // Возвращаем собранный текст из файла
        return builder.toString()
    }
}
