package com.example.choi.tour;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.kakao.auth.Session;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    Intent intentUserinfo;
    private GoogleApiClient mGoogleApiClient;
    private static int GoogleLoginFlag;
    private static int KaKaoLoginFlag;
    private Button logOutButton;
    private ImageButton userInfoButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton travelExpenses = (ImageButton) findViewById(R.id.btn_travel_expense);
        ImageButton travelRoute = (ImageButton) findViewById(R.id.btn_travel_route);

        intentUserinfo = getIntent();


        //google login 관련 정보 만들기
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();



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


        //google login일때

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            GoogleLoginFlag = 1;
            checkLoginState();
            Log.d("opr.isDone", "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            GoogleLoginFlag = 0;
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            //showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    checkLoginState();
                    //hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }

        if (Session.getCurrentSession().isOpened()) {
            KaKaoLoginFlag = 1;
            // 로그인이 되었을때에
            checkLoginState();

        } else {
            KaKaoLoginFlag = 0;
            // 카카오 로그인이 되지 않았을때

            checkLoginState();
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

    // 구글 로그인 인증 부분
    @Override
    public void onStart() {
        super.onStart();
    }
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("handleSignInResult", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            //startActivity(intent);
            //finish();



        } else {
            // Signed out, show unauthenticated UI.

        }
    }

    private void onClickKaKaoLogout() {
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

    private void onClickGoogleLogout() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        // [START_EXCLUDE]
                        //updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }



    private void checkLoginState(){
        logOutButton = (Button) findViewById(R.id.btn_login);
        userInfoButton = (ImageButton) findViewById(R.id.btn_user_info);

        if(GoogleLoginFlag == 0 && KaKaoLoginFlag == 0){
            //둘다 로그인이 안되어있는 상태
            logOutButton.setText("Login");
            userInfoButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            });
            logOutButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            });

        }else if(GoogleLoginFlag == 1 &&KaKaoLoginFlag == 0){
            //구글만 로그인이 되어있는 상태
            logOutButton.setText("LOGOUT");
            findViewById(R.id.btn_login).setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                            onClickGoogleLogout();
                    }
                }
            );

            userInfoButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
//                    intentUserinfo.getSerializableExtra("userProfile");
//                    Bundle bundle = intentUserinfo.getExtras();
//                    final UserProfile userProfile = bundle.getParcelable("userProfile");
//                    Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
//                    intent.putExtra("userProfile", userProfile);
//                    startActivity(intent);
                }
            });

        }else if(GoogleLoginFlag == 0 && KaKaoLoginFlag == 1){
            //카카오톡만 로그인이 되어있는 상태
            logOutButton.setText("LOGOUT");
            findViewById(R.id.btn_login).setOnClickListener(
                    new Button.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            onClickKaKaoLogout();
                        }
                    }
            );
            userInfoButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    intentUserinfo.getSerializableExtra("userProfile");
                    Bundle bundle = intentUserinfo.getExtras();
                    final UserProfile userProfile = bundle.getParcelable("userProfile");
                    Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
                    intent.putExtra("userProfile", userProfile);
                    startActivity(intent);
                }
            });


        }else if(GoogleLoginFlag == 1 && KaKaoLoginFlag == 1){
            //둘다 로그인이 되어있는 상태 둘중 하나를 꺼야함
        }
    }


}