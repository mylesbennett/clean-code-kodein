package com.aimicor.latinpost.viewmodel.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aimicor.latinpost.PostSummary
import com.aimicor.latinpost.viewmodel.R
import com.aimicor.latinpost.viewmodel.databinding.MainListItemBinding
import com.aimicor.latinpost.viewmodel.inflateBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class MainAdapter : ListAdapter<PostSummary, MainAdapter.ViewHolder>(DiffCallback()) {

    private val clickSubject = PublishSubject.create<Int>()
    internal val clickEvent: Observable<Int> = clickSubject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflateBinding(R.layout.main_list_item, parent))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.item = getItem(position)
        holder.binding.clickSubject = clickSubject
    }

    inner class ViewHolder(val binding: MainListItemBinding) : RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<PostSummary>() {
        override fun areItemsTheSame(oldItem: PostSummary, newItem: PostSummary) = oldItem.postId == newItem.postId
        override fun areContentsTheSame(oldItem: PostSummary, newItem: PostSummary) = oldItem.title == newItem.title
    }
}