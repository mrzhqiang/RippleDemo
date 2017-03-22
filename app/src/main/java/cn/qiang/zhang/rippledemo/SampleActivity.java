package cn.qiang.zhang.rippledemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;
import cn.qiang.zhang.rippledemo.widgets.LakeLayout;

/**
 * <p>
 * Created by mrZQ on 2017/3/21.
 */
public class SampleActivity extends AppCompatActivity {

    // todo should create dragonflyDap Fragment
    Button btnStart;
    Button btnStop;

    LakeLayout dragonflyDapView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int type = getIntent().getIntExtra(RippleDemoActivity.TAG, 0);

        switch (type) {
            case 0:
                showDragonflyDap();
                break;
        }

    }

    private void showDragonflyDap() {
        setContentView(R.layout.activity_dragonfly_dap);

        btnStart = ButterKnife.findById(this, R.id.start_anim);
        btnStop = ButterKnife.findById(this, R.id.stop_anim);

        dragonflyDapView = ButterKnife.findById(this, R.id.dragonfly_dap_view);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dragonflyDapView.startRippleAnimation();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dragonflyDapView.stopRippleAnimation();
            }
        });

    }
}
