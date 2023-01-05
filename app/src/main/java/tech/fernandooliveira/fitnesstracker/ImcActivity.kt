package tech.fernandooliveira.fitnesstracker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ImcActivity : AppCompatActivity() {

    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)

        editWeight = findViewById(R.id.edit_bmi_weight)
        editHeight = findViewById(R.id.edit_bmi_height)

        val btnSend: Button = findViewById(R.id.btn_bmi_send)
        btnSend.setOnClickListener {
            if (!validateFields()) {
                Toast.makeText(this, R.string.fields_error_message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


        }


    }

    private fun validateFields(): Boolean {
        // cant start with 0
        // cant be empty
        val validation_condition: Boolean =
            (editWeight.text.toString().isNotEmpty() && editHeight.text.toString()
                .isNotEmpty() && !editWeight.text.toString()
                .startsWith("0") && !editHeight.text.toString().startsWith("0"))

        return validation_condition
    }

}