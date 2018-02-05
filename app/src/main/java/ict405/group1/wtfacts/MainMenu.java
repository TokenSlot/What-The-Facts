package ict405.group1.wtfacts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {

    Boolean vibrateOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        if( getIntent().getBooleanExtra("Exit me", false)){
            finish();
            return;
        }

        ImageButton btnStart = findViewById(R.id.btnStart);
        ImageButton btnOptions = findViewById(R.id.btnOptions);
        ImageButton btnExit = findViewById(R.id.btnExit);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selectLevel = new Intent(getApplicationContext(), LevelSelect.class);
                selectLevel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(selectLevel);
            }
        });

        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuSettings();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitApp();
            }
        });

    }

    public void creditActivity() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.activity_about, null);
        Button closeAbout = mView.findViewById(R.id.closeAbout);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        closeAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public void menuSettings() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.activity_menu_settings, null);
        Button btnBack = mView.findViewById(R.id.btnBack);
        Button btnReset = mView.findViewById(R.id.btnReset);
        Button btnAbout = mView.findViewById(R.id.btnAbout);
        final Switch vibOn = mView.findViewById(R.id.vibration);

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creditActivity();
            }
        });

        if(getVibBool()) {
            vibOn.setChecked(true);
        } else {
            vibOn.setChecked(false);
        }

        vibOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    vibOn.setText("Vibration On");
                    vibrateOn = true;
                    Toast.makeText(getApplicationContext(), "Vibration On", Toast.LENGTH_SHORT).show();
                } else {
                    vibOn.setText("Vibration Off");
                    vibrateOn = false;
                    Toast.makeText(getApplicationContext(), "Vibration off", Toast.LENGTH_SHORT).show();
                }
                vibData(vibrateOn);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetData();
                Toast.makeText(getApplicationContext(), "Data has been reset", Toast.LENGTH_SHORT).show();
            }
        });
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        btnBack.setOnClickListener(new View.OnClickListener() {
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

    public void vibData(Boolean isOn) {
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean("vibrate", isOn);

        mEditor.apply();
    }

    public void resetData() {
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();

        mEditor.putInt("userScore", 0);
        mEditor.putInt("correctAns", 0);

        mEditor.putInt("userScore2", 0);
        mEditor.putInt("correctAns2", 0);

        mEditor.putInt("userScore3", 0);
        mEditor.putInt("correctAns3", 0);

        mEditor.putInt("userScore4", 0);
        mEditor.putInt("correctAns4", 0);

        mEditor.putInt("userScore5", 0);
        mEditor.putInt("correctAns5", 0);

        mEditor.putBoolean("vibrate", true);

        mEditor.apply();
    }

    @Override
    public void onBackPressed() {
        exitApp();
    }

    public void exitApp() {
        Intent intent = new Intent(getApplicationContext(), MainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Exit me", true);
        startActivity(intent);
        finish();
    }

}

    /* TODO
    * Designs
    * Landscape? Design
    * Sound effects. Maybe */
