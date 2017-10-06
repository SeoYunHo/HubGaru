package teampj.java.dsm.hubgaruandroid.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import teampj.java.dsm.hubgaruandroid.Adapter.ViewPagerAdapter;
import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by user on 2017-08-28.
 */

public class TabLayoutActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    private ArrayList<String> infoArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout);
        /*
                    intent.putExtra("part", part);
                    intent.putExtra("intro", intro);
                    intent.putExtra("picture", picture);
                    intent.putExtra("name", name);
                    intent.putExtra("phone", phone);
        */

        Intent intent = getIntent();
        String part = intent.getStringExtra("part");
        String intro = intent.getStringExtra("intro");
        String picture = intent.getStringExtra("picture");
        String name = intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");

        Log.d(part + ", " + intro + ", " + picture + ", " + name + ", " + phone, "intentCheck");

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        tabLayout.addTab(tabLayout.newTab().setText("Hub"));
        tabLayout.addTab(tabLayout.newTab().setText("My Hub"));;
        tabLayout.addTab(tabLayout.newTab().setText("Garu"));
        tabLayout.addTab(tabLayout.newTab().setText("My Page"));

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
