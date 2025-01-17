package ru.me.a33recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Кастомный адаптер для RecyclerView, который принимает список строк (names) и отображает их в элементах списка
class CustomRecyclerAdapter(private val names: List<String>) : RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

    // Внутренний класс ViewHolder для хранения ссылок на элементы интерфейса, которые будут использоваться в отдельных элементах списка
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Большой TextView для отображения значения из списка
        val largeTextView: TextView = itemView.findViewById(R.id.textViewLarge)
        // Маленький TextView для отображения статического значения ("кот")
        val smallTextView: TextView = itemView.findViewById(R.id.textViewSmall)
    }

    // Создание нового ViewHolder, вызывается LayoutInflater для создания отображения одного элемента списка
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Используем LayoutInflater, чтобы загрузить макет (recyclerview_item) и передать его во ViewHolder
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView) // Возвращаем новый ViewHolder с макетом элемента
    }

    // Привязка данных к элементу списка на определённой позиции
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Устанавливаем текст в largeTextView из списка строк (по позиции)
        holder.largeTextView.text = names[position]
        // Устанавливаем статический текст для smallTextView ("кот")
        holder.smallTextView.text = "кот"
    }

    // Возвращает количество элементов в списке (определяет размер RecyclerView)
    override fun getItemCount(): Int {
        return names.size // Количество элементов равно количеству строк в списке
    }
}