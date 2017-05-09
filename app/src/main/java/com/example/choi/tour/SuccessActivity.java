package com.example.choi.tour;

/**
 * Created by choi on 2017-05-08.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

public class SuccessActivity extends AppCompatActivity implements Serializable {
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        Intent intent = getIntent();
        intent.getSerializableExtra("userProfile");
        Bundle bundle = intent.getExtras();
        final UserProfile userProfile = bundle.getParcelable("userProfile");

        userProfile.getEmail();
        userProfile.getId();
        userProfile.getNickname();
        userProfile.getProfileImagePath();

        ImageView imageView = (ImageView) findViewById(R.id.imageView1);


        //  안드로이드에서 네트워크 관련 작업을 할 때는
        //  반드시 메인 스레드가 아닌 별도의 작업 스레드에서 작업해야 합니다.

        Thread mThread = new Thread() {

            @Override
            public void run() {

                try {
                    URL url = new URL(userProfile.getProfileImagePath()); // URL 주소를 이용해서 URL 객체 생성

                    //  아래 코드는 웹에서 이미지를 가져온 뒤
                    //  이미지 뷰에 지정할 Bitmap을 생성하는 과정

                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);

                } catch(IOException ex) {

                }
            }
        };

        mThread.start(); // 웹에서 이미지를 가져오는 작업 스레드 실행.

        try {

            //  메인 스레드는 작업 스레드가 이미지 작업을 가져올 때까지
            //  대기해야 하므로 작업스레드의 join() 메소드를 호출해서
            //  메인 스레드가 작업 스레드가 종료될 까지 기다리도록 합니다.

            mThread.join();

            //  이제 작업 스레드에서 이미지를 불러오는 작업을 완료했기에
            //  UI 작업을 할 수 있는 메인스레드에서 이미지뷰에 이미지를 지정합니다.

            imageView.setImageBitmap(bitmap);
        } catch (InterruptedException e) {

        }



        //UserProfile userProfile = (UserProfile) intent.getSerializableExtra("userProfile");
        //intent.getExtras();
        //Log.d("hello", bundle.toString());
        findViewById(R.id.button_logout).setOnClickListener(
                new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        onClickLogout();
                    }
                }
        );

    }


    private void onClickLogout() {
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                //redirectLoginActivity();
                Intent intent = new Intent(SuccessActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
