package be.kristofbuts.android.customeroverview.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import be.kristofbuts.android.customeroverview.R

/**
* This activity doesn't have any logic.
* It is just a static view with some information which is configured in the layout file.
 */
class AboutActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }
}
