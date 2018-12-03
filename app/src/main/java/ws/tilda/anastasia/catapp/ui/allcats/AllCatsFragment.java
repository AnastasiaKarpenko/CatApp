package ws.tilda.anastasia.catapp.ui.allcats;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ws.tilda.anastasia.catapp.R;
import ws.tilda.anastasia.catapp.data.repository.Repository;
import ws.tilda.anastasia.catapp.ui.adapters.CatsAdapter;
import ws.tilda.anastasia.catapp.ui.cat.CatActivity;
import ws.tilda.anastasia.catapp.ui.cat.CatFragment;
import ws.tilda.anastasia.catapp.ui.viewmodels.CatsViewModel;

public class AllCatsFragment extends Fragment implements
        CatsAdapter.OnItemClickListener {

//    private RecyclerView mRecyclerView;
//    private CatsAdapter mCatsAdapter;
//    private View mErrorView;
//    private SwipeRefreshLayout mSwipeRefreshLayout;

    CatsViewModel mCatsViewModel;

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
            Repository repository = ((Repository.RepositoryOwner) context).obtainRepository();
            mCatsViewModel = new CatsViewModel(repository);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cats_grid, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        mSwipeRefreshLayout = view.findViewById(R.id.refresher);
//        mRecyclerView = view.findViewById(R.id.cats_recyclerview);
//        mErrorView = view.findViewById(R.id.errorView);
//        mErrorView.setVisibility(View.GONE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        mSwipeRefreshLayout.setOnRefreshListener(this);
//
//        mCatsAdapter = new CatsAdapter(this);
        int SPAN_COUNT = 2;
//        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));
//        mRecyclerView.setAdapter(mCatsAdapter);

//        onRefresh();
    }

    @Override
    public void onDetach() {
//        mRepository = null;
//        if (mDisposable != null) {
//            mDisposable.dispose();
//        }
        super.onDetach();
    }

//    @Override
//    public void onRefresh() {
//        getAllCats();
//    }


    @Override
    public void onItemClick(String catId) {
        Intent intent = new Intent(getActivity(), CatActivity.class);
        Bundle args = new Bundle();
        args.putString(CatFragment.CAT_ID_KEY, catId);
        intent.putExtra(CatActivity.CAT_ID_KEY, args);
        startActivity(intent);
    }
}
