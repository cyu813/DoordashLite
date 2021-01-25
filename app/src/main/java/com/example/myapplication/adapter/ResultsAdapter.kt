package com.example.myapplication.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.RestaurantDetailActivity
import com.example.myapplication.model.ResultItem
import com.example.myapplication.model.ResultItemOnClickListener

class ResultsAdapter(private val context: Context, private val results: List<ResultItem>) : RecyclerView.Adapter<ResultsAdapter.ResultItemViewHolder>(), ResultItemOnClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.restaurant_list_item, parent, false)
        return ResultItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: ResultItemViewHolder, position: Int) {
        val item = results[position]
        holder.name.text = item.name
        holder.desc.text = item.description
        holder.delivery_time.text = item.status
        Glide.with(context).load(item.cover_img_url).fitCenter().into(holder.coverImage)
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    class ResultItemViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {
        val name = containerView.findViewById<TextView>(R.id.tv_name)
        val desc = containerView.findViewById<TextView>(R.id.tv_desc)
        val delivery_time = containerView.findViewById<TextView>(R.id.tv_delivery_time)
        val coverImage = containerView.findViewById<ImageView>(R.id.iv_cover_image)
    }

    override fun onItemClick(item: ResultItem) {
        val intent = Intent(context, RestaurantDetailActivity::class.java).apply {
            putExtra(RestaurantDetailActivity.RESTAURANT_ID, item.id)
        }
        context.startActivity(intent)
    }
}