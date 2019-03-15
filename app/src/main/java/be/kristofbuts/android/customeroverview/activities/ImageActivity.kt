package be.kristofbuts.android.customeroverview.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import be.kristofbuts.android.customeroverview.R
import be.kristofbuts.android.customeroverview.model.Customer
import be.kristofbuts.android.customeroverview.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ImageActivity : AppCompatActivity() {

    private lateinit var imgView: ImageView
    private var index: Int = 0
    var customers: Array<Customer> = arrayOf()
        set(value) {
            field = value
            setImage()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        this.initialiseViews()
        this.addEventHandlers()

        // when coming from overview, customer id is passed in, otherwise start at first element
        index = intent.getIntExtra(CUSTOMER_INDEX, 0)

        this.loadData()
    }

    private fun initialiseViews() {
        this.imgView = findViewById(R.id.imageView)
    }

    private fun addEventHandlers() {
        // nothing to do here (yet)
    }

    private fun loadData() {
        RestClient(this)
            .getCustomers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                customers = it
            }, {
                Toast.makeText(
                    this@ImageActivity,
                    it.message,
                    Toast.LENGTH_LONG)
                    .show()
            })
    }

    private fun setImage() {
        this.imgView.setImageBitmap(customers[index].imageBitmap)
    }
}
