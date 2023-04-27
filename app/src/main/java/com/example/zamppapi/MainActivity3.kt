package com.example.zamppapi

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.zamppapi.databinding.ActivityMain3Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity3 : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityMain3Binding
    private lateinit var text: String
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)
//        val fuelType = resources.getStringArray(R.array.FuelType)
//
//        if (binding.spinner != null) {
//            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, fuelType)
//            binding.spinner.adapter = adapter
//        }
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.FuelType,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
       binding. spinner.adapter = adapter
       binding.spinner.onItemSelectedListener =this
            val bundle: Bundle? = intent.extras
            binding.carNameText2.text = bundle!!.getString("carName")
            binding.carName.text = bundle!!.getString("carName")
            binding.carOwnerName2.text = bundle!!.getString("carOwnerName")
            binding.carNumber2.text = bundle!!.getString("carNumber")
            binding.carFuelType2.text = bundle!!.getString("carFuelType")
            binding.carModal2.text = bundle!!.getString("modal")
            var temp = bundle!!.getString("isActive")?.toInt()
            if (temp == 1) {
                binding.isActive2.text = "yes"
            } else {
                binding.isActive2.text = "no"
            }


            var km = intent.getFloatExtra("coverKm", 3F)
            binding.carKm2.text = km.toString()
//        intent.putExtra("carName",carData.get(position).name)
//        intent.putExtra("id",carData.get(position).id)
//        intent.putExtra("carOwnerName",carData.get(position).ownName)
//        intent.putExtra("carNumber",carData.get(position).number)
//        intent.putExtra("carFuelType",carData.get(position).fuelType)
//        intent.putExtra("isActive",carData.get(position).isActive)
//        intent.putExtra("coverKm",carData.get(position).coverKM)
//        intent.putExtra("modal",carData.get(position).model)

            val id = intent.getIntExtra("id", 0)
            binding.carEdit.setOnClickListener {


//            binding.carEdit.visibility=View.GONE
//            binding.carName.visibility=View.GONE
//            binding.carNameText.visibility=View.GONE
//            binding.carNameText2.visibility=View.GONE
//            binding.carNumber.visibility=View.GONE
//            binding.carNumber2.visibility=View.GONE
//            binding.carOwnerName.visibility=View.GONE
//            binding.carOwnerName2.visibility=View.GONE
//            binding.isActive.visibility=View.GONE
//            binding.isActive2.visibility=View.GONE
//            binding.carImage.visibility=View.GONE
//            binding.carKm.visibility=View.GONE
//            binding.carKm2.visibility=View.GONE
//            binding.carModal.visibility=View.GONE
//            binding.carModal2.visibility=View.GONE
                binding.layout1.visibility = View.GONE
                binding.layout2.visibility = View.VISIBLE

//
//
//            binding.edit1.visibility=View.VISIBLE
//            binding.edit2.visibility=View.VISIBLE
//            binding.updateBtn.visibility=View.VISIBLE
//            binding.edit3.visibility=View.VISIBLE


            }
            binding.updateBtn.setOnClickListener {

                var apiEndPoint: ApiEndPoint
                var retrofit = ServerConnection.getConnection()
                apiEndPoint = retrofit!!.create(ApiEndPoint::class.java)


                //enquue means press send button in thunderClient

                apiEndPoint.updateCarData(
                    id,

                    binding.editName.text.toString(),
                    binding.editOwnerName.text.toString(),
                    binding.editNumber.text.toString(),
                    binding.editIsActive.text.toString(),
                    binding.carFuelType2.text.toString(),
                    binding.editModel.text.toString(), binding.editKm.text.toString()
                ).enqueue(object : Callback<ApiUpdateResponse?> {
                    override fun onResponse(
                        call: Call<ApiUpdateResponse?>,
                        response: Response<ApiUpdateResponse?>?
                    ) {
                        Toast.makeText(
                            this@MainActivity3,
                            "Car Updated Successfully",
                            Toast.LENGTH_SHORT
                        ).show()


                    }

                    override fun onFailure(call: Call<ApiUpdateResponse?>, t: Throwable) {
                        Toast.makeText(this@MainActivity3, "onFailed", Toast.LENGTH_SHORT).show()
                    }

                })
            }


        }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
         text = parent?.getItemAtPosition(position).toString()
        binding.carFuelType2.text = text
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}