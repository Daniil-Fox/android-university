package ru.me.clickcrowjava;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Переменная для хранения количества "ворон"
    private int mCount = 0;
    // Кнопка для подсчета ворон
    private Button mCrowsCounterButton;
    // Текстовое поле для отображения информации
    private TextView mInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Вызов метода суперкласса для базовой инициализации активности
        EdgeToEdge.enable(this); // Включение Edge-to-Edge режима для интерфейса
        setContentView(R.layout.activity_main); // Установка макета активности

        // Привязка кнопки и текстового поля к элементам разметки
        mCrowsCounterButton = findViewById(R.id.button_counter); // Кнопка для подсчета ворон
        mInfoTextView = findViewById(R.id.textView); // Текстовое поле для отображения результата

        // Устанавливаем слушатель на кнопку для подсчета ворон
        mCrowsCounterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // При нажатии на кнопку увеличиваем счетчик и обновляем текстовое поле
                mInfoTextView.setText("Я насчитал " + ++mCount + " ворон");
            }
        });

        // Обработка системных отступов для корректного отображения интерфейса на устройствах с вырезами/системными панелями
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()); // Получение размеров системных панелей
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom); // Применение отступов к корневому виду
            return insets; // Возврат insets для дальнейшей обработки
        });
    }

    // Метод, связанный с обработкой кликов для других элементов (не используется непосредственно в данном коде)
    public void onClick(View view) {
        // Устанавливаем текстовое поле на "Hello Kitty!" при вызове метода
        mInfoTextView.setText("Hello Kitty!");
    }
}