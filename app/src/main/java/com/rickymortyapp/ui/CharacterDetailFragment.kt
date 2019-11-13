package com.rickymortyapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rickmortyapp.domain.model.CharacterDetail
import com.rickymortyapp.R
import com.rickymortyapp.databinding.CharacterDetailFragmentBinding
import kotlinx.android.synthetic.main.character_detail_fragment.*

class CharacterDetailFragment : DialogFragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    companion object {
        const val EXTRA_CHARACTER: String = "character_details_to_show"

        fun getDialog(character : CharacterDetail): CharacterDetailFragment {
            val dialog = CharacterDetailFragment()
            val arguments = Bundle()
            arguments.putSerializable(EXTRA_CHARACTER, character)
            dialog.arguments = arguments
            dialog.setStyle( STYLE_NO_FRAME, R.style.Base_ThemeOverlay_AppCompat_Dialog_Alert )

            return dialog
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.setCanceledOnTouchOutside(true)

        val detailFragmentBinding = CharacterDetailFragmentBinding.inflate(inflater, container, false)
        val characterDetail = arguments?.getSerializable(EXTRA_CHARACTER) as CharacterDetail?

        detailFragmentBinding.character = characterDetail

        characterDetail?.let { it.location?.let { location -> viewModel.getLocation(location.getLocationId()) } }

        observeViewModel()

        return detailFragmentBinding.root
    }

    private fun observeViewModel() {
        viewModel.locationType.observe(this, Observer {
            tvType.text = getString(R.string.type, it)
        })
    }

}