package com.example.demexam.model

import java.net.IDN

data class User(
    var id: String,
    var email: String,
    var firstname: String,
    var lastName: String,
    var nickName: String,
    var image: String,
    var city: String
)
