package teampj.java.dsm.hubgaruandroid.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by user on 2017-09-11.
 */

public class MyPageViewPager extends FragmentPagerAdapter {

    private final int PAGE_NUM = 3;

    public MyPageViewPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0 :
            case 1:
            case 2:
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 0;
    }
}
