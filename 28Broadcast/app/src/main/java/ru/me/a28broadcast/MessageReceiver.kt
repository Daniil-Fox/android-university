package ru.me.a28broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MessageReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Обнаружено сообщение: " +
                intent.getStringExtra("ru.me.a28broadcast.Message"),
            Toast.LENGTH_SHORT).show()
    }
}