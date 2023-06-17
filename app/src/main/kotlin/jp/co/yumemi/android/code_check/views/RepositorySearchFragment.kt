/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import jp.co.yumemi.android.code_check.OneViewModel
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentOneBinding
import jp.co.yumemi.android.code_check.model.GitHubAccounts
import jp.co.yumemi.android.code_check.util.GitHubAccountAdapter


class OneFragment: Fragment(R.layout.fragment_one){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        val _binding= FragmentOneBinding.bind(view)

        val _viewModel= OneViewModel(context!!)

        val _layoutManager= LinearLayoutManager(context!!)
        val _dividerItemDecoration=
            DividerItemDecoration(context!!, _layoutManager.orientation)
        val _adapter= GitHubAccountAdapter(object : GitHubAccountAdapter.OnItemClickListener {
            override fun itemClick(item: GitHubAccounts){
                gotoRepositoryFragment(item)
            }
        })

        _binding.searchInputText
            .setOnEditorActionListener{ editText, action, _ ->
                if (action== EditorInfo.IME_ACTION_SEARCH){
                    editText.text.toString().let {
                        _viewModel.searchResults(it).apply{
                            _adapter.submitList(this)
                        }
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        _binding.recyclerView.also{
            it.layoutManager= _layoutManager
            it.addItemDecoration(_dividerItemDecoration)
            it.adapter= _adapter
        }
    }

    fun gotoRepositoryFragment(item: GitHubAccounts)
    {
        val _action=
            OneFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(item = item)
        findNavController().navigate(_action)
    }
}

val diff_util= object: DiffUtil.ItemCallback<GitHubAccounts>(){
    override fun areItemsTheSame(oldItem: GitHubAccounts, newItem: GitHubAccounts): Boolean
    {
        return oldItem.name== newItem.name
    }

    override fun areContentsTheSame(oldItem: GitHubAccounts, newItem: GitHubAccounts): Boolean
    {
        return oldItem== newItem
    }

}


