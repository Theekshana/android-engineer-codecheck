package jp.co.yumemi.android.code_check.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.databinding.RepositoryListItemBinding
import jp.co.yumemi.android.code_check.model.GitHubAccounts

/**
 * GitHubAccountAdapter for displaying GitHub account items.
 */
class GitHubAccountAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<GitHubAccounts, GitHubAccountAdapter.ViewHolder>(diff_util) {

    inner class ViewHolder(
        val viewBinding: RepositoryListItemBinding
    ) : RecyclerView.ViewHolder(viewBinding.root)

    interface OnItemClickListener {
        fun itemClick(item: GitHubAccounts)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            RepositoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = getItem(position)

        holder.viewBinding.repositoryNameView.text = currentItem.name
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

