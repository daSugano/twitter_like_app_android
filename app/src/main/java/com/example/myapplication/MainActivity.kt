package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts


class MainActivity : AppCompatActivity() {

    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val message = intent?.getStringExtra("message")

            val input = findViewById<EditText>(R.id.messageInput)
            val textView = findViewById<TextView>(R.id.textView)
            textView.text = message
            message?.let {
                input.setText(it, TextView.BufferType.EDITABLE)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val input = findViewById<EditText>(R.id.messageInput)
        val textView = findViewById<TextView>(R.id.textView)

        val button = findViewById<Button>(R.id.submitButton)
        button.setOnClickListener {
            Log.i("MainActivity", "button clicked")
            textView.text = input.text

            // To SubActivity (intent)
            val intent = Intent(applicationContext, SubActivity::class.java)

            intent.putExtra("message", input.text.toString())

            startForResult.launch(intent)
        }

        // open cellList
        findViewById<Button>(R.id.buttonOpenList).setOnClickListener {
            val input = findViewById<EditText>(R.id.messageInput)

            TweetHandler(null).post(input.text.toString())

            val intent = Intent(applicationContext, ListActivity::class.java)
            startActivity(intent)
        }
    }
}