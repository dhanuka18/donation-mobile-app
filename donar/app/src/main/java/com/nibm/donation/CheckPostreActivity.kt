package com.nibm.donation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class CheckPostreActivity : AppCompatActivity() {

    private lateinit var buttonBack: Button
    private lateinit var buttonDeletePost1: Button

    private lateinit var textViewName: TextView
    private lateinit var textViewJob: TextView
    private lateinit var textViewContact: TextView
    private lateinit var textViewDescription: TextView
    private lateinit var textViewDate: TextView

    private val db = FirebaseFirestore.getInstance()  // Firestore instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_post_re)

        // Initialize UI components
        buttonBack = findViewById(R.id.buttonBack)
        buttonDeletePost1 = findViewById(R.id.buttonDeletePost1)

        textViewName = findViewById(R.id.textViewName)
        textViewJob = findViewById(R.id.textViewJob)
        textViewContact = findViewById(R.id.textViewContact)
        textViewDescription = findViewById(R.id.textViewDescription)
        textViewDate = findViewById(R.id.textViewDate)

        // Get the post ID passed via Intent
        val postId = intent.getStringExtra("postId")

        if (postId != null) {
            loadPostData(postId)
        } else {
            Toast.makeText(this, "Post not found", Toast.LENGTH_SHORT).show()
        }

        // Navigate back to the previous activity when the Back button is clicked
        buttonBack.setOnClickListener {
            finish()  // This will close the current activity and return to the previous one
        }

        // Delete the post when the Delete button is clicked
        buttonDeletePost1.setOnClickListener {
            postId?.let {
                deletePost(it)
            } ?: run {
                Toast.makeText(this, "Post not found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to load post data from Firestore
    private fun loadPostData(postId: String) {
        db.collection("donors")  // Assuming posts are stored in the 'donors' collection
            .document(postId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    // Set the TextViews with the post data
                    textViewName.text = "Name: ${document.getString("name")}"
                    textViewJob.text = "Job: ${document.getString("job")}"
                    textViewContact.text = "Contact: ${document.getString("contact")}"
                    textViewDescription.text = "Description: ${document.getString("description")}"
                    textViewDate.text = "Date: ${document.getString("date")}"  // Assuming a 'date' field exists
                } else {
                    Toast.makeText(this, "Post not found", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load post", Toast.LENGTH_SHORT).show()
            }
    }

    // Function to delete a post from Firestore
    private fun deletePost(postId: String) {
        db.collection("donors")  // Assuming posts are stored in the 'donors' collection
            .document(postId)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Post deleted successfully", Toast.LENGTH_SHORT).show()

                // Navigate back to the RecipientActivity after successful deletion
                val intent = Intent(this, RecipientActivity::class.java)
                startActivity(intent)
                finish()  // Close the current activity
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to delete post", Toast.LENGTH_SHORT).show()
            }
    }
}
