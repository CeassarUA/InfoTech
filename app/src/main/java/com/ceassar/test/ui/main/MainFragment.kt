package com.ceassar.test.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.ceassar.test.databinding.FragmentMainBinding
import com.ceassar.test.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    private val adapter = ListAdapter {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToMapsFragment(it))
    }
    override val viewModel: MainViewModel by viewModel()
    override fun initialization(view: View, savedInstanceState: Bundle?) {
        safeBind {

            rvList.adapter = adapter
            viewModel.cityLive.observe(viewLifecycleOwner) {
                adapter.submitData(lifecycle, it)
            }

        }
    }

    override fun FragmentMainBinding.dataBind() {
        viewmodel = viewModel
    }

}