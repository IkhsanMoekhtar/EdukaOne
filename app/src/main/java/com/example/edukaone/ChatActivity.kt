package com.example.edukaone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.edukaone.adapter.ChatAdapter
import com.example.edukaone.data.ChatMessage
import com.example.edukaone.databinding.ActivityChatBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

class ChatActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    private val chatMessages: MutableList<ChatMessage> = mutableListOf()
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        auth = Firebase.auth
        val firebaseUser = auth.currentUser

        if (firebaseUser == null) {
            // Not signed in, launch the Login activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        binding.overflowMenuButton.setOnClickListener {
            showPopupMenu(it)
        }

        binding.recyclerViewChat.layoutManager = LinearLayoutManager(this)

        // Add some sample messages
        chatMessages.add(ChatMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elithgfdjlkfdgjdflkjgdflkjgfldkjglkfdjgljfdgjklfdjglfdjklgjfdlkjgfdjlgjfdljglkfdjgfdlkgjfdlkgjfdklgjfdkljgflkdjglkdfjglkdfjgjlfdkjglkfd.", ChatMessage.TYPE_RECEIVED))
        chatMessages.add(ChatMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elitgfhgjhlkjdfjalk;dksauyhhfhdlhjfuksdhf dujhdfksahd dkasjfkdslg dsafdsfhldh fkjdshf jkdsh fkjdsjh fjkdhfkjdshf uksdhf uiewhf kuhsdukf huekhfj jkdshf yuiswe.", ChatMessage.TYPE_SENT))
        chatMessages.add(ChatMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elitgfhgjhlkjdfjalk;dksauyhhfhdlhjfuksdhf dujhdfksahd dkasjfkdslg dsafdsfhldh fkjdshf jkdsh fkjdsjh fjkdhfkjdshf uksdhf uiewhf kuhsdukf huekhfj jkdshf yuiswe.", ChatMessage.TYPE_SENT))
        chatMessages.add(ChatMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elithgfdjlkfdgjdflkjgdflkjgfldkjglkfdjgljfdgjklfdjglfdjklgjfdlkjgfdjlgjfdljglkfdjgfdlkgjfdlkgjfdklgjfdkljgflkdjglkdfjglkdfjgjlfdkjglkfd.", ChatMessage.TYPE_RECEIVED))

        chatAdapter = ChatAdapter(chatMessages)
        binding.recyclerViewChat.adapter = chatAdapter
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
                    signOut()
                    true
                }
                // Add more cases if needed
                else -> false
            }
        }

        popupMenu.show()
    }

    private fun signOut() {
        lifecycleScope.launch {
            try {
                val credentialManager = CredentialManager.create(this@ChatActivity)
                auth.signOut()
                credentialManager.clearCredentialState(ClearCredentialStateRequest())
                startActivity(Intent(this@ChatActivity, LoginActivity::class.java))
                finish()
            } catch (e: Exception) {
                // Tangani kesalahan saat logout
                Log.e("SignOutError", "Error during sign out: ${e.message}")
                Toast.makeText(this@ChatActivity, "Gagal keluar: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
