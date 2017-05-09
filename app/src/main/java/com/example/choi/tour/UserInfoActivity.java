package com.example.choi.tour;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by choi on 2017-04-12.
 */

public class UserInfoActivity extends AppCompatActivity{
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);


        ImageView imageView = (ImageView) findViewById(R.id.image_userProfile);

        TextView userInfoId = (TextView) findViewById(R.id.userInfo_id);
        TextView userInfoEmail = (TextView) findViewById(R.id.userInfo_email);

        Intent intent = getIntent();
        intent.getSerializableExtra("userProfile");
        Bundle bundle = intent.getExtras();
        final UserProfile userProfile = bundle.getParcelable("userProfile");

        userInfoId.setText(Long.toString(userProfile.getId()));
        userInfoEmail.setText(userProfile.getEmail());




        //userProfile.getEmail();
        //serProfile.getId();
        //userProfile.getNickname();
        //userProfile.getProfileImagePath();



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

        Button deleteUserButton = (Button) findViewById(R.id.btn_deleteUser);

        deleteUserButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onClickUnlink();
                //UserInfo userinfo = new UserInfo();
                //userinfo.deleteUserInfo();
            }
        });
    }
    private void onClickUnlink() {
        final String appendMessage = getString(R.string.com_kakao_confirm_unlink);
        new AlertDialog.Builder(this)
                .setMessage(appendMessage)
                .setPositiveButton(getString(R.string.com_kakao_ok_button),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UserManagement.requestUnlink(new UnLinkResponseCallback() {
                                    @Override
                                    public void onFailure(ErrorResult errorResult) {
                                        Logger.e(errorResult.toString());
                                    }

                                    @Override
                                    public void onSessionClosed(ErrorResult errorResult) {
                                        //redirectLoginActivity();
                                    }

                                    @Override
                                    public void onNotSignedUp() {
                                        //redirectSignupActivity();
                                    }

                                    @Override
                                    public void onSuccess(Long userId) {
                                        Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
                                        Toast.makeText(getApplicationContext(),"DB에서 사용자 데이터를 삭제합니다.", Toast.LENGTH_SHORT).show();
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton(getString(R.string.com_kakao_cancel_button),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

    }
}
