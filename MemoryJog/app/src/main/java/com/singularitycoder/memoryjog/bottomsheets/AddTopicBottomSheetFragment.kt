package com.singularitycoder.memoryjog.bottomsheets

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.singularitycoder.memoryjog.MainActivity
import com.singularitycoder.memoryjog.databinding.FragmentAddTopicBottomSheetBinding
import com.singularitycoder.memoryjog.dpToPx
import com.singularitycoder.memoryjog.setTransparentBackground
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTopicBottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = AddTopicBottomSheetFragment()
    }

    private lateinit var binding: FragmentAddTopicBottomSheetBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddTopicBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTransparentBackground()
        binding.setupUserActionListeners()
    }

    private fun FragmentAddTopicBottomSheetBinding.setupUserActionListeners() {
        etTopicName.editText?.doAfterTextChanged { it: Editable? ->
            if (etTopicName.editText?.text.isNullOrBlank()) {
                etTopicName.error = "This is required!"
            } else {
                etTopicName.error = null
            }
        }
        etTopicName.editText?.setOnFocusChangeListener { view, isFocused ->
            etTopicName.boxStrokeWidth = if (isFocused) 2.dpToPx() else 0
        }
        btnAddTopic.setOnClickListener {
            if (etTopicName.editText?.text.isNullOrBlank()){
                etTopicName.boxStrokeWidth = 2.dpToPx()
                etTopicName.error = "This is required!"
                return@setOnClickListener
            }
            (activity as? MainActivity)?.addTopic(topic = etTopicName.editText?.text.toString())
            dismiss()
        }
    }
}