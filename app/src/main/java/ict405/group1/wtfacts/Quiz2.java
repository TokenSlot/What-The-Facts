package ict405.group1.wtfacts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Quiz2 extends AppCompatActivity {

    private TextView textLevel, textQuestions, textQuestionNum, textScore;
    private Button btnChoice1, btnChoice2, btnChoice3, btnChoice4, btn5050;
    private ImageButton btnSettings, btnSkip;
    private RatingBar rb_life;

    private Animation animShake;

    private String correctAnswer, getChoice1, getChoice2, getChoice3;
    private boolean vibrateOn, soundOn;
    public int levelScore = 0;
    private int questionNum = 1;
    private int userLife = 3;
    private int mScore = 0;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();

    // Format >> {Question, Answer, Choice1, Choice 2, Choice 3}
    String quizData[][] = {
            {"In past times, what would a gentleman keep in his fob pocket?","Watch","Money","Keys","Notebook"},
            {"What is a group of bats called?","Cauldron","Horde","Pace","Gaggle"},
            {"What are tarot cards usually used for?","Fortune Telling","Playing", "Trading", "Magic"},
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
            {"Which of the following words means “not tight”?","Loose","Lose","Roose","Moose"},
            {"Which of these elements on the Periodic Table is a Noble Gas?","Neon","Potassium","Iodine","Cobalt"},
            {"What is a male goose called?","Gander","Rooster","Gobbler","Drake"},
            {"Who was the Greek equivalent of the Roman god Cupid?","Eros","Artemis","Janus","Tyche"},
            {"What is the largest animal currently on Earth?","Blue Whale","African Elephant","Colossal Squid","Masai Giraffe"},
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
        animShake = AnimationUtils.loadAnimation(this, R.anim.shake);

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
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
                    resultDialog(userLife, 3, mScore, Quiz3.class);
                }
                btnSkip.setBackgroundResource(R.drawable.button_default_pressed);
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
                btn5050.setBackgroundResource(R.drawable.button_default_pressed);
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



    public void checkAnswer(View view) {
        Button answerBtn = findViewById(view.getId());
        String btnText = answerBtn.getText().toString();

        //Adds score when the answer is correct
        if (btnText.equals(correctAnswer)) {
            playCorrect();
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
            playWrong();
            view.setBackgroundResource(R.drawable.button_red_pressed);
            view.startAnimation(animShake);
            vibration();
            updateTextViews();
            //When userLife reaches 0 when subtracted by 1, game over dialog will appear
            if (userLife == 0) {
                gameOverDialog(2, levelScore, mScore, Quiz2.class);
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
                    resultDialog(userLife, 3, mScore, Quiz3.class);
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
                Intent chooseLevel = new Intent(getApplicationContext(), Quiz3.class);
                chooseLevel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                chooseLevel.putExtra("quizLvl", 3);
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
        mEditor.putInt("userScore2", score);
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
        final Switch vibOn = mView.findViewById(R.id.vibration);
        final Switch sounds = mView.findViewById(R.id.sounds);

        if(getVibBool()) {
            vibOn.setChecked(true);
        } else {
            vibOn.setChecked(false);
        }

        if(getSoundBool()) {
            sounds.setChecked(true);
        } else {
            sounds.setChecked(false);
        }

        vibOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    vibOn.setText("Vibration On");
                    vibrateOn = true;
                } else {
                    vibOn.setText("Vibration Off");
                    vibrateOn = false;
                }
                vibData(vibrateOn);
            }
        });

        sounds.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    sounds.setText("Sounds On");
                    soundOn = true;
                } else {
                    sounds.setText("Sounds Off");
                    soundOn = false;
                }
                soundData(soundOn);
            }
        });

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

    public boolean getVibBool() {
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        return mSharedPreferences.getBoolean("vibrate",  true);
    }

    public boolean getSoundBool() {
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        return mSharedPreferences.getBoolean("sound",  true);
    }

    public void vibration() {
        if (getVibBool()) {
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(200);
        }
    }

    public void playCorrect() {
        if(getSoundBool()) {
            MediaPlayer mpCorrect = MediaPlayer.create(this, R.raw.correct);
            mpCorrect.start();
        }
    }

    public void playWrong() {
        if(getSoundBool()) {
            MediaPlayer mpWrong = MediaPlayer.create(this,R.raw.wrong);
            mpWrong.start();
        }
    }

    public void vibData(Boolean isOn) {
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean("vibrate", isOn);
        mEditor.apply();
    }

    public void soundData(Boolean isOn) {
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean("sound", isOn);
        mEditor.apply();
    }

}