package com.smartown.library.ui.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.smartown.library.ui.base.FragmentContainerActivity;

/**
 * Author:Tiger[https://github.com/KungFuBrother]
 * <p>
 * CrateTime:2016-10-20 13:27
 * <p>
 * Description:JumpUtils
 */
public class JumpUtils {

    public static void jump(Context context, String title, Class fragmentClass, Bundle fragmentArgument) {
        Intent intent = new Intent(context, FragmentContainerActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("fragmentClass", fragmentClass.getName());
        if (fragmentArgument != null) {
            intent.putExtra("fragmentArgument", fragmentArgument);
        }
        context.startActivity(intent);
    }

    public static void jump(Context context, String title, Class fragmentClass) {
        jump(context, title, fragmentClass, null);
    }

}
