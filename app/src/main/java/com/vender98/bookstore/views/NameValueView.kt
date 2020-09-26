package com.vender98.bookstore.views

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.vender98.bookstore.R

class NameValueView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val nameTextView: TextView
    private val valueTextView: TextView

    init {
        inflate(context, R.layout.view_name_value, this)

        nameTextView = findViewById(R.id.view_name_value_name)
        valueTextView = findViewById(R.id.view_name_value_value)

        context.withStyledAttributes(attrs, R.styleable.NameValueView, defStyleAttr, 0) {
            val name = getString(R.styleable.NameValueView_name)
                ?: throw RuntimeException("value should be specified")
            val value = getString(R.styleable.NameValueView_value)

            nameTextView.text = name
            valueTextView.text = value
        }
    }

    fun setValueOrMakeGone(value: String?) {
        valueTextView.text = value
        isVisible = value != null
    }

}
