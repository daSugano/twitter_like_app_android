package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class SubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)

        val message = intent.getStringExtra("message")
        Log.i("SubActivity", "receive = $message")

        val subMessageInput = findViewById<EditText>(R.id.SubMessageInput)
        message?.let {
            subMessageInput.setText(it, TextView.BufferType.EDITABLE)
        }

        val button = findViewById<Button>(R.id.subSubmitButton)
        button.setOnClickListener {
            val intent = Intent()
            intent.putExtra("message", "hello world")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}