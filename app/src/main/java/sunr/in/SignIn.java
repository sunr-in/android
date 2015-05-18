package sunr.in;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import com.getbase.floatingactionbutton.FloatingActionButton;

import sunr.in.widget.MultiSelectSpinner;

/**
 * Created by LNTCS on 2015-05-07.
 * Project-Juilliard
 */
public class SignIn extends AppCompatActivity implements View.OnClickListener{
    RelativeLayout fbLay;
    LoginButton loginButton;
    ImageView doneImg,indicateImg;
    TextView statusTv,graduTv,undergraduTv,hakbunTv;
    CallbackManager callbackManager;
    Context mContext;
    ProfileTracker profileTracker;
    Spinner majorSpn;
    MultiSelectSpinner branchSpn;
    boolean isGraduate = true;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    FloatingActionButton fabDone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        pref = getSharedPreferences("Sunr.in", Context.MODE_PRIVATE);
        editor = pref.edit();

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
            case R.id.signin_tv_graduate:
                if(isGraduate) {
                    Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.move_right);
                    indicateImg.startAnimation(animation);
                    graduTv.setTextColor(Color.WHITE);
                    undergraduTv.setTextColor(getResources().getColor(R.color.primary_sign));
                    isGraduate = false;
                }
                break;
            case R.id.signin_tv_undergraduate:
                if(!isGraduate) {
                    Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.move_left);
                    indicateImg.startAnimation(animation);
                    undergraduTv.setTextColor(Color.WHITE);
                    graduTv.setTextColor(getResources().getColor(R.color.primary_sign));
                    isGraduate = true;
                }
                break;
            case R.id.signin_fab_ok:
                Profile profile = Profile.getCurrentProfile();
                if(AccessToken.getCurrentAccessToken() != null && profile != null){
                    editor.putString("FB_ID",profile.getId());
                    editor.putString("FB_Name",profile.getName());
                    editor.commit();
                }
                break;
            case R.id.signin_hakbun:
                
                break;
        }
    }
    public void initLayout(){
        fbLay = (RelativeLayout) findViewById(R.id.signin_lay_fb);
        doneImg = (ImageView) findViewById(R.id.signin_img_done);
        indicateImg = (ImageView) findViewById(R.id.signin_img_indicator);
        statusTv = (TextView) findViewById(R.id.signin_tv_fbstatus);
        graduTv = (TextView) findViewById(R.id.signin_tv_graduate);
        undergraduTv = (TextView) findViewById(R.id.signin_tv_undergraduate);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        majorSpn = (Spinner) findViewById(R.id.signin_spn_major);
        branchSpn = (MultiSelectSpinner) findViewById(R.id.signin_spn_branch);
        fabDone = (FloatingActionButton) findViewById(R.id.signin_fab_ok);
        hakbunTv = (TextView) findViewById(R.id.signin_hakbun);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.major_array,R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        majorSpn.setAdapter(adapter);
        branchSpn.setItems(getResources().getStringArray(R.array.branch_array));
        //TODO 스피너들 초기 값 설정

        loginButton.setReadPermissions("user_friends");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i("Facebook Callback", "Success");
                updateUI();
            }

            @Override
            public void onCancel() {
                Log.i("Facebook Callback", "Cancel");
                updateUI();
            }

            @Override
            public void onError(FacebookException exception) {
                Log.i("Facebook Callback", "Error");
                updateUI();
            }
        });
        ((GradientDrawable)fbLay.getBackground()).setColor(getResources().getColor(R.color.facebook));
        ((GradientDrawable)indicateImg.getBackground()).setColor(getResources().getColor(R.color.primary_sign));
        fbLay.setOnClickListener(this);
        graduTv.setOnClickListener(this);
        undergraduTv.setOnClickListener(this);
        fabDone.setOnClickListener(this);
        hakbunTv.setOnClickListener(this);
    }

    public void updateUI(){
        boolean enableButtons = AccessToken.getCurrentAccessToken() != null;
        Profile profile = Profile.getCurrentProfile();
        if (enableButtons && profile != null) {
            doneImg.setVisibility(View.VISIBLE);
            statusTv.setText(getString(R.string.login_success)+" : " + profile.getName());
        }else{
            doneImg.setVisibility(View.GONE);
            statusTv.setText(getString(R.string.plz_login));
        }
    }
}