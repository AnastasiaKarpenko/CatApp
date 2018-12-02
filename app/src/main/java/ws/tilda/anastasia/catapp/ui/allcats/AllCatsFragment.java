package ws.tilda.anastasia.catapp.ui.allcats;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ws.tilda.anastasia.catapp.R;
import ws.tilda.anastasia.catapp.data.api.ApiService;
import ws.tilda.anastasia.catapp.data.repository.Repository;
import ws.tilda.anastasia.catapp.ui.RefreshOwner;
import ws.tilda.anastasia.catapp.ui.Refreshable;
import ws.tilda.anastasia.catapp.ui.cat.CatActivity;
import ws.tilda.anastasia.catapp.ui.cat.CatFragment;

public class AllCatsFragment extends Fragment implements Refreshable, AllCatsAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private AllCatsAdapter mAllCatsAdapter;
    private RefreshOwner mRefreshOwner;
    private View mErrorView;
    private Repository mRepository;
    private Disposable mDisposable;

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

        if (context instanceof RefreshOwner) {
            mRefreshOwner = ((RefreshOwner) context);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cats_grid, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.cats_recyclerview);
        mErrorView = view.findViewById(R.id.errorView);
        mErrorView.setVisibility(View.GONE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAllCatsAdapter = new AllCatsAdapter(this);
        int SPAN_COUNT = 2;
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));
        mRecyclerView.setAdapter(mAllCatsAdapter);

        onRefreshData();
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefreshData();
    }

    @Override
    public void onDetach() {
        mRepository = null;
        mRefreshOwner = null;
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        super.onDetach();
    }

    @Override
    public void onRefreshData() {
        getAllCats();
    }

    private void getAllCats() {
        mDisposable = ApiService.getApiService().getAllCats("small", "DESC", 0, 10)
                .doOnSuccess(response -> mRepository.insertCats(response))
                .onErrorReturn(throwable ->
                        ApiService.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ? mRepository.getAllCats() : null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mRefreshOwner.setRefreshState(true))
                .doFinally(() -> mRefreshOwner.setRefreshState(false))
                .subscribe(
                        response -> {
                            mErrorView.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            mAllCatsAdapter.addData(response, true);
                        },
                        throwable -> {
                            mErrorView.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.GONE);
                        });
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
