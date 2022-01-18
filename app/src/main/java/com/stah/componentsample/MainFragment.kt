package com.stah.componentsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stah.componentsample.MainActivity.Companion.ARG_OBJECT


class ItemViewModel : ViewModel() {
    private val mutableSelectedItem = MutableLiveData<Boolean>()
    val selectedItem: LiveData<Boolean> get() = mutableSelectedItem

    fun tabJump() {
        mutableSelectedItem.value = false
    }
}

class MainFragment : Fragment() {

    private val viewModel: ItemViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.slide_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            val textView: TextView = view.findViewById(R.id.text)
            textView.text = getInt(ARG_OBJECT).toString()
        }

        view.findViewById<Button>(R.id.button).setOnClickListener {
            viewModel.tabJump()
        }

    }

}


