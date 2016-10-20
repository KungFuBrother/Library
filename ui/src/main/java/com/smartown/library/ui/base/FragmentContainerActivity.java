package com.smartown.library.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smartown.library.ui.R;

import java.lang.reflect.Field;

/**
 * Author:Tiger[https://github.com/KungFuBrother]
 * <p>
 * CreateTime:2016-10-20 13:24:29
 * <p>
 * Description:FragmentContainerActivity
 */
public class FragmentContainerActivity extends BaseActivity {

    private View statusBar;
    private ImageView backButton;
    private TextView titleTextView;

    private String title;
    private String fragmentClass;
    private Bundle fragmentArgument;

    private int statusBarHeight = 0;

    @Override
    protected void init() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("title")) {
                title = intent.getStringExtra("title");
            }
            if (intent.hasExtra("fragmentClass")) {
                fragmentClass = intent.getStringExtra("fragmentClass");
            }
            if (intent.hasExtra("fragmentArgument")) {
                fragmentArgument = intent.getBundleExtra("fragmentArgument");
            }
        }
        calculateStatusBarHeight();
        findViews(R.layout.activity_fragment_container);
    }

    @Override
    protected void findViews() {
        statusBar = findViewById(R.id.container_title_status);
        backButton = (ImageView) findViewById(R.id.container_title_back);
        titleTextView = (TextView) findViewById(R.id.container_title_text);
    }

    @Override
    protected void initViews() {
        if (statusBarHeight > 0) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
            statusBar.setLayoutParams(layoutParams);
        }
        titleTextView.setText(title);
        try {
            Fragment fragment = (Fragment) Class.forName(fragmentClass).newInstance();
            if (fragmentArgument != null) {
                fragment.setArguments(fragmentArgument);
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, fragment).commit();

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void registerViews() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Calculate statusBar's height.
     */
    private void calculateStatusBarHeight() {
        try {
            Class c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            statusBarHeight = getResources().getDimensionPixelSize(Integer.parseInt(field.get(obj).toString()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void setTitle(String title) {
        this.title = title;
        titleTextView.setText(title);
    }

    public int getStatusBarHeight() {
        return statusBarHeight;
    }

}
