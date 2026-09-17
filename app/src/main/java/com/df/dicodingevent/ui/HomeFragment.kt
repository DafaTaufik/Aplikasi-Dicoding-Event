package com.df.dicodingevent.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.df.dicodingevent.databinding.FragmentHomeBinding
import com.df.dicodingevent.ui.adapter.EventCardAdapter
import com.df.dicodingevent.ui.adapter.EventAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel
    private val eventCardAdapter = EventCardAdapter()
    private val eventAdapter = EventAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewUpcomingEvents.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = eventCardAdapter
        }
        binding.recyclerViewFinishedEvents.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventAdapter
        }
    }

    private fun observeViewModel() {
        homeViewModel.upcomingEvents.observe(viewLifecycleOwner) { events ->
            eventCardAdapter.submitList(events)
        }
        homeViewModel.finishedEvents.observe(viewLifecycleOwner) { events ->
            eventAdapter.submitList(events)
        }
        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showShimmer(isLoading)
        }
        homeViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            message?.let { /* TODO: toast/snackbar */ }
        }
    }

    private fun showShimmer(isLoading: Boolean) {
        if (isLoading) {
            binding.shimmerUpcomingEvents.startShimmer()
            binding.shimmerUpcomingEvents.visibility = View.VISIBLE
            binding.recyclerViewUpcomingEvents.visibility = View.GONE

            binding.shimmerFinishedEvents.startShimmer()
            binding.shimmerFinishedEvents.visibility = View.VISIBLE
            binding.recyclerViewFinishedEvents.visibility = View.GONE
        } else {
            binding.shimmerUpcomingEvents.stopShimmer()
            binding.shimmerUpcomingEvents.visibility = View.GONE
            binding.recyclerViewUpcomingEvents.visibility = View.VISIBLE

            binding.shimmerFinishedEvents.stopShimmer()
            binding.shimmerFinishedEvents.visibility = View.GONE
            binding.recyclerViewFinishedEvents.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.shimmerUpcomingEvents.stopShimmer()
        binding.shimmerFinishedEvents.stopShimmer()
        _binding = null
    }
}