package com.smartown.demo;

import com.smartown.library.ui.base.BaseActivity;
import com.smartown.library.ui.utils.JumpUtils;

public class MainActivity extends BaseActivity {

    @Override
    protected void init() {
        JumpUtils.jump(this, "Demo", SimpleListFragment.class);
        finish();
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
