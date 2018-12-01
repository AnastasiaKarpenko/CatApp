package ws.tilda.anastasia.catapp.ui.all;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ws.tilda.anastasia.catapp.R;
import ws.tilda.anastasia.catapp.repository.StubRepository;

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
        mAllCatsAdapter.addData(new StubRepository().getCats());

    }

}
