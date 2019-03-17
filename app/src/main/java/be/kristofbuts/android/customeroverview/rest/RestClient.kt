package be.kristofbuts.android.customeroverview.rest

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.util.Log
import be.kristofbuts.android.customeroverview.model.Customer
import be.kristofbuts.android.customeroverview.model.Order
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

// define base url
const val BASE_URL = "http://10.0.2.2:3000"

class RestClient(
    private val context: Context // necessary as we access a system service
) {
    /**
     * Get a connection going, which we can use in other methods for calling a specific endpoint
     */
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

    /**
     * Get all customers
     */
    fun getCustomers(): Observable<Array<Customer>> {
        val observable = Observable.create<Array<Customer>> { emitter ->
            try {
                var connection = connect("${BASE_URL}/customers")

                val gson = GsonBuilder()
                    .registerTypeAdapter(GregorianCalendar::class.java, GregorianCalendarDeserialiser()) // custom parsing from string to GregorianCalendar
                    .create()

                // convert json data to array of Customer objects
                val customers = gson
                    .fromJson(InputStreamReader(
                        connection.inputStream),
                        Array<Customer>::class.java
                    )

                // load pictures and add to objects
                customers.forEach {
                    connection = connect("${BASE_URL}/${it.image}")
                    it.imageBitmap = BitmapFactory.decodeStream(connection.inputStream)
                }

                emitter.onNext(customers)
            } catch (e: IOException) {
                Log.i("Exception", e.message)
                emitter.onError(e)
            }
        }

        return observable
    }

    /**
     * Get a single customer, based on his ID
     */
    fun getCustomer(id: Int): Observable<Customer> {
        val observable = Observable.create<Customer> {emitter ->
            try {
                var connection = connect("${BASE_URL}/customers/${id}")

                val gson = GsonBuilder()
                    .registerTypeAdapter(GregorianCalendar::class.java, GregorianCalendarDeserialiser()) // custom parsing from string to GregorianCalendar
                    .create()

                val customer = gson
                    .fromJson(InputStreamReader(
                        connection.inputStream),
                        Customer::class.java
                    )

                // load picture
                connection = connect("${BASE_URL}/${customer.image}")
                customer.imageBitmap = BitmapFactory.decodeStream(connection.inputStream)

                emitter.onNext(customer)
            } catch (e: IOException) {
                Log.i("Exception", e.message)
                emitter.onError(e)
            }
        }

        return observable
    }

    /**
     * Get all orders, never used since we filter on a customer
     * May be used later in extra feature
     */
    fun getOrders(): Observable<Array<Order>> {
        val observable = Observable.create<Array<Order>> { emitter ->
            try {
                val connection = connect("${BASE_URL}/orders")

                val gson = GsonBuilder()
                    .registerTypeAdapter(GregorianCalendar::class.java, GregorianCalendarDeserialiser()) // custom parsing from string to GregorianCalendar
                    .create()

                val orders = gson
                    .fromJson(InputStreamReader(
                        connection.inputStream),
                        Array<Order>::class.java
                    )

                emitter.onNext(orders)
            } catch (e: IOException) {
                Log.i("Exception", e.message)
                emitter.onError(e)
            }
        }

        return observable
    }

    /**
     * Get orders for a specific user, this feature is supported by our web server so it's more efficient.
     */
    fun getOrdersForCustomer(customerId: Int): Observable<Array<Order>> {
        val observable = Observable.create<Array<Order>> { emitter ->
            try {
                val connection = connect("${BASE_URL}/orders?customerId=${customerId}")

                val gson = GsonBuilder()
                    .registerTypeAdapter(GregorianCalendar::class.java, GregorianCalendarDeserialiser()) // custom parsing from string to GregorianCalendar
                    .create()

                val orders = gson
                    .fromJson(InputStreamReader(
                        connection.inputStream),
                        Array<Order>::class.java
                    )

                emitter.onNext(orders)
            } catch (e: IOException) {
                Log.i("Exception", e.message)
                emitter.onError(e)
            }
        }

        return observable
    }

    /**
     * Get a single customer image based on a path to the file on the web server.
     */
    fun getCustomerImage(imgStr: String): Observable<Bitmap> {
        val observable = Observable.create<Bitmap> { emitter ->
            try {
                val connection = connect("${BASE_URL}/${imgStr}")
                val imageBitmap = BitmapFactory.decodeStream(connection.inputStream)

                emitter.onNext(imageBitmap)
            } catch (e: IOException) {
                Log.i("Exception", e.message)
                emitter.onError(e)
            }
        }

        return observable
    }
}