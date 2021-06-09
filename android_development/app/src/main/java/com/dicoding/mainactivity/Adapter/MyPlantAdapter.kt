package com.dicoding.mainactivity.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.mainactivity.Data.MyPlantData
import com.dicoding.mainactivity.R
import com.dicoding.mainactivity.databinding.ItemRowListBinding

class MyPlantAdapter(): RecyclerView.Adapter<MyPlantAdapter.PlantViewHolder>() {
    private var onItemClickedCallback : OnItemClickedCallback? = null

    private val userList = ArrayList<MyPlantData>()

    var listPlant = ArrayList<MyPlantData>()
        set(listNotes) {
            if (listNotes.size > 0) {
                this.listPlant.clear()
            }
            this.listPlant.addAll(listNotes)
            notifyDataSetChanged()
        }
    fun addItem(note: MyPlantData) {
        this.listPlant.add(note)
        notifyItemInserted(this.listPlant.size - 1)
    }
    fun updateItem(position: Int, note: MyPlantData) {
        this.listPlant[position] = note
        notifyItemChanged(position, note)
    }
    fun removeItem(position: Int) {
        this.listPlant.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listPlant.size)
    }
    inner class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowListBinding.bind(itemView)
        fun bind(data: MyPlantData) {

            binding.root.setOnClickListener {
                onItemClickedCallback?.onItemClicked(data)
            }
            binding.apply {
                tvName.text = data.plant_name.toString()
                Glide.with(itemView)
                    .load(data.PHOTO)
                    .apply(RequestOptions().override(250, 250))
                    .into(imageView2)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_list, parent, false)
        return PlantViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        holder.bind(listPlant[position])
    }
    override fun getItemCount(): Int = this.listPlant.size
    interface OnItemClickedCallback{
        fun onItemClicked(data : MyPlantData)
    }

}