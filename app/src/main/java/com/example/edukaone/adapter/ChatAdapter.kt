package com.example.edukaone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.edukaone.R
import com.example.edukaone.data.ChatMessage

class ChatAdapter(private val chatMessages: List<ChatMessage>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_SENT = 1
    private val VIEW_TYPE_RECEIVED = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_SENT -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_chat, parent, false)
                SentMessageViewHolder(view)
            }
            VIEW_TYPE_RECEIVED -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_response, parent, false)
                ReceivedMessageViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chatMessage = chatMessages[position]
        when (holder.itemViewType) {
            VIEW_TYPE_SENT -> {
                val sentHolder = holder as SentMessageViewHolder
                sentHolder.bind(chatMessage)
            }
            VIEW_TYPE_RECEIVED -> {
                val receivedHolder = holder as ReceivedMessageViewHolder
                receivedHolder.bind(chatMessage)
            }
        }
    }

    override fun getItemCount(): Int = chatMessages.size

    override fun getItemViewType(position: Int): Int {
        return when (chatMessages[position].type) {
            ChatMessage.TYPE_SENT -> VIEW_TYPE_SENT
            ChatMessage.TYPE_RECEIVED -> VIEW_TYPE_RECEIVED
            else -> throw IllegalArgumentException("Invalid message type")
        }
    }

    inner class SentMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageText: TextView = itemView.findViewById(R.id.text_message_send)

        fun bind(chatMessage: ChatMessage) {
            messageText.text = chatMessage.message

            // Atur ukuran dan posisi background drawable sesuai dengan panjang teks
            val drawable = messageText.background
            drawable?.let {
                it.setBounds(0, 0, messageText.width, messageText.height)
                messageText.background = it
            }
        }
    }

    inner class ReceivedMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageText: TextView = itemView.findViewById(R.id.text_message_receive)

        fun bind(chatMessage: ChatMessage) {
            messageText.text = chatMessage.message

            // Atur ukuran dan posisi background drawable sesuai dengan panjang teks
            val drawable = messageText.background
            drawable?.let {
                it.setBounds(0, 0, messageText.width, messageText.height)
                messageText.background = it
            }
        }
    }

}
