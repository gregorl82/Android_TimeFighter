package com.example.android.timeflighter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    internal var score = 0

    internal lateinit var tapMeButton: Button
    internal lateinit var gameScoreTextView: TextView
    internal lateinit var timerTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tapMeButton = findViewById(R.id.btn_tap_button)
        gameScoreTextView = findViewById(R.id.tv_score)
        timerTextView = findViewById(R.id.tv_timer)

        val startScore = getString(R.string.tv_score_text, score)
        gameScoreTextView.text = startScore

        tapMeButton.setOnClickListener { view ->
            incrementScore()
        }
    }

    private fun incrementScore() {
        score += 1
        val newScore = getString(R.string.tv_score_text, score)
        gameScoreTextView.text = newScore;
    }
}
