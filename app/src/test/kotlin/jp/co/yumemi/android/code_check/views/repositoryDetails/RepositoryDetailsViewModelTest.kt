package jp.co.yumemi.android.code_check.views.repositoryDetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import jp.co.yumemi.android.code_check.model.GitHubAccounts
import jp.co.yumemi.android.code_check.model.Owner
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@Suppress("DEPRECATION")
@RunWith(JUnit4::class)
class RepositoryDetailsViewModelTest {

    // This rule ensures that LiveData updates are executed instantly on the same thread.
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // Mock the observer for LiveData
    @Mock
    private lateinit var repositoryListObserver: Observer<GitHubAccounts>

    private lateinit var viewModel: RepositoryDetailsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = RepositoryDetailsViewModel()
        viewModel.repositoryList.observeForever(repositoryListObserver)
    }

    /**
     * Test the behavior of [RepositoryDetailsViewModel.loadRepositoryList].
     */
    @Test
    fun testLoadRepositoryList() {
        // Create a mock GitHub repository
        val mockRepository = GitHubAccounts(
            name = "Repository Name",
            owner = Owner("https://avatars.githubusercontent.com/u/20487725?v=4"),
            safeLanguage = "Kotlin",
            stargazersCount = 100,
            watchersCount = 200,
            forksCount = 50,
            openIssuesCount = 10
        )

        // Call the function to load the repository data
        viewModel.loadRepositoryList(mockRepository)

        // Verify that the LiveData was updated with the expected repository data
        Mockito.verify(repositoryListObserver).onChanged(mockRepository)
    }
}