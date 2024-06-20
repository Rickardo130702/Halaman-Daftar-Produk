package com.example.daftarhalamanproduk

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DaftarProductActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var searchEditText: EditText
    private lateinit var sortSpinner: Spinner
    private lateinit var filterSpinner: Spinner
    private var productList: MutableList<Product> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.daftar_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recyclerView)
        searchEditText = findViewById(R.id.searchEditText)
        sortSpinner = findViewById(R.id.sortSpinner)
        filterSpinner = findViewById(R.id.filterSpinner)

        initializeProductList()
        setupRecyclerView()
        setupSearch()
        setupSortSpinner()
        setupFilterSpinner()
    }

    private fun initializeProductList() {
        productList.add(Product("Ponte Nero Terra Dark Choco", 200000, 4.5f, "https://bro.do/cdn/shop/files/2_d0d2fb6b-1d69-49e7-a064-50e8b3b37e01_1800x1800.jpg"))
        productList.add(Product("Signore Low Full Black", 150000, 4.0f, "https://bro.do/cdn/shop/products/481-2_800x800_e7ee0392-1853-43b9-9f2a-023d3a1c9e33_1800x1800.jpg?v=1579601057"))
        productList.add(Product("Alesto Dark Brown", 300000, 5.0f, "https://www.ginomariani.com/cdn/shop/products/56290b98-e1a8-4c5c-90d8-d9456bba9409-6.jpg?v=1707209151&width=600"))
        productList.add(Product("Ponte Nero Terra Dark Choco", 200000, 4.5f, "https://bro.do/cdn/shop/files/2_d0d2fb6b-1d69-49e7-a064-50e8b3b37e01_1800x1800.jpg"))
        productList.add(Product("Signore Low Full Black", 150000, 4.0f, "https://bro.do/cdn/shop/products/481-2_800x800_e7ee0392-1853-43b9-9f2a-023d3a1c9e33_1800x1800.jpg?v=1579601057"))
        productList.add(Product("Alesto Dark Brown", 300000, 5.0f, "https://www.ginomariani.com/cdn/shop/products/56290b98-e1a8-4c5c-90d8-d9456bba9409-6.jpg?v=1707209151&width=600"))

    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(productList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = productAdapter
        recyclerView.addItemDecoration(SpacingItemDecoration(16))
    }

    private fun setupSearch() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filter(text: String) {
        val filteredList = productList.filter {
            it.name.contains(text, true)
        }
        productAdapter.filterList(filteredList)
    }

    private fun setupSortSpinner() {
        val sortOptions = arrayOf("Harga", "Rating")
        sortSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, sortOptions)
        sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> sortByPrice()
                    1 -> sortByRating()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun sortByPrice() {
        val sortedList = productList.sortedBy { it.price }
        productAdapter.filterList(sortedList)
    }

    private fun sortByRating() {
        val sortedList = productList.sortedByDescending { it.rating }
        productAdapter.filterList(sortedList)
    }

    private fun setupFilterSpinner() {
        val filterOptions = arrayOf("Semua", "Harga < 200000", "Harga >= 200000")
        filterSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, filterOptions)
        filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> productAdapter.filterList(productList)
                    1 -> filterByPriceRange(0, 200000)
                    2 -> filterByPriceRange(200000, Int.MAX_VALUE)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun filterByPriceRange(minPrice: Int, maxPrice: Int) {
        val filteredList = productList.filter {
            it.price in minPrice..maxPrice
        }
        productAdapter.filterList(filteredList)

    }
}