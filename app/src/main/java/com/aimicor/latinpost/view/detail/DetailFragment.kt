package com.aimicor.latinpost.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aimicor.latinpost.view.R
import com.aimicor.latinpost.viewmodel.databinding.DetailFragmentBinding
import com.aimicor.latinpost.viewmodel.detail.DetailViewModel
import com.aimicor.latinpost.viewmodel.inflateBinding

class DetailFragment : Fragment() {

    companion object {
        private val ARGUMENT = "detail_view_model_argument"

        fun with(detailViewModel: DetailViewModel): Bundle {
            val args = Bundle()
            args.putSerializable(ARGUMENT, detailViewModel)
            return args
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val dataBinding = inflater.inflateBinding<DetailFragmentBinding>(R.layout.detail_fragment, container)
        dataBinding.detail = arguments?.getSerializable(ARGUMENT) as DetailViewModel
        return dataBinding.root
    }
}