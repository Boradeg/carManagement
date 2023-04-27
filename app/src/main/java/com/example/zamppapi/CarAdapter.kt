package com.example.zamppapi

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.Toast

import androidx.recyclerview.widget.RecyclerView
import com.example.zamppapi.databinding.CarDesignBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CarAdapter(var context: Context, var carData:ArrayList<CarPojo>): RecyclerView.Adapter<CarAdapter.ViewHolder>()
{
    private lateinit var mlistener:onItemClickListener
    interface onItemClickListener
    {
    fun onItemClick(position:Int)
   }
    fun setOnItemClickListener(listener: onItemClickListener){
        mlistener=listener
    }
    class ViewHolder(var binding: CarDesignBinding,listener:onItemClickListener) : RecyclerView.ViewHolder(binding.root)
    {
       init{
           itemView.setOnClickListener {
               listener.onItemClick(adapterPosition)
           }
       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CarDesignBinding.inflate(LayoutInflater.from(context), parent, false),mlistener)
    }

    override fun getItemCount(): Int {
        return carData.size
    }


    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.binding.carName.text = carData.get(position).name
        holder.binding.carOwnName.text = carData.get(position).ownName

        Picasso.get().load("https://source.unsplash.com/random/car/").into(holder.binding.carImage);
//        holder.binding.carEdit.setOnClickListener {
//            startActivity(Intent(this, MainActivity3::class.java))
//        }

//holder.itemView.setOnClickListener {
//   detailedIntrface.getDetail(holder.binding.carName.toString(),holder.binding.carOwnName.toString())
//}

        holder.itemView.setOnLongClickListener(object : OnLongClickListener {

            override fun onLongClick(v: View?): Boolean {

                val builder = AlertDialog.Builder(context)
                builder.setTitle(R.string.dialogTitle)
                builder.setMessage(R.string.dialogMessage)
                builder.setIcon(android.R.drawable.ic_dialog_alert)
                builder.setPositiveButton("Delete")
                { dialogInterface, which ->
                    var apiEndPoint: ApiEndPoint
                    var retrofit = ServerConnection.getConnection()
                    apiEndPoint = retrofit!!.create(ApiEndPoint::class.java)
                    apiEndPoint.deleteCarData(carData.get(position).id!!)!!
                        .enqueue(object : Callback<ApiDeleteResponse?> {
                            override fun onResponse(
                                call: Call<ApiDeleteResponse?>,
                                response: Response<ApiDeleteResponse?>
                            ) {
                                carData.removeAt(position)
                                notifyDataSetChanged()
                                Toast.makeText(
                                    context,
                                    "car deleted successfully",
                                    Toast.LENGTH_SHORT
                                ).show()


                                //  Toast.makeText(context,response.body()?.status,Toast.LENGTH_SHORT).show()
                            }

                            override fun onFailure(call: Call<ApiDeleteResponse?>, t: Throwable) {


                                Toast.makeText(
                                    context,
                                    "ONFAILURE car deleted successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })

                }
                builder.setNeutralButton("Cancel") { dialogInterface, which ->
                    Toast.makeText(context, "clicked cancel\n operation cancel", Toast.LENGTH_LONG)
                        .show()
                }
                builder.setNegativeButton("Add") { dialogInterface, which ->
                   var intent= Intent(context,MainActivity2::class.java)
                    context.startActivity(intent)
                    Toast.makeText(context, "Update", Toast.LENGTH_SHORT).show()
                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(true)
                alertDialog.show()
                return true
            }
        })

    }


}




//performing positive action

//performing cancel action

//performing negative action

// Create the AlertDialog

