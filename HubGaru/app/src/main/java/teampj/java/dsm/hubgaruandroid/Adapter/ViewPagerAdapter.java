package teampj.java.dsm.hubgaruandroid.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import teampj.java.dsm.hubgaruandroid.Fragment.MyHubFragment;
import teampj.java.dsm.hubgaruandroid.Fragment.MyPageFragment;
import teampj.java.dsm.hubgaruandroid.Fragment.New_TOPHubFragment;
import teampj.java.dsm.hubgaruandroid.Fragment.SproutHubFragment;

/**
 * Created by user on 2017-08-22.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter{

    private final int PAGE_NUM = 4;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0 :
                New_TOPHubFragment newHub = new New_TOPHubFragment();
                return newHub;
            case 1 :
                MyHubFragment myHub = new MyHubFragment();
                return myHub;
            case 2 :
                SproutHubFragment sproutHub = new SproutHubFragment();
                return sproutHub;
            case 3 :
                MyPageFragment mypage = new MyPageFragment();
                return mypage;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }

}
