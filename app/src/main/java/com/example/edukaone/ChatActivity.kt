package com.example.edukaone

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.edukaone.adapter.ChatAdapter
import com.example.edukaone.data.ChatMessage

class ChatActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    private val chatMessages: MutableList<ChatMessage> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val menuButton: ImageButton = findViewById(R.id.overflow_menu_button)
        menuButton.setOnClickListener {
            showPopupMenu(menuButton)
        }

        recyclerView = findViewById(R.id.recycler_view_chat)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Add some sample messages
        chatMessages.add(ChatMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elithgfdjlkfdgjdflkjgdflkjgfldkjglkfdjgljfdgjklfdjglfdjklgjfdlkjgfdjlgjfdljglkfdjgfdlkgjfdlkgjfdklgjfdkljgflkdjglkdfjglkdfjgjlfdkjglkfd.", ChatMessage.TYPE_RECEIVED))
        chatMessages.add(ChatMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elitgfhgjhlkjdfjalk;dksauyhhfhdlhjfuksdhf dujhdfksahd dkasjfkdslg dsafdsfhldh fkjdshf jkdsh fkjdsjh fjkdhfkjdshf uksdhf uiewhf kuhsdukf huekhfj jkdshf yuiswe.", ChatMessage.TYPE_SENT))
        chatMessages.add(ChatMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elitgfhgjhlkjdfjalk;dksauyhhfhdlhjfuksdhf dujhdfksahd dkasjfkdslg dsafdsfhldh fkjdshf jkdsh fkjdsjh fjkdhfkjdshf uksdhf uiewhf kuhsdukf huekhfj jkdshf yuiswe.", ChatMessage.TYPE_SENT))
        chatMessages.add(ChatMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elithgfdjlkfdgjdflkjgdflkjgfldkjglkfdjgljfdgjklfdjglfdjklgjfdlkjgfdjlgjfdljglkfdjgfdlkgjfdlkgjfdklgjfdkljgflkdjglkdfjglkdfjgjlfdkjglkfd.", ChatMessage.TYPE_RECEIVED))

        chatAdapter = ChatAdapter(chatMessages)
        recyclerView.adapter = chatAdapter
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.popup_menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_item1 -> {
                    // Handle action item 1
                    true
                }
                R.id.action_item2 -> {
                    // Handle action item 2
                    true
                }
                // Add more cases if needed
                else -> false
            }
        }

        popupMenu.show()
    }

}
