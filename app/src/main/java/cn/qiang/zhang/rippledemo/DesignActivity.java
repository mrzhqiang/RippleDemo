package cn.qiang.zhang.rippledemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * <p>
 * Created by mrZQ on 2017/3/21.
 */
public class DesignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int type = getIntent().getIntExtra(RippleDemoActivity.TAG, 0);

        switch (type) {
            case 0:// 布局水波纹
                setContentView(R.layout.ripple_animation);
                break;
        }
    }

}
