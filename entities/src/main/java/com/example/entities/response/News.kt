package com.example.entities.response

data class News(
        var id: String,
        var title: String,
        var url: String,
        var imageUrl: String,
        var newsSite: String,
        var summary: String,
        var publishedAt: String,
        var updatedAt: String,
        var featured: Boolean,
        var launches: List<Launch>,
        var events: List<Any>
)
