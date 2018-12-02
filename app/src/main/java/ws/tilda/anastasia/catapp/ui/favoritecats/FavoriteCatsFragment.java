package ws.tilda.anastasia.catapp.ui.favoritecats;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ws.tilda.anastasia.catapp.R;
import ws.tilda.anastasia.catapp.data.api.ApiService;
import ws.tilda.anastasia.catapp.data.model.FavoriteCat;
import ws.tilda.anastasia.catapp.ui.RefreshOwner;
import ws.tilda.anastasia.catapp.ui.Refreshable;
import ws.tilda.anastasia.catapp.ui.cat.CatActivity;
import ws.tilda.anastasia.catapp.ui.cat.CatFragment;

public class FavoriteCatsFragment extends Fragment implements Refreshable, FavoriteCatsAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private FavoriteCatsAdapter mFavoriteCatsAdapter;
    private RefreshOwner mRefreshOwner;
    private View mErrorView;
    private View mEmptyView;

    public FavoriteCatsFragment() {
        // Required empty public constructor
    }

    public static FavoriteCatsFragment newInstance() {
        return new FavoriteCatsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

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

        mEmptyView = view.findViewById(R.id.emptyView);
        mEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mFavoriteCatsAdapter = new FavoriteCatsAdapter(this);
        int SPAN_COUNT = 2;
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));
        mRecyclerView.setAdapter(mFavoriteCatsAdapter);
        onRefreshData();
    }



    @Override
    public void onResume() {
        super.onResume();
        onRefreshData();
    }

    @Override
    public void onDetach() {
        mRefreshOwner = null;
        super.onDetach();
    }

    @Override
    public void onRefreshData() {
        getFavoriteCats();
    }

    private void getFavoriteCats() {
        Call<List<FavoriteCat>> call = ApiService.getApiService().getFavoriteCats();
        call.enqueue(new Callback<List<FavoriteCat>>() {
            @Override
            public void onResponse(@NonNull Call<List<FavoriteCat>> call, @NonNull Response<List<FavoriteCat>> response) {
                List<FavoriteCat> catsResponse = response.body();

                if (catsResponse != null && !catsResponse.isEmpty()) {
                    mFavoriteCatsAdapter.addData(catsResponse, true);
                    mRefreshOwner.setRefreshState(false);
                    mErrorView.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    mEmptyView.setVisibility(View.VISIBLE);
                }
                mRefreshOwner.setRefreshState(false);
            }

            @Override
            public void onFailure(@NonNull Call<List<FavoriteCat>> call, Throwable t) {
                mErrorView.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
                mRefreshOwner.setRefreshState(false);
                Log.d("RETROFIT ERROR", "Error received:" + t.getMessage());
            }
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
