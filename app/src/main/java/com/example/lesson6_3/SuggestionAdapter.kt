package com.example.lesson6_3

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson6_3.databinding.ItemSuggestionBinding

class SuggestionAdapter(
    private val list: ArrayList<String>,
    private val clickListener: (String) -> Unit
) :
    RecyclerView.Adapter<SuggestionAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {

        return MessageViewHolder(
            ItemSuggestionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {

        holder.onBind(list[position])

    }

    override fun getItemCount() = list.size

    inner class MessageViewHolder(private val binding: ItemSuggestionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(text: String) {
            binding.tvHashTags.text = text
            itemView.setOnClickListener {
                clickListener(text)
            }
        }
    }
}