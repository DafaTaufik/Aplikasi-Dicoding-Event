package com.df.dicodingevent.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.df.dicodingevent.databinding.FragmentHomeBinding
import com.df.dicodingevent.ui.adapter.EventAdapter
import com.df.dicodingevent.ui.adapter.FinishedEventAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel
    private val eventAdapter = EventAdapter()
    private val finishedEventAdapter = FinishedEventAdapter()

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
            adapter = eventAdapter
        }
        binding.recyclerViewFinishedEvents.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = finishedEventAdapter
        }
    }

    private fun observeViewModel() {
        homeViewModel.upcomingEvents.observe(viewLifecycleOwner) { events ->
            eventAdapter.submitList(events)
        }
        homeViewModel.finishedEvents.observe(viewLifecycleOwner) { events ->
            finishedEventAdapter.submitList(events)
        }
        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            // TODO: progress bar
        }
        homeViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            message?.let { /* TODO: toast/snackbar */ }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}