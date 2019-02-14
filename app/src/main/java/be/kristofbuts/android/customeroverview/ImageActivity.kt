package be.kristofbuts.android.customeroverview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

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

    }

    private fun setImage(image: Int) {
        this.imgView.setImageDrawable(getDrawable(image))
    }
}
