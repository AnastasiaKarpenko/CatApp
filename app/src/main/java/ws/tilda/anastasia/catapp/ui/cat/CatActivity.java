package ws.tilda.anastasia.catapp.ui.cat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import ws.tilda.anastasia.catapp.MyApplication;
import ws.tilda.anastasia.catapp.R;
import ws.tilda.anastasia.catapp.data.repository.Repository;
import ws.tilda.anastasia.catapp.ui.RefreshOwner;
import ws.tilda.anastasia.catapp.ui.Refreshable;

public class CatActivity extends AppCompatActivity implements Repository.RepositoryOwner,
        SwipeRefreshLayout.OnRefreshListener, RefreshOwner {
    public static final String CAT_ID_KEY = "CAT_ID_KEY";

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);

        mSwipeRefreshLayout = findViewById(R.id.refresher);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        if (savedInstanceState == null) {
            setFragment(getFragment());
        }

    }
    protected Fragment getFragment() {
        if (getIntent() != null) {
            return CatFragment.newInstance(getIntent().getBundleExtra(CAT_ID_KEY));
        }
        throw new IllegalStateException("getIntent cannot be null");
    }


    public void setFragment(Fragment fragment) {
        boolean addToBackStack = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer) != null;

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment);

        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }

        transaction.commit();
    }

    @Override
    public void onRefresh() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (fragment instanceof Refreshable) {
            ((Refreshable) fragment).onRefreshData();
        } else {
            setRefreshState(false);
        }
    }

    @Override
    public Repository obtainRepository() {
        return ((MyApplication) getApplicationContext()).getRepository();
    }

    @Override
    public void setRefreshState(boolean refreshing) {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(refreshing));
    }
}
