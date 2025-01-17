package ru.me.a30fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController

/**
 * A simple [Fragment] subclass.
 * Use the [MessageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MessageFragment : Fragment() {

    // Вызывается при создании фрагмента
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // Создаёт и привязывает макет для данного фрагмента
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Инфлейт (привязка) макета к фрагменту
        val view = inflater.inflate(R.layout.fragment_message, container, false)

        // Инициализация EditText для ввода сообщения
        val messageEditText = view.findViewById<EditText>(R.id.messagefragment_edit)

        // Инициализация кнопки для перевода сообщения
        val translateButton = view.findViewById<Button>(R.id.messagefragment_translate_button)
        translateButton.setOnClickListener {
            // Получаем текст, введённый пользователем
            val message = messageEditText.text.toString()
            // Создаём навигационное действие с передачей аргумента
            val action = MessageFragmentDirections.actionMessageFragmentToConverterFragment(message)
            // Выполняем навигацию на ConverterFragment с передачей аргумента
            view.findNavController().navigate(action)
            // Альтернативный способ навигации на ConverterFragment (без передачи аргументов)
            // view.findNavController().navigate(R.id.action_messageFragment_to_converterFragment)
        }

        return view // Возврат корневого элемента макета
    }

    // Объект-компаньон с методом для создания экземпляра фрагмента
    companion object {
        @JvmStatic
        fun newInstance() = MessageFragment() // Создание нового экземпляра MessageFragment
    }
}
