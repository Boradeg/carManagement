package com.example.zamppapi


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.zamppapi.databinding.ActivityMain2Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity2 : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var text: String
    private lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.FuelType,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding. spinner.adapter = adapter
        binding.spinner.onItemSelectedListener =this
        binding.submitBtn.setOnClickListener {

            var apiEndPoint: ApiEndPoint
            var retrofit = ServerConnection.getConnection()
            apiEndPoint = retrofit!!.create(ApiEndPoint::class.java)

            //enquue means press send button in thunderClient
            apiEndPoint.addCarData(
                binding.editName.text.toString(),
                binding.editOwnerName.text.toString(),
                binding.editNumber.text.toString(),
                binding.editIsActive.text.toString(),
                text.toString(),
                binding.editModel.text.toString()
                ,binding.editKm.text.toString()
            )!!.enqueue(object : Callback<ApiAddResponse?> {

                    override fun onFailure(call: Call<ApiAddResponse?>, t: Throwable) {
                        Toast.makeText(this@MainActivity2, "on failure car added", Toast.LENGTH_SHORT).show()

                    }

                override fun onResponse(
                    call: Call<ApiAddResponse?>, response: Response<ApiAddResponse?>
                ) {
                    Toast.makeText(
                        this@MainActivity2,
                        response.body()?.status,
                        Toast.LENGTH_SHORT
                    ).show()
                    Toast.makeText(this@MainActivity2, "car added", Toast.LENGTH_SHORT).show()
                }
                })


            startActivity(Intent(this@MainActivity2, MainActivity::class.java))
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        text = parent?.getItemAtPosition(position).toString()


    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}
