package com.example.androidproject.Update

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.androidproject.MainActivity
import com.example.androidproject.R
import com.example.androidproject.User
import com.example.androidproject.UserApiService
import kotlinx.android.synthetic.main.activity_update.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class UpdateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        //retrofit object
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.1.25:8082/api/students/")
            .build()

        val studentApi = retrofit.create(UserApiService::class.java)

        /***
         * getStringExtra to get extra variable values passed into RecyclerView click function
         */
        var strUserId: String = intent.getStringExtra("userId").toString()
        var name: String = intent.getStringExtra("name").toString()
        var university: String = intent.getStringExtra("university").toString()
        var term: String = intent.getStringExtra("term").toString()
        var isAdd: Boolean = strUserId.equals("null");
        if (isAdd) {
            textId.setEnabled(true)
            textId.visibility= View.INVISIBLE
            delete_button.visibility = View.INVISIBLE
            submit.setText("Add")
        }else{
            textId.setText(strUserId)
            textId.setEnabled(false)
            submit.setText("Update")
        }
        if (!name.equals("null")) {
            textName.setText(name)
        }
        if (!university.equals("null")) {
            textUniversity.setText(university)
        }
        if (!term.equals("null")) {
            textTerm.setText(term)
        }


        /***
         * Update API call
         */
        var inputId = textId.text
        var inputName = textName.text
        var inputTerm = textTerm.text
        var inputUniversity = textUniversity.text

        submit.setOnClickListener{
            update(studentApi,"$inputId", "$inputName", "$inputTerm", "$inputUniversity", isAdd)
            //Toast.makeText(baseContext,"PUT called", Toast.LENGTH_LONG).show();
            println("PUT performed")
            Thread.sleep(3000)

            if(inputName.toString().equals("")) {
                println("name can't be empty")
                Toast.makeText(this,"Name can't be empty", Toast.LENGTH_LONG).show()
            } else if(inputUniversity.toString().equals("")) {
            println("University can't be empty")
            Toast.makeText(this,"University can't be empty", Toast.LENGTH_LONG).show()
            } else if(inputTerm.toString().equals("")) {
                println("name can't be empty")
                Toast.makeText(this,"Term can't be empty", Toast.LENGTH_LONG).show()
            } else{
                val navigateOtherActivity = Intent(this, MainActivity::class.java)
                startActivity(navigateOtherActivity)
            }
        }

        delete_button.setOnClickListener{
            delete(studentApi,"$inputId")
            println("Delete performed")
            navigateToRecyclerView()
        }
    }

    /***
     * Update and Add API call based on the button state Add/Update
     */
    private fun update(studentApi: UserApiService, id: String, name: String,  term: String, university: String, isAdd: Boolean) {
        val pcall: Call<Void>
        if (isAdd) {
            pcall = studentApi.set(name, term, university);
        } else {
            pcall = studentApi.update(id, name, term, university);
        }
        pcall.enqueue(object : Callback<Void>
        {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                println("PUT Failed"+t.message);
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                println("PUT Pass");
            }

        });
    }

    /***
     * delete API call
     */
    private fun delete(studentApi: UserApiService, id: String) {
        val dcall = studentApi.delete(id);
        dcall.enqueue(object : Callback<Void>
        {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                println("POST Failed"+t.message);
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                println("response delete");
            }

        });
    }

    /***
     * To hide soft keyboard
     */
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        currentFocus?.let { currFocus ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currFocus.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    fun navigateToRecyclerView(){
        val navigateOtherActivity = Intent(this, MainActivity::class.java)
        startActivity(navigateOtherActivity)
    }


}
