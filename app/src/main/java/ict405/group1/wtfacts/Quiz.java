package ict405.group1.wtfacts;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Quiz extends AppCompatActivity {

    private TextView textLevel, textQuestions, textQuestionNum, textScore;
    private Button btnChoice1, btnChoice2, btnChoice3, btnChoice4, btn5050;
    private ImageButton btnSettings, btnSkip;
    private RatingBar rb_life;

    private String correctAnswer, getChoice1, getChoice2, getChoice3;
    public int levelScore = 0;
    private int questionNum = 1;
    private int userLife = 3;
    private int mScore = 0;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();

    // Format >> {Question, Answer, Choice1, Choice 2, Choice 3}
    String quizData[][] = {
            {"What is the collective noun for a group of crows?","Murder","Pack","Gaggle","Herd"},
            {"About 71% of the Earth's surface is made up of _____", "Water", "Deserts","Continents","Forests"},
            {"What does 360 degrees make?","Circle","Cube","Square","Triangle"},
            {"What do you call a baby bat?","Pup","Cub","Chick","Kid"},
            {"Which is the most abundant element in the universe?","Hydrogen","Helium","Lithium","Oxygen"},
            {"Which of these mythological creatures is said to be half-man and half-horse?","Centaur","Minotaur","Pegasus","Gorgon"},
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
        rb_life = findViewById(R.id.rb_life);

        btnSettings = findViewById(R.id.btnSettings);
        btnChoice1 = findViewById(R.id.btnChoice1);
        btnChoice2 = findViewById(R.id.btnChoice2);
        btnChoice3 = findViewById(R.id.btnChoice3);
        btnChoice4 = findViewById(R.id.btnChoice4);
        btn5050 = findViewById(R.id.btn5050);
        btnSkip = findViewById(R.id.btnSkip);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpDialog();
            }
        });

        btn5050.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fiftyDialog();
            }
        });


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
        /**
        Random random = new Random();

        LinearLayout bg_layout = findViewById(R.id.bg_layout);
        Resources res = getResources();
        TypedArray bgs = res.obtainTypedArray(R.array.bgs);

        int randomInt = random.nextInt(bgs.length());
        int drawableID = bgs.getResourceId(randomInt, -1);
        bg_layout.setBackgroundResource(drawableID);
        bgs.recycle();
         **/
    }

    public void showNextQuiz() {
        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());

        ArrayList<String> quiz = quizArray.get(randomNum);
        //Sets question and the answer into a variable
        textQuestions.setText(quiz.get(0));
        correctAnswer = quiz.get(1);

        //Shuffles the choices and gets wrong choices
        quiz.remove(0);

        getChoice1 = quiz.get(1);
        getChoice2 = quiz.get(2);
        getChoice3 = quiz.get(3);

        Collections.shuffle(quiz);
        //Sets the choices randomly
        btnChoice1.setText(quiz.get(0));
        btnChoice2.setText(quiz.get(1));
        btnChoice3.setText(quiz.get(2));
        btnChoice4.setText(quiz.get(3));

        //Removes the question and its choices
        quizArray.remove(randomNum);

        btnChoice1.setEnabled(true);
        btnChoice2.setEnabled(true);
        btnChoice3.setEnabled(true);
        btnChoice4.setEnabled(true);
    }
    public void jumpDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.activity_sure, null);
        Button btnYES = mView.findViewById(R.id.btnYES);
        Button btnNo = mView.findViewById(R.id.btnNO);
        TextView txtTitle = mView.findViewById(R.id.txtTitle);
        TextView txtDesc = mView.findViewById(R.id.txtDesc);
        TextView txtDesc2 = mView.findViewById(R.id.txtDesc2);
        TextView txtDesc3 = mView.findViewById(R.id.txtDesc3);

        txtTitle.setText("Jump A Question");
        txtDesc.setText("Skips a question but no additional score.");
        txtDesc2.setText("(Can only be used once)");
        txtDesc3.setText("Are you sure you want to use this now?");

        btnYES.setText("Yes");
        btnNo.setText("No");

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        dialog.setCancelable(true);

        btnYES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionNum <= 9) {
                    jumpQuestion();
                } else {
                    questionNum++;
                    levelScore++;
                    updateTextViews();
                    resultDialog(userLife, 2, mScore, Quiz2.class);
                }
                btnSkip.setBackgroundResource(R.drawable.button_green_pressed);
                btnSkip.setImageResource(R.drawable.jump_button_disabled);
                btnSkip.setEnabled(false);
                dialog.dismiss();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    public void jumpQuestion() {
        questionNum++;
        updateTextViews();
        levelScore++;
        showNextQuiz();
    }


    public void fiftyDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.activity_sure, null);
        Button btnYES = mView.findViewById(R.id.btnYES);
        Button btnNo = mView.findViewById(R.id.btnNO);
        TextView txtTitle = mView.findViewById(R.id.txtTitle);
        TextView txtDesc = mView.findViewById(R.id.txtDesc);
        TextView txtDesc2 = mView.findViewById(R.id.txtDesc2);
        TextView txtDesc3 = mView.findViewById(R.id.txtDesc3);

        txtTitle.setText("50:50");
        txtDesc.setText("Removes two wrong choices.");
        txtDesc2.setText("(Can only be used once)");
        txtDesc3.setText("Are you sure you want to use this now?");

        btnYES.setText("Yes");
        btnNo.setText("No");

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        dialog.setCancelable(true);

        btnYES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeChoices();
                btn5050.setEnabled(false);
                btn5050.setBackgroundResource(R.drawable.button_green_pressed);
                dialog.dismiss();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    public void removeChoices() {
        remover1();
        Random r = new Random();
        int i = r.nextInt(2);

        if (i == 1) {
            remover2();
        } else {
            remover3();
        }

    }

    public void remover1() {
        if (btnChoice1.getText().toString().equals(getChoice1)) {
            btnChoice1.setEnabled(false);
        } else if (btnChoice2.getText().toString().equals(getChoice1)) {
            btnChoice2.setEnabled(false);
        } else if (btnChoice3.getText().toString().equals(getChoice1)) {
            btnChoice3.setEnabled(false);
        } else if (btnChoice4.getText().toString().equals(getChoice1)) {
            btnChoice4.setEnabled(false);
        }
    }

    public void remover2() {
        if (btnChoice1.getText().toString().equals(getChoice2)) {
            btnChoice1.setEnabled(false);
        } else if (btnChoice2.getText().toString().equals(getChoice2)) {
            btnChoice2.setEnabled(false);
        } else if (btnChoice3.getText().toString().equals(getChoice2)) {
            btnChoice3.setEnabled(false);
        } else if (btnChoice4.getText().toString().equals(getChoice2)) {
            btnChoice4.setEnabled(false);
        }
    }

    public void remover3() {
        if (btnChoice1.getText().toString().equals(getChoice3)) {
            btnChoice1.setEnabled(false);
        } else if (btnChoice2.getText().toString().equals(getChoice3)) {
            btnChoice2.setEnabled(false);
        } else if (btnChoice3.getText().toString().equals(getChoice3)) {
            btnChoice3.setEnabled(false);
        } else if (btnChoice4.getText().toString().equals(getChoice3)) {
            btnChoice4.setEnabled(false);
        }
    }

    public void updateTextViews() {
        String qText = "Question " + questionNum + "/" + questionCount;
        String showScore = "" + mScore;

        rb_life.setRating(userLife);

        textQuestionNum.setText(qText);
        textScore.setText(showScore);

        giffScore(levelScore, mScore);
    }

    public boolean getVibBool() {
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        return mSharedPreferences.getBoolean("vibrate",  true);
    }

    public void vibration() {
        if (getVibBool()) {
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(200);
        }
    }

    public void checkAnswer(View view) {
        Button answerBtn = findViewById(view.getId());
        String btnText = answerBtn.getText().toString();

        //Adds score when the answer is correct
        if (btnText.equals(correctAnswer)) {
            levelScore = levelScore + 1;
            mScore = mScore + 25;
            view.setBackgroundResource(R.drawable.button_green_pressed);
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

        //Checks if the answer is wrong else correct dialog will appear
        if (!correctAnswer.equals(btnText) && userLife != 0) {
            userLife = userLife - 1;
            view.setBackgroundResource(R.drawable.button_red_pressed);
            vibration();
            updateTextViews();
            //When userLife reaches 0 when subtracted by 1, game over dialog will appear
            if (userLife == 0) {
                gameOverDialog(1, levelScore, mScore, Quiz.class);
            }
        } else {
            correctDialog(correctAnswer);
        }

        //Returns button to its Original Color
        getColorButton(view);
    }

    public void correctDialog(String showAnswer) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.activity_quiz_correct, null);
        TextView text_Answer = mView.findViewById(R.id.text_Answer);
        Button btnNext = mView.findViewById(R.id.btnNext);

        String doneQuiz = "Done";

        text_Answer.setText(showAnswer);

        if(levelScore == questionCount) {
            btnNext.setText(doneQuiz);
        }

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        dialog.setCancelable(false);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionNum == questionCount) {
                    resultDialog(userLife, 2, mScore, Quiz2.class);
                } else {
                    questionNum++;
                    showNextQuiz();
                }
                updateTextViews();
                dialog.dismiss();
            }
        });
    }

    public void gameOverDialog(final Integer level, Integer correct, Integer score, final Class myClass) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.activity_quiz_fail, null);

        ProgressBar progress_correct = mView.findViewById(R.id.progress_correct);
        TextView text_failScore = mView.findViewById(R.id.text_failScore);
        TextView tv_correct = mView.findViewById(R.id.tv_correct);
        TextView text_Answer = mView.findViewById(R.id.text_Answer);
        TextView text_Check = mView.findViewById(R.id.text_Check);

        Button button_again = mView.findViewById(R.id.button_again);
        Button button_menu = mView.findViewById(R.id.button_menu);
        Button button_next = mView.findViewById(R.id.button_over_next);

        int checkScore = getCorrectAnswer();

        if (checkScore >= 7 && levelScore >= 7) {
            text_Check.setVisibility(View.VISIBLE);
            button_next.setVisibility(View.VISIBLE);
        } else {
            text_Check.setVisibility(View.GONE);
            button_next.setVisibility(View.GONE);
        }

        String userCorrect = correct + "/10";
        String userScore = score.toString();

        tv_correct.setText(userCorrect);
        text_failScore.setText(userScore);
        text_Answer.setText(correctAnswer);
        progress_correct.setProgress(correct);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        dialog.setCancelable(false);

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseLevel = new Intent(getApplicationContext(), Quiz2.class);
                chooseLevel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                chooseLevel.putExtra("quizLvl", 2);
                startActivity(chooseLevel);
                dialog.dismiss();
            }
        });

        button_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseLevel = new Intent(getApplicationContext(), myClass);
                chooseLevel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                chooseLevel.putExtra("quizLvl", level);
                startActivity(chooseLevel);
                dialog.dismiss();
            }
        });

        button_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseLevel = new Intent(getApplicationContext(), MainMenu.class);
                chooseLevel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(chooseLevel);
                dialog.dismiss();
            }
        });
    }

    public void resultDialog(Integer userLife, final Integer level, Integer score, final Class myClass) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.activity_quiz_result, null);

        Button button_next = mView.findViewById(R.id.button_next);
        Button button_select = mView.findViewById(R.id.button_select);

        TextView text_plusScore = mView.findViewById(R.id.text_plusScore);
        TextView text_score = mView.findViewById(R.id.text_score);
        TextView text_finalScore = mView.findViewById(R.id.text_finalScore);

        Integer scoring = score - (userLife * 5);
        String userScore = scoring.toString();
        String finalScore = score.toString();
        String plusScore = userLife + " (+" + (userLife * 5) + " points)";

        text_plusScore.setText(plusScore);
        text_score.setText(userScore);
        text_finalScore.setText(finalScore);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        dialog.setCancelable(false);

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseLevel = new Intent(getApplicationContext(), myClass);
                chooseLevel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                chooseLevel.putExtra("quizLvl", level);
                startActivity(chooseLevel);
                dialog.dismiss();
            }
        });

        button_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseLevel = new Intent(getApplicationContext(), LevelSelect.class);
                chooseLevel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(chooseLevel);
                dialog.dismiss();
            }
        });
    }

    public void getColorButton(final View view) {
        Handler timer = new Handler(Looper.getMainLooper());
        timer.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (view.getId() == R.id.btnChoice1) {
                    view.setBackgroundResource(R.drawable.button_choice1);
                } else if (view.getId() == R.id.btnChoice2) {
                    view.setBackgroundResource(R.drawable.button_choice2);
                } else if (view.getId() == R.id.btnChoice3) {
                    view.setBackgroundResource(R.drawable.button_choice3);
                } else if (view.getId() == R.id.btnChoice4) {
                    view.setBackgroundResource(R.drawable.button_choice4);
                }
            }
        }, 500);
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

}
