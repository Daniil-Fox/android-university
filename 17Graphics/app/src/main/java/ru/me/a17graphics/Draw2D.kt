package ru.me.a17graphics

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View

class Draw2D(context: Context) : View(context) {

    // Создание объекта Paint для настройки стиля рисования
    private val paint: Paint = Paint()

    // Прямоугольник для возможного использования в рисовании
    private val rect: Rect = Rect()

    // Получаем ресурсы из контекста
    val res: Resources = this.resources

    // Загружаем картинку из ресурсов
    private var bitmap: Bitmap = BitmapFactory.decodeResource(res, R.drawable.pinkhellokitty)

    // Основной метод для рисования на Canvas
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Настраиваем стиль закрашивания
        paint.apply {
            style = Paint.Style.FILL // Устанавливаем тип заливки
            color = Color.CYAN       // Задаём цвет закраски (голубой)
        }
        // Заливаем весь холст цветом
        canvas.drawPaint(paint)

        // Рисуем жёлтый круг
        paint.apply {
            isAntiAlias = true       // Включаем сглаживание
            color = Color.YELLOW     // Устанавливаем жёлтый цвет
        }
        canvas.drawCircle(950F, 150F, 100F, paint) // Координаты и радиус круга

        // Рисуем зелёный прямоугольник
        paint.color = Color.GREEN                     // Устанавливаем зелёный цвет
        canvas.drawRect(20F, 1050F, 1050F, 2100F, paint) // Задаём координаты прямоугольника

        // Рисуем синий текст
        paint.apply {
            color = Color.BLUE        // Устанавливаем синий цвет
            style = Paint.Style.FILL  // Задаём заливку
            isAntiAlias = true        // Включаем сглаживание
            textSize = 40F            // Устанавливаем размер текста
        }
        canvas.drawText("Лужайка только для котов", 40F, 1150F, paint) // Текст и его координаты

        // Настраиваем текст серого цвета для поворота
        val x = 810F // Координаты X
        val y = 300F // Координаты Y

        paint.apply {
            color = Color.GRAY       // Устанавливаем серый цвет
            style = Paint.Style.FILL // Тип заливки
            textSize = 30F           // Размер текста
        }

        // Текст, который будет повёрнут
        val str2rotate = "Лучик солнца!"

        // Сохраняем состояние канвы перед поворотом
        canvas.save()

        // Поворачиваем холст на -45 градусов вокруг определённых координат
        canvas.rotate(-45F, x + rect.exactCenterX(), y + rect.exactCenterY())
        // Рисуем текст на повернутом холсте
        canvas.drawText(str2rotate, x, y, paint)

        // Восстанавливаем состояние канвы (отменяем поворот)
        canvas.restore()

        // Рисуем картинку из ресурсов
        canvas.drawBitmap(
            bitmap,
            (width - bitmap.width).toFloat() - 100F, // Координата X (справа с отступом 100)
            (height - bitmap.height - 10).toFloat() - 350F, // Координата Y (снизу с отступом)
            paint
        )
    }
}
