package com.android.uiapp.animations

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.uiapp.R

class AnimationsFragment : Fragment() {

    companion object {
        fun newInstance() = AnimationsFragment()
    }

    private lateinit var viewModel: AnimationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.animations_fragment, container, false)
    }


}