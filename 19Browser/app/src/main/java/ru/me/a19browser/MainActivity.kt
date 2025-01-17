package ru.me.a19browser

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Инициализация WebView
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Включаем поддержку интерфейса edge-to-edge
        enableEdgeToEdge()

        // Устанавливаем разметку для MainActivity
        setContentView(R.layout.activity_main)

        // Инициализация WebView
        webView = findViewById(R.id.webView)

        // Устанавливаем кастомный WebViewClient для обработки переходов по ссылкам внутри WebView
        webView.webViewClient = MyWebViewClient()

        // Включаем поддержку JavaScript
        webView.settings.javaScriptEnabled = true

        // Загружаем начальный URL
        webView.loadUrl("https://yandex.ru")
    }

    // Кастомный WebViewClient для обработки загрузки страниц
    private class MyWebViewClient : WebViewClient() {

        @TargetApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            // Загружаем URL для Android N и выше
            view.loadUrl(request.url.toString())
            return true
        }

        // Для старых версий устройств (до Android N)
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }

    // Обрабатываем кнопку "Назад" для WebView
    override fun onBackPressed() {
        // Если WebView может вернуться на предыдущую страницу
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            // Если нет истории страниц, вызываем стандартное поведение кнопки "Назад"
            super.onBackPressed()
        }
    }
}
