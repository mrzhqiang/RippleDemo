package cn.qiang.zhang.rippledemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RippleDemoActivity extends AppCompatActivity {
    public static final String TAG = "RippleDemoActivity";

    private String[] funcName = {"L新动画", "加载布局", "对象动画", "视图动画", "动画框架", "简单应用"};

    private String[] designs = {"布局水波纹", "创建水波纹", "水波纹集合"};

    private String[] xmls = {"帧动画", "属性动画"};

    private String[] objects = {"代码控制", "静态创建", "布局+代码"};

    private String[] views = {"视图自带", "视图启动"};

    private String[] nines = {"缩放", "平移", "旋转", "透明"};

    private String[] samples = {"蜻蜓点水"};

    private Map<String, String[]> listMap = new LinkedHashMap<>();

    @BindView(R.id.ripple_demo_list)
    ExpandableListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple_demo);
        ButterKnife.bind(this);

        listMap.put(funcName[0], designs);
        listMap.put(funcName[1], xmls);
        listMap.put(funcName[2], objects);
        listMap.put(funcName[3], views);
        listMap.put(funcName[4], nines);
        listMap.put(funcName[5], samples);

        DemoAdapter adapter = new DemoAdapter();

        listView.setAdapter(adapter);

    }

    private boolean handlerChildClick(int groupPosition, int childPosition) {
        switch (groupPosition) {
            case 0:
                return handlerDesign(childPosition);
            case 1:
                return handlerXML(childPosition);
            case 2:
                return handlerObject(childPosition);
            case 3:
                return handlerView(childPosition);
            case 4:
                return handlerNine(childPosition);
            case 5:
                return handlerSample(childPosition);
        }
        return false;
    }

    private boolean handlerSample(int childPosition) {
        Intent intent = new Intent(this, SampleActivity.class);
        intent.putExtra(TAG, childPosition);
        startActivity(intent);
        return true;
    }

    private boolean handlerNine(int childPosition) {
        Intent intent = new Intent(this, NineActivity.class);
        intent.putExtra(TAG, childPosition);
        startActivity(intent);
        return true;
    }

    private boolean handlerView(int childPosition) {
        Intent intent = new Intent(this, ViewActivity.class);
        intent.putExtra(TAG, childPosition);
        startActivity(intent);
        return true;
    }

    private boolean handlerObject(int childPosition) {
        Intent intent = new Intent(this, ObjectActivity.class);
        intent.putExtra(TAG, childPosition);
        startActivity(intent);
        return true;
    }

    private boolean handlerXML(int childPosition) {
        Intent intent = new Intent(this, XmlActivity.class);
        intent.putExtra(TAG, childPosition);
        startActivity(intent);
        return true;
    }

    private boolean handlerDesign(int childPosition) {
        Intent intent = new Intent(this, DesignActivity.class);
        intent.putExtra(TAG, childPosition);
        startActivity(intent);
        return true;
    }

    private class DemoAdapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            return funcName.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            if (listMap != null && listMap.size() > 0) {
                String key = funcName[groupPosition];
                if (listMap.get(key) != null) {
                    return listMap.get(key).length;
                }
            }
            return 0;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return funcName[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return listMap.get(funcName[groupPosition])[childPosition];
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String groupName = funcName[groupPosition];
            if (convertView == null) {
                convertView = LayoutInflater.from(RippleDemoActivity.this).inflate(R.layout.item_group, null);
            }

            TextView groupTitle = ButterKnife.findById(convertView, R.id.group_title);
            groupTitle.setText(groupName);

            return convertView;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
                                 View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(RippleDemoActivity.this).inflate(R.layout.item_child, null);
            }

            String name = listMap.get(funcName[groupPosition])[childPosition];

            TextView childName = ButterKnife.findById(convertView, R.id.child_name);
            childName.setText(name);
            childName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handlerChildClick(groupPosition, childPosition);
                }
            });

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }
}
