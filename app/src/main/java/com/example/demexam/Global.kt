package com.example.demexam

import com.example.demexam.model.CityModel
import com.example.demexam.model.DepModel
import com.example.demexam.model.User

class Global {
    companion object{
        var base_url = "http://wsk2019.mad.hakta.pro/api/"
        var token: String = ""
        var user = User("", "","","","","","")
        var citys = ArrayList<CityModel>()
        var selectCity = ""
        var depList = ArrayList<DepModel>()
        var name = ""
    }
}