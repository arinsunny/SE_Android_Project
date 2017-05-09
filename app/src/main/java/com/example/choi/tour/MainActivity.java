package com.example.choi.tour;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.kakao.auth.Session;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton userInfoButton = (ImageButton) findViewById(R.id.btn_userinfo);
        ImageButton travelExpenses = (ImageButton) findViewById(R.id.btn_travel_expense);
        ImageButton travelRoute = (ImageButton) findViewById(R.id.btn_travelRoute);
        Button logOutButton = (Button) findViewById(R.id.btn_login);


        //hash key 값 가져오기
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.choi.tour", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (Session.getCurrentSession().isOpened()) {
            // 로그인이 되었을때에
            logOutButton.setText("LOGOUT");
            findViewById(R.id.btn_login).setOnClickListener(
                    new Button.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            onClickLogout();
                        }
                    }
            );
            userInfoButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
                    startActivity(intent);
                }
            });
        } else {

            logOutButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            });
        }


        travelExpenses.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TravelExpensesActivity.class);
                startActivity(intent);
            }
        });

        travelRoute.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TravelRouteActivity.class);
                startActivity(intent);
            }
        });

    }

    private void onClickLogout() {
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                //redirectLoginActivity();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
