package com.example.apicallingvolley

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), VolleyInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_get.setOnClickListener {
            VolleyRequest.getInstance(this@MainActivity, this@MainActivity)
                .getRequest("https://jsonplaceholder.typicode.com/posts/todo/1")
        }

        btn_post.setOnClickListener {
            VolleyRequest.getInstance(this@MainActivity, this@MainActivity)
                .postRequest("https://jsonplaceholder.typicode.com/posts/posts")
        }

        btn_put.setOnClickListener {
            VolleyRequest.getInstance(this@MainActivity, this@MainActivity)
                .putRequest("https://jsonplaceholder.typicode.com/posts/posts/1")
        }

        btn_patch.setOnClickListener {
            VolleyRequest.getInstance(this@MainActivity, this@MainActivity)
                .patchRequest("https://jsonplaceholder.typicode.com/posts/posts/1")
        }

        btn_delete.setOnClickListener {
            VolleyRequest.getInstance(this@MainActivity, this@MainActivity)
                .deleteRequest("https://jsonplaceholder.typicode.com/posts/posts/1")
        }

        btn_image_loader.setOnClickListener {
            image_view.setImageUrl("", VolleyRequest.getInstance(this@MainActivity).imageLoader)
        }
    }

    override fun onResponse(response: String) {
        Toast.makeText(this@MainActivity, "" + response, Toast.LENGTH_SHORT).show()
    }
}