package ws.tilda.anastasia.catapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import ws.tilda.anastasia.catapp.R;
import ws.tilda.anastasia.catapp.ui.all.AllCatsFragment;
import ws.tilda.anastasia.catapp.ui.favorite.FavoriteCatsFragment;

public class MainActivity extends AppCompatActivity {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    final Fragment mFragment1 = AllCatsFragment.newInstance();
    final Fragment mFragment2 = FavoriteCatsFragment.newInstance();
    final FragmentManager mFragmentManager = getSupportFragmentManager();
    Fragment mActiveFragment = mFragment1;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_all:
                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(mFragment1).commit();
                    mActiveFragment = mFragment1;
                    return true;
                case R.id.navigation_favorite:
                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(mFragment2).commit();
                    mActiveFragment = mFragment2;
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwipeRefreshLayout = findViewById(R.id.refresher);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        mFragmentManager.beginTransaction().add(R.id.main_container, mFragment2, "2").hide(mFragment2).commit();
//        mFragmentManager.beginTransaction().add(R.id.main_container, mFragment1, "1").commit();

        switchToFragment(mFragment1);
    }

    private void switchToFragment(Fragment fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment).commit();
    }

}
