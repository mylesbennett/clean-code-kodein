package com.aimicor.latinpost.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.aimicor.latinpost.view.R
import com.aimicor.latinpost.view.ViewApplication.Companion.appContext
import com.aimicor.latinpost.view.databinding.MainFragmentBinding
import com.aimicor.latinpost.view.detail.DetailFragment
import com.aimicor.latinpost.view.lazyViewModel
import com.aimicor.latinpost.view.observe
import com.aimicor.latinpost.viewmodel.detail.DetailViewModel
import com.aimicor.latinpost.viewmodel.inflateBinding
import com.aimicor.latinpost.viewmodel.main.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

class MainFragment : Fragment(), KodeinAware {
    override val kodein: Kodein by closestKodein(appContext)

    private val progress by lazy { activity?.findViewById(R.id.progress) as View }
    private val viewModel: MainViewModel by lazyViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.showProgress.observe(this, Observer { showProgress(it) })
        viewModel.listItemClickEvent.observe(this.lifecycle) { showDetail(it) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val dataBinding = inflater.inflateBinding<MainFragmentBinding>(R.layout.main_fragment, container)
        dataBinding.viewModel = viewModel
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvData.addItemDecoration(DividerItemDecoration(activity!!, DividerItemDecoration.VERTICAL))
    }

    private fun showProgress(show: Boolean) {
        if (show) progress.visibility = View.VISIBLE else progress.visibility = View.GONE
    }

    private fun showDetail(detailViewModel: DetailViewModel) {
        showProgress(false)
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_mainFragment_to_detailFragment, DetailFragment.with(detailViewModel))
    }
}