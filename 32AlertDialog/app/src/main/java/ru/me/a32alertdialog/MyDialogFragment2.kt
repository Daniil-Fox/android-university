package ru.me.a32alertdialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class MyDialogFragment2 : DialogFragment() {

    // Массив с именами котов
    private val catNames = arrayOf("Васька", "Рыжик", "Мурзик")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Создание конструктора AlertDialog
            val builder = AlertDialog.Builder(it)

            // Установка заголовка диалога
            builder.setTitle("Выберите кота")
                // Установка списка элементов (имен котов) и обработчика кликов
                .setItems(catNames) { dialog, which ->
                    // Показ Toast с выбранным именем кота
                    Toast.makeText(activity, "Выбранный кот: ${catNames[which]}", Toast.LENGTH_SHORT).show()
                }

            builder.create() // Создание диалога
        } ?: throw IllegalStateException("Activity cannot be null") // Исключение, если Activity равна null
    }
}
