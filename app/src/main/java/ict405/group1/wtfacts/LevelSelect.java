package ict405.group1.wtfacts;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class LevelSelect extends AppCompatActivity {

    Button btnLvl1;
    ImageButton btnHome;
    public int myNumber = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);


        btnLvl1 = (Button) findViewById(R.id.button_lvl1);
        btnHome = (ImageButton) findViewById(R.id.button_home);


        btnLvl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseLevel1 = new Intent(getApplicationContext(), Quiz.class);
                startActivity(chooseLevel1);
                myNumber = 0;
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainMenu = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(mainMenu);
            }
        });

    }
}
