package org.hyperskill.secretdiary

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.hyperskill.secretdiary.databinding.ActivityMainBinding
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.number

const val PREFERENCES_NAME = "PREF_DIARY"
const val PREFERENCES_KEY = "KEY_DIARY_TEXT"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editText: EditText
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editText = binding.etNewWriting
        textView = binding.tvDiary

        sharedPreferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val diaryText = sharedPreferences.getString(PREFERENCES_KEY, "")
        textView.text = diaryText

        fun removeLastEntry() {
            val currentText = textView.text.toString()
            if (currentText.contains("\n\n")) {
                textView.text = currentText.substringAfter("\n\n")
            } else {
                textView.text = ""
            }
            editor.putString(PREFERENCES_KEY, textView.text.toString()).apply()
        }

        binding.btnUndo.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.confirm_undo))
                .setMessage(getString(R.string.confirm_undo_message))
                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                    removeLastEntry()
                }
                .setNegativeButton(getString(R.string.no), null)
                .show()
        }

        binding.btnSave.setOnClickListener {
            if (editText.text.isNotEmpty() && editText.text.isNotBlank()) {
                val currentInstant = Clock.System.now()
                val zonedDateTime = currentInstant
                    .toLocalDateTime(TimeZone.currentSystemDefault())
                val year = zonedDateTime.year.toString().padStart(4, '0')
                val month = zonedDateTime.month.number.toString().padStart(2, '0')
                val day = zonedDateTime.dayOfMonth.toString().padStart(2, '0')
                val hour = zonedDateTime.hour.toString().padStart(2, '0')
                val minute = zonedDateTime.minute.toString().padStart(2, '0')
                val second = zonedDateTime.second.toString().padStart(2, '0')
                val dateTimeText = "$year-$month-$day $hour:$minute:$second"
                val newWriting = "$dateTimeText\n${editText.text}"

                if (textView.text.isEmpty()) {
                    textView.text = newWriting
                } else {
                    textView.text = "$newWriting\n\n${textView.text}"
                }
                editText.text.clear()
                editor.putString(PREFERENCES_KEY, textView.text.toString()).apply()
            } else {
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
