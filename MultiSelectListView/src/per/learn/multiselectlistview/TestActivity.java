package per.learn.multiselectlistview;

import per.learn.multiselectlistview.lib.MultiSelectListView;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class TestActivity extends Activity {
    private MultiSelectListView mMsLv;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mMsLv = (MultiSelectListView)findViewById(R.id.multi_select_lv);
        mAdapter = new MyAdapter(this, 20);
        mMsLv.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test, menu);
        return true;
    }

    public class MyAdapter extends BaseAdapter {
        private Context mContext;
        private int mItemCount = 10;

        public MyAdapter(Context c, int count) {
            mContext = c;
            mItemCount = count;
        }

        @Override
        public int getCount() {
            return mItemCount;
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if(convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.layout_list_item, null);
                holder = new ViewHolder();
                holder.mTv = (TextView)convertView.findViewById(R.id.content_tv);
                holder.mCb = (CheckBox)convertView.findViewById(R.id.select_cb);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }

            holder.mTv.setText("Item " + position);

            return convertView;
        }
    }

    private static class ViewHolder {
        TextView mTv;
        CheckBox mCb;
    }
}
