package ws.tilda.anastasia.catapp.ui.allcats;

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
import ws.tilda.anastasia.catapp.data.model.Cat;
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
        return inflater.inflate(R.layout.fragment_all_cats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.all_cats_recyclerview);
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
    public void onDetach() {
        mRepository = null;
        mRefreshOwner = null;
        super.onDetach();
    }

    @Override
    public void onRefreshData() {
        getAllCats();
    }

    private void getAllCats() {
        Call<List<Cat>> call = ApiService.getApiService().getAllCats("small", "DESC", 0, 10);
        call.enqueue(new Callback<List<Cat>>() {
            @Override
            public void onResponse(@NonNull Call<List<Cat>> call, @NonNull Response<List<Cat>> response) {
                List<Cat> catsResponse = response.body();
                mErrorView.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mRepository.insertCats(catsResponse);
                mAllCatsAdapter.addData(catsResponse, true);
                mRefreshOwner.setRefreshState(false);
            }

            @Override
            public void onFailure(@NonNull Call<List<Cat>> call, Throwable t) {
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
