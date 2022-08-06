package com.singularitycoder.memoryjog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.singularitycoder.memoryjog.bottomsheets.MenuBottomSheetFragment
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

    private val viewModel: SharedViewModel by activityViewModels()

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
        observeForData()
    }

    private fun FragmentQuestionsBinding.setupUI() {
        etSearch.hint = "Search ${topicParam?.lowercase()} questions"
    }

    private fun FragmentQuestionsBinding.setupUserActionListeners() {
        fabMenu.setOnClickListener {
            MenuBottomSheetFragment.newInstance().show(requireActivity().supportFragmentManager, TAG_MENU_MODAL_BOTTOM_SHEET)
        }
    }

    private fun observeForData() {
        viewModel.menuLiveData.observe(viewLifecycleOwner) { it: MenuAction? ->
            it ?: return@observe
            when (it) {
                MenuAction.SHOW_HIDE_ANSWERS -> {
                    binding.root.showSnackBar("answers")
                }
                MenuAction.SHOW_HIDE_HINTS -> {
                    binding.root.showSnackBar("hints")
                }
                MenuAction.ADD_QUESTION -> {
                    binding.root.showSnackBar("add question")
                }
                MenuAction.IMPORT_QUESTION_FROM_CSV -> {
                    binding.root.showSnackBar("Import from csv")
                }
            }
        }
    }
}

private const val ARG_PARAM_TOPIC = "ARG_PARAM_TOPIC"
