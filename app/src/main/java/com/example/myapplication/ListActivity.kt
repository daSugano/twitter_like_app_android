package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

data class TweetContent(
    val content: String
)
data class Tweets(
    val tweets: MutableList<TweetContent>
)

class ListActivity : AppCompatActivity(), TweetHandlerDelegate {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        TweetHandler(this).get()

        findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }




    class CustomAdapter(private val dataSet: MutableList<String>): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

        class ViewHolder(view: View): RecyclerView.ViewHolder(view)  {
            val cellLabel: TextView

            init {
                // Define click listener for the ViewHolder's View.
                cellLabel = view.findViewById(R.id.cellLabel)
            }
        }


        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            // Create a new view, which defines the UI of the list item
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.list_cell, viewGroup, false)

            return ViewHolder(view)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            viewHolder.cellLabel.text = dataSet[position]
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = dataSet.size

    }

    override fun didFetchData(tweetHandler: TweetHandler, posts: List<TweetContent>) {

        val dataSet = mutableListOf<String>()

        println(posts)
        for (post in posts) {
            dataSet.add(post.content)
        }

        val adapter = CustomAdapter(dataSet)

        val rvList = findViewById<RecyclerView>(R.id.rvList)
        rvList.adapter = adapter
        rvList.layoutManager = LinearLayoutManager(applicationContext)

        adapter.notifyDataSetChanged()
    }
}

