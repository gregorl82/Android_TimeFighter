package com.example.android.timeflighter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    internal var score = 0

    internal lateinit var tapMeButton: Button
    internal lateinit var gameScoreTextView: TextView
    internal lateinit var timerTextView: TextView

    internal var gameStarted = false

    internal lateinit var countDownTimer: CountDownTimer
    internal val initialCountDown: Long = 60000
    internal val countDownInterval: Long = 1000
    internal var timeLeftOnTimer: Long = 60000

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private const val SCORE_KEY = "SCORE_KEY"
        private const val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate called, score is $score")

        tapMeButton = findViewById(R.id.btn_tap_button)
        gameScoreTextView = findViewById(R.id.tv_score)
        timerTextView = findViewById(R.id.tv_timer)

        tapMeButton.setOnClickListener { view ->
            val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce)
            view.startAnimation(bounceAnimation)
            incrementScore()
        }

        if (savedInstanceState != null) {
            score = savedInstanceState.getInt(SCORE_KEY)
            timeLeftOnTimer = savedInstanceState.getLong(TIME_LEFT_KEY)
            restoreGame()
        } else {
            resetGame()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_about) {
            showInfo()
        }
        return true
    }

    private fun showInfo() {
        val dialogTitle = getString(R.string.aboutTitle, BuildConfig.VERSION_NAME)
        val dialogMessage = getString(R.string.aboutMessage)

        val builder = AlertDialog.Builder(this)
        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.create().show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(SCORE_KEY, score)
        outState.putLong(TIME_LEFT_KEY, timeLeftOnTimer)
        countDownTimer.cancel()

        Log.d(TAG, "onSaveInstanceState: Saving score: $score & Time left: $timeLeftOnTimer")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called.")
    }

    private fun resetGame() {
        score = 0
        
        gameScoreTextView.text = getString(R.string.tv_score_text, score)

        val initialTimeLeft = initialCountDown / 1000
        timerTextView.text = getString(R.string.tv_timer_text, initialTimeLeft)

        countDownTimer = object : CountDownTimer(initialCountDown, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftOnTimer = millisUntilFinished
                val timeLeft = millisUntilFinished / 1000
                timerTextView.text = getString(R.string.tv_timer_text, timeLeft)
            }

            override fun onFinish() {
                endGame()
            }
        }

        gameStarted = false
    }

    private fun restoreGame() {
        gameScoreTextView.text = getString(R.string.tv_score_text, score)

        val restoredTime = timeLeftOnTimer / 1000
        timerTextView.text = getString(R.string.tv_timer_text, restoredTime)

        countDownTimer = object : CountDownTimer(timeLeftOnTimer, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftOnTimer = millisUntilFinished
                val timeLeft = millisUntilFinished / 1000
                timerTextView.text = getString(R.string.tv_timer_text, timeLeft)
            }

            override fun onFinish() {
                endGame()
            }
        }

        countDownTimer.start()
        gameStarted = true
    }

    private fun incrementScore() {
        if (!gameStarted) {
            startGame()
        }

        score += 1
        val newScore = getString(R.string.tv_score_text, score)
        gameScoreTextView.text = newScore

        val blinkAnimation = AnimationUtils.loadAnimation(this, R.anim.blink)
        gameScoreTextView.startAnimation(blinkAnimation)
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
