package com.nibm.donation

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class DonorActivity : AppCompatActivity() {
    private lateinit var editTextName: EditText
    private lateinit var editTextJob: EditText
    private lateinit var editTextContact: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var buttonPost: Button

    private val db = FirebaseFirestore.getInstance() // Firestore instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post_donor)

        // Initialize UI components
        editTextName = findViewById(R.id.editTextName)
        editTextJob = findViewById(R.id.editTextJob)
        editTextContact = findViewById(R.id.editTextContact)
        editTextDescription = findViewById(R.id.editTextDescription)
        buttonPost = findViewById(R.id.buttonPost)

        // Post Data Button Click
        buttonPost.setOnClickListener {
            postDonorData()
        }
    }

    private fun postDonorData() {
        val name = editTextName.text.toString().trim()
        val job = editTextJob.text.toString().trim()
        val contact = editTextContact.text.toString().trim()
        val description = editTextDescription.text.toString().trim()

        // Validate input fields
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(job) ||
            TextUtils.isEmpty(contact) || TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Generate unique ID for Firestore
        val donorId = db.collection("donors").document().id

        // Create donor data object
        val donor = hashMapOf(
            "id" to donorId,
            "name" to name,
            "job" to job,
            "contact" to contact,
            "description" to description
        )

        // Store donor data in Firestore
        db.collection("donors").document(donorId).set(donor)
            .addOnSuccessListener {
                Toast.makeText(this, "Donor added successfully!", Toast.LENGTH_SHORT).show()
                clearFields()

                // Create intent to pass data to CheckPostActivity
                val intent = Intent(this, CheckPostActivity::class.java).apply {
                    putExtra("name", name)
                    putExtra("job", job)
                    putExtra("contact", contact)
                    putExtra("description", description)
                }
                // Navigate to CheckPostActivity after success
                startActivity(intent)
                finish() // Close the current activity
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to add donor", Toast.LENGTH_SHORT).show()
            }
    }

    // Clear input fields after posting
    private fun clearFields() {
        editTextName.setText("")
        editTextJob.setText("")
        editTextContact.setText("")
        editTextDescription.setText("")
    }
}
