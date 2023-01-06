package tech.fernandooliveira.fitnesstracker

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow

class BmiActivity : AppCompatActivity() {

    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        editWeight = findViewById(R.id.edit_bmi_weight)
        editHeight = findViewById(R.id.edit_bmi_height)

        val btnSend: Button = findViewById(R.id.btn_bmi_send)
        btnSend.setOnClickListener {
            if (!validateFields()) {
                Toast.makeText(this, R.string.fields_error_message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val weight = editWeight.text.toString().toInt()
            val height = editHeight.text.toString().toInt()

            val result = calculateBmi(weight, height)

            val bmiResponseId = bmiResponse(result)

            val title = getString(R.string.bmi_response_title, result)

            val dialog = AlertDialog.Builder(this).setTitle(title).setMessage(bmiResponseId)
                .setPositiveButton(R.string.ok) { dialog, which ->
                    dialog.dismiss()
                }.create().show()

            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        }


    }

    @StringRes
    private fun bmiResponse(bmi: Double): Int {
        return when (bmi) {
            in 0.0..14.9 -> R.string.bmi_severely_low_weight
            in 15.0..15.9 -> R.string.bmi_very_low_weight
            in 16.0..18.4 -> R.string.bmi_very_low_weight
            in 18.5..24.9 -> R.string.normal
            in 25.0..29.9 -> R.string.bmi_high_weight
            in 30.0..34.9 -> R.string.bmi_so_high_weight
            in 35.0..39.9 -> R.string.bmi_severely_high_weight
            in 40.0..Double.MAX_VALUE -> R.string.bmi_extreme_weight
            else -> R.string.bmi_response_error
        }
    }

    private fun calculateBmi(weight: Int, height: Int): Double {
        return weight / ((height / 100.0).pow(2.0))
    }

    private fun validateFields(): Boolean {
        // cant start with 0
        // cant be empty
        return (editWeight.text.toString().isNotEmpty() && editHeight.text.toString()
            .isNotEmpty() && !editWeight.text.toString()
            .startsWith("0") && !editHeight.text.toString().startsWith("0"))
    }

}