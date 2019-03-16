package be.kristofbuts.android.customeroverview.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import be.kristofbuts.android.customeroverview.R
import be.kristofbuts.android.customeroverview.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.customer_list_item.*

class ImageActivity : AppCompatActivity() {

    private lateinit var imgView: ImageView
    private var imgUrlStr: String = ""
    set(value) {
        field = value
        loadImage()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        this.initialiseViews()
        this.addEventHandlers()

        // when coming from overview, customer id is passed in, otherwise start at first element
        imgUrlStr = intent.getStringExtra(CUSTOMER_IMG_URL)

        this.loadImage()
    }

    private fun initialiseViews() {
        this.imgView = findViewById(R.id.imageView)
    }

    private fun addEventHandlers() {
        // nothing to do here (yet)
    }

    private fun loadImage() {
        RestClient(this)
            .getCustomerImage(imgUrlStr)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                this.imgView.setImageBitmap(it)
            }, {
                Toast.makeText(
                    this@ImageActivity,
                    it.message,
                    Toast.LENGTH_LONG)
                    .show()
            })
    }
}
