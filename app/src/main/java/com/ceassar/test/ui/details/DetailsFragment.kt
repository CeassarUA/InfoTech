package com.ceassar.test.ui.details

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.ceassar.test.R
import com.ceassar.test.databinding.FragmentMapsBinding
import com.ceassar.test.ui.base.BaseFragment

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class DetailsFragment : BaseFragment<FragmentMapsBinding>(FragmentMapsBinding::inflate) {

    private val args by navArgs<DetailsFragmentArgs>()
    override val viewModel: DetailsViewModel by viewModel {
        parametersOf(args.dto)
    }

    private val callback = OnMapReadyCallback { googleMap ->

        val pos = LatLng(args.dto.coord.lat, args.dto.coord.lon)
        googleMap.addMarker(MarkerOptions().position(pos).title(args.dto.name))
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(11f))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(pos))

    }

    override fun initialization(view: View, savedInstanceState: Bundle?) {

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        viewModel.weatherLiveData.observe(viewLifecycleOwner) {
            safeBind {
                tvDescribe.text = args.dto.name
                tvCurrent.text = it.main.temp.toString()
                tvMinMax.text = requireContext().getString(
                    R.string.min_max_temp_value,
                    it.main.temp_min.toString(),
                    it.main.temp_max.toString()
                )
                tvHumidity.text = it.main.humidity.toString()
                tvWind.text = it.wind.speed.toString()
            }

        }
    }
}