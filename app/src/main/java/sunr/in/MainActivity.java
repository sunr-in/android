package sunr.in;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.extras.toolbar.MaterialMenuIconToolbar;

import java.util.ArrayList;

import sunr.in.util.BaseActivityHelper;
import sunr.in.util.MainGridAdapter;

/**
 * Created by LNTCS on 2015-05-07.
 * Project-Juilliard
 */

public class MainActivity extends AppCompatActivity {

    Context mContext;
    private MaterialMenuIconToolbar materialMenu;
    protected BaseActivityHelper helper;
    LayoutInflater inflater;
    GridView mainGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        initLay();
    }

    public void initLay(){
        inflater = LayoutInflater.from(mContext);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mainGrid = (GridView) findViewById(R.id.main_grid);

        setSupportActionBar(toolbar);

//        startActivity(new Intent(mContext, SignIn.class));   //Start Sign in for test

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.init(getWindow().getDecorView(), materialMenu);
            }
        });
        materialMenu = new MaterialMenuIconToolbar(this, Color.GRAY, MaterialMenuDrawable.Stroke.THIN) {
            @Override
            public int getToolbarViewId() {
                return R.id.toolbar;
            }
        };
        materialMenu.setNeverDrawTouch(true);
        helper = new BaseActivityHelper();
        helper.init(getWindow().getDecorView(), materialMenu);
        ArrayList<Integer> items = new ArrayList<Integer>();
        for(int i = 0 ; i < 6; i++) items.add(i);
        MainGridAdapter gridAdapter = new MainGridAdapter(mContext,items);
        mainGrid.setAdapter(gridAdapter);
    }
}
