package jp.co.yumemi.android.code_check.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.model.GitHubAccounts

/**
 * GitHubAccountAdapter for displaying GitHub account items.
 */
class GitHubAccountAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<GitHubAccounts, GitHubAccountAdapter.ViewHolder>(diff_util) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OnItemClickListener {
        fun itemClick(item: GitHubAccounts)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        (holder.itemView.findViewById<View>(R.id.repositoryNameView) as TextView).text =
            currentItem.name
        holder.itemView.setOnClickListener {
            itemClickListener.itemClick(currentItem)
        }
    }

}

val diff_util = object : DiffUtil.ItemCallback<GitHubAccounts>() {
    override fun areItemsTheSame(oldItem: GitHubAccounts, newItem: GitHubAccounts): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: GitHubAccounts, newItem: GitHubAccounts): Boolean {
        return oldItem == newItem
    }

}

