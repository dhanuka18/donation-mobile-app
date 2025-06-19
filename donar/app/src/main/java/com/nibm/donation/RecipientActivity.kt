package com.nibm.donation

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class RecipientActivity : AppCompatActivity() {

    // Initialize Firestore instance
    private val db = FirebaseFirestore.getInstance()

    // Declare UI components
    private lateinit var editTextNameRecipient: EditText
    private lateinit var editTextProblem: EditText
    private lateinit var editTextContactRecipient: EditText
    private lateinit var editTextDescriptionRecipient: EditText
    private lateinit var buttonPostRecipient: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post_recipient)  // Set your layout here

        // Initialize the UI components
        editTextNameRecipient = findViewById(R.id.editTextNameRecipient)
        editTextProblem = findViewById(R.id.editTextProblem)
        editTextContactRecipient = findViewById(R.id.editTextContactRecipient)
        editTextDescriptionRecipient = findViewById(R.id.editTextDescriptionRecipient)
        buttonPostRecipient = findViewById(R.id.buttonPostRecipient)

        // Set onClickListener for the "Post Now" button
        buttonPostRecipient.setOnClickListener {
            postRecipientData()
        }
    }

    // Function to post recipient data to Firestore
    private fun postRecipientData() {
        val name = editTextNameRecipient.text.toString().trim()
        val problem = editTextProblem.text.toString().trim()
        val contact = editTextContactRecipient.text.toString().trim()
        val description = editTextDescriptionRecipient.text.toString().trim()

        // Validate input fields
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(problem) ||
            TextUtils.isEmpty(contact) || TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Generate unique ID for Firestore
        val recipientId = db.collection("recipients").document().id

        // Create recipient data object
        val recipient = hashMapOf(
            "id" to recipientId,
            "name" to name,
            "problem" to problem,
            "contact" to contact,
            "description" to description
        )

        // Store recipient data in Firestore
        db.collection("recipients").document(recipientId).set(recipient)
            .addOnSuccessListener {
                // Create intent to pass data to CheckPostActivity
                val intent = Intent(this, CheckPostreActivity::class.java).apply {
                    putExtra("name", name)
                    putExtra("job", problem) // Assuming the problem is treated as "job" for this case
                    putExtra("contact", contact)
                    putExtra("description", description)
                }
                // Navigate to CheckPostActivity after success
                startActivity(intent)
                finish() // Close the current activity
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to add recipient", Toast.LENGTH_SHORT).show()
            }
    }

    // Function to clear input fields after posting
    private fun clearFields() {
        editTextNameRecipient.setText("")
        editTextProblem.setText("")
        editTextContactRecipient.setText("")
        editTextDescriptionRecipient.setText("")
    }
}
