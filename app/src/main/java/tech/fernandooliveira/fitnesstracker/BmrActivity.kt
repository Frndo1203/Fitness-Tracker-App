package tech.fernandooliveira.fitnesstracker

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import tech.fernandooliveira.fitnesstracker.model.App
import tech.fernandooliveira.fitnesstracker.model.Calc

class BmrActivity : AppCompatActivity() {

    private lateinit var lifestyle: AutoCompleteTextView
    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText
    private lateinit var editAge: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmr_acitivy)

        editWeight = findViewById(R.id.edit_bmr_weight)
        editHeight = findViewById(R.id.edit_bmr_height)
        editAge = findViewById(R.id.edit_bmr_age)

        lifestyle = findViewById(R.id.auto_lifestyle)
        val items = resources.getStringArray(R.array.bmr_lifestyle)
        lifestyle.setText(items.first())

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        lifestyle.setAdapter(adapter)

        val btnSend: Button = findViewById(R.id.btn_bmr_send)
        btnSend.setOnClickListener {
            if (!validateFields()) {
                Toast.makeText(this, R.string.fields_error_message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val weight = editWeight.text.toString().toInt()
            val height = editHeight.text.toString().toInt()
            val age = editAge.text.toString().toInt()

            val result = calculateBmr(weight, height, age)
            val response = bmrRequest(result, items)
            val message = getString(R.string.bmr_response_title, response)

            AlertDialog.Builder(this).setMessage(message)

                .setPositiveButton(R.string.ok) { dialog, _ ->
                    dialog.dismiss()
                }.setNegativeButton(R.string.save) { _, _ ->
                    Thread {
                        val app = application as App
                        val dao = app.db.calcDao()
                        dao.insert(Calc(type = "bmr", res = response))

                        runOnUiThread { // every Ui event should happen in UI Thread
                            openListActivity()
                        }
                    }.start()
                }.create().show()

            val service = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)


        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_search) {
            finish()
            openListActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openListActivity() {
        val intent = Intent(this, ListCalcActivity::class.java)
        intent.putExtra("type", "bmr")
        startActivity(intent)
    }

    private fun calculateBmr(weight: Int, height: Int, age: Int): Double {
        return 66 + (13.8 * weight) + (5 * height) - (6.8 * age)
    }

    private fun bmrRequest(bmr: Double, bmr_items: Array<String>): Double {
        return when (lifestyle.text.toString()) {
            bmr_items[0] -> bmr * 1.2
            bmr_items[1] -> bmr * 1.375
            bmr_items[2] -> bmr * 1.55
            bmr_items[3] -> bmr * 1.725
            bmr_items[4] -> bmr * 1.9
            else -> return 0.0
        }
    }

    private fun validateFields(): Boolean {
        // cant start with 0
        // cant be empty
        return (editWeight.text.toString().isNotEmpty() && editHeight.text.toString()
            .isNotEmpty() && editAge.text.toString().isNotEmpty() && !editWeight.text.toString()
            .startsWith("0") && !editHeight.text.toString()
            .startsWith("0") && !editAge.text.toString().startsWith("0"))
    }
}