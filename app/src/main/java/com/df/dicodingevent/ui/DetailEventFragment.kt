package com.df.dicodingevent.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.df.dicodingevent.R
import com.df.dicodingevent.databinding.FragmentDetailEventBinding
import androidx.core.net.toUri

class DetailEventFragment : Fragment() {

    private var _binding: FragmentDetailEventBinding? = null
    private val binding get() = _binding!!

    private val args: DetailEventFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val event = args.event
        val isFinished = args.isFinished
        val cleanedDescription = event.description
            .replace(Regex("<img[^>]*>"), "")

        binding.tvEventName.text = event.name
        binding.tvEventCategory.text = event.category
        binding.tvEventCity.text = event.cityName
        binding.tvEventQuota.text = "${event.registrants} / ${event.quota} pendaftar"
        binding.tvEventTime.text = "${event.beginTime} - ${event.endTime}"
        binding.tvEventSummary.text = event.summary
        binding.tvEventDescription.text = HtmlCompat.fromHtml(
            cleanedDescription,
            HtmlCompat.FROM_HTML_MODE_COMPACT
        )

        Glide.with(requireContext())
            .load(event.mediaCover)
            .into(binding.ivEventCover)

        if (isFinished) {
            binding.btnRegister.visibility = View.GONE
        } else {
            binding.btnRegister.visibility = View.VISIBLE
            binding.btnRegister.setOnClickListener {
                event.link.let { url ->
                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                    startActivity(intent)
                }
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.btnRegister) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val params = v.layoutParams as ViewGroup.MarginLayoutParams
            params.bottomMargin = systemBars.bottom + resources.getDimensionPixelSize(R.dimen.default_margin)
            v.layoutParams = params
            insets
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}