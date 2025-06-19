package com.nibm.donation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.ImageView

class Go_to_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_go_to)

        // Apply window insets to prevent UI overlap
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.imageViewLogo)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize buttons
        val buttonGoToDonor = findViewById<Button>(R.id.buttonGoToDonor)
        val buttonGoToRecipient = findViewById<Button>(R.id.buttonGoToRecipient)

        // Set up button click listeners
        buttonGoToDonor.setOnClickListener {
            val intent = Intent(this, DonorActivity::class.java)
            startActivity(intent)
        }

        buttonGoToRecipient.setOnClickListener {
            val intent = Intent(this, RecipientActivity::class.java)
            startActivity(intent)
        }
    }
}
