package com.example.paybacktask.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.paybacktask.di.DaggerApiComponent
import com.example.paybacktask.model.Data
import com.example.paybacktask.service.NetworkService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DataViewModel : ViewModel() {

    @Inject
    lateinit var networkService: NetworkService
    @Inject
    lateinit var compositeDisposable: CompositeDisposable
    @Inject
    lateinit var dataListMLD: MutableLiveData<List<Data>>
    @Inject
    lateinit var inProgressMLD: MutableLiveData<Boolean>
    @Inject
    lateinit var isErrorMLD: MutableLiveData<Boolean>

    init {
        DaggerApiComponent.create().inject(this)
        fetchResults("fruits")
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

     fun fetchResults(query: String) {
        compositeDisposable.add( //API call get stored in compositeDisposable
            networkService.fetchData(query) //Makes the call to the endpoint
                .subscribeOn(Schedulers.io()) //Subscribes on a background thread, which is Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread()) //Displays the result on the main thread (UI thread)
                .map { it.hits } //Takes the list of data in SearchResult pass it on to the next operator
                .subscribeWith(createDataObserver()) //The glue that connects networkService.fetchdata() with createdataObserver()
        )
    }

     fun createDataObserver(): DisposableSingleObserver<List<Data>> {
        return object : DisposableSingleObserver<List<Data>>() {

            override fun onSuccess(data: List<Data>) {
                inProgressMLD.value = true
                isErrorMLD.value = false
                dataListMLD.value = data
                inProgressMLD.value = false
            }

            override fun onError(e: Throwable) {
                inProgressMLD.value = true
                isErrorMLD.value = true
                Log.e("onError()", "Error: ${e.message}")
                inProgressMLD.value = false
            }
        }
    }
}
