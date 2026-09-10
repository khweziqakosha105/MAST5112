package com.example.assignment1

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private lateinit var spinnerCourse: Spinner
    private lateinit var editTextDishName: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var editTextPrice: EditText
    private lateinit var buttonSave: Button
    private lateinit var recyclerViewMenu: RecyclerView
    private lateinit var textViewEmptyMessage: TextView

    private val menuList = mutableListOf<MenuItem>()
    private lateinit var menuAdapter: MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI components
        spinnerCourse = findViewById(R.id.spinnerCourse)
        editTextDishName = findViewById(R.id.editTextDishName)
        editTextDescription = findViewById(R.id.editTextDescription)
        editTextPrice = findViewById(R.id.editTextPrice)
        buttonSave = findViewById(R.id.buttonSave)
        recyclerViewMenu = findViewById(R.id.recyclerViewMenu)
        textViewEmptyMessage = findViewById(R.id.textViewEmptyMessage)

        // Setup RecyclerView
        menuAdapter = MenuAdapter(menuList)
        recyclerViewMenu.layoutManager = LinearLayoutManager(this)
        recyclerViewMenu.adapter = menuAdapter

        // Setup Spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.course_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCourse.adapter = adapter
        }

        // Handle Save Button click
        buttonSave.setOnClickListener {
            saveMenuItem()
        }
    }

    private fun saveMenuItem() {
        val course = spinnerCourse.selectedItem.toString()
        val dishName = editTextDishName.text.toString().trim()
        val description = editTextDescription.text.toString().trim()
        val priceString = editTextPrice.text.toString().trim()

        // Validation
        if (dishName.isEmpty() || description.isEmpty() || priceString.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_fields_required), Toast.LENGTH_SHORT).show()
            return
        }

        val price = priceString.toDoubleOrNull()
        if (price == null) {
            Toast.makeText(this, getString(R.string.error_invalid_price), Toast.LENGTH_SHORT).show()
            return
        }

        // Create and add new menu item
        val newItem = MenuItem(
            id = UUID.randomUUID().toString(),
            course = course,
            dishName = dishName,
            description = description,
            price = price
        )

        menuList.add(newItem)
        menuAdapter.updateData(menuList)
        
        // Update UI visibility
        updateListView()

        // Success message and clear fields
        Toast.makeText(this, getString(R.string.success_message), Toast.LENGTH_SHORT).show()
        clearFields()
    }

    private fun updateListView() {
        if (menuList.isEmpty()) {
            textViewEmptyMessage.visibility = View.VISIBLE
            recyclerViewMenu.visibility = View.GONE
        } else {
            textViewEmptyMessage.visibility = View.GONE
            recyclerViewMenu.visibility = View.VISIBLE
        }
    }

    private fun clearFields() {
        editTextDishName.text.clear()
        editTextDescription.text.clear()
        editTextPrice.text.clear()
        spinnerCourse.setSelection(0)
    }
}
