package com.demo.metaweather.screens.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.demo.metaweather.databinding.FragmentLocationDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationDetailsFragment : Fragment() {

    private lateinit var content: FragmentLocationDetailsBinding
    private val args:LocationDetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?): View {
        content = FragmentLocationDetailsBinding.inflate(inflater, parent, false)
        content.locationDetails.text = args.whereOnEarthId.toString()
        return content.root
    }

}
