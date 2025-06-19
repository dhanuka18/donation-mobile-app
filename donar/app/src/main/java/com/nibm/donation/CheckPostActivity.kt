package com.nibm.donation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class CheckPostActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance() // Firestore instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_post)  // Set the layout

        // Retrieve donor data passed via intent
        val donorId = intent.getStringExtra("donorId") ?: ""
        val name = intent.getStringExtra("name")
        val job = intent.getStringExtra("job")
        val contact = intent.getStringExtra("contact")
        val description = intent.getStringExtra("description")

        // Cyheck if the data is received, and if not, show a toast
        if (name == null || job == null || contact == null || description == null) {
            Toast.makeText(this, "Missing donor data", Toast.LENGTH_SHORT).show()
            return
        }

        // Display donor data in the TextViews
        findViewById<TextView>(R.id.textViewName).text = "Name: $name"
        findViewById<TextView>(R.id.textViewJob).text = "Job: $job"
        findViewById<TextView>(R.id.textViewContact).text = "Contact: $contact"
        findViewById<TextView>(R.id.textViewDescription).text = "Description: $description"

        val buttonDelete: Button = findViewById(R.id.buttonDeletePost1)
        buttonDelete.setOnClickListener {
            deleteDonorData(donorId)
        }


    }

    private fun deleteDonorData(donorId: String) {
        db.collection("donors").document(donorId).delete()
            .addOnSuccessListener {
// Navigate back to DonorActivity after successful deletion
                val intent = Intent(this, DonorActivity::class.java)
                startActivity(intent)
                finish()  // Close the activity after deletion
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to delete donor", Toast.LENGTH_SHORT).show()
            }
    }
}
