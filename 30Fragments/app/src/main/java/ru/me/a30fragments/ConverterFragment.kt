package ru.me.a30fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * A simple [Fragment] subclass.
 * Use the [ConverterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConverterFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_converter, container, false)

        // Получение аргументов фрагмента (переданное сообщение)
        val message = ConverterFragmentArgs.fromBundle(requireArguments()).message

        // Преобразование переданного сообщения (добавление "мяу " к каждому символу)
        var meow = ""
        repeat(message.length) {
            meow += "мяу "
        }

        // Нахождение TextView в макете и установка преобразованного текста
        val translatedText = view.findViewById<TextView>(R.id.converterfragment_text)
        translatedText.text = meow

        return view // Возврат корневого элемента макета
    }

    // Объект-компаньон с методом для создания экземпляра фрагмента
    companion object {
        @JvmStatic
        fun newInstance() = ConverterFragment() // Создание нового экземпляра ConverterFragment
    }
}
