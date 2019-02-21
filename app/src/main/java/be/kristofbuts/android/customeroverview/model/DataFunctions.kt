package be.kristofbuts.android.customeroverview.model

import be.kristofbuts.android.customeroverview.R
import java.util.*
import kotlin.collections.ArrayList

fun getCustomers(): ArrayList<Customer> {
    val ret: ArrayList<Customer> = ArrayList<Customer>()
    ret.add(Customer(0, "Michael", "Lucas", "Geekus", "michael.lucas@geekus.biz", 9, GregorianCalendar(2015,4,15), true, R.drawable.michael))
    ret.add(Customer(1, "Briggs", "Puckett", "Bicol", "briggs.puckett@bicol.co.uk", 18, GregorianCalendar(2015,11,9), true, R.drawable.briggs))
    ret.add(Customer(2, "Gail", "Jarvis", "Qot", "gail.jarvis@qot.ca", 20, GregorianCalendar(2015,3,1), true, R.drawable.gail))
    ret.add(Customer(3, "Sandra", "Abbott", "Emoltra", "sandra.abbott@emoltra.tv", 6, GregorianCalendar(2017,3,16), false, R.drawable.sandra))
    ret.add(Customer(4, "Osborne", "Bates", "Rubadub", "osborne.bates@rubadub.org", 0, GregorianCalendar(2017,11,3), false, R.drawable.osborne))
    ret.add(Customer(5, "Sellers", "Nash", "Synkgen", "sellers.nash@synkgen.name", 20, GregorianCalendar(2018,2,7), true, R.drawable.sellers))
    ret.add(Customer(6, "Vang", "Conway", "Extragene", "vang.conway@extragene.io", 15, GregorianCalendar(2016,6,3), true, R.drawable.vang))
    ret.add(Customer(7, "Jessie", "Sargent", "Niguent", "jessie.sargent@niquent.info", 8, GregorianCalendar(2015,4,29), false, R.drawable.jessie))
    ret.add(Customer(8, "Cooley", "Edwards", "Zillacom", "cooley.edwards@zillacom.biz", 15, GregorianCalendar(2014,0,21), false, R.drawable.cooley))
    ret.add(Customer(9, "Cunningham", "Lowe", "Zolarex", "cunningham.lowe@zolarex.com", 7, GregorianCalendar(2018, 6,18), true, R.drawable.cunningham))
    ret.add(Customer(10, "Cornelia", "Peck", "Tubalum", "cornelia.peck@tubalum.me", 10, GregorianCalendar(2017,11,29), true, R.drawable.cornelia))
    ret.add(Customer(11, "Farrell", "Ballard", "Kongene", "farrell.ballard@kongene.us", 7, GregorianCalendar(2018,7,16), false, R.drawable.farrell))
    ret.add(Customer(12, "Carlene", "Galloway", "Cytrek", "carlene.galloway@cytrek.biz", 17, GregorianCalendar(2014, 6,15), false, R.drawable.carlene))
    ret.add(Customer(13, "Cherry", "Vaughan", "Corporana", "cherry.vaughan@corporana.co.uk", 19, GregorianCalendar(2014,5,29), false, R.drawable.cherry))
    ret.add(Customer(14, "Baxter", "Wolfe", "Gaptec", "baxter.wolfe@gaptec.ca", 19, GregorianCalendar(2015,10,18), false, R.drawable.baxter))

    return ret
}