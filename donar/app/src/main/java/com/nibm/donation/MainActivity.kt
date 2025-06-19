package com.nibm.donation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_go_to)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.imageViewLogo)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnGoTo = findViewById<Button>(R.id.buttonGoToRecipient)
        btnGoTo.setOnClickListener {
            val intent = Intent(this, Go_to_Activity::class.java)
            startActivity(intent)
        }
    }
}
