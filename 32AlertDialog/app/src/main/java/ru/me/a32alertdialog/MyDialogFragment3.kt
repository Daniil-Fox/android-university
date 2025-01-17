package ru.me.a32alertdialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class MyDialogFragment3 : DialogFragment() {

    // Массив с именами котов
    private val catNames = arrayOf("Васька", "Рыжик", "Мурзик")
    // Массив с состояниями выбора (по умолчанию "Рыжик" выбран)
    private val checkedItems = booleanArrayOf(false, true, false)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Создание конструктора AlertDialog
            val builder = AlertDialog.Builder(it)

            // Установка заголовка диалога
            builder.setTitle("Выберите котов")
                // Установка списка с возможностью множественного выбора
                .setMultiChoiceItems(catNames, checkedItems) { _, which, isChecked ->
                    checkedItems[which] = isChecked // Обновление состояния выбора для текущего элемента
                }
                // Обработка нажатия на кнопку "Готово"
                .setPositiveButton("Готово") { _, _ ->
                    for (i in catNames.indices) {
                        val checked = checkedItems[i]
                        if (checked) {
                            println(catNames[i]) // Выводим в консоль имена выбранных котов
                        }
                    }
                }
                // Обработка нажатия на кнопку "Отмена"
                .setNegativeButton("Отмена") { dialog, _ ->
                    dialog.cancel() // Закрытие диалога
                }

            builder.create() // Создание диалога
        } ?: throw IllegalStateException("Activity cannot be null") // Исключение, если Activity равна null
    }
}
