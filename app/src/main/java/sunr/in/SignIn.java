package sunr.in;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;

/**
 * Created by LNTCS on 2015-05-07.
 * Project-Juilliard
 */
public class SignIn extends AppCompatActivity{
    RelativeLayout fbLay;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fbLay = (RelativeLayout) findViewById(R.id.signin_lay_fb);
        ((GradientDrawable)fbLay.getBackground()).setColor(getResources().getColor(R.color.facebook));

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
}
