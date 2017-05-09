package com.example.choi.tour;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by choi on 2017-04-12.
 */

public class OthersTravelPlaceListActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.others_travelplace_expenses);
        ImageButton userInfoButton = (ImageButton) findViewById(R.id.btn_othersTravelInfo);
        userInfoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BookTravelActivity.class);
                startActivity(intent);
            }
        });
    }
}
