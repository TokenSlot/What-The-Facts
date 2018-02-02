package ict405.group1.wtfacts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class LevelSelect extends AppCompatActivity {

    Button btnLvl1, btnLvl2, btnLvl3, btnLvl4, btnLvl5;
    ImageButton btnHome;
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
        Integer countCorrect = getCorrectAnswer();
        Integer countScore = getUserScore();
        if(countCorrect >= 7) {
            btnLvl2.setEnabled(true);
            btnLvl2.setText("2");
            btnLvl2.setTextSize(30);
        } else {
            btnLvl2.setEnabled(false);
        }

        //Level 2
        Integer countCorrect2 = getCorrectAnswer2();
        Integer countScore2 = getUserScore2();
        if(countCorrect2 >= 7) {
            btnLvl3.setEnabled(true);
            btnLvl3.setText("3");
            btnLvl3.setTextSize(30);
        } else {
            btnLvl3.setEnabled(false);
        }

        //Level 3
        Integer countCorrect3 = getCorrectAnswer3();
        Integer countScore3 = getUserScore3();
        if(countCorrect3 >= 7) {
            btnLvl4.setEnabled(true);
            btnLvl4.setText("4");
            btnLvl4.setTextSize(30);
        } else {
            btnLvl4.setEnabled(false);
        }

        //Level 4
        Integer countCorrect4 = getCorrectAnswer4();
        Integer countScore4 = getUserScore4();
        if(countCorrect4 >= 7) {
            btnLvl5.setEnabled(true);
            btnLvl5.setText("5");
            btnLvl5.setTextSize(30);
        } else {
            btnLvl5.setEnabled(false);
        }

        //Level 5
        Integer countCorrect5 = getCorrectAnswer5();
        Integer countScore5 = getUserScore5();

        btnLvl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseLevel = new Intent(getApplicationContext(), Quiz.class);
                chooseLevel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                chooseLevel.putExtra("quizLvl", 1);
                startActivity(chooseLevel);
            }
        });

        btnLvl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseLevel = new Intent(getApplicationContext(), Quiz2.class);
                chooseLevel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                chooseLevel.putExtra("quizLvl", 2);
                startActivity(chooseLevel);
            }
        });

        btnLvl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseLevel = new Intent(getApplicationContext(), Quiz3.class);
                chooseLevel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                chooseLevel.putExtra("quizLvl", 3);
                startActivity(chooseLevel);
            }
        });

        btnLvl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseLevel = new Intent(getApplicationContext(), Quiz4.class);
                chooseLevel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                chooseLevel.putExtra("quizLvl", 4);
                startActivity(chooseLevel);
            }
        });

        btnLvl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseLevel = new Intent(getApplicationContext(), Quiz5.class);
                chooseLevel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                chooseLevel.putExtra("quizLvl", 5);
                startActivity(chooseLevel);
            }
        });


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainMenu = new Intent(getApplicationContext(), MainMenu.class);
                mainMenu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainMenu);
            }
        });

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
