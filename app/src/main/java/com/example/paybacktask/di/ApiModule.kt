package com.example.paybacktask.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.MutableLiveData
import com.example.paybacktask.MyApplication
import com.example.paybacktask.adapter.DataAdapter
import com.example.paybacktask.api_endpoint.SearchApi
import com.example.paybacktask.model.Data
import com.example.paybacktask.service.NetworkService
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit


@Module
class ApiModule {

    val context: Context = MyApplication.applicationContext()

    @Provides
    fun provideSearchResultApi(): SearchApi {

        // Create a cache object
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val httpCacheDirectory = File(context.getCacheDir(), "http-cache")
        val cache = Cache(httpCacheDirectory, cacheSize.toLong())


        // Create the httpClient, configure it
        // with cache, network cache interceptor and logging interceptor
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(offlineInterceptor)
            .addNetworkInterceptor(onlineInterceptor)
            .cache(cache)
            .build()


        return Retrofit.Builder()
            .baseUrl(NetworkService.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .client(httpClient)
            .build()
            .create(SearchApi::class.java)
    }

    @Provides
    fun provideataList(): ArrayList<Data> {
        return ArrayList()
    }

    @Provides
    fun provideDataAdapter(data: ArrayList<Data>): DataAdapter {
        return DataAdapter(data)
    }

    @Provides
    fun provideNetworkService(): NetworkService {
        return NetworkService()
    }

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    fun provideLiveDataListOfdatas(): MutableLiveData<List<Data>> {
        return MutableLiveData()
    }

    @Provides
    fun provideLiveDataBoolean(): MutableLiveData<Boolean> {
        return MutableLiveData()
    }

    var onlineInterceptor: Interceptor = Interceptor { chain ->
        val response = chain.proceed(chain.request())
        val maxAge = 60 // read from cache for 60 seconds even if there is internet connection
        response.newBuilder()
            .header("Cache-Control", "public, max-age=$maxAge")
            .removeHeader("Pragma")
            .build()
    }

    var offlineInterceptor: Interceptor = Interceptor { chain ->
        var request = chain.request()
        if (!hasNetwork(context)!!) {
            val maxStale = 60 * 60 * 24 * 30 // Offline cache available for 30 days
            request = request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .removeHeader("Pragma")
                .build()
        }
        chain.proceed(request)
    }

    fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }
}