package com.example.abcd

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var et_name: String
    lateinit var et_location:String
    lateinit var et_email: String
    lateinit var et_phone: String
    lateinit var et_shopName: String
    lateinit var et_areaName: String
    lateinit var et_cityName: String
    lateinit var et_password: String
    lateinit var et_deliveryCharges: String
    lateinit var et_minDeliveryOrder: String
    lateinit var et_conpassword: String
    lateinit var et_adress: String
    lateinit var et_areaId: String
    lateinit var et_cityId: String
    lateinit var btn_register: Button
    lateinit var et_pincode: String
    lateinit var service_Id: String
    lateinit var radio_group: RadioGroup
    lateinit var radio_delivery_group:RadioGroup
    var deliveryStatus:String="No"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Register Yourself"
        auth = FirebaseAuth.getInstance()
        service_Id = "1"
        var HomeBuisnees = "No"
        btn_register = findViewById(R.id.btn_register)
        var spinner = findViewById<Spinner>(R.id.spinner_service)
        val serviceList = arrayOf(
            "Grocery & Essentials",
            "Vegetables & Fruits",
            "Stationary and Books",
            "Electrical & Electronics",
            "Nogozo Specials",
            "Fashion & Personal Care",
            "Dairy Products",
            "Food Outlets & Restaurants"
        )
        spinner.adapter = ArrayAdapter<String>(
            this@MainActivity,
            android.R.layout.simple_list_item_1,
            serviceList
        )
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                service_Id = "none"
            }

            override fun onItemSelected(
                p0: AdapterView<*>?,
                p1: android.view.View?,
                p2: Int,
                p3: Long
            ) {
                if(serviceList[p2].equals("Grocery & Essentials"))
                    service_Id = "serviceid"
                else if(serviceList[p2].equals("Vegetables & Fruits"))
                    service_Id = "serviceid1"
                else if(serviceList[p2].equals("Stationary and Books"))
                    service_Id = "serviceid2"
                else if(serviceList[p2].equals("Electrical & Electronics"))
                    service_Id = "serviceid3"
                else if(serviceList[p2].equals("Nogozo Specials"))
                    service_Id = "serviceid4"
                else if(serviceList[p2].equals("Fashion & Personal Care"))
                    service_Id = "serviceid5"
                else if(serviceList[p2].equals("Dairy Products"))
                    service_Id = "serviceid6"
                else if(serviceList[p2].equals("Food Outlets & Restaurants"))
                    service_Id = "serviceid7"
            }

        }
        btn_register.setOnClickListener {
            et_password = findViewById<EditText>(R.id.et_password).text.toString().trim()
            et_conpassword = findViewById<EditText>(R.id.et_Conpassword).text.toString().trim()
            et_name = findViewById<EditText>(R.id.et_name).text.toString().trim()
            et_location=findViewById<EditText>(R.id.et_location).text.toString().trim()
            et_minDeliveryOrder =
                findViewById<EditText>(R.id.et_mindeliveryOrder).text.toString().trim()
            et_deliveryCharges =
                findViewById<EditText>(R.id.et_deliveryCharges).text.toString().trim()
            et_email = findViewById<EditText>(R.id.et_email).text.toString().trim()
            et_adress = findViewById<EditText>(R.id.et_address).text.toString().trim()
            et_phone = findViewById<EditText>(R.id.et_phone).text.toString().trim()
            et_areaName = findViewById<EditText>(R.id.et_areaName).text.toString().trim()
            et_cityName = findViewById<EditText>(R.id.et_cityName).text.toString().trim()
            et_cityId = findViewById<EditText>(R.id.et_cityId).text.toString().trim()
            et_areaId = findViewById<EditText>(R.id.et_areaId).text.toString().trim()
            et_pincode = findViewById<EditText>(R.id.et_pincode).text.toString().trim()
            radio_group = findViewById(R.id.radio_group)

            var id: Int = radio_group.checkedRadioButtonId
            if (id != -1) {
                val radio: RadioButton = findViewById(id)
                HomeBuisnees = radio.text as String
            } else {
                HomeBuisnees = "none"
            }
            radio_delivery_group=findViewById(R.id.radio_delivery_group)
            var id_delivery: Int = radio_delivery_group.checkedRadioButtonId
            if (id_delivery != -1) {
              //  var radio_delivery: RadioButton = findViewById(id)
                val selectedID: Int = radio_delivery_group.getCheckedRadioButtonId()
                if(selectedID==R.id.rb_delivery_Yes) {
                    deliveryStatus = "Delivering"
                }
                else
                {
                    deliveryStatus = "Not Delivering"
                }
            } else {
                deliveryStatus = "None"
            }
            et_shopName =
                findViewById<EditText>(R.id.et_shopName).text.toString().trim()
            if (et_name.isEmpty() || et_shopName.isEmpty() || et_adress.isEmpty() || et_phone.isEmpty() || et_password.isEmpty()) {
                Toast.makeText(
                    this@MainActivity,
                    "The Input field cannot be empty",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (et_password.equals(et_conpassword)) {
                    auth.createUserWithEmailAndPassword(et_email, et_password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Regsitration Succesfull",
                                    Toast.LENGTH_SHORT
                                ).show()
                                var currentUId =
                                    FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
                                val ref = FirebaseDatabase.getInstance().getReference("users")
                                // val shopId = ref!!.push().key!!
                                var ordNum = OrderNumber(et_adress, "1", "OPEN")
                                val shopDetail = ShopDetails(
                                    currentUId,
                                    et_name,
                                    et_shopName,
                                    et_phone,
                                    et_email,
                                    et_adress,
                                    et_areaName,
                                    et_cityName,
                                    et_cityId,
                                    et_areaId,
                                    et_deliveryCharges,
                                    et_minDeliveryOrder,
                                    et_pincode,
                                    HomeBuisnees,
                                    "2",
                                    deliveryStatus,et_location
                                )
                               /** var shopid: String = "1",
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
                                var deliveryminOrder: String = "none",
                                var pincode: String = "none",
                                var homebusiness: String = "no",
                                var profilelevel: String = "none",
                                var deliverystatus: String = "Not Delivering"**/
                                ref.child("shop").child(currentUId).setValue(ordNum)
                                ref.child("shop").child(currentUId).child("profile")
                                    .setValue(shopDetail)
                                val hashMapstats: HashMap<String, String> =
                                    HashMap<String, String>()
                                hashMapstats.put("total_amount", "0")
                                hashMapstats.put("total_orders", "0")
                                val hashMapstatus: HashMap<String, String> =
                                    HashMap<String, String>()
                                hashMapstatus.put("status", "Close")
                                ref.child("shop").child(currentUId).child("stats").child("202010")
                                    .setValue(hashMapstats)
                                val shopsIdRef =
                                    FirebaseDatabase.getInstance().getReference("shops")
                                val shopid = ShopId(
                                    et_areaId,
                                    deliveryStatus,
                                    et_shopName
                                )//(var areaId:String,var deliverystatus:String="false",var serviceId:String,var shopName:String)
                                shopsIdRef.child("ucitycode").child(currentUId).setValue(shopid)
                                    .addOnCompleteListener {
                                        Toast.makeText(
                                            this@MainActivity,
                                            "shopId details Added",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                val hashMap: HashMap<String, Boolean> = HashMap<String, Boolean>()
                                hashMap.put(service_Id,true)
                                shopsIdRef.child("ucitycode").child(currentUId).child("serviceid")
                                    .setValue(hashMap)
                            } else {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Regsitration Error",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    //ShopDetails(var shopId:String,var customerName:String,var shopName:String,var phone:String,var address:String,var areaName:String,var cityName:String)

                } else {
                    Toast.makeText(this@MainActivity, "Password Doesnot Match", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

}

