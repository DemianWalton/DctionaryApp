package com.dictionary

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.dictionary.core.util.DataState
import com.dictionary.databinding.ActivityMainBinding
import com.dictionary.feature_dictionary.presentation.WordInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: WordInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.etSearch.doAfterTextChanged { e ->
            e?.let {
                viewModel.onSearch(e.toString())
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.searchQuery.collect { event ->
                when (event) {
                    is DataState.Success -> {
                        binding.pbLoading.isVisible = false
                        Log.i("TAG", "collect: Success $event")
                        try {
                            binding.tvTest.text = event.result[0].word
                            binding.tvTest2.text =
                                event.result[0].meanings[0].definitions[0].definition
                        } catch (ignored: Exception) {
                        }

                    }
                    is DataState.Failure -> {
                        binding.pbLoading.isVisible = false
                        Log.i("TAG", "collect: Failure $event")
                        try {
                            binding.tvTest.text = event.result[0].word
                            binding.tvTest2.text =
                                event.result[0].meanings[0].definitions[0].definition
                        } catch (ignored: Exception) {
                        }
                    }
                    is DataState.Loading -> {
                        Log.i("TAG", "collect: Loading $event")
                        binding.pbLoading.isVisible = true
                        try {
                            binding.tvTest.text = event.result[0].word
                            binding.tvTest2.text =
                                event.result[0].meanings[0].definitions[0].definition
                        } catch (ignored: Exception) {
                        }
                    }
                    else -> Unit
                }
            }
        }
    }
}