package ru.me.a31basicviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.me.a31basicviews.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    // Переменная для хранения привязки макета
    private var _binding: FragmentFirstBinding? = null

    // Геттер для получения привязки, безопасное использование (_binding!! используется только внутри жизненного цикла фрагмента)
    private val binding get() = _binding!!

    // Создание и привязка макета фрагмента
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Инициализация binding для доступа к элементам интерфейса
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root // Возврат корневого элемента макета
    }

    // Настройка действий после создания представления
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Установка обработчика для кнопки, который выполняет навигацию на SecondFragment
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    // Очистка binding после уничтожения представления
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Освобождение ресурса привязки (чтобы избежать утечек памяти)
    }
}