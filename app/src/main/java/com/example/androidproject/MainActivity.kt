package com.example.androidproject

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidproject.Update.UpdateActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_update.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.user_row.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/***
 * Followed tutorials from
 * https://www.youtube.com/c/DanielMalone/videos
 */
class MainActivity : AppCompatActivity() , UsersAdapter.OnItemClickListener {

    //created this 2****
    //private val adapter = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.1.25:8082/api/students/")
            .build()

        val studentApi = retrofit.create(UserApiService::class.java)

    //created this 2****
        //recyclerView.adapter = adapter

        /***
         * show data in recycler view using get api
         */
        studentApi.getAll().enqueue(object: Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                d("ApiCall:calledAndFailed", "onFailure")
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                showData(response.body()!!)
                d("ApiCall:calledAndPassed", "onResponse : ${response.body()!![0].name}")
            }

        })

        fabAdd.setOnClickListener(){
            val navigateOtherActivity = Intent(this, UpdateActivity::class.java)
            startActivity(navigateOtherActivity)
        }

    }

    private fun showData(users: List<User>) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = UsersAdapter( users, listener = this@MainActivity) }

    }

    //created this 2****
    override fun onItemClick(position: Int , userId:String, name:String, university:String, term:String) {
        Toast.makeText(this, "Item $position clicked & userId  $userId", Toast.LENGTH_LONG).show()

        val navigateOtherActivity = Intent(this, UpdateActivity::class.java)
        navigateOtherActivity.putExtra("userId",userId)
        navigateOtherActivity.putExtra("name",name)
        navigateOtherActivity.putExtra("university",university)
        navigateOtherActivity.putExtra("term",term)
        startActivity(navigateOtherActivity)
    }




}
