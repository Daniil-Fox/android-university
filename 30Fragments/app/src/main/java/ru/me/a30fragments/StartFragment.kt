package ru.me.a30fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController

/**
 * A simple [Fragment] subclass.
 * Use the [StartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start, container, false)

        val startButton = view.findViewById<Button>(R.id.startfragment_button_start)
        startButton.setOnClickListener {
            val navController = view.findNavController() // получаем ссылку на контроллер
            // переходим на цель
            navController.navigate(R.id.action_startFragment_to_messageFragment)

            // или без создания переменной
            // view.findNavController().navigate(R.id.action_startFragment_to_messageFragment)
        }

        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment StartFragment.
         */
        @JvmStatic
        fun newInstance() = StartFragment()
    }
}