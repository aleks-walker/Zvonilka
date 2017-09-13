package kg.kloop.android.zvonilka.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import kg.kloop.android.zvonilka.fragments.CallBackClientFragment;
import kg.kloop.android.zvonilka.fragments.CallClientFragment;
import kg.kloop.android.zvonilka.fragments.DontCallClientFragment;

/**
 * Created by alexwalker on 12.09.17.
 */

public class CustomFragmetPagerAdapter extends FragmentPagerAdapter {

    String[] tabTitles = {"Call", "Call Back", "Don't call"};

    public CustomFragmetPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new CallClientFragment();
            case 1:
                return new CallBackClientFragment();
            case 2:
                return new DontCallClientFragment();
            default:
                return new CallClientFragment();
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}