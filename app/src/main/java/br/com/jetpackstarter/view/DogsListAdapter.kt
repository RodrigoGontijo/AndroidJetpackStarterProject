package br.com.jetpackstarter.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import br.com.jetpackstarter.R
import br.com.jetpackstarter.databinding.ItemDogBinding
import br.com.jetpackstarter.model.dogsRepository.DogBreed
import br.com.jetpackstarter.util.getProgressDrawable
import br.com.jetpackstarter.util.loadImage
import kotlinx.android.synthetic.main.item_dog.view.*

class DogsListAdapter(val dogsList: ArrayList<DogBreed>) : RecyclerView.Adapter<DogsListAdapter.DogViewHolder>(), DogClickListener{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemDogBinding>(inflater, R.layout.item_dog, parent, false)
        return DogViewHolder(view)
    }

    override fun getItemCount() = dogsList.size


    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.view.dog = dogsList[position]
        holder.view.listener = this
    }

    override fun onDogClick(v: View) {
        val action = DogsListFragmentDirections.actionDetailFragment()
        action.dogUnid = v.dogId.text.toString().toInt()
        Navigation.findNavController(v).navigate(action)
    }

    fun updateDogsList(newDogsList : List<DogBreed>){
        dogsList.clear()
        dogsList.addAll(newDogsList)
        notifyDataSetChanged()
    }


    class DogViewHolder(var view: ItemDogBinding) : RecyclerView.ViewHolder(view.root)
}