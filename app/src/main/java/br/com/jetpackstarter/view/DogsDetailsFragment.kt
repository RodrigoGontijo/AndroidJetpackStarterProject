package br.com.jetpackstarter.view


import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.palette.graphics.Palette

import br.com.jetpackstarter.R
import br.com.jetpackstarter.databinding.FragmentDogsDetailsBinding
import br.com.jetpackstarter.databinding.ItemDogBinding
import br.com.jetpackstarter.model.dogsRepository.DogPalette
import br.com.jetpackstarter.util.getProgressDrawable
import br.com.jetpackstarter.util.loadImage
import br.com.jetpackstarter.viewmodel.DetailDogViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.fragment_dogs_details.*
import kotlinx.android.synthetic.main.fragment_dogs_list.*
import kotlinx.android.synthetic.main.item_dog.view.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class DogsDetailsFragment : Fragment() {

    protected lateinit var binding: FragmentDogsDetailsBinding
    private var dogUuid = 0
    private val viewModel: DetailDogViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentDogsDetailsBinding>(inflater, R.layout.fragment_dogs_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            dogUuid = DogsDetailsFragmentArgs.fromBundle(it).dogUnid
            viewModel.fetch(dogUuid)
        }

        observeViewlModel()
    }

    private fun observeViewlModel(){
        viewModel.dogLiveData.observe(this, Observer { dog ->
            dog?.let{
                binding.dogDetail = dog
                it.imageUrl?.let {
                    setupBackgroundColor(it)
                }
            }
        })
    }

    private fun setupBackgroundColor(url: String){
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate { pallete ->
                            val intColor = pallete?.lightMutedSwatch?.rgb ?: 0
                            val myPallete = DogPalette(intColor)
                            binding.palette = myPallete
                        }
                }

                override fun onLoadCleared(placeholder: Drawable?) {


                }

            })
    }
}
