package ws.tilda.anastasia.catapp.ui.allcats;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ws.tilda.anastasia.catapp.R;
import ws.tilda.anastasia.catapp.data.api.ApiService;
import ws.tilda.anastasia.catapp.data.model.Cat;
import ws.tilda.anastasia.catapp.data.model.MainCat;
import ws.tilda.anastasia.catapp.data.repository.Repository;
import ws.tilda.anastasia.catapp.ui.adapters.CatsAdapter;
import ws.tilda.anastasia.catapp.ui.cat.CatActivity;
import ws.tilda.anastasia.catapp.ui.cat.CatFragment;

public class AllCatsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        CatsAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private CatsAdapter mCatsAdapter;
    private View mErrorView;
    private Repository mRepository;
    private Disposable mDisposable;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public AllCatsFragment() {
        // Required empty public constructor
    }

    public static AllCatsFragment newInstance() {
        return new AllCatsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Repository.RepositoryOwner) {
            mRepository = ((Repository.RepositoryOwner) context).obtainRepository();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cats_grid, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mSwipeRefreshLayout = view.findViewById(R.id.refresher);
        mRecyclerView = view.findViewById(R.id.cats_recyclerview);
        mErrorView = view.findViewById(R.id.errorView);
        mErrorView.setVisibility(View.GONE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        mCatsAdapter = new CatsAdapter(this);
        int SPAN_COUNT = 2;
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));
        mRecyclerView.setAdapter(mCatsAdapter);

        onRefresh();
    }

    @Override
    public void onDetach() {
        mRepository = null;
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        super.onDetach();
    }

    @Override
    public void onRefresh() {
        getAllCats();
    }

    private void getAllCats() {
        mDisposable = ApiService.getApiService().getAllCats("small", "DESC", 0, 10)
                .doOnSuccess(response -> mRepository.insertCats(getMainCats(response)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mSwipeRefreshLayout.setRefreshing(true))
                .doFinally(() -> mSwipeRefreshLayout.setRefreshing(false))
                .subscribe(
                        response -> {
                            mErrorView.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);

//                            mAllCatsAdapter.addData(response, true);
                            mCatsAdapter.addData(getMainCats(response), true);

                        },
                        throwable -> {
                            mErrorView.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.GONE);
                            Log.d("ERROR", throwable.getMessage());
                        });
    }

    private List<MainCat> getMainCats(List<Cat> cats) {
        List<MainCat> mainCats = new ArrayList<>();
        for (Cat cat : cats) {
            MainCat mainCat = new MainCat();
            mainCat.setCatId(cat.getId());
            mainCat.setPhotoUrl(cat.getUrl());
            mainCats.add(mainCat);
        }
        return mainCats;
    }

    @Override
    public void onItemClick(String catId) {
        Intent intent = new Intent(getActivity(), CatActivity.class);
        Bundle args = new Bundle();
        args.putString(CatFragment.CAT_ID_KEY, catId);
        intent.putExtra(CatActivity.CAT_ID_KEY, args);
        startActivity(intent);
    }
}
