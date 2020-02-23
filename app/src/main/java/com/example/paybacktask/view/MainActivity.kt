package com.example.paybacktask.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paybacktask.R
import com.example.paybacktask.adapter.DataAdapter
import com.example.paybacktask.di.DaggerApiComponent
import com.example.paybacktask.model.Data
import com.example.paybacktask.viewmodel.DataViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import com.example.paybacktask.databinding.ActivityMainBinding
import androidx.appcompat.widget.SearchView


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mDataAdapter: DataAdapter
    private lateinit var dataViewModel: DataViewModel
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        DaggerApiComponent.create().inject(this)
        dataViewModel = ViewModelProviders.of(this).get(DataViewModel::class.java)

        main_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mDataAdapter
        }

        observeLiveData()

        activityMainBinding.searchView.isActivated = true
        activityMainBinding.searchView.onActionViewExpanded()
        activityMainBinding.searchView.clearFocus()

        activityMainBinding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty()) {
                    dataViewModel.fetchResults(newText)
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    activityMainBinding.searchView.clearFocus()
                    dataViewModel.fetchResults(query)
                }
                return false
            }

        })
    }

    private fun observeLiveData() {
        observeInProgress()
        observeIsError()
        observeDataList()
    }

    private fun observeDataList() {
        val dataListLD: LiveData<List<Data>> = dataViewModel.dataListMLD
        dataListLD.observe(this, Observer { alldatas ->
            alldatas.let {
                main_recycler_view.visibility = View.VISIBLE
                mDataAdapter.setUpdatas(it)
            }
        })
    }

    private fun observeInProgress() {
        val inProgressLD: LiveData<Boolean> = dataViewModel.inProgressMLD
        data_fetch_error.text = getString(R.string.fetching_data)
        inProgressLD.observe(this, Observer { isLoading ->
            isLoading.let {
                if (it) {
                    data_fetch_error.visibility = View.GONE
                    data_fetch_progress.visibility = View.VISIBLE
                } else {
                    data_fetch_error.text = getString(R.string.no_fetching_data)
                    data_fetch_progress.visibility = View.GONE
                }
            }
        })
    }

    private fun observeIsError() {
        val isErrorLD: LiveData<Boolean> = dataViewModel.isErrorMLD
        isErrorLD.observe(this, Observer { isError ->
            isError.let {
                data_fetch_error.text = getString(R.string.error_fetching_data)
                data_fetch_error.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
    }
}
