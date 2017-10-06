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
    private static String part, intro, picture, name, phone, id;

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
        part = intent.getStringExtra("part");
        intro = intent.getStringExtra("intro");
        picture = intent.getStringExtra("picture");
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        id = intent.getStringExtra("id");


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

    public static String getPart() {
        return part;
    }

    public static String getIntro() {
        return intro;
    }

    public static String getPicture() {
        return picture;
    }

    public static String getName() {
        return name;
    }

    public static String getPhone() {
        return phone;
    }

    public static String getId() {
        return id;
    }
}
