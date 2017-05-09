package com.example.choi.tour;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by choi on 2017-04-12.
 */

public class BookTravelActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.book_travel);
        final BookInfo bookInfo = new BookInfo();

        Button bookPlane = (Button) findViewById(R.id.btn_bookingPlane);
        bookPlane.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                bookInfo.bookPlane();
            }
        });

        Button bookAcommodation = (Button) findViewById(R.id.btn_booking_accommodation);

        bookAcommodation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                bookInfo.bookAccommodation();
            }
        });
    }

}