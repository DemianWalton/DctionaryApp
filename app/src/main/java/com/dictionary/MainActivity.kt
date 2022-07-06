package com.dictionary

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.dictionary.core.util.DataState
import com.dictionary.databinding.ActivityMainBinding
import com.dictionary.feature_dictionary.data.local.entity.Car
import com.dictionary.feature_dictionary.data.local.entity.Component
import com.dictionary.feature_dictionary.presentation.CarInfoViewModel
import com.dictionary.feature_dictionary.presentation.WordInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: WordInfoViewModel by viewModels()
    private val viewModelCars: CarInfoViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.etSearch.doAfterTextChanged { e ->
            e?.let {
                viewModel.onSearch(e.toString(), getCar())
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.searchQuery.collect { event ->
                when (event) {
                    is DataState.Success -> {
                        binding.pbLoading.isVisible = false
                        //binding.tvTest.text = event.result[0].word
                        //binding.tvTest2.text =                            event.result[0].meanings[0].definitions[0].definition
                    }
                    is DataState.Failure -> {
                        binding.pbLoading.isVisible = false
                        //binding.tvTest.text = event.result[0].word
                        //binding.tvTest2.text =                            event.result[0].meanings[0].definitions[0].definition
                    }
                    is DataState.Loading -> {
                        binding.pbLoading.isVisible = true
                        //binding.tvTest.text = event.result[0].word
                        //binding.tvTest2.text =                            event.result[0].meanings[0].definitions[0].definition
                    }
                    else -> Unit
                }
            }
            /*viewModelCars.searchCar.collect { event ->
                when (event) {
                    is CarState.Success -> {
                        binding.pbLoading.isVisible = false
                    }
                    is CarState.Failure -> {
                        binding.pbLoading.isVisible = false
                    }
                    is CarState.Loading -> {
                        binding.pbLoading.isVisible = true

                    }
                    else -> Unit
                }
            }*/

        }



        binding.carBtn.setOnClickListener {
            //WordsDatabase.getDatabase(this)
            val text = getRandomString(5)
            var componentList = mutableListOf<Component>()
            val componen1 = Component(text, text, Date().toString())
            val componen2 = Component(text, text, Date().toString())
            componentList.add(componen1)
            componentList.add(componen2)
            val Car = Car(0, text, componentList)
            viewModelCars.onInsertCar(Car)
        }
    }

    fun getCar(): Car {
        val text = getRandomString(5)
        var componentList = mutableListOf<Component>()
        val componen1 = Component(text, text, Date().toString())
        val componen2 = Component(text, text, Date().toString())
        componentList.add(componen1)
        componentList.add(componen2)
        return Car(0, text, componentList)
    }

    companion object {
        private val ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm"
    }

    private fun getRandomString(sizeOfRandomString: Int): String {
        val random = Random()
        val sb = StringBuilder(sizeOfRandomString)
        for (i in 0 until sizeOfRandomString)
            sb.append(ALLOWED_CHARACTERS[random.nextInt(ALLOWED_CHARACTERS.length)])
        return sb.toString()
    }

}
