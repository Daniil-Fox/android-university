class MainActivity : AppCompatActivity() {

    // Метод, вызываемый при создании активности
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Вызов метода суперкласса для базовой инициализации активности
        enableEdgeToEdge() // Включение Edge-to-Edge режима для полноэкранного отображения
        setContentView(R.layout.activity_main) // Установка макета активности

        // Привязка элементов интерфейса к переменным
        val ultraText: TextView = findViewById(R.id.text_ultra) // Текстовое поле для фразы "ultra"
        val tiredText: TextView = findViewById(R.id.text_tired) // Текстовое поле для фразы "tired"
        val hmText: TextView = findViewById(R.id.text_hm)       // Текстовое поле для фразы "hm"
        val dangerousText: TextView = findViewById(R.id.text_dangerous) // Текстовое поле для фразы "dangerous"
        val rightBottomImage: ImageView = findViewById(R.id.right_bottom_image) // Изображение в правом нижнем углу

        // Устанавливаем слушатель на нажатие изображения
        rightBottomImage.setOnClickListener {
            val phrases = listOf(
                "Лучший",   // Первая фраза
                "Ну и ну",  // Вторая фраза
                "Фантазёр", // Третья фраза
                "Наруто",   // Четвёртая фраза
                "Крутой"    // Пятая фраза
            )

            // Перемешиваем список фраз случайным образом
            val shuffledList = phrases.shuffled()

            // Устанавливаем перемешанные фразы в соответствующие текстовые поля
            ultraText.text = shuffledList[0]    // Первая фраза идёт в текстовое поле ultraText
            tiredText.text = shuffledList[1]   // Вторая фраза идёт в текстовое поле tiredText
            hmText.text = shuffledList[2]      // Третья фраза идёт в текстовое поле hmText
            dangerousText.text = shuffledList[3] // Четвёртая фраза идёт в текстовое поле dangerousText
        }

        // Устанавливаем обработчик системных отступов для адаптации интерфейса на устройствах с вырезами/панелями
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()) // Получение размеров системных панелей
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom) // Применение отступов к корневому виду
            insets // Возврат insets для дальнейшей обработки
        }
    }
}
