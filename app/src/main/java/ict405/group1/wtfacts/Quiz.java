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

public class Quiz extends AppCompatActivity {

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
            {"Which company did Valve cooperate with in the creation of the Vive?","HTC","Oculus","Google","Razer"},
            {"What does 360 degrees make?","Circle","Cube","Square","Triangle"},
            {"What's the name of Batman's parents?","Thomas & Martha","Joey & Jackie","Jason & Sarah","Todd & Mira"},
            {"What does a funambulist walk on?","A Tight Rope","Broken Glass","Balls","The Moon"},
            {"Who is the Roman God of War?","Mars","Jupiter","Juno","Ares"},
            {"What is the largest organ of the human body?","Skin","Heart","large Intestine","Liver"},
            {"Which sign of the zodiac is represented by the Crab?","Cancer","Libra","Virgo","Sagittarius"},
            {"What does the 'S' stand for in the abbreviation SIM, as in SIM card?","Subscriber","Single","Secure","Solid"},
            {"Which American president appears on a one dollar bill?","George Washington","Thomas Jefferson","Abraham Lincoln","Benjamin Franklin"},
            {"What is Tasmania?","An Australian State","An ice-cream flavor","A disorder","A cartoon character"},
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

        done.setPositiveButton("Next Level", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                giffScore(levelScore, mScore);
                Intent selectLevel = new Intent(getApplicationContext(), Quiz2.class);
                selectLevel.putExtra("quizLvl", 2);
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
                Intent chooseLevel = new Intent(getApplicationContext(), Quiz.class);
                chooseLevel.putExtra("quizLvl", 1);
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
        mEditor.putInt("userScore", score);
        mEditor.apply();
    }

    public void saveLevelScore(int correctAnswers) {
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putInt("correctAns", correctAnswers);
        mEditor.apply();
    }

    public int getCorrectAnswer() {
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        return mSharedPreferences.getInt("correctAns", 0);
    }

    public int getUserScore() {
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        return mSharedPreferences.getInt("userScore", 0);
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
