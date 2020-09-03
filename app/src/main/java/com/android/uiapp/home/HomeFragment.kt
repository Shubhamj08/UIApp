package com.android.uiapp.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.uiapp.R
import com.android.uiapp.databinding.HomeFragmentBinding

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: HomeFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)

        binding.profileCard.setOnClickListener {
            this.findNavController().navigate(
                HomeFragmentDirections
                    .actionHomeFragmentToProfileFragment()
            )
        }

        return binding.root
    }

}