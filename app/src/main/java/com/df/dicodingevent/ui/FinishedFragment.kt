package com.df.dicodingevent.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.df.dicodingevent.databinding.FragmentFinishedBinding
import com.df.dicodingevent.ui.adapter.EventAdapter

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!

    private lateinit var finishedViewModel: FinishedViewModel
    private val eventAdapter = EventAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        finishedViewModel = FinishedViewModel()

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView(){
        binding.recyclerViewFinishedEventList.apply {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
            adapter = eventAdapter
        }
    }

    private fun observeViewModel(){
        finishedViewModel.finishedEvents.observe(viewLifecycleOwner) { events ->
            eventAdapter.submitList(events)
        }
        finishedViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            // TODO: progress bar
        }
        finishedViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            message?.let { /* TODO: toast/snackbar */ }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}