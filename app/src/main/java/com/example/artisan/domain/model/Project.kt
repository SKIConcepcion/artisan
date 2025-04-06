package com.example.artisan.domain.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class Project(
    val projectId: String = "",
    val title: String = "",
    val owner: String = "",
    val email: String = "",
    val likes: Int = 0,
    val downloads: Int = 0,
    val solo: Boolean = true,
    val public: Boolean = false,
    val collabs: List<String> = emptyList(),

    @ServerTimestamp
    val datecreated: Timestamp? = null,

    @ServerTimestamp
    val datemodified: Timestamp? = null
)

