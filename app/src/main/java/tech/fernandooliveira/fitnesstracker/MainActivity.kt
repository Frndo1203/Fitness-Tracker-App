package tech.fernandooliveira.fitnesstracker

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    //    private lateinit var btnIMC: LinearLayout
    private lateinit var rvMain: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        btnIMC = findViewById(R.id.btn_imc)
        val adapter = MainAdapter()
        rvMain = findViewById(R.id.rv_main)
        rvMain.adapter = adapter
        rvMain.layoutManager = LinearLayoutManager(this)
//
//        btnIMC.setOnClickListener {
//            // navegar para a proxima tela
//            val i = Intent(this, ImcActivity::class.java)
//            startActivity(i)
//        }
    }

    private inner class MainAdapter : RecyclerView.Adapter<MainViewHolder>() {
        // 1 - What is the XML layout for each item
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)

            return MainViewHolder(view)

        }

        // 2 - Triggered when the RecyclerView needs to display an item
        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            TODO("Not yet implemented")
        }

        // 3 - How many items are in the list
        override fun getItemCount(): Int {
            return 10
        }

    }

    private class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}