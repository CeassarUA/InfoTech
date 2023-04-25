package com.ceassar.test.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ceassar.test.R
import com.ceassar.test.databinding.FragmentMainBinding
import com.ceassar.test.ui.base.BaseFragment

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    val adapter = ListAdapter()
    override fun initialization(view: View, savedInstanceState: Bundle?) {

    }
}