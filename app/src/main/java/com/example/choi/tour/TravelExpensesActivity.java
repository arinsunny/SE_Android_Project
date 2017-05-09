package com.example.choi.tour;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by choi on 2017-04-12.
 */

public class TravelExpensesActivity extends AppCompatActivity{

    @Override
    protected void  onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.travel_expenses);


        Button othersTravelPlaceExpensesButton = (Button) findViewById(R.id.btn_othersTravelPlaceExpenses);
        othersTravelPlaceExpensesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OthersTravelPlaceListActivity.class);
                startActivity(intent);
            }
        });
    }
}
