package com.android.uiapp.custom_view

import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.uiapp.LabelClass
import com.android.uiapp.MyCustomView
import com.android.uiapp.R
import kotlinx.android.synthetic.main.custom_view_fragment.*

class CustomViewFragment : Fragment() {

    companion object {
        fun newInstance() = CustomViewFragment()
    }

    private lateinit var viewModel: CustomViewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.custom_view_fragment, container, false)
    }

}