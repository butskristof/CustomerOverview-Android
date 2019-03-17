package be.kristofbuts.android.customeroverview.activities

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.kristofbuts.android.customeroverview.R
import be.kristofbuts.android.customeroverview.adapters.CustomerAdapter
import be.kristofbuts.android.customeroverview.fragments.CustomerDetailFragment
import be.kristofbuts.android.customeroverview.rest.RestClient
import com.google.android.material.navigation.NavigationView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * This is the new opening screen. It will contains a list of customers with their most vital information and a picture.
 */
class OverviewActivity :
    AppCompatActivity(),
    CustomerAdapter.CustomerSelectionListener {

    private lateinit var landscapeFragment: CustomerDetailFragment // this doesn't get initialised if we're in portrait

    private lateinit var rvCustomers: RecyclerView

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        this.initialiseViews()
        this.addEventHandlers()

        this.loadCustomerData()
    }

    private fun initialiseViews() {
//        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        this.drawerLayout = findViewById(R.id.drawer_layout)

        this.rvCustomers = findViewById(R.id.rvCustomers)
        this.rvCustomers
            .apply {
                layoutManager = LinearLayoutManager(this@OverviewActivity) // ctx is current activity
                // adapter requires context and listener
                adapter = CustomerAdapter(this@OverviewActivity, this@OverviewActivity)
            }

        // fragment is directly in this activity if user is in landscape
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            this.landscapeFragment = supportFragmentManager
                .findFragmentById(R.id.customerDetailFragment) as CustomerDetailFragment
        }
    }

    private fun addEventHandlers() {
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.setChecked(true)
            drawerLayout.closeDrawers()

            // add code here

            true
        }

    }

    // add menu in upper right
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
        } else if (item?.itemId == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawers()
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    /**
     * We react to a user selecting a customer in the list.
     * Depending on the orientation we either start a new activity (MainActivity), or show the selected customer's
     * data in the Fragment.
     */
    override fun onCustomerSelected(pos: Int) {
        val orientation = resources.configuration.orientation

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            // pass in index so the activity knows which customer to display
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(getString(R.string.CUSTOMER_INDEX), pos)
            startActivity(intent)
        } else {
            // setting the index will trigger an update in the fragment
            this.landscapeFragment.setCustomerIndex(pos)
        }
    }

    /**
     * This will make an asynchronous API call to get the customers' data, which we then pass on to the RecylcerView
     */
    private fun loadCustomerData() {
        RestClient(this)
            .getCustomers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                (rvCustomers.adapter as CustomerAdapter).customers = it
            }, {
                Toast.makeText(
                    this@OverviewActivity,
                    it.message,
                    Toast.LENGTH_LONG)
                    .show()
            })
    }
}
