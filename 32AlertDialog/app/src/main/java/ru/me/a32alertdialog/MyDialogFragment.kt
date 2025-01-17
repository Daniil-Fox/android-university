package ru.me.a32alertdialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment


class MyDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it) // Создание конструктора AlertDialog

            builder.setCancelable(true) // Установка возможности закрытия диалога по кнопке назад
                .setTitle("Важное сообщение!") // Заголовок диалога
                .setMessage("Выберите правильный ответ") // Основное сообщение диалога
                .setIcon(R.drawable.pinkhellokitty) // Указание иконки для диалога
                .setPositiveButton("Мяу", DialogInterface.OnClickListener { dialogInterface, i ->
                    (it as MainActivity).okClicked() // Обработка положительной кнопки: вызов метода "okClicked" в MainActivity
                })
                .setNegativeButton("Гав", DialogInterface.OnClickListener { dialogInterface, i ->
                    (it as MainActivity).cancelClicked() // Обработка отрицательной кнопки: вызов метода "cancelClicked" в MainActivity
                })

            builder.create() // Создание диалога на основе указанного конфигуратора
        } ?: throw IllegalStateException("Activity cannot be null") // Исключение, если Activity равна null
    }
}
