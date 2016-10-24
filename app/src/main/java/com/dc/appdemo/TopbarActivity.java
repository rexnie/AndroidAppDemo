package com.dc.appdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class TopbarActivity extends AppCompatActivity {
    private static final String TAG = "TopbarActivity";

    private TopBar mTopbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_topbar);
        mTopbar = (TopBar) findViewById(R.id.topbar);
        mTopbar.setTopBarClickListener(mTopbarListener);
        mTopbar.setButtonVisable(0, true);
        mTopbar.setButtonVisable(1, true);

    }

    /**
     * 实现左右button的点击事件
     */
    TopBar.TopBarClickListener mTopbarListener = new TopBar.TopBarClickListener() {
        @Override
        public void leftClick() {
            Toast.makeText(TopbarActivity.this, "left button click", Toast.LENGTH_LONG).show();
        }

        @Override
        public void rightClick() {
            Toast.makeText(TopbarActivity.this, "right button click", Toast.LENGTH_LONG).show();
        }
    };
}
