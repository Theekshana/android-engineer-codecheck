/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentTwoBinding

class RepositoryDetailsFragment : Fragment(R.layout.fragment_two) {

    private val args: RepositoryDetailsFragmentArgs by navArgs()

    private var binding: FragmentTwoBinding? = null
    private val _binding get() = binding!!

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTwoBinding.bind(view)

        val listItem = args.item

        _binding.ownerIconView.load(listItem.ownerIconUrl)
        _binding.nameView.text = listItem.name
        _binding.languageView.text = listItem.language
        _binding.starsView.text = "${listItem.stargazersCount} stars"
        _binding.watchersView.text = "${listItem.watchersCount} watchers"
        _binding.forksView.text = "${listItem.forksCount} forks"
        _binding.openIssuesView.text = "${listItem.openIssuesCount} open issues"
    }
}
