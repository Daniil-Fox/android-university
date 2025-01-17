package ru.me.a31basicviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.me.a31basicviews.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    // Переменная для привязки макета
    private var _binding: FragmentSecondBinding? = null

    // Геттер для безопасного использования привязки (работает только между onCreateView и onDestroyView)
    private val binding get() = _binding!!

    // Создание и привязка макета фрагмента
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false) // Привязка макета
        return binding.root // Возврат корневого элемента макета
    }

    // Настройка элементов интерфейса после создания представления
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Обработка нажатия кнопки для навигации обратно на FirstFragment
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    // Очистка привязки после уничтожения представления (чтобы избежать утечек памяти)
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Сброс привязки
    }
}
