package com.singularitycoder.memoryjog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.singularitycoder.memoryjog.databinding.ListItemQuestionBinding

class QuestionsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var questionList = emptyList<Question>()
    private var questionClickListener: (question: Question) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = ListItemQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as QuestionViewHolder).setData(questionList[position])
    }

    override fun getItemCount(): Int = questionList.size

    override fun getItemViewType(position: Int): Int = position

    fun setOnQuestionClickListener(listener: (question: Question) -> Unit) {
        questionClickListener = listener
    }

    inner class QuestionViewHolder(
        private val itemBinding: ListItemQuestionBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun setData(question: Question) {
            itemBinding.apply {
                tvQuestion.text = question.question
                tvHint.text = question.hint
                tvAnswer.text = question.answer

                tvHint.isVisible = question.isHintVisible
                ivHint.isVisible = question.isHintVisible

                tvAnswer.isVisible = question.isAnswerVisible
                ivAnswer.isVisible = question.isAnswerVisible

                root.setOnClickListener {
                    questionClickListener.invoke(question)
                }
            }
        }
    }
}
