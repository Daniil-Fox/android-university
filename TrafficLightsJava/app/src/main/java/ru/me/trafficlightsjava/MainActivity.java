package ru.me.trafficlightsjava;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    // Переменная для главного ConstraintLayout, который используется для изменения фона.
    private ConstraintLayout mConstraintLayout;

    // Переменная для TextView, предназначенного для отображения информации о текущем цвете.
    private TextView mInfoTextView;

    // Главный метод активности, вызываемый при создании экрана.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Вызов метода суперкласса onCreate (обязательный шаг при инициализации).

        // Включение полноэкранного режима (Edge-to-Edge UI).
        EdgeToEdge.enable(this);

        // Установка макета XML-файла для активности.
        setContentView(R.layout.activity_main);

        // Привязка ConstraintLayout из activity_main.xml по его идентификатору.
        mConstraintLayout = findViewById(R.id.constraintLayout);

        // Привязка TextView, отображающего текущий выбранный цвет.
        mInfoTextView = findViewById(R.id.textViewInfo);

        // Привязка кнопки "Желтый" из макета XML.
        Button yellowButton = (Button) findViewById(R.id.button_yellow);

        // Добавление слушателя нажатий для кнопки "Желтый".
        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Изменение текста на "Желтый".
                mInfoTextView.setText(R.string.yellow);

                // Изменение цвета фона на желтый.
                mConstraintLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.yellowColor));
            }
        });

        // Установка слушателя для обработки системных вставок (Insets) на ConstraintLayout.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.constraintLayout), (v, insets) -> {
            // Получение отступов системных панелей (status bar, navigation bar).
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            // Установка внутренних отступов (padding) для ConstraintLayout на основе системных вставок.
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            // Возвращаем неизмененные вставки для дальнейшей обработки.
            return insets;
        });
    }

    // Обработчик нажатия кнопки "Красный" (указанный в XML через атрибут `onClick`).
    public void onRedButtonClick(View view) {
        // Изменение текста на "Красный".
        mInfoTextView.setText(R.string.red);

        // Изменение цвета фона на красный.
        mConstraintLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.redColor));
    }

    // Обработчик нажатия кнопки "Зеленый" (указанный в XML через атрибут `onClick`).
    public void onGreenButtonClick(View view) {
        // Изменение текста на "Зеленый".
        mInfoTextView.setText(R.string.green);

        // Изменение цвета фона на зеленый.
        mConstraintLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.greenColor));
    }
}