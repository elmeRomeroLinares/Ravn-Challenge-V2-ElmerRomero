package com.example.ravnchallengev2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ravnchallengev2.R
import com.example.ravnchallengev2.databinding.CharacterAttributesListItemBinding
import com.example.ravnchallengev2.databinding.CharacterVehiclesListItemBinding
import com.example.starwarsserver.CharacterDetailsQuery

class VehiclesRecyclerViewAdapter(
        private val vehiclesList: List<CharacterDetailsQuery.Vehicle>
) : RecyclerView.Adapter<VehiclesRecyclerViewAdapter.VehicleViewHolder>() {

    class VehicleViewHolder(
            val binding: CharacterVehiclesListItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return if (vehiclesList.isEmpty()){
            1
        } else {
            vehiclesList.size
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        return VehicleViewHolder(CharacterVehiclesListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        ))
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        val noVehicleString = holder.itemView.context.getString(R.string.no_vehicle)
        if (vehiclesList.isNotEmpty()) {
            val vehicle = vehiclesList[position]
            holder.binding.dynamicVehicleTextView.text = vehicle.name
        } else {
            holder.binding.dynamicVehicleTextView.text = noVehicleString
        }
    }
}