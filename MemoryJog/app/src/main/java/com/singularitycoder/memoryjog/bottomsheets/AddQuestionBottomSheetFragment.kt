package com.singularitycoder.memoryjog.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.singularitycoder.memoryjog.databinding.FragmentAddQuestionBottomSheetBinding
import com.singularitycoder.memoryjog.setTransparentBackground

class AddQuestionBottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = AddQuestionBottomSheetFragment()
    }

    private lateinit var binding: FragmentAddQuestionBottomSheetBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddQuestionBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTransparentBackground()
        binding.setupUserActionListeners()
    }

    private fun FragmentAddQuestionBottomSheetBinding.setupUserActionListeners() {

    }
}