package com.rickymortyapp.ui

import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rickmortyapp.domain.model.CharacterDetail
import com.rickymortyapp.R
import com.rickymortyapp.commons.BaseActivity
import com.rickymortyapp.databinding.ActivityMainBinding
import com.rickymortyapp.paging.CharacterPagedListAdapter

class MainActivity : BaseActivity(), CharacterPagedListAdapter.ItemClickedLister  {

    private lateinit var binding : ActivityMainBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        (menu.findItem(R.id.menu_search).actionView as SearchView).setOnQueryTextListener(viewModel)
        (menu.findItem(R.id.menu_search).actionView as SearchView).setOnCloseListener {
            setupRecycler(false)
            true
        }
        return true
    }

    override fun onStart() {
        super.onStart()
        setupRecycler(false)
        viewModel.isQueryLiveData.observe(this, Observer { setupRecycler(it) })

    }

    private fun setupRecycler(isQuery : Boolean) {
        binding.main.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.main.itemAnimator = DefaultItemAnimator()

        val adapter = CharacterPagedListAdapter(this)
        binding.main.adapter = adapter

        if (!isQuery){
            viewModel.listLiveData!!.observe(this, Observer { adapter.submitList(it) })
        } else {
            viewModel.queryLiveData?.observe(this, Observer { adapter.submitList(it) })
        }
    }

    override fun onItemClicked(character: CharacterDetail?) {
        val bundle = Bundle()
        bundle.putSerializable(CharacterDetailFragment.EXTRA_CHARACTER, character)
        character?.let {  CharacterDetailFragment.getDialog(it).show(supportFragmentManager, "") }
    }
}
