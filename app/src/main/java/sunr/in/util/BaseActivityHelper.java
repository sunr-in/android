package sunr.in.util;

/**
 * Created by LNTCS on 2015-05-11.
 * Project-Juilliard
 */

import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;

import com.balysv.materialmenu.MaterialMenu;
import com.balysv.materialmenu.MaterialMenuDrawable;

import sunr.in.R;

public class BaseActivityHelper {
    private MaterialMenu materialIcon;
    static DrawerLayout drawerLayout;
    private RelativeLayout main_grid_cont_dim;
    private boolean direction;

    public void init(View parent, MaterialMenu actionBarIcon) {
        materialIcon = actionBarIcon;
        drawerLayout = (DrawerLayout) parent.findViewById(R.id.drawer_layout);
        main_grid_cont_dim = (RelativeLayout)parent.findViewById(R.id.main_grid_cont_dim);
        main_grid_cont_dim.setBackgroundColor(parent.getResources().getColor(R.color.dimm));
        drawerLayout.setScrimColor(Color.parseColor("#00000000"));
        materialIcon.animateState(MaterialMenuDrawable.IconState.ARROW);
        drawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                materialIcon.setTransformationOffset(
                        MaterialMenuDrawable.AnimationState.BURGER_ARROW,
                        direction ? 2 - slideOffset : slideOffset
                );
                Log.d("asd",slideOffset + "");
                main_grid_cont_dim.setBackgroundColor(Color.argb((int)(slideOffset*150),0,0,0));
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                direction = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                direction = false;
                materialIcon.animateState(MaterialMenuDrawable.IconState.BURGER);
            }
        });
        materialIcon.animateState(MaterialMenuDrawable.IconState.ARROW);
        drawerLayout.openDrawer(Gravity.LEFT);
    }
    public static void CloseDraw(){
        drawerLayout.closeDrawers();
    }
    public void refreshDrawerState() {
        this.direction = drawerLayout.isDrawerOpen(Gravity.START);
    }
}