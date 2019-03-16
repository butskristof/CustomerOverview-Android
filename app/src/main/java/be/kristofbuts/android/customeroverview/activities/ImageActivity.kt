package be.kristofbuts.android.customeroverview.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import be.kristofbuts.android.customeroverview.R
import be.kristofbuts.android.customeroverview.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * This activity will show the customer's image in a screen-filling imageView.
 */
class ImageActivity : AppCompatActivity() {

    private lateinit var imgView: ImageView
    private var imgUrlStr: String = "" // path to user image, passed through intent
        set(value) { // when changed, call method for loading the image
            field = value
            loadImage()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        this.initialiseViews()
        this.addEventHandlers()

        // path is passed in through the intent
        imgUrlStr = intent.getStringExtra(getString(R.string.CUSTOMER_IMG_URL))

        // will call the web server to get the image
        this.loadImage()
    }

    private fun initialiseViews() {
        // just the image view
        this.imgView = findViewById(R.id.imageView)
    }

    private fun addEventHandlers() {
        // nothing to do here
    }

    /**
     * This method will call start a REST call to get just the customer's image as a Bitmap object.
     * When returned, we'll update the image view.
     */
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
