package br.com.jetpackstarter.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import br.com.jetpackstarter.R
import kotlinx.android.synthetic.main.fragment_dogs_details.*
import kotlinx.android.synthetic.main.fragment_dogs_list.*

/**
 * A simple [Fragment] subclass.
 */
class DogsDetailsFragment : Fragment() {

    private var dogUuid = 0

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
        }
    }
}
