package com.singularitycoder.memoryjog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.singularitycoder.memoryjog.databinding.FragmentQuestionsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionsFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(topic: String) = QuestionsFragment().apply {
            arguments = Bundle().apply { putString(ARG_PARAM_TOPIC, topic) }
        }
    }

    private lateinit var binding: FragmentQuestionsBinding

    private var topicParam: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topicParam = arguments?.getString(ARG_PARAM_TOPIC)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentQuestionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setupUI()
        binding.setupUserActionListeners()
    }

    private fun FragmentQuestionsBinding.setupUI() {
        etSearch.hint = "Search ${topicParam?.lowercase()} questions"
    }

    private fun FragmentQuestionsBinding.setupUserActionListeners() {
        fabMenu.setOnClickListener {
            MenuBottomSheetFragment.newInstance().show(requireActivity().supportFragmentManager, TAG_MODAL_BOTTOM_SHEET)
        }
    }
}

private const val ARG_PARAM_TOPIC = "ARG_PARAM_TOPIC"
private const val TAG_MODAL_BOTTOM_SHEET = "TAG_MODAL_BOTTOM_SHEET"