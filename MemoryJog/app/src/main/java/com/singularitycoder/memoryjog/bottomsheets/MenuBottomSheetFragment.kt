package com.singularitycoder.memoryjog.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.singularitycoder.memoryjog.MenuAction
import com.singularitycoder.memoryjog.R
import com.singularitycoder.memoryjog.SharedViewModel
import com.singularitycoder.memoryjog.databinding.FragmentMenuBottomSheetBinding

class MenuBottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = MenuBottomSheetFragment()
    }

    private lateinit var binding: FragmentMenuBottomSheetBinding

    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // https://stackoverflow.com/questions/37104960/bottomsheetdialog-with-transparent-background
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMenuBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvShowHideAnswers.setOnClickListener {
                viewModel.menuLiveData.value = MenuAction.SHOW_HIDE_ANSWERS
                dismiss()
            }
            tvShowHideHints.setOnClickListener {
                viewModel.menuLiveData.value = MenuAction.SHOW_HIDE_HINTS
                dismiss()
            }
            tvAddQuestion.setOnClickListener {
                viewModel.menuLiveData.value = MenuAction.ADD_QUESTION
                dismiss()
            }
            tvImportQuestionsCsv.setOnClickListener {
                viewModel.menuLiveData.value = MenuAction.IMPORT_QUESTION_FROM_CSV
                dismiss()
            }
        }
    }
}