package sunr.in;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by LNTCS on 2015-05-07.
 * Project-Juilliard
 */

public class MainActivity extends AppCompatActivity {
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.actionbar);
        setSupportActionBar(toolbar);
        mContext = MainActivity.this;
        startActivity(new Intent(mContext, SignIn.class));   //Start Sign in for test
    }
}
