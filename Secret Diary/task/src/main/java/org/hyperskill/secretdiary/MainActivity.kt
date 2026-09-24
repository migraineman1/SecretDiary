package org.hyperskill.secretdiary

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.annotation.RequiresApi
import org.hyperskill.secretdiary.databinding.ActivityMainBinding
import kotlin.time.ExperimentalTime
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    lateinit var editText: EditText
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            editText = binding.etNewWriting
            textView = binding.tvDiary

            if(editText.text.isNotEmpty() && editText.text.isNotBlank()) {
                textView.text = editText.text.toString() + "\n\n" + textView.text.toString()
                editText.text.clear()
            }
            else {
                Toast.makeText(this, getString(R.string.empty_input), Toast.LENGTH_SHORT).show()
            }
        }

        /*
            Tests for android can not guarantee the correctness of solutions that make use of
            mutation on "static" variables to keep state. You should avoid using those.
            Consider "static" as being anything on kotlin that is transpiled to java
            into a static variable. That includes global variables and variables inside
            singletons declared with keyword object, including companion object.
            This limitation is related to the use of JUnit on tests. JUnit re-instantiate all
            instance variable for each test method, but it does not re-instantiate static variables.
            The use of static variable to hold state can lead to state from one test to spill over
            to another test and cause unexpected results.
            Using mutation on static variables to keep state
            is considered a bad practice anyway and no measure
            attempting to give support to that pattern will be made.
         */
    }
}