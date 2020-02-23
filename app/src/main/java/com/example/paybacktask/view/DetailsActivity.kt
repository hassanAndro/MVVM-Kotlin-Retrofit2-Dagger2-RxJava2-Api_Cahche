package com.example.paybacktask.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.paybacktask.R
import com.example.paybacktask.databinding.ActivityDetailsBinding
import com.example.paybacktask.model.Data
import android.view.View


class DetailsActivity : AppCompatActivity() {

    private lateinit var mData: Data
    private lateinit var mainBinding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        mData = intent.getParcelableExtra("data")
        mainBinding.data = mData

    }

    fun onClick(view: View) {
        finish()
    }
}
