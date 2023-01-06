package tech.fernandooliveira.fitnesstracker

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    //    private lateinit var btnIMC: LinearLayout
    private lateinit var rvMain: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainItems = mutableListOf<MainItem>()
        mainItems.add(
            MainItem(
                id = 1,
                drawableId = R.drawable.ic_baseline_monitor_weight_24,
                textStringId = R.string.label_bmi,
                color = Color.GREEN
            )
        )
        mainItems.add(
            MainItem(
                id = 2,
                drawableId = R.drawable.ic_baseline_local_fire_department_24,
                textStringId = R.string.label_bmr,
                color = Color.BLUE
            )
        )

        rvMain = findViewById(R.id.rv_main)

        val adapter = MainAdapter(mainItems)
        rvMain.adapter = adapter

        rvMain.layoutManager = LinearLayoutManager(this)
    }

    private inner class MainAdapter(private val mainItems: List<MainItem>) :
        RecyclerView.Adapter<MainViewHolder>() {
        // 1 - What is the XML layout for each item
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)

            return MainViewHolder(view)
        }

        // 2 - Triggered when the RecyclerView needs to display an item
        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val currentItem: MainItem = mainItems[position]
            holder.bind(currentItem)
        }

        // 3 - How many items are in the list
        override fun getItemCount(): Int {
            return mainItems.size
        }

    }

    private class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: MainItem) {
            val img: ImageView = itemView.findViewById(R.id.item_img)
            val title: TextView = itemView.findViewById(R.id.item_title)
            val container: LinearLayout = itemView as LinearLayout

            img.setImageResource(item.drawableId)
            title.setText(item.textStringId)
            container.setBackgroundColor(item.color)
        }
    }
}