package com.example.zamppapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zamppapi.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    lateinit var carData:ArrayList<CarPojo>
    lateinit var carAdapter:CarAdapter
    lateinit var apiEndPoint: ApiEndPoint

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.rv.layoutManager=LinearLayoutManager(this)
//        var arrayList= ArrayList<CarPojo>()
//        arrayList.add(CarPojo(1,"audi","s1","gy657","gokul","petrol",1989f,false))
//        arrayList.add(CarPojo(2,"audi","s1","gy657","gokul","petrol",1989f,false))
//        arrayList.add(CarPojo(3,"audi","s1","gy657","gokul","petrol",1989f,false))
//        arrayList.add(CarPojo(4,"audi","s1","gy657","gokul","petrol",1989f,false))
//        arrayList.add(CarPojo(5,"audi","s1","gy657","gokul","petrol",1989f,false))
//        arrayList.add(CarPojo(6,"audi","s1","gy657","gokul","petrol",1989f,false))
//        arrayList.add(CarPojo(7,"audi","s1","gy657","gokul","petrol",1989f,false))
//         binding.rv.adapter=CarAdapter(this,arrayList)
        binding.rv.layoutManager=LinearLayoutManager(this)
        carData=ArrayList()
        carAdapter= CarAdapter(this,carData)

        carAdapter.setOnItemClickListener(object :CarAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(this@MainActivity, "item ${carData.get(position).id}", Toast.LENGTH_SHORT).show()
                var intent=Intent(this@MainActivity,MainActivity3::class.java)
                intent.putExtra("carName",carData.get(position).name)
                intent.putExtra("id",carData.get(position).id)
                intent.putExtra("carOwnerName",carData.get(position).ownName)
                intent.putExtra("carNumber",carData.get(position).number)
                intent.putExtra("carFuelType",carData.get(position).fuelType)
                intent.putExtra("isActive",carData.get(position).isActive)
                intent.putExtra("coverKm",carData.get(position).coverKm)
                intent.putExtra("modal",carData.get(position).model)
                startActivity(intent)
            }

        })
        binding.rv.adapter=carAdapter

        initBlock()
        getData()
        binding.btnAddCar.setOnClickListener {
            startActivity(Intent(this,MainActivity2::class.java))
        }



    }

    private fun initBlock() {
        var retrofit=ServerConnection.getConnection()
        if (retrofit != null) {
            apiEndPoint=retrofit.create(ApiEndPoint::class.java)
        }
    }

    private fun getData() {
        //enquue means press send button in thunderClient
        apiEndPoint.getCarsData()!!.enqueue(object : Callback<ApiGetRespon?> {
            override fun onResponse(call: Call<ApiGetRespon?>,
                                    response: Response<ApiGetRespon?>)
            {

               for(i in response.body()?.data!!)
               {
                   carData.add(i)
               }
                carAdapter.notifyDataSetChanged()


            }

            override fun onFailure(call: Call<ApiGetRespon?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failed", Toast.LENGTH_SHORT).show()
            }
        })

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         when (item.itemId) {
            R.id.refresh -> {
          startActivity(Intent(this,MainActivity::class.java))
                true
            }

        }
        return super.onOptionsItemSelected(item)
    }


}