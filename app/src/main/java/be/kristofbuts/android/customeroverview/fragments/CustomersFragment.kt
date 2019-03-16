package be.kristofbuts.android.customeroverview.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import be.kristofbuts.android.customeroverview.R

/**
 * This just exists to be able to use the RecyclerView as part of a landscape UI in OverviewActivity.
 */
class CustomersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customers, container, false)
    }

}
