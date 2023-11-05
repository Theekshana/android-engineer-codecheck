/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.views.repositoryDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.RepoDetailsFragmentBinding
import timber.log.Timber


/**
 * Fragment for displaying details of a GitHub repository.
 */
class RepositoryDetailsFragment : Fragment() {

    init {
        Timber.tag("RepositoryDetailsFragment")
    }

    private var binding: RepoDetailsFragmentBinding? = null
    private val args: RepositoryDetailsFragmentArgs by navArgs()
    private lateinit var viewModel: RepositoryDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RepoDetailsFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[RepositoryDetailsViewModel::class.java]

        binding?.item = viewModel
        binding?.lifecycleOwner = this

        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRepositoryDetails()
        showAvatarImage()

    }

    /**
     * Set up the details of the GitHub repository.
     */
    private fun setupRepositoryDetails() {
        val item = args.item
        viewModel.loadRepositoryList(item)
        Timber.d("Item: $item")
    }

    /**
     * Show the avatar image of the GitHub repository's owner.
     */
    private fun showAvatarImage() {
        viewModel.repositoryList.observe(viewLifecycleOwner) { imageResult ->
            imageResult?.let {
                val imageUrl = it.owner?.avatarUrl
                if (!imageUrl.isNullOrBlank()) {
                    binding?.ownerIconView?.let { it1 ->
                        Glide.with(this)
                            .load(imageUrl)
                            .into(it1)
                    }
                } else {
                    binding?.ownerIconView?.let { it1 ->
                        Glide.with(this)
                            .load(R.drawable.ic_image_placeholder)
                            .into(it1)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
