package be.kristofbuts.android.customeroverview.activities

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.kristofbuts.android.customeroverview.R
import be.kristofbuts.android.customeroverview.adapters.CustomerAdapter
import be.kristofbuts.android.customeroverview.fragments.CustomerDetailFragment

const val CUSTOMER_INDEX: String = "CUSTOMER_INDEX"

class OverviewActivity :
    AppCompatActivity(),
    CustomerAdapter.CustomerSelectionListener {

    private lateinit var rvCustomers: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        this.initialiseViews()
    }

    private fun initialiseViews() {
        this.rvCustomers = findViewById(R.id.rvCustomers)
        this.rvCustomers
            .apply {
                layoutManager = LinearLayoutManager(this@OverviewActivity) // ctx is current activity
                // adapter requires context and listener
                adapter = CustomerAdapter(this@OverviewActivity, this@OverviewActivity)
            }
    }

    // add menu in upper left
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null && item.itemId == R.id.about) {
            // start about activity
            val intent = Intent(applicationContext, AboutActivity::class.java)
            startActivity(intent)
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    override fun onCustomerSelected(pos: Int) {
        // start intent or update fragment
        /*
        Wanneer een item uit de lijst geselecteerd wordt in portrait-oriëntatie verschijnt de detail-informatie
        van dit item in een aparte Activity. Je kan hiervoor de activity uit de opdracht van vorige week hergebruiken.
        Wanneer het toestel in landscape-oriëntatie staat, dan zou de lijst en de detail-informatie naast elkaar
        getoond moeten worden: je krijgt links de lijst met items, en rechts de detail-informatie van het item.
        Maak gebruik van Fragments om dit te realiseren.
         */

        val orientation = resources.configuration.orientation

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            // move to new activity and pass in index
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(CUSTOMER_INDEX, pos)
            startActivity(intent)
        } else {
            // get fragment and update to new customer
            val fragment = supportFragmentManager
                .findFragmentById(R.id.customerDetailFragment) as CustomerDetailFragment
            // this triggers the update
            fragment.setCustomerIndex(pos)
        }
    }

}
