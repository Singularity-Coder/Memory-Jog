package com.singularitycoder.memoryjog.bottomsheets

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import com.singularitycoder.memoryjog.Question
import com.singularitycoder.memoryjog.SharedViewModel
import com.singularitycoder.memoryjog.databinding.FragmentAddQuestionBottomSheetBinding
import com.singularitycoder.memoryjog.dpToPx
import com.singularitycoder.memoryjog.setTransparentBackground

class AddQuestionBottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = AddQuestionBottomSheetFragment()
    }

    private val viewModel: SharedViewModel by activityViewModels()

    private lateinit var binding: FragmentAddQuestionBottomSheetBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddQuestionBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTransparentBackground()
        binding.setupUserActionListeners()
        binding.observeForData()
    }

    private fun FragmentAddQuestionBottomSheetBinding.setupUserActionListeners() {
        etQuestion.doTextFieldEmptyValidation()
        etQuestion.setBoxStrokeOnFocusChange()

        etHint.doTextFieldEmptyValidation()
        etHint.setBoxStrokeOnFocusChange()

        etAnswer.doTextFieldEmptyValidation()
        etAnswer.setBoxStrokeOnFocusChange()

        btnAddQuestion.setOnClickListener {
            if (isValidateInput().not()) return@setOnClickListener
            viewModel.questionAccidentBackupLiveData.value = null
            viewModel.questionLiveData.value = viewModel.questionLiveData.value?.copy(
                question = etQuestion.editText?.text.toString(),
                hint = etHint.editText?.text.toString(),
                answer = etAnswer.editText?.text.toString()
            )
            dismiss()
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.questionAccidentBackupLiveData.value = viewModel.questionLiveData.value?.copy(
                    question = etQuestion.editText?.text.toString(),
                    hint = etHint.editText?.text.toString(),
                    answer = etAnswer.editText?.text.toString()
                )
            }
        })
    }

    private fun FragmentAddQuestionBottomSheetBinding.observeForData() {
        viewModel.questionAccidentBackupLiveData.observe(viewLifecycleOwner) { it: Question? ->
            it ?: return@observe
            etQuestion.editText?.setText(viewModel.questionAccidentBackupLiveData.value?.question)
            etHint.editText?.setText(viewModel.questionAccidentBackupLiveData.value?.hint)
            etAnswer.editText?.setText(viewModel.questionAccidentBackupLiveData.value?.answer)
        }
    }

    private fun FragmentAddQuestionBottomSheetBinding.isValidateInput(): Boolean {
        if (etQuestion.editText?.text.isNullOrBlank()) {
            etQuestion.boxStrokeWidth = 2.dpToPx()
            etQuestion.error = "This is required!"
            return false
        }

        if (etHint.editText?.text.isNullOrBlank()) {
            etHint.boxStrokeWidth = 2.dpToPx()
            etHint.error = "This is required!"
            return false
        }

        if (etAnswer.editText?.text.isNullOrBlank()) {
            etAnswer.boxStrokeWidth = 2.dpToPx()
            etAnswer.error = "This is required!"
            return false
        }

        return true
    }

    private fun TextInputLayout.doTextFieldEmptyValidation() {
        editText?.doAfterTextChanged { it: Editable? ->
            if (editText?.text.isNullOrBlank()) {
                error = "This is required!"
            } else {
                error = null
                isErrorEnabled = false
            }
        }
    }

    private fun TextInputLayout.setBoxStrokeOnFocusChange() {
        editText?.setOnFocusChangeListener { view, isFocused ->
            boxStrokeWidth = if (isFocused) 2.dpToPx() else 0
        }
    }
}