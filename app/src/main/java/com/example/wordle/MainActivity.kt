package com.example.wordle

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast


object FourLetterWordList {
    // List of most common 4 letter words from: https://7esl.com/4-letter-words/
    const val fourLetterWords =
        "Area,Army,Baby,Back,Ball,Band,Bank,Base,Bill,Body,Book,Call,Card,Care,Case,Cash,City,Club,Cost,Date,Deal,Door,Duty,East,Edge,Face,Fact,Farm,Fear,File,Film,Fire,Firm,Fish,Food,Foot,Form,Fund,Game,Girl,Goal,Gold,Hair,Half,Hall,Hand,Head,Help,Hill,Home,Hope,Hour,Idea,Jack,John,Kind,King,Lack,Lady,Land,Life,Line,List,Look,Lord,Loss,Love,Mark,Mary,Mind,Miss,Move,Name,Need,News,Note,Page,Pain,Pair,Park,Part,Past,Path,Paul,Plan,Play,Post,Race,Rain,Rate,Rest,Rise,Risk,Road,Rock,Role,Room,Rule,Sale,Seat,Shop,Show,Side,Sign,Site,Size,Skin,Sort,Star,Step,Task,Team,Term,Test,Text,Time,Tour,Town,Tree,Turn,Type,Unit,User,View,Wall,Week,West,Wife,Will,Wind,Wine,Wood,Word,Work,Year,Bear,Beat,Blow,Burn,Call,Care,Cast,Come,Cook,Cope,Cost,Dare,Deal,Deny,Draw,Drop,Earn,Face,Fail,Fall,Fear,Feel,Fill,Find,Form,Gain,Give,Grow,Hang,Hate,Have,Head,Hear,Help,Hide,Hold,Hope,Hurt,Join,Jump,Keep,Kill,Know,Land,Last,Lead,Lend,Lift,Like,Link,Live,Look,Lose,Love,Make,Mark,Meet,Mind,Miss,Move,Must,Name,Need,Note,Open,Pass,Pick,Plan,Play,Pray,Pull,Push,Read,Rely,Rest,Ride,Ring,Rise,Risk,Roll,Rule,Save,Seek,Seem,Sell,Send,Shed,Show,Shut,Sign,Sing,Slip,Sort,Stay,Step,Stop,Suit,Take,Talk,Tell,Tend,Test,Turn,Vary,View,Vote,Wait,Wake,Walk,Want,Warn,Wash,Wear,Will,Wish,Work,Able,Back,Bare,Bass,Blue,Bold,Busy,Calm,Cold,Cool,Damp,Dark,Dead,Deaf,Dear,Deep,Dual,Dull,Dumb,Easy,Evil,Fair,Fast,Fine,Firm,Flat,Fond,Foul,Free,Full,Glad,Good,Grey,Grim,Half,Hard,Head,High,Holy,Huge,Just,Keen,Kind,Last,Late,Lazy,Like,Live,Lone,Long,Loud,Main,Male,Mass,Mean,Mere,Mild,Nazi,Near,Neat,Next,Nice,Okay,Only,Open,Oral,Pale,Past,Pink,Poor,Pure,Rare,Real,Rear,Rich,Rude,Safe,Same,Sick,Slim,Slow,Soft,Sole,Sore,Sure,Tall,Then,Thin,Tidy,Tiny,Tory,Ugly,Vain,Vast,Very,Vice,Warm,Wary,Weak,Wide,Wild,Wise,Zero,Ably,Afar,Anew,Away,Back,Dead,Deep,Down,Duly,Easy,Else,Even,Ever,Fair,Fast,Flat,Full,Good,Half,Hard,Here,High,Home,Idly,Just,Late,Like,Live,Long,Loud,Much,Near,Nice,Okay,Once,Only,Over,Part,Past,Real,Slow,Solo,Soon,Sure,That,Then,This,Thus,Very,When,Wide"

    // Returns a list of four letter words as a list
    fun getAllFourLetterWords(): List<String> {
        return fourLetterWords.split(",")
    }

    // Returns a random four letter word from the list in all caps
    fun getRandomFourLetterWord(): String {
        val allWords = getAllFourLetterWords()
        val randomNumber = (0..allWords.size).shuffled().last()
        return allWords[randomNumber].uppercase()
    }


}

class MainActivity : AppCompatActivity() {
    private val targetWords = FourLetterWordList.getRandomFourLetterWord()
    private fun checkGuess(guess: String): String {
        var result = ""

        for (i in 0..3) {
            if (guess[i] == targetWords[i]) {
                result += "O"
            } else if (guess[i] in targetWords) {
                result += "+"
            } else {
                result += "X"
            }
        }
        return result
    }
    //check if the input is valid
    private fun isValid(guess: String): Boolean{
        val wordList = FourLetterWordList.getAllFourLetterWords()
        val word = guess.lowercase().replaceFirstChar { it.uppercase() } // capitalize
//        Log.d(word, word) //debug
        if (guess.length != 4){
            return false
        }
        if (!wordList.contains(word))
        {
            return false
        }

        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var guessCount = 0

        val textView = findViewById<TextView>(R.id.textView)
        val button = findViewById<Button>(R.id.button)

        val newGameButton = findViewById<Button>(R.id.playAgain)
        newGameButton.visibility = View.INVISIBLE

        var guess1 = findViewById<TextView>(R.id.guess1)
        var guess2 = findViewById<TextView>(R.id.guess2)
        var guess3 = findViewById<TextView>(R.id.guess3)
        var hint1 = findViewById<TextView>(R.id.textView5)
        var hint2 = findViewById<TextView>(R.id.textView6)
        var hint3 = findViewById<TextView>(R.id.textView7)
        //Edit Text
        textView.text = targetWords
        textView.visibility = View.INVISIBLE
        button.setOnClickListener {
            var simpleEditText = findViewById<View>(R.id.guessText) as EditText
            var guess = simpleEditText.text.toString().uppercase()
//            Toast.makeText(it.context, "word is $targetWords", Toast.LENGTH_SHORT).show()
            var valid = isValid(guess)
            if (!valid) {
                Toast.makeText(it.context, "Invalid input. Please try again.", Toast.LENGTH_SHORT).show()
                simpleEditText.setText(null)
            } else {
                var check = checkGuess(guess)

                if (check == "OOOO") {
//                    Toast.makeText(it.context, "YOU WIN!", Toast.LENGTH_SHORT).show()
                    textView.text = "You win! The word is " + textView.text
                    button.visibility = View.INVISIBLE
                } else {
                    guessCount++
                    if (guessCount == 1) {
                        guess1.text = guess
                        hint1.text = check
                    } else if (guessCount == 2) {
                        guess2.text = guess
                        hint2.text = check
                    } else if (guessCount == 3) {
                        guess3.text = guess
                        hint3.text = check
//                        Toast.makeText(it.context, "You've guessed 3 times", Toast.LENGTH_SHORT).show()
                        textView.text = "You lost! The word is " + textView.text
                        textView.visibility = View.VISIBLE
                        button.visibility = View.INVISIBLE
                        newGameButton.visibility = View.VISIBLE
                        simpleEditText.isEnabled = false
                        simpleEditText.visibility = View.INVISIBLE
                    }
                }

                simpleEditText.setText(null)
            }
            newGameButton.setOnClickListener {
                this.recreate()
            }
        }
    }


}
