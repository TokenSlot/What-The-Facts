package ict405.group1.wtfacts;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class LevelSelect extends AppCompatActivity {

    Button btnLvl1, btnLvl2;
    ImageButton btnHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        btnLvl1 = (Button) findViewById(R.id.button_lvl1);
        btnLvl2 = (Button) findViewById(R.id.button_lvl2);
        btnHome = (ImageButton) findViewById(R.id.button_home);

        /**
        Intent getScore1 = getIntent();
        int scoreQuiz1 = getScore1.getIntExtra("scoreQuiz1", 0);
        if(scoreQuiz1 == 3) {
            btnLvl2.setEnabled(true);
            btnLvl2.setText("2");
            btnLvl2.setTextSize(30);
        } else {
            btnLvl2.setEnabled(false);
        }
        **/

        btnLvl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseLevel = new Intent(getApplicationContext(), Quiz2.class);
                chooseLevel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                chooseLevel.putExtra("quizLvl", 1);
                startActivity(chooseLevel);
            }
        });

        btnLvl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseLevel = new Intent(getApplicationContext(), Quiz.class);
                chooseLevel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                chooseLevel.putExtra("quizLvl", 2);
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

    @Override
    public void onBackPressed() {
        Intent mainMenu = new Intent(getApplicationContext(), MainMenu.class);
        startActivity(mainMenu);
    }

}
