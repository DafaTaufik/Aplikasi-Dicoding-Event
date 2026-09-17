package com.df.dicodingevent.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.fragment.app.Fragment
import com.df.dicodingevent.databinding.FragmentUpcomingBinding
import com.df.dicodingevent.ui.adapter.EventAdapter

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!

    private lateinit var upcomingViewModel: UpcomingViewModel
    private val eventAdapter = EventAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        upcomingViewModel = UpcomingViewModel()

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewUpcomingEventList.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = eventAdapter
        }
    }

    private fun observeViewModel(){
        upcomingViewModel.upcomingEvents.observe(viewLifecycleOwner) { events ->
            eventAdapter.submitList(events)
        }
        upcomingViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showShimmer(isLoading)
        }
        upcomingViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            message?.let { /* TODO: toast/snackbar */ }
        }
    }

    private fun showShimmer(isLoading: Boolean) {
        if (isLoading) {
            binding.shimmerUpcomingEvents.startShimmer()
            binding.shimmerUpcomingEvents.visibility = View.VISIBLE
            binding.recyclerViewUpcomingEventList.visibility = View.GONE
        } else {
            binding.shimmerUpcomingEvents.stopShimmer()
            binding.shimmerUpcomingEvents.visibility = View.GONE
            binding.recyclerViewUpcomingEventList.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.shimmerUpcomingEvents.stopShimmer()
        _binding = null
    }
}
