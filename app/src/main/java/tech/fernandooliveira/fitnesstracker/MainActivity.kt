package tech.fernandooliveira.fitnesstracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {

    private lateinit var btnIMC: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnIMC = findViewById(R.id.btn_imc)

        btnIMC.setOnClickListener {
            // navegar para a proxima tela
            val i = Intent(this, ImcActivity::class.java)
            startActivity(i)
        }
    }
}