package com.example.diceroller

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.example.diceroller.databinding.ActivityMainBinding
import java.util.Random

class MainActivity : AppCompatActivity() {
    private var turn:Boolean = true
    private var score1 = 0
    private var score2 = 0
    private var game:Boolean = true
    val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.p2.pauseAnimation()
        binding.player1.setTypeface(null,Typeface.BOLD)
        binding.p1Score.setTypeface(null, Typeface.BOLD)
        // Handler for button click delay
        val handler = Handler(Looper.getMainLooper())

        binding.roll.visibility = View.INVISIBLE
        binding.materialCardView.visibility = View.INVISIBLE
        binding.materialCardView2.visibility = View.INVISIBLE
        binding.comp.visibility = View.INVISIBLE
        binding.p1Score.visibility = View.INVISIBLE
        binding.p2Score.visibility = View.INVISIBLE
        binding.textView6.visibility = View.INVISIBLE


        // Post a delayed task to re-enable the button after 1 second
        handler.postDelayed({
            binding.roll.visibility = View.VISIBLE
            binding.materialCardView.visibility = View.VISIBLE
            binding.materialCardView2.visibility = View.VISIBLE
            binding.comp.visibility = View.VISIBLE
            binding.p1Score.visibility = View.VISIBLE
            binding.p2Score.visibility = View.VISIBLE
            binding.textView6.visibility = View.VISIBLE
        }, 3000) // 1000 milliseconds = 1 second

        binding.roll.setOnClickListener {
            // Generate a random number between 1 and 6 (inclusive)
            binding.roll.isEnabled = false

            // Post a delayed task to re-enable the button after 1 second
            handler.postDelayed({
                binding.roll.isEnabled = true
            }, 3400) // 1000 milliseconds = 1 second
            if(!game){
                game=!game
                binding.statusp1winner.visibility = View.INVISIBLE
                binding.statusp1looser.visibility = View.INVISIBLE
                binding.shower1.visibility = View.INVISIBLE
                binding.statusp2winner.visibility = View.INVISIBLE
                binding.statusp2looser.visibility = View.INVISIBLE
                binding.shower2.visibility = View.INVISIBLE
            }
            val randomNumber = Random().nextInt(6) + 1

            // Call setupAnim() with the random number
            setupAnim(randomNumber)
            binding.comp.playAnimation()

            turn = !turn
            if(turn) {
                score2+=randomNumber
                binding.p2Score.text=score2.toString()
                binding.player1.setTypeface(null,Typeface.BOLD)
                binding.player2.setTypeface(null,Typeface.NORMAL)
                binding.p1Score.setTypeface(null, Typeface.BOLD)
                binding.p2Score.setTypeface(null, Typeface.NORMAL)
                binding.p1.playAnimation()
                binding.p2.pauseAnimation()
            }else {
                score1+=randomNumber
                binding.p1Score.text=score1.toString()
                binding.player2.setTypeface(null,Typeface.BOLD)
                binding.player1.setTypeface(null,Typeface.NORMAL)
                binding.p2Score.setTypeface(null, Typeface.BOLD)
                binding.p1Score.setTypeface(null, Typeface.NORMAL)
                binding.p2.playAnimation()
                binding.p1.pauseAnimation()
            }

            if(score1>=30){
                binding.p1.playAnimation()
                binding.p2.pauseAnimation()
                binding.statusp1winner.visibility = View.VISIBLE
                binding.statusp2looser.visibility = View.VISIBLE
                binding.shower1.visibility = View.VISIBLE
                score1 = 0
                score2 = 0
                binding.p1Score.text=score1.toString()
                binding.p2Score.text=score2.toString()
                game = false
            }
            if(score2>=30){
                binding.p2.playAnimation()
                binding.p1.pauseAnimation()
                binding.statusp2winner.visibility = View.VISIBLE
                binding.statusp1looser.visibility = View.VISIBLE
                binding.shower2.visibility = View.VISIBLE
                score1 = 0
                score2 = 0
                binding.p1Score.text=score1.toString()
                binding.p2Score.text=score2.toString()
                game = false
            }

        }
    }

    private fun setupAnim(diceNumber: Int) {
        when (diceNumber) {
            1 -> binding.animationView.setAnimation(R.raw.dice_one)
            2 -> binding.animationView.setAnimation(R.raw.dice_two)
            3 -> binding.animationView.setAnimation(R.raw.dice_three)
            4 -> binding.animationView.setAnimation(R.raw.dice_four)
            5 -> binding.animationView.setAnimation(R.raw.dice_five)
            else -> {
                binding.animationView.setAnimation(R.raw.dice_six)
            }
        }
        binding.animationView.playAnimation()

    }
}