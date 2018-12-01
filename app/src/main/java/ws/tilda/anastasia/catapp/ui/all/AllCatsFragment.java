package ws.tilda.anastasia.catapp.ui.all;

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
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ws.tilda.anastasia.catapp.R;
import ws.tilda.anastasia.catapp.model.Cat;
import ws.tilda.anastasia.catapp.network.NetworkingService;

public class AllCatsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private final int SPAN_COUNT = 2;
    private AllCatsAdapter mAllCatsAdapter;


    public AllCatsFragment() {
        // Required empty public constructor
    }

    public static AllCatsFragment newInstance() {
        AllCatsFragment fragment = new AllCatsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_cats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.all_cats_recyclerview);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAllCatsAdapter = new AllCatsAdapter();
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));
        mRecyclerView.setAdapter(mAllCatsAdapter);

        getAllCats();

    }

    private void getAllCats() {
        Call<List<Cat>> call = NetworkingService.getApiService().getAllCats("small", "DESC", 0, 10);
        call.enqueue(new Callback<List<Cat>>() {
            @Override
            public void onResponse(Call<List<Cat>> call, Response<List<Cat>> response) {
                List<Cat> getCatResponse = response.body();
                mAllCatsAdapter.addData(getCatResponse);
            }

            @Override
            public void onFailure(Call<List<Cat>> call, Throwable t) {
                Toast.makeText(getContext(), "Error received", Toast.LENGTH_SHORT).show();
                Log.d("RETROFIT ERROR", "Error received" + t.getMessage());
            }
        });
    }

}
