package be.kristofbuts.android.customeroverview.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import be.kristofbuts.android.customeroverview.R
import be.kristofbuts.android.customeroverview.model.Customer
import be.kristofbuts.android.customeroverview.rest.RestClient
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ChartActivity : AppCompatActivity() {

    private lateinit var chart: PieChart
    private var xData: ArrayList<String> = arrayListOf()
    private var yData: ArrayList<Float> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(be.kristofbuts.android.customeroverview.R.layout.activity_chart)

        initialiseViews()
        loadData()
    }

    private fun initialiseViews() {
        this.chart = findViewById(R.id.chart)
    }

    private fun buildChart() {
        val ds = buildDataSet()

        chart.legend.isEnabled = false
        chart.description.isEnabled = false

        chart.data = PieData(ds)
        chart.invalidate()
    }

    private fun buildDataSet(): PieDataSet {
        val yEntries: ArrayList<PieEntry> = arrayListOf()

        yData.forEachIndexed { i, el ->
            yEntries.add(i, PieEntry(el, xData[i]))
        }

        // create data set
        val ds = PieDataSet(yEntries, getString(R.string.chart_description))
        ds.colors = ColorTemplate.MATERIAL_COLORS.toList()
//        ds.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        ds.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        ds.valueTextColor = Color.BLACK
        chart.setEntryLabelColor(Color.BLACK)

        return ds
    }

    private fun loadData() {
        RestClient(this)
            .getCustomers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                xData.clear()
                yData.clear()
                it.forEach { c ->
                    xData.add(c.getName())
                    yData.add(c.callsToServiceLine.toFloat())
                }

                buildChart()
            }, {
                Toast.makeText(
                    this@ChartActivity,
                    it.message,
                    Toast.LENGTH_LONG)
                    .show()
            })
    }
}
