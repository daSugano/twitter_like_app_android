package com.example.myapplication

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.google.gson.Gson

interface TweetHandlerDelegate {
    fun didFetchData(tweetHandler: TweetHandler, posts: List<TweetContent>)
}


class TweetHandler(val delegate: TweetHandlerDelegate?) {

    fun get() {

        val headers = hashMapOf(
            "Content-Type" to "application/json"
        )

        Fuel.get("http://10.0.2.2:50010/init_posts").header(headers).responseJson { _, response, result ->
            when (result) {
                is Result.Success -> {
                    val res = result.get().obj()
                    val posts = res.getJSONArray("posts")
                    val postsAsList = mutableListOf<TweetContent>()
                    for (i in 0 until posts.length()) {
                        val post = Gson().fromJson(posts[i].toString(), TweetContent::class.java)
                        postsAsList.add(post)
                    }
                    delegate?.didFetchData(this, postsAsList)
                }
                is Result.Failure -> {
                    println("取得に失敗しました")
                }
            }

        }
    }

    fun post(contentTxt: String) {
        val headers = hashMapOf(
            "Content-Type" to "application/json"
        )

        Fuel.post("http://10.0.2.2:50010/post").header(headers).body(
            Gson().toJson(hashMapOf("content" to contentTxt))
        ).responseJson { _, response, result ->
            when (result) {
                is Result.Success -> {
                    println(response.data)
                }

                is Result.Failure -> {
                    println("登録失敗")
                }
            }
        }
    }
}