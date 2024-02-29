package com.example.basedemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2


class ExclusiveOfferPagerAdapter(private val images: List<Int>, private val viewPager2: ViewPager2) : RecyclerView.Adapter<ExclusiveOfferPagerAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exclusive_offer, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageRes = images[position % images.size]
        holder.imageView.setBackgroundResource(imageRes)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ConstraintLayout = itemView.findViewById(R.id.clOfferImage)
    }
}
