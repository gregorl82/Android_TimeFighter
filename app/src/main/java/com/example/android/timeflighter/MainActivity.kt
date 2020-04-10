package com.example.android.timeflighter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    internal var score = 0

    internal lateinit var tapMeButton: Button
    internal lateinit var gameScoreTextView: TextView
    internal lateinit var timerTextView: TextView

    internal var gameStarted = false

    internal lateinit var countDownTimer: CountDownTimer
    internal var initialCountDown: Long = 60000
    internal var countDownInterval: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tapMeButton = findViewById(R.id.btn_tap_button)
        gameScoreTextView = findViewById(R.id.tv_score)
        timerTextView = findViewById(R.id.tv_timer)

        tapMeButton.setOnClickListener { view ->
            incrementScore()
        }

        resetGame()
    }

    private fun resetGame() {
        score = 0
        
        gameScoreTextView.text = getString(R.string.tv_score_text, score)

        val initialTimeLeft = initialCountDown / 1000
        timerTextView.text = getString(R.string.tv_timer_text, initialTimeLeft)

        countDownTimer = object : CountDownTimer(initialCountDown, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = millisUntilFinished / 1000
                timerTextView.text = getString(R.string.tv_timer_text, timeLeft)
            }

            override fun onFinish() {
                endGame()
            }
        }

        gameStarted = false
    }

    private fun incrementScore() {
        if (!gameStarted) {
            startGame()
        }

        score += 1
        val newScore = getString(R.string.tv_score_text, score)
        gameScoreTextView.text = newScore;
    }

    private fun startGame() {
        countDownTimer.start()
        gameStarted = true
    }

    private fun endGame() {
        Toast.makeText(this, getString(R.string.finish_message, score), Toast.LENGTH_LONG).show()
        resetGame()
    }
}
