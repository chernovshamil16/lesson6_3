package com.example.lesson6_3

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lesson6_3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var hashTags = arrayListOf<String>()
    private var suggestionAdapter = SuggestionAdapter(hashTags, this::clickListener)
    private var oldHashTag = ""
    private var oldPositionCursor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        savedHashTagsEditText()
        initListener()
    }

    private fun initListener() {
        binding.etInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val words = text.toString().replace(",", " ").split(" ")

                for (word in words) {
                    if (word.startsWith("#")) {
                        oldHashTag = word

                        val cursorPosition: Int = binding.etInput.selectionEnd
                        oldPositionCursor = cursorPosition
                    }
                    binding.rvSuggestion.isVisible = word.startsWith("#")
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun initAdapter() {
        binding.rvSuggestion.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = suggestionAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun savedHashTagsEditText() {
        binding.btnSend.setOnClickListener {
            if (binding.etInput.text.isNotEmpty()) {
                val suggestions = binding.etInput.text
                val words: List<String> = suggestions.toString().replace(",", " ").split(" ")

                for (word in words) {
                    if (word.startsWith("#")) {
                        hashTags.add(word)
                    }
                }

                binding.etInput.text.clear()
                suggestionAdapter.notifyDataSetChanged()

            } else {
                Toast.makeText(this, "Your input is empty!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun clickListener(hashTag: String) {
        binding.rvSuggestion.isVisible = false
        binding.etInput.setText(binding.etInput.text.toString().replace(oldHashTag, hashTag))
        binding.etInput.setSelection(binding.etInput.text.length)
    }
}