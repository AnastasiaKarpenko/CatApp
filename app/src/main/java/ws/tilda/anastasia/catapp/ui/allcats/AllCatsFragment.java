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

public class AllCatsFragment extends Fragment {

    private CatsViewModel mCatsViewModel;
    private CatsAdapter.OnItemClickListener mOnItemClickListener =
            catId -> {
                Intent intent = new Intent(getActivity(), CatActivity.class);
                Bundle args = new Bundle();
                args.putString(CatFragment.CAT_ID_KEY, catId);
                intent.putExtra(CatActivity.CAT_ID_KEY, args);
                startActivity(intent);
            };

    public static AllCatsFragment newInstance() {
        return new AllCatsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Repository.RepositoryOwner) {
            Repository repository = ((Repository.RepositoryOwner) context).obtainRepository();
            mCatsViewModel = new CatsViewModel(repository, mOnItemClickListener);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cats_grid, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mCatsViewModel.loadAllCats();
    }

    @Override
    public void onDetach() {
        mCatsViewModel.dispatchDetach();
        super.onDetach();
    }

}
