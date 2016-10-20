package com.smartown.demo;

import android.support.v4.app.Fragment;

import com.smartown.library.ui.base.BaseActivity;
import com.smartown.library.ui.utils.JumpUtils;

public class MainActivity extends BaseActivity {

    @Override
    protected void init() {
        findViews(R.layout.activity_main);
        JumpUtils.jump(this, "TestJump", Fragment.class);
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void registerViews() {

    }
}
