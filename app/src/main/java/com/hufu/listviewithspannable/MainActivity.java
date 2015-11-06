package com.hufu.listviewithspannable;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String[] texts = {
            "But you have to type this manually",
            "I manually set the variable for each heading. ",
            "So you should not manually enable them in this way. ",
            "However, you can also create them manually. ",
            "Therefore, you must perform this step manually with any of the migration approaches.",
            "I do it manually because that’s how you find the nasty logical security problems.",
            "You can utilize the generator either manually or as an iterator.",
            "Therefore, you need to manually change the title property weight in each object map that contains that object.",
    };


    /* 显示普通字符串文本的ListView */
    private ListView text_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title);
        //noinspection deprecation
        toolbar.setTitleTextColor(getResources().getColor(R.color.pink));
        setSupportActionBar(toolbar);

        text_list = (ListView) findViewById(R.id.list_text);
        text_list.setAdapter(new TextAdapter());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    class TextAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return texts.length;
        }

        @Override
        public Object getItem(int position) {
            return texts[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.listview_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            SpannableString spannableString = new SpannableString(texts[position]);
            spannableString.setSpan(new ForegroundColorSpan(Color.GREEN),1,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.BLUE),8,12, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.rgb(12,56,76)),17,26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            if(position != 2)
                spannableString.setSpan(new URLSpan("tel:415555121"+String.valueOf(position)), 17, 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            else{
                spannableString.setSpan(new URLSpan("https://www.baidu.com"), 17, 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

//            holder.text.setAutoLinkMask(Linkify.ALL);

            holder.text.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    boolean ret = false;
                    CharSequence text = ((TextView) v).getText();
                    Spannable stext = Spannable.Factory.getInstance().newSpannable(text);
                    TextView widget = (TextView) v;
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_UP) {
                        int x = (int) event.getX();
                        int y = (int) event.getY();
                        x -= widget.getTotalPaddingLeft();
                        y -= widget.getTotalPaddingTop();
                        x += widget.getScrollX();
                        y += widget.getScrollY();
                        Layout layout = widget.getLayout();
                        int line = layout.getLineForVertical(y);
                        int off = layout.getOffsetForHorizontal(line, x);
                        ClickableSpan[] link = stext.getSpans(off, off, ClickableSpan.class);
                        if (link.length != 0) {
                            if (action == MotionEvent.ACTION_UP) {
                                link[0].onClick(widget);
                            }
                            ret = true;
                        }
                    }

                    return ret;
                }
            });
            holder.text.setText(spannableString);
            return convertView;
        }
    }

    static class ViewHolder {

        private TextView text;

        ViewHolder(View view) {
            text = (TextView) view.findViewById(R.id.text);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
