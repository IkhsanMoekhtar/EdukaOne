package com.example.edukaone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
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

        //binding.recyclerViewChat.layoutManager = LinearLayoutManager(this)

        /*
        // Add some sample messages
        chatMessages.add(ChatMessage("Lorem ipsum dolor sit amet, consectetur adipiscing .", ChatMessage.TYPE_RECEIVED))
        chatMessages.add(ChatMessage("Lorem ipsum dolor sit amet, consectetur adipiscing .", ChatMessage.TYPE_SENT))
        chatMessages.add(ChatMessage("Lorem ipsum dolor sit amet, consectetur adipiscing .", ChatMessage.TYPE_SENT))
        chatMessages.add(ChatMessage("Lorem ipsum dolor sit amet, consectetur adipiscing .", ChatMessage.TYPE_RECEIVED))

        chatAdapter = ChatAdapter(chatMessages)
        binding.recyclerViewChat.adapter = chatAdapter
        */

        /*
        // Handle send button click
        binding.imageIconSend.setOnClickListener {
            val message = binding.editTextInput.text.toString()
            if (message.isNotEmpty()) {
                addMessage(message, ChatMessage.TYPE_SENT)
                binding.editTextInput.text.clear()
                binding.recyclerViewChat.scrollToPosition(chatAdapter.itemCount - 1)
            }
        }*/

        val webView = findViewById<WebView>(R.id.webView)
        webView.settings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                view.loadUrl("javascript:alert('Load Succes')")
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onJsAlert(view: WebView, url: String, message: String, result: android.webkit.JsResult): Boolean {
                Toast.makeText(this@ChatActivity, message, Toast.LENGTH_LONG).show()
                result.confirm()
                return true
            }
        }

        webView.loadUrl("https://edukaone-0-streamlit-app-zl7k4x74ma-et.a.run.app/")
    }

    private fun addMessage(message: String, type: Int) {
        val chatMessage = ChatMessage(message, type)
        chatMessages.add(chatMessage)
        chatAdapter.notifyItemInserted(chatMessages.size - 1)
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
