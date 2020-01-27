package br.com.jetpackstarter.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import br.com.jetpackstarter.R
import br.com.jetpackstarter.util.getProgressDrawable
import br.com.jetpackstarter.util.loadImage
import br.com.jetpackstarter.viewmodel.DetailDogViewModel
import kotlinx.android.synthetic.main.fragment_dogs_details.*
import kotlinx.android.synthetic.main.fragment_dogs_list.*
import kotlinx.android.synthetic.main.item_dog.view.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class DogsDetailsFragment : Fragment() {

    private var dogUuid = 0
    private val viewModel: DetailDogViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dogs_details, container, false)
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
                dogName.text = dog.dogBreed
                dogTemperament.text = dog.temperature
                dogPorpouse.text = dog.bredFor
                dogLifespan.text = dog.lifeSpam
                dogImage.loadImage(dog.imageUrl, getProgressDrawable(dogImage.context))
            }
        })
    }
}
