package sunr.in;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.extras.toolbar.MaterialMenuIconToolbar;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import sunr.in.util.BaseActivityHelper;
import sunr.in.util.MainGridAdapter;
import sunr.in.widget.RoundImage;

/**
 * Created by LNTCS on 2015-05-07.
 * Project-Juilliard
 */

public class MainActivity extends AppCompatActivity {

    Context mContext;
    private MaterialMenuIconToolbar materialMenu;
    protected BaseActivityHelper helper;
    LayoutInflater inflater;
    TextView profileName;
    GridView mainGrid;
    RoundImage profileImg;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        pref = getSharedPreferences("Sunr.in", Context.MODE_PRIVATE);
        editor = pref.edit();
        initLay();
        startActivity(new Intent(mContext, SignIn.class));   //Start Sign in for test

    }
    private TextView getActionBarTextView(Toolbar mToolBar) {
        TextView titleTextView = null;

        try {
            Field f = mToolBar.getClass().getDeclaredField("mTitleTextView");
            f.setAccessible(true);
            titleTextView = (TextView) f.get(mToolBar);
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }
        return titleTextView;
    }
    public void initLay(){
        inflater = LayoutInflater.from(mContext);
        Toolbar toolbar = (Toolbar) findViewById(R.id.actionbar);

        profileName = (TextView) findViewById(R.id.main_side_name);
        mainGrid = (GridView) findViewById(R.id.main_grid);
        profileImg = (RoundImage) findViewById(R.id.img_main_profile);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.init(getWindow().getDecorView(), materialMenu);
            }
        });
        materialMenu = new MaterialMenuIconToolbar(this, Color.GRAY, MaterialMenuDrawable.Stroke.THIN) {
            @Override
            public int getToolbarViewId() {
                return R.id.actionbar;
            }
        };
        materialMenu.setNeverDrawTouch(true);
        helper = new BaseActivityHelper();
        helper.init(getWindow().getDecorView(), materialMenu);
        ArrayList<Integer> items = new ArrayList<Integer>();
        for(int i = 0 ; i < 6; i++) items.add(i);
        MainGridAdapter gridAdapter = new MainGridAdapter(mContext,items);
        mainGrid.setAdapter(gridAdapter);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Ubuntu-M.ttf");
        getActionBarTextView(toolbar).setTypeface(face);

        String profileID = pref.getString("FB_ID", "");
        if(!profileID.equals("")) new getFacebookProfilePicture().execute(profileID);
        profileName.setText(pref.getString("FB_Name","Name"));
    }
    class getFacebookProfilePicture extends AsyncTask<String,String,String>{
        Bitmap bitmap;
        @Override
        protected String doInBackground(String... params) {
            URL imageURL = null;
            try {
                imageURL = new URL("https://graph.facebook.com/" + params[0] + "/picture?type=large");
                bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            profileImg.setImageBitmap(bitmap);
        }
    }
}

