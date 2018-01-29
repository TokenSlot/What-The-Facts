package ict405.group1.wtfacts;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Quiz extends AppCompatActivity {

    private userData user = new userData();

    private TextView textLevel, textQuestions, textQuestionNum, textScore, textLife;
    private Button btnChoice1, btnChoice2, btnChoice3, btnChoice4, btnSettings;

    private String correctAnswer;
    public int levelScore = 0;
    private int questionNum = 1;
    private int userLife = 3;
    private int mScore = user.Score;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();

    // Format >> {Question, Answer, Choice1, Choice 2, Choice 3}
    String quizData[][] = {
            //Samples
            {"Which company did Valve cooperate with in the creation of the Vive?","HTC","Oculus","Google","Razer"},
            {"What does 360 degrees make?","Circle","Cube","Square","Tringale"},
            {"What's the name of Batman's parents?","Thomas & Martha","Joey & Jackie","Jason & Sarah","Todd & Mira"},
            {"What does a funambulist walk on?","A Tight Rope","Broken Glass","Balls","The Moon"},
            {"What is the most sensitive","Smell","Touch","Sight","Hearing"},
            {"What is the largest organ of the human body?","Skin","Heart","large Intestine","Liver"},
            {"Which sign of the zodiac is represented by the Crab?","Cancer","Libra","Virgo","Sagittarius"},
            {"What does the 'S' stand for in the abbreviation SIM, as in SIM card?","Subscriber","Single","Secure","Solid"},
            {"Which American president appears on a one dollar bill?","George Washington","Thomas Jefferson","Abraham Lincoln","Benjamin Franklin"},
            {"What is Tasmania?","An Australian State","An ice-cream flavor","A disorder","A cartoon character"},

            //Level 2 Questions
            {"In past times, what would a gentleman keep in his fob pocket?","Watch","Money","Keys","Notebook"},
            {"What is a group of bats called","Cauldron","Horde","Pace","Gaggle"},
            {"Which sign of the zodiac comes between Virgo and Scorpio?","Libra","Leo","Gemini","Capricorn"},
            {"What machine element is located in the center of fidget spinners?","","","",""},
            {"What mythology did the god Apollo came from?","Greek and Roman","Norse and Spanish","Greek and Chinese","Norse and Greek"},
            {"What is the more scientific name for quicksilver","Mercury","Cadmium","Lead","Bromine"},
            {"What is the smallest country in the world?","Vatican City","Maldives","Monaco","Malta"},
            {"How long does it take for Earth to travel around the Sun?","1 Year","4 days","10 weeks","12 minutes"},
            {"How many vowels are in the word beautify?","4","5","3","7"},
            {"What is the fastest-running terrestrial animal?","Cheetah","Lion","Man","Jaguar"},
            {"What’s the best drink for your body?","Water","Coffee","Tea","Energy Drink"},
            {"Which country’s flag is commonly referred to as the “Rising Sun”?","Japan","Vietnam","Korea","China"},
            {"The “Mona Lisa” is an example of what kind of art technique?","Oil Painting","Acrylic Painting","Casein Painting","Panel Painting"},
            {"Which of the following words means “not tight”?","Loose","Lose","Roose","Host"},
            {"Which company was established on April 1st, 1976 by Steve Jobs, Steve Wozniak and Ronald Wayne?","Apple","Microsoft","Atari","Commodore"},
            {"What is a male goose called","Gander","Rooster","Gobbler","Drake"},
            {"Who was the Greek equivalent of the Roman god Cupid","Eros","Artemis","Janus","Tyche"},
            {"What is the largest animal currently on Earth?","Blue Whale","Orca","Colossal Squid","Giraffe"},
            {"On Twitter, what is the character limit for a Tweet?","140","120","100","110"},
            {"The body of the Egyptian Sphinx was based on which animal?","Lion","Bull","Horse","Dog"},

            //Level 3 Questions
            {"Galileo discovered how many moons of Jupiter?","4","9","2","15"},
            {"What is the capital of Canada?","Ottawa","Toronto","Vancouver","Victoria"},
            {"What is the middle region of an insect called?","Thorax","Head","Abdomen","Wings"},
            {"The body of the Egyptian Sphinx was based on which animal?","Lion","Bull","Horse","Dog"},
            {"Which of these mammals lay eggs?","Platypus","Antelope","Beavers","Tarsier"},
            {"What are the plastic bits at the end of shoelaces called?","Aglet","Sole","Seam","Vamp"},
            {"Which US state has the highest population?","California","New York","Texas","Florida"},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},

            //Level 4 Questions
            {"Which of these countries has an element named after it?","Poland","Greece","Philippines","Japan"},
            {"Oneirophobia is the fear of?","Dreams","Truth","Light","Sleeping"},
            {"Pollination by birds is called","Ornithophily","Autogamy","Entomophily","Anemophily"},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},

            //Level 5 Questions
            {"How many Apollo missions landed men on the moon?","Six","Two","Five","Nine"},
            {"When was youtube founded","February 14, 2005","May 22, 2004","September 12, 2002","July 19, 2009"},
            {"Which of the following chemicals are found in eggplant seeds?","Nicotine","Mescaline","Cyanide","Psilocybin"},
            {"Sciophobia is the fear of what?","Shadows","Eating","Bright lights","Transportation"},
            {"What do you call someone who collects matchbooks?","Phillumenist","Clavichordist","Thanatologist","Funambulist"},
            {"The Leaning Tower of Pisa leans in what direction?","South","North","West","East"},
            {"What is the capital of Australia?","Canberra","Sydney","Melbourne","Brisbane"},
            {"Melanite is a deep-black variety of what gemstone?","Garnet","Amethyst","Pearl","Peridot"},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
    };

    private int questionCount = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Intent getScore1 = getIntent();
        int level = getScore1.getIntExtra("quizLvl", 0);

        textQuestions = findViewById(R.id.textQuestion);
        textLevel = findViewById(R.id.textLevel);
        textQuestionNum = findViewById(R.id.textQuestionNum);
        textScore = findViewById(R.id.textScore);
        textLife = findViewById(R.id.textLife);

        btnChoice1 = findViewById(R.id.btnChoice1);
        btnChoice2 = findViewById(R.id.btnChoice2);
        btnChoice3 = findViewById(R.id.btnChoice3);
        btnChoice4 = findViewById(R.id.btnChoice4);

        String showLevel = "Level " + level;
        textLevel.setText(showLevel);

        for (int i = 0; i < quizData.length; i++) {

            ArrayList<String> tmpArray = new ArrayList<>();
            tmpArray.add(quizData[i][0]); //Question
            tmpArray.add(quizData[i][1]); //Answer
            tmpArray.add(quizData[i][2]); //Choice1
            tmpArray.add(quizData[i][3]); //Choice2
            tmpArray.add(quizData[i][4]); //Choice3

            quizArray.add(tmpArray);
        }
        updateTextViews();
        showNextQuiz();

    }

    public void showNextQuiz() {
        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());

        ArrayList<String> quiz = quizArray.get(randomNum);
        //Sets question and the answer into a variable
        textQuestions.setText(quiz.get(0));
        correctAnswer = quiz.get(1);

        //Shuffles the choices
        quiz.remove(0);
        Collections.shuffle(quiz);
        //Sets the choices randomly
        btnChoice1.setText(quiz.get(0));
        btnChoice2.setText(quiz.get(1));
        btnChoice3.setText(quiz.get(2));
        btnChoice4.setText(quiz.get(3));
        //Removes the question and its choices
        quizArray.remove(randomNum);
    }

    public void updateTextViews() {
        String qText = "Question " + questionNum + "/" + questionCount;
        String showScore = "" + mScore;
        String showLife = "" + userLife;
        textLife.setText(showLife);
        textQuestionNum.setText(qText);
        textScore.setText(showScore);
    }

    public void gameOver() {
        AlertDialog.Builder gameOver = new AlertDialog.Builder(this);
        gameOver.setTitle("Game Over...");
        gameOver.setMessage("Try again?");
        gameOver.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent chooseLevel = new Intent(getApplicationContext(), Quiz.class);
                chooseLevel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(chooseLevel);
            }
        });
        gameOver.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent mainMenu = new Intent(getApplicationContext(), MainMenu.class);
                mainMenu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainMenu);
            }
        });
        gameOver.setCancelable(false);
        gameOver.show();
    }

    public void checkAnswer(View view) {
        Button answerBtn = findViewById(view.getId());
        String btnText = answerBtn.getText().toString();

        //Adds score when the answer is correct
        if (btnText.equals(correctAnswer)) {
            levelScore = levelScore + 1;
            mScore = mScore + 25;
        }

        AlertDialog.Builder quizPrompt = new AlertDialog.Builder(this);
        final AlertDialog.Builder done = new AlertDialog.Builder(this);

        done.setTitle("Result");
        done.setMessage("Score: " + mScore);
        done.setNeutralButton("Back to Menu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent selectLevel = new Intent(getApplicationContext(), LevelSelect.class);
                selectLevel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(selectLevel);
            }
        });
        done.setCancelable(false);

        quizPrompt.setTitle("Correct!");
        quizPrompt.setMessage("Answer: " + correctAnswer);
        quizPrompt.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (questionNum == questionCount) {
                        done.show();
                } else {
                    questionNum++;
                    showNextQuiz();
                }
                updateTextViews();
            }
        });
        quizPrompt.setCancelable(false);

        //Checks if the answer is wrong else correct dialog will appear
        if (!correctAnswer.equals(btnText) && userLife != 0) {
            userLife = userLife - 1;
            Toast rong = new Toast(this);
            rong.makeText(getApplicationContext(), "Wrong!", Toast.LENGTH_LONG).show();
            updateTextViews();
            vibration();
            //When userLife reaches 0 when subtracted by 1, game over dialog will appear
            if (userLife == 0) {
                gameOver();
            }
        } else {
            quizPrompt.show();
        }
    }

    @Override
    public  void onBackPressed() {
        //Show Settings
    }

    public void vibration() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(200);
    }


    /* TODO
    * Database
    * Switch Questions according to selected level
    * Back button + Menu
    * Designs
    * Landscape? Design
    * Actual Questions
    * Sound effects. Maybe
    * -- Before final week of January -- */


}
