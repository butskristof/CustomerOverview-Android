package be.kristofbuts.android.customeroverview.rest

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import be.kristofbuts.android.customeroverview.model.Customer
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

// define base url
// TODO switch to emulator
const val BASE_URL: String = "http://192.168.1.206:3000"

class RestClient(
    private val context: Context
) {
    private fun connect(urlString: String): HttpURLConnection {

        val connectionManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val networkInfo = connectionManager.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {
            val url = URL(urlString) // create url object from received string

            val connection = url.openConnection() as HttpURLConnection

            connection.apply {
                connectTimeout = 15000
                readTimeout = 10000
                connect()
            }

            return connection
        }

        throw IOException("Unable to connection to network.")
    }

    fun getCustomers(): Observable<Array<Customer>> {
        val observable = Observable.create<Array<Customer>> { emitter ->
            try {
                var connection = connect("${BASE_URL}/customers")

                val gson = GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .registerTypeAdapter(GregorianCalendar::class.java, GregorianCalendarDeserialiser())
                    .create()

                val customers = gson
                    .fromJson(InputStreamReader(
                        connection.inputStream),
                        Array<Customer>::class.java
                    )

                // load pictures

                emitter.onNext(customers)
            } catch (e: IOException) {
                Log.i("Exception", e.message)
                emitter.onError(e)
            }
        }

        return observable
    }
}