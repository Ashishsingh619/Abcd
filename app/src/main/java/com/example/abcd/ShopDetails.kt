package com.example.abcd

data class ShopDetails(
    var shopid: String = "1",
    var name: String,
    var shopname: String,
    var phone: String,
    var email:String,
    var address: String = "none",
    var areaname: String = "none",
    var cityname: String = "none",
    var cityid: String = "none",
    var areaid: String = "none",
    var deliverycharges: String = "none",
    var deliveryminorder: String = "none",
    var pincode: String = "none",
    var homebusiness: String = "no",
    var profilelevel: String = "none",
    var deliverystatus: String = "Not Delivering",
    var location:String="NOGOZO"
) {
}