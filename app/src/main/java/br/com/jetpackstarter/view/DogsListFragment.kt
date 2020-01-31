package br.com.jetpackstarter.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import br.com.jetpackstarter.viewmodel.DogsListViewModel
import kotlinx.android.synthetic.main.fragment_dogs_list.*
import org.koin.android.viewmodel.ext.android.viewModel

import android.view.*
import androidx.navigation.Navigation
import br.com.jetpackstarter.R.menu.*
import br.com.jetpackstarter.R.*


/**
 * A simple [Fragment] subclass.
 */
class DogsListFragment : Fragment() {

    private val viewModel: DogsListViewModel by viewModel()
    private val dogsListAdapter = DogsListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(layout.fragment_dogs_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.refresh()

        dogsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dogsListAdapter
        }

        refreshLayout.setOnRefreshListener {
            dogsList.visibility = View.GONE
            listError.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            viewModel.refreshBypassCache()
            refreshLayout.isRefreshing = false
        }

        observeVielModel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override  fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
           br.com.jetpackstarter.R.id.preferences ->{
               view?.let {
                   val action = DogsListFragmentDirections.ActionSettingsFragment()
                   Navigation.findNavController(it).navigate(action)
               }
           }
        }
        return true

    }

    override fun onDestroyView() {
        super.onDestroyView()
        removeObservers()
    }

    fun observeVielModel(){

        viewModel.dogs.observe(this, Observer {
            it?.let {
                dogsList.visibility = View.VISIBLE
                dogsListAdapter.updateDogsList(it)
            }
        })

        viewModel.dogsLoadError.observe(this, Observer {
            it?.let {
                listError.visibility = if(it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(this, Observer {
            it?.let {
                if(it){
                    loadingView.visibility = View.VISIBLE
                    listError.visibility = View.GONE
                    dogsList.visibility = View.GONE
                }else {
                    loadingView.visibility = View.GONE
                }
            }
        })
    }

    fun removeObservers(){
        viewModel.loading.removeObservers(this)
        viewModel.dogsLoadError.removeObservers(this)
        viewModel.dogs.removeObservers(this)
    }
}
