package ict405.group1.wtfacts;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Quiz5 extends AppCompatActivity {

    private TextView textLevel, textQuestions, textQuestionNum, textScore, textLife;
    private Button btnChoice1, btnChoice2, btnChoice3, btnChoice4;
    private ImageButton btnSettings;

    private String correctAnswer;
    public int levelScore = 0;
    private int questionNum = 1;
    private int userLife = 3;
    private int mScore = 0;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();

    // Format >> {Question, Answer, Choice1, Choice 2, Choice 3}
    String quizData[][] = {
            {"How many Apollo missions landed men on the moon?","Six","Two","Five","Nine"},
            {"When was youtube founded?","February 14, 2005","May 22, 2004","September 12, 2002","July 19, 2009"},
            {"Which of the following chemicals are found in eggplant seeds?","Nicotine","Mescaline","Cyanide","Psilocybin"},
            {"Sciophobia is the fear of what?","Shadows","Eating","Bright lights","Transportation"},
            {"What do you call someone who collects matchbooks?","Phillumenist","Clavichordist","Thanatologist","Funambulist"},
            {"The Leaning Tower of Pisa leans in what direction?","South","North","West","East"},
            {"What is the capital of Australia?","Canberra","Sydney","Melbourne","Brisbane"},
            {"Melanite is a deep-black variety of what gemstone?","Garnet","Amethyst","Pearl","Peridot"},
            {"Which is the chemical name of H2O?","Dihydrogen Monoxide","Ammonium chloride","Anhydrous Sodium Carbonate","Manganese dioxide"},
            {"Which is not a type of neuron?","Perception Neuron","Sensory Neuron","Motor Neuron","Interneuron"},
            {"Which of the following is NOT a real element?","Vitrainium","Praseodymium","Hassium","Lutetium"},
            {"Who is the Egyptian god of reproduction and lettuce?","Min","Menu","Mut","Meret"},
            {"Which of the following is NOT a god in Norse Mythology?","Jens","Loki","Tyr","Snotra"},
            {"In Norse mythology, what is the name of the serpent which eats the roots of the ash tree Yggdrasil?","Nidhogg","Bragi","Shenron","Ymir"},
            {"What type of creature is a Bonobo?","Ape","Lion","Parrot","Wildcat"},
            {"Which species of Brown Bear is not extinct?","Syrian Brown Bear","California Grizzly Bear","Atlas Bear","Mexican Grizzly Bear"},
            {"What is the Gray Wolf' scientific name?","Canis Lupus","Canis Aureus","Canis Latrans","Canis Lupus Lycaon"},
            {"Which of these is NOT a city in India?","Islamabad","Hyderabad","Ahmedabad","Ghaziabad"},
            {"Which is the largest freshwater lake in the world?","Lake Superior","Lake Laogai","Lake Michigan","Lake Huron"},
            {"What is the name of rocky region that spans most of eastern Canada?","Canadian Shield","Rocky Mountains","Appalachian Mountains","Himalayas"},
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

        btnSettings = findViewById(R.id.btnSettings);
        btnChoice1 = findViewById(R.id.btnChoice1);
        btnChoice2 = findViewById(R.id.btnChoice2);
        btnChoice3 = findViewById(R.id.btnChoice3);
        btnChoice4 = findViewById(R.id.btnChoice4);

        String showLevel = "Level " + level;
        textLevel.setText(showLevel);

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizSettings();
            }
        });

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

        //Sets the background of the buttons
        String choice1 = btnChoice1.getText().toString();
        String choice2 = btnChoice2.getText().toString();
        String choice3 = btnChoice3.getText().toString();
        String choice4 = btnChoice4.getText().toString();

        if (choice1.equals(correctAnswer)) {
            btnChoice1.setBackgroundResource(R.drawable.btncorrect_bg);
        } else {
            btnChoice1.setBackgroundResource(R.drawable.btnwrong_bg);
        }

        if (choice2.equals(correctAnswer)) {
            btnChoice2.setBackgroundResource(R.drawable.btncorrect_bg);
        } else {
            btnChoice2.setBackgroundResource(R.drawable.btnwrong_bg);
        }

        if (choice3.equals(correctAnswer)) {
            btnChoice3.setBackgroundResource(R.drawable.btncorrect_bg);
        } else {
            btnChoice3.setBackgroundResource(R.drawable.btnwrong_bg);
        }

        if (choice4.equals(correctAnswer)) {
            btnChoice4.setBackgroundResource(R.drawable.btncorrect_bg);
        } else {
            btnChoice4.setBackgroundResource(R.drawable.btnwrong_bg);
        }
    }

    public void updateTextViews() {
        String qText = "Question " + questionNum + "/" + questionCount;
        String showScore = "" + mScore;
        String showLife = "" + userLife;
        textLife.setText(showLife);
        textQuestionNum.setText(qText);
        textScore.setText(showScore);
    }

    public void checkAnswer(final View view) {
        Button answerBtn = findViewById(view.getId());
        String btnText = answerBtn.getText().toString();

        AlertDialog.Builder quizPrompt = new AlertDialog.Builder(this);
        AlertDialog.Builder gameOver = new AlertDialog.Builder(this);
        final AlertDialog.Builder done = new AlertDialog.Builder(this);

        //Adds score when the answer is correct
        if (btnText.equals(correctAnswer)) {
            levelScore = levelScore + 1;
            mScore = mScore + 25;
            updateTextViews();
        }

        //Subtracts 5 from the score if answer is incorrect and the score isn't zero
        if (mScore != 0 && !correctAnswer.equals(btnText)) {
            mScore = mScore - 5;
        }

        //If all questions have been answered, all remaining life points are
        // multiplied by 5 and will be added to the final score
        if (levelScore == questionCount) {
            mScore = mScore + (userLife * 5);
        }

        //When all questions are done
        done.setTitle("Result");
        done.setMessage("Correct Answers: " + levelScore + "/" + questionCount +
                        "\nRemaining Life: " + userLife + "(+" + (userLife * 5) + " score)" +
                        "\nScore: " + mScore );
        done.setNegativeButton("Select Level", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                giffScore(levelScore, mScore );
                Intent selectLevel = new Intent(getApplicationContext(), LevelSelect.class);
                selectLevel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(selectLevel);
            }
        });

        done.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                giffScore(levelScore, mScore);
                Intent selectLevel = new Intent(getApplicationContext(), MainMenu.class);
                selectLevel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(selectLevel);
            }
        });
        done.setCancelable(false);

        //When the user fails to answer the question
        gameOver.setTitle("Game Over...");
        gameOver.setMessage("Answer: " + correctAnswer +
                "\nCorrect Answers: " + levelScore + "/" + questionCount +
                "\nScore: " + mScore +
                "\n\n Try Again?");

        gameOver.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                giffScore(levelScore, mScore);
                Intent chooseLevel = new Intent(getApplicationContext(), Quiz5.class);
                chooseLevel.putExtra("quizLvl", 5);
                chooseLevel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(chooseLevel);
            }
        });
        gameOver.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                giffScore(levelScore, mScore );
                Intent mainMenu = new Intent(getApplicationContext(), MainMenu.class);
                mainMenu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainMenu);
            }
        });
        gameOver.setCancelable(false);

        //When the answer is correct
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
            }
        });
        quizPrompt.setCancelable(false);

        //Checks if the answer is wrong else correct dialog will appear
        if (!correctAnswer.equals(btnText) && userLife != 0) {
            userLife = userLife - 1;
            vibration();
            updateTextViews();
            //When userLife reaches 0 when subtracted by 1, game over dialog will appear
            if (userLife == 0) {
                gameOver.show();
            }
        } else {
            quizPrompt.show();
        }

    }

    public void giffScore(int correctAnswers,int score) {
        if (getCorrectAnswer() < correctAnswers) {
            saveLevelScore(correctAnswers);
        }
        if (getUserScore() < score) {
            saveScore(score);
        }
    }

    public void saveScore(int score) {
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putInt("userScore5", score);
        mEditor.apply();
    }

    public void saveLevelScore(int correctAnswers) {
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putInt("correctAns5", correctAnswers);
        mEditor.apply();
    }

    public int getCorrectAnswer() {
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        return mSharedPreferences.getInt("correctAns5", 0);
    }

    public int getUserScore() {
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        return mSharedPreferences.getInt("userScore5", 0);
    }

    @Override
    public void onBackPressed() {
        quizSettings();
    }

    public void quizSettings() {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.activity_quiz_settings, null);
        Button btnResume = mView.findViewById(R.id.btnResume);
        Button btnLevel = mView.findViewById(R.id.btnLevel);
        Button btnExit = mView.findViewById(R.id.btnExit);

        btnLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selectLevel = new Intent(getApplicationContext(), LevelSelect.class);
                selectLevel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(selectLevel);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Exit me", true);
                startActivity(intent);
                finish();
            }
        });

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        btnResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void vibration() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(200);
    }

}