package com.example.paybacktask.di

import com.example.paybacktask.service.NetworkService
import com.example.paybacktask.view.MainActivity
import com.example.paybacktask.viewmodel.DataViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(networkService: NetworkService)

    fun inject(dataViewModel: DataViewModel)

    fun inject(mainActivity: MainActivity)
}