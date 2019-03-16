package be.kristofbuts.android.customeroverview.activities

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.kristofbuts.android.customeroverview.R
import be.kristofbuts.android.customeroverview.adapters.CustomerAdapter
import be.kristofbuts.android.customeroverview.fragments.CustomerDetailFragment
import be.kristofbuts.android.customeroverview.model.Customer
import be.kristofbuts.android.customeroverview.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

const val CUSTOMER_INDEX: String = "CUSTOMER_INDEX"
const val CUSTOMER_IMG_URL: String = "CUSTOMER_IMG_URL"
const val CUSTOMER_NAME: String = "CUSTOMER_NAME"

class OverviewActivity :
    AppCompatActivity(),
    CustomerAdapter.CustomerSelectionListener {
    private lateinit var landscapeFragment: CustomerDetailFragment

    private lateinit var rvCustomers: RecyclerView
//    private var customers: Array<Customer> = arrayOf()

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

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            this.landscapeFragment = supportFragmentManager
                .findFragmentById(R.id.customerDetailFragment) as CustomerDetailFragment
        }

        RestClient(this)
            .getCustomers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                (rvCustomers.adapter as CustomerAdapter).customers = it
//                customers = it
            }, {
                Toast.makeText(
                    this@OverviewActivity,
                    it.message,
                    Toast.LENGTH_LONG)
                    .show()
            })
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
            // this triggers the update
//            this.landscapeFragment?.setCustomerIndex(pos)
            this.landscapeFragment.setCustomerIndex(pos)
        }
    }

}
