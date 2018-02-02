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

public class Quiz2 extends AppCompatActivity {

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
            {"In past times, what would a gentleman keep in his fob pocket?","Watch","Money","Keys","Notebook"},
            {"What is a group of bats called?","Cauldron","Horde","Pace","Gaggle"},
            {"Hippocampus is the Latin name for which marine creature?","Seahorse","Dolphin","Whale","Octopus"},
            {"Alzheimer's disease primarily affects which part of the human body?","Brain","Lungs","Skin","Heart"},
            {"What mythology did the god Apollo came from?","Greek and Roman","Norse and Spanish","Greek and Chinese","Norse and Greek"},
            {"What is the more scientific name for quicksilver?","Mercury","Cadmium","Lead","Bromine"},
            {"What is the smallest country in the world?","Vatican City","Maldives","Monaco","Malta"},
            {"How long does it take for Earth to travel around the Sun?","1 Year","4 days","10 weeks","12 minutes"},
            {"How many vowels are in the word beautify?","4","5","3","7"},
            {"What is the fastest-running terrestrial animal?","Cheetah","Lion","Man","Jaguar"},
            {"What’s the best drink for your body?","Water","Coffee","Tea","Energy Drink"},
            {"Which country’s flag is commonly referred to as the “Rising Sun”?","Japan","Vietnam","Korea","China"},
            {"The “Mona Lisa” is an example of what kind of art technique?","Oil Painting","Acrylic Painting","Casein Painting","Panel Painting"},
            {"Which of the following words means “not tight”?","Loose","Lose","Roose","Host"},
            {"Which of these elements on the Periodic Table is a Noble Gas?","Neon","Potassium","Iodine","Colbalt"},
            {"What is a male goose called?","Gander","Rooster","Gobbler","Drake"},
            {"Who was the Greek equivalent of the Roman god Cupid?","Eros","Artemis","Janus","Tyche"},
            {"What is the largest animal currently on Earth?","Blue Whale","Orca","Colossal Squid","Giraffe"},
            {"On Twitter, what is the character limit for a Tweet?","140","120","100","110"},
            {"The body of the Egyptian Sphinx was based on which animal?","Lion","Bull","Horse","Dog"},
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
    }

    public void updateTextViews() {
        String qText = "Question " + questionNum + "/" + questionCount;
        String showScore = "" + mScore;
        String showLife = "" + userLife;
        textLife.setText(showLife);
        textQuestionNum.setText(qText);
        textScore.setText(showScore);
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
        AlertDialog.Builder gameOver = new AlertDialog.Builder(this);
        final AlertDialog.Builder done = new AlertDialog.Builder(this);

        //When all questions are done
        done.setTitle("Result");
        done.setMessage("Score: " + mScore);
        done.setNegativeButton("Select Level", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                giffScore(levelScore, mScore);
                Intent selectLevel = new Intent(getApplicationContext(), LevelSelect.class);
                selectLevel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(selectLevel);
            }
        });

        done.setPositiveButton("Next Level", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                giffScore(levelScore, mScore);
                Intent selectLevel = new Intent(getApplicationContext(), Quiz3.class);
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
                Intent chooseLevel = new Intent(getApplicationContext(), Quiz2.class);
                chooseLevel.putExtra("quizLvl", 2);
                chooseLevel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(chooseLevel);
            }
        });
        gameOver.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                giffScore(levelScore, mScore);
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
                gameOver.show();
            }
        } else {
            quizPrompt.show();
        }
    }

    public void giffScore(int correctAnswers,int score) {
        saveScore(score);
        saveLevelScore(correctAnswers);
    }

    public void saveScore(int score) {
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putInt("userScore4", score);
        mEditor.apply();
    }

    public void saveLevelScore(int correctAnswers) {
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putInt("correctAns2", correctAnswers);
        mEditor.apply();
    }

    public int getCorrectAnswer() {
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        return mSharedPreferences.getInt("correctAns2", 0);
    }

    public int getUserScore() {
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        return mSharedPreferences.getInt("userScore2", 0);
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