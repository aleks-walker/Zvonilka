package kg.kloop.android.zvonilka.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import kg.kloop.android.zvonilka.fragments.CallBackClientFragment;
import kg.kloop.android.zvonilka.fragments.CallClientFragment;
import kg.kloop.android.zvonilka.fragments.DontCallClientFragment;
import kg.kloop.android.zvonilka.fragments.SuccessClientFragment;

/**
 * Created by alexwalker on 12.09.17.
 */

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {

    String[] tabTitles = {"Call", "Call Back", "Success", "Don't call"};

    public CustomFragmentPagerAdapter(FragmentManager fm) {
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
                return new SuccessClientFragment();
            case 3:
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
