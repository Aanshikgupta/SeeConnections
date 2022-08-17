package com.example.seeconnections

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.seeconnections.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dsuObject: DSU;
    private var firstNodeValue: Int = 0;
    private var secondNodeValue: Int = 0;


    var checkclicked: Boolean = false;
    var addClicked: Boolean = false;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //create DIjoint Set Union Object
        dsuObject = DSU(2001);



        onFirstOrSecondEditTextClick()

        onCheckConnectionButtonClicked()

        onAddButtonClicked()

        onAddIcClicked()

        onCloseIcClicked()


    }

    private fun onCheckConnectionButtonClicked() {
        binding.checkConnectionButton.setOnClickListener {

            if (validateEditText() == false) {
                return@setOnClickListener
            }

            firstNodeValue = (binding.firstNodeTextField.editText?.text.toString()).toInt();
            secondNodeValue = (binding.secondNodeTextField.editText?.text.toString()).toInt();

            var isConnectedOrNot = isConnected(firstNodeValue, secondNodeValue);

            binding.connectedNotTextView.visibility = View.VISIBLE
            binding.instructionText.visibility = View.VISIBLE

            binding.addButton.visibility = View.GONE
            binding.checkConnectionButton.visibility = View.GONE

            var str: String = "connected";
            checkclicked = true;
            if (isConnectedOrNot) {
                str = "connected"
            } else {
                str = "not connected"
            }


            binding.connectedNotTextView.setText("($firstNodeValue,$secondNodeValue) are $str nodes.")
        }
    }

    private fun onFirstOrSecondEditTextClick() {
        binding.firstNodeTextField.editText?.setOnFocusChangeListener(View.OnFocusChangeListener { view, b ->
            view.performClick()
        })
        binding.firstNodeTextField.editText?.setOnClickListener {
            emptyOnEditTextClick()
        }

        binding.secondNodeTextField.editText?.setOnFocusChangeListener(View.OnFocusChangeListener { view, b ->
            view.performClick()
        })
        binding.secondNodeTextField.editText?.setOnClickListener {
            emptyOnEditTextClick()
        }
    }

    private fun emptyOnEditTextClick() {
        if (checkclicked || addClicked) {
            emptyEditText()
            binding.connectedNotTextView.visibility = View.GONE
            binding.instructionText.visibility = View.GONE
            if (checkclicked) {
                binding.addButton.visibility = View.GONE
                binding.checkConnectionButton.visibility = View.VISIBLE
                checkclicked = false
            } else if (addClicked) {
                binding.addButton.visibility = View.VISIBLE
                binding.checkConnectionButton.visibility = View.GONE
                addClicked = false
            }

            binding.firstNodeTextField.editText?.clearFocus()
            binding.secondNodeTextField.editText?.clearFocus()
        }
    }

    private fun onAddButtonClicked() {
        binding.addButton.setOnClickListener {

            if (!validateEditText())
                return@setOnClickListener

            addClicked = true
            firstNodeValue = (binding.firstNodeTextField.editText?.text.toString()).toInt();
            secondNodeValue = (binding.secondNodeTextField.editText?.text.toString()).toInt();

            binding.connectedNotTextView.visibility = View.VISIBLE

            binding.connectedNotTextView.setText("($firstNodeValue,$secondNodeValue) are now connected.")

            binding.addButton.visibility = View.VISIBLE
            binding.checkConnectionButton.visibility = View.GONE



            dsuObject.union(firstNodeValue, secondNodeValue);

        }
    }

    private fun onAddIcClicked() {
        binding.addIcOption.setOnClickListener {


            addClicked = false
            checkclicked = false

            binding.addIcOption.visibility = View.GONE
            binding.closeIcOption.visibility = View.VISIBLE

            binding.checkConnectionButton.visibility = View.GONE
            binding.addButton.visibility = View.VISIBLE

            binding.connectedNotTextView.visibility = View.GONE
            binding.instructionText.visibility = View.GONE



            emptyEditText()

        }
    }

    private fun onCloseIcClicked() {
        binding.closeIcOption.setOnClickListener {


            addClicked = false
            checkclicked = false

            binding.addIcOption.visibility = View.VISIBLE
            binding.closeIcOption.visibility = View.GONE

            binding.checkConnectionButton.visibility = View.VISIBLE
            binding.addButton.visibility = View.GONE

            binding.connectedNotTextView.visibility = View.GONE
            binding.instructionText.visibility = View.GONE

            emptyEditText()

        }
    }

    fun emptyEditText() {
        binding.firstNodeTextField.editText?.setText("")
        binding.secondNodeTextField.editText?.setText("")

        binding.firstNodeTextField.editText?.clearFocus()
        binding.secondNodeTextField.editText?.clearFocus()
    }

    fun validateEditText(): Boolean {
        var first: String = binding.firstNodeTextField.editText?.text.toString();
        var second: String = binding.secondNodeTextField.editText?.text.toString();

        first = first.trim()
        second = second.trim()

        var flag: Boolean = true;

        if (first.isNullOrBlank() || first.length == 0) {
            flag = false;
            binding.firstNodeTextField.editText?.setError("Enter Valid data")
        }

        if (second.isNullOrBlank() || second.length == 0) {
            flag = false;
            binding.secondNodeTextField.editText?.setError("Enter Valid data")
        }

        if (!flag)
            return false

        var firstData: Int = binding.firstNodeTextField.editText?.text.toString().toInt()
        var secondData: Int = binding.secondNodeTextField.editText?.text.toString().toInt()

        if (firstData <= 0 || firstData > 2000) {
            flag = false;
            binding.firstNodeTextField.editText?.setError("Enter Valid data")
        }

        if (secondData <= 0 || secondData > 2000) {
            flag = false;
            binding.secondNodeTextField.editText?.setError("Enter Valid data")
        }

        return flag;
    }

    fun doUnion(firstNode: Int, secondNode: Int) {
        dsuObject.union(firstNode, secondNode)
    }

    fun isConnected(firstNode: Int, secondNode: Int): Boolean {
        return dsuObject.checkConnected(firstNode, secondNode)
    }
}
