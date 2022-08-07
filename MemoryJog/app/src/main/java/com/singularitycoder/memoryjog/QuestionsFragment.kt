package com.singularitycoder.memoryjog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.singularitycoder.memoryjog.bottomsheets.AddQuestionBottomSheetFragment
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
    private val questionsAdapter = QuestionsAdapter()
    private val questionList = mutableListOf<Question>()

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
        rvQuestions.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = questionsAdapter
        }
    }

    private fun FragmentQuestionsBinding.setupUserActionListeners() {
        fabMenu.setOnClickListener {
            MenuBottomSheetFragment.newInstance().show(requireActivity().supportFragmentManager, TAG_MENU_MODAL_BOTTOM_SHEET)
        }
        questionsAdapter.setOnQuestionClickListener { it: Question ->
        }
    }

    private fun observeForData() {
        viewModel.menuLiveData.observe(viewLifecycleOwner) { it: MenuAction? ->
            it ?: return@observe
            when (it) {
                MenuAction.SHOW_HIDE_ANSWERS -> {
                    questionsAdapter.questionList = questionList.map {
                        it.isAnswerVisible = it.isAnswerVisible.not()
                        it
                    }
                    questionsAdapter.notifyDataSetChanged()
                }
                MenuAction.SHOW_HIDE_HINTS -> {
                    questionsAdapter.questionList = questionList.map {
                        it.isHintVisible = it.isHintVisible.not()
                        it
                    }
                    questionsAdapter.notifyDataSetChanged()
                }
                MenuAction.ADD_QUESTION -> {
                    AddQuestionBottomSheetFragment.newInstance().show(requireActivity().supportFragmentManager, TAG_ADD_QUESTION_MODAL_BOTTOM_SHEET)
                }
                MenuAction.IMPORT_QUESTION_FROM_CSV -> {
                    binding.root.showSnackBar("Import from csv")
                }
            }
        }

        viewModel.questionLiveData.observe(viewLifecycleOwner) { it: Question? ->
            it ?: return@observe
            questionsAdapter.questionList = questionList.apply { add(it) }
            questionsAdapter.notifyItemInserted(if (questionList.isEmpty()) 0 else questionList.lastIndex)
        }
    }
}

private const val ARG_PARAM_TOPIC = "ARG_PARAM_TOPIC"
