package ict405.group1.wtfacts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LevelSelect extends AppCompatActivity {

    Button btnLvl1, btnLvl2, btnLvl3, btnLvl4, btnLvl5;
    ImageButton btnHome;
    Integer countCorrect, countCorrect2, countCorrect3, countCorrect4, countCorrect5;
    Integer countScore, countScore2, countScore3, countScore4, countScore5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        btnLvl1 = findViewById(R.id.button_lvl1);
        btnLvl2 = findViewById(R.id.button_lvl2);
        btnLvl3 = findViewById(R.id.button_lvl3);
        btnLvl4 = findViewById(R.id.button_lvl4);
        btnLvl5 = findViewById(R.id.button_lvl5);

        btnHome = findViewById(R.id.button_home);

        //Level 1
        countCorrect = getCorrectAnswer();
        countScore = getUserScore();
        if(countCorrect >= 7) {
            btnLvl2.setEnabled(true);
            btnLvl2.setText("2");
            btnLvl2.setTextSize(30);
        } else {
            btnLvl2.setEnabled(false);
        }

        //Level 2
        countCorrect2 = getCorrectAnswer2();
        countScore2 = getUserScore2();
        if(countCorrect2 >= 7) {
            btnLvl3.setEnabled(true);
            btnLvl3.setText("3");
            btnLvl3.setTextSize(30);
        } else {
            btnLvl3.setEnabled(false);
        }

        //Level 3
        countCorrect3 = getCorrectAnswer3();
        countScore3 = getUserScore3();
        if(countCorrect3 >= 7) {
            btnLvl4.setEnabled(true);
            btnLvl4.setText("4");
            btnLvl4.setTextSize(30);
        } else {
            btnLvl4.setEnabled(false);
        }

        //Level 4
        countCorrect4 = getCorrectAnswer4();
        countScore4 = getUserScore4();
        if(countCorrect4 >= 7) {
            btnLvl5.setEnabled(true);
            btnLvl5.setText("5");
            btnLvl5.setTextSize(30);
        } else {
            btnLvl5.setEnabled(false);
        }

        //Level 5
        countCorrect5 = getCorrectAnswer5();
        countScore5 = getUserScore5();

        btnLvl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizIntro(1, countCorrect, countScore, Quiz.class);
            }
        });

        btnLvl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizIntro(2, countCorrect2, countScore2, Quiz2.class);
            }
        });

        btnLvl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizIntro(3, countCorrect3, countScore3, Quiz3.class);
            }
        });

        btnLvl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizIntro(4, countCorrect4, countScore4, Quiz4.class);
            }
        });

        btnLvl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizIntro(5, countCorrect5, countScore5, Quiz5.class);
            }
        });


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseLevel = new Intent(getApplicationContext(), MainMenu.class);
                chooseLevel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(chooseLevel);
            }
        });

    }

    public void quizIntro(final Integer level, Integer correct, Integer score, final Class myClass) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.activity_show_result, null);

        ProgressBar progress_correct = mView.findViewById(R.id.progress_correct);
        TextView tv_level = mView.findViewById(R.id.tv_level);
        TextView tv_correct = mView.findViewById(R.id.tv_correct);
        TextView tv_highScore = mView.findViewById(R.id.tv_highScore);

        Button btnGo = mView.findViewById(R.id.btnGo);
        Button btnStop = mView.findViewById(R.id.btnStop);

        String userLevel = "Level " + level;
        String userCorrect = correct + "/10";
        String userScore = score.toString();

        tv_level.setText(userLevel);
        tv_correct.setText(userCorrect);
        tv_highScore.setText(userScore);
        progress_correct.setProgress(correct);

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseLevel = new Intent(getApplicationContext(), myClass);
                chooseLevel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                chooseLevel.putExtra("quizLvl", level);
                startActivity(chooseLevel);
            }
        });
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //get data from level 1
    public int getCorrectAnswer() {
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        return mSharedPreferences.getInt("correctAns", 0);
    }

    public int getUserScore() {
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        return mSharedPreferences.getInt("userScore", 0);
    }

    //get data from level 2
    public int getCorrectAnswer2() {
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        return mSharedPreferences.getInt("correctAns2", 0);
    }

    public int getUserScore2() {
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        return mSharedPreferences.getInt("userScore2", 0);
    }

    //get data from level 3
    public int getCorrectAnswer3() {
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        return mSharedPreferences.getInt("correctAns3", 0);
    }

    public int getUserScore3() {
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        return mSharedPreferences.getInt("userScore3", 0);
    }

    //get data from level 4
    public int getCorrectAnswer4() {
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        return mSharedPreferences.getInt("correctAns4", 0);
    }

    public int getUserScore4() {
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        return mSharedPreferences.getInt("userScore4", 0);
    }

    //get data from level 5
    public int getCorrectAnswer5() {
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        return mSharedPreferences.getInt("correctAns5", 0);
    }

    public int getUserScore5() {
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        return mSharedPreferences.getInt("userScore5", 0);
    }


    @Override
    public void onBackPressed() {
        Intent mainMenu = new Intent(getApplicationContext(), MainMenu.class);
        startActivity(mainMenu);
    }

}
