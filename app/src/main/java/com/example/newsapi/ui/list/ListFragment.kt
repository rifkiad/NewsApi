package com.example.newsapi.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapi.R
import com.example.newsapi.data.response.NewsResponse
import com.example.newsapi.databinding.FragmentListBinding
import com.example.newsapi.ui.list.adapter.ListNewsAdapter
import com.example.newsapi.viewModel.ListViewModel
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.launch

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListViewModel by viewModels()
    private lateinit var adapter: ListNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)

        setupSharedAxisTransitions()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAppBar()
        initRecyclerView()
        fetchData()
    }

    private fun setupSharedAxisTransitions() {
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    }

    private fun setupAppBar() {
        binding.topAppBar.setOnMenuItemClickListener { menu ->
            when (menu.itemId) {
                R.id.action_profil -> {
                    navigateToProfile()
                    true
                }
                else -> false
            }
        }
    }

    private fun initRecyclerView() {
        adapter = ListNewsAdapter()
        binding.rvListUser.layoutManager = LinearLayoutManager(requireContext())
        binding.rvListUser.adapter = adapter
    }

    private fun fetchData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getNewsData("Apple")
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.data.observe(viewLifecycleOwner) { newsResponse ->
            displayNewsData(newsResponse)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }
    }

    private fun displayNewsData(newsResponse: NewsResponse) {
        adapter.setData(newsResponse.articles ?: emptyList())
    }

    private fun showLoading(loading: Boolean) {
        binding.spinKit.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun navigateToProfile() {
        findNavController().navigate(R.id.action_listFragment_to_profileFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
