package ws.tilda.anastasia.catapp.ui;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import ws.tilda.anastasia.catapp.MyApplication;
import ws.tilda.anastasia.catapp.R;
import ws.tilda.anastasia.catapp.data.repository.Repository;
import ws.tilda.anastasia.catapp.ui.allcats.AllCatsFragment;
import ws.tilda.anastasia.catapp.ui.favoritecats.FavoriteCatsFragment;

public class CatsGridActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        RefreshOwner, Repository.RepositoryOwner {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    final Fragment mFragment1 = AllCatsFragment.newInstance();
    final Fragment mFragment2 = FavoriteCatsFragment.newInstance();
    final FragmentManager mFragmentManager = getSupportFragmentManager();


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_all:
                switchToFragment(mFragment1);
                return true;
            case R.id.navigation_favorite:
                switchToFragment(mFragment2);
                return true;
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cats_grid);

        mSwipeRefreshLayout = findViewById(R.id.refresher);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        switchToFragment(mFragment1);
    }

    private void switchToFragment(Fragment fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment).commit();
    }

    @Override
    public void onRefresh() {
        Fragment fragment = mFragmentManager.findFragmentById(R.id.main_container);
        if (fragment instanceof Refreshable) {
            ((Refreshable) fragment).onRefreshData();
        } else {
            setRefreshState(false);
        }
    }

    @Override
    public void setRefreshState(boolean refreshing) {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(refreshing));
    }

    @Override
    public Repository obtainRepository() {
        return ((MyApplication) getApplicationContext()).getRepository();
    }
}
