package be.kristofbuts.android.customeroverview.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import be.kristofbuts.android.customeroverview.R

class ImageActivity : AppCompatActivity() {

    private lateinit var imgView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        this.initialiseViews()
        this.addEventHandlers()
        this.setImage(intent.getIntExtra("image", 0))
    }

    private fun initialiseViews() {
        this.imgView = findViewById(R.id.imageView)
    }

    private fun addEventHandlers() {
        // nothing to do here (yet)
    }

    private fun setImage(image: Int) {
        this.imgView.setImageDrawable(getDrawable(image))
    }
}
