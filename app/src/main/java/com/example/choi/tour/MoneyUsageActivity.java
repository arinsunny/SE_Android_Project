package com.example.choi.tour;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by choi on 2017-04-12.
 */

public class MoneyUsageActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.money_usage);

        Button goalMenuButton = (Button) findViewById(R.id.btn_goalMenu);
        goalMenuButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InputGoalMoneyActivity.class);
                startActivity(intent);
            }
        });

        Button consumeMenuButton = (Button) findViewById(R.id.btn_consumeMenu);
        consumeMenuButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InputConsumeMoneyActivity.class);
                startActivity(intent);
            }
        });
    }
}
