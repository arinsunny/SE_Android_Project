package com.example.choi.tour;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by choi on 2017-04-12.
 */

public class UserInfoActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.user_info);

        Button deleteUserButton = (Button) findViewById(R.id.btn_deleteUser);

        deleteUserButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                UserInfo userinfo = new UserInfo();
                userinfo.deleteUserInfo();
            }
        });
    }
}
