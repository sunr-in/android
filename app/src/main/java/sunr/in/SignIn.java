package sunr.in;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * Created by LNTCS on 2015-05-07.
 * Project-Juilliard
 */
public class SignIn extends AppCompatActivity implements View.OnClickListener{
    RelativeLayout fbLay;
    LoginButton loginButton;
    ImageView doneImg;
    TextView statusTv;
    CallbackManager callbackManager;
    Context mContext;
    ProfileTracker profileTracker;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_signin);
        mContext = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initLayout();
        updateUI();

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                updateUI();
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        updateUI();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signin_lay_fb:
                loginButton.performClick();
                break;
        }
    }
    public void initLayout(){
        fbLay = (RelativeLayout) findViewById(R.id.signin_lay_fb);
        doneImg = (ImageView) findViewById(R.id.signin_img_done);
        statusTv = (TextView) findViewById(R.id.signin_tv_fbstatus);
        loginButton= (LoginButton) findViewById(R.id.login_button);

        loginButton.setReadPermissions("user_friends");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.i("Facebook Callback", "Success");
                updateUI();
            }
            @Override
            public void onCancel() {
                // App code
                Log.i("Facebook Callback", "Cancel");
                updateUI();
            }
            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.i("Facebook Callback", "Error");
                updateUI();
            }
        });
        ((GradientDrawable)fbLay.getBackground()).setColor(getResources().getColor(R.color.facebook));
        fbLay.setOnClickListener(this);
    }

    public void updateUI(){
        boolean enableButtons = AccessToken.getCurrentAccessToken() != null;
        Profile profile = Profile.getCurrentProfile();
        if (enableButtons && profile != null) {
            doneImg.setVisibility(View.VISIBLE);
            statusTv.setText("로그인 완료 : " + profile.getName());
        }else{
            doneImg.setVisibility(View.GONE);
            statusTv.setText("facebook 로그인을 해주세요");
        }
    }
}
