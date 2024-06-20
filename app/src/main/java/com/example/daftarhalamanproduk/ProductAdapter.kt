package com.example.daftarhalamanproduk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductAdapter(private var products: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.productImageView)
        val nameTextView: TextView = itemView.findViewById(R.id.productNameTextView)
        val priceTextView: TextView = itemView.findViewById(R.id.productPriceTextView)
        val ratingTextView: TextView = itemView.findViewById(R.id.productRatingTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.nameTextView.text = product.name
        holder.priceTextView.text = product.price.toString()
        holder.ratingTextView.text = product.rating.toString()

        Glide.with(holder.itemView.context)
            .load(product.imageUrl)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(holder.imageView)

    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun filterList(filteredList: List<Product>) {
        products = filteredList
        notifyDataSetChanged()
    }
}
