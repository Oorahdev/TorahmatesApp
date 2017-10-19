package com.vb.torahmate.utils;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.vb.torahmate.R;
import com.vb.torahmate.main.MainFragment;

/**
 * Created by psoloveichik on 9/14/2016.
 */

public class Nav {

    public static void mainFragment(FragmentManager fragmentManager) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MainFragment fragment = new MainFragment();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}
