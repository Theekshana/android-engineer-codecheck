/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.databinding.RepoDetailsFragmentBinding

/**
 * Fragment for displaying details of a GitHub repository.
 */
class RepositoryDetailsFragment : Fragment() {

    private lateinit var binding: RepoDetailsFragmentBinding
    private val args: RepositoryDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RepoDetailsFragmentBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listItem = args.item

        binding.ownerIconView.load(listItem.owner?.avatarUrl)
        binding.nameView.text = listItem.name
        binding.languageView.text = listItem.language
        binding.starsView.text = "${listItem.stargazersCount} stars"
        binding.watchersView.text = "${listItem.watchersCount} watchers"
        binding.forksView.text = "${listItem.forksCount} forks"
        binding.openIssuesView.text = "${listItem.openIssuesCount} open issues"
    }
}
