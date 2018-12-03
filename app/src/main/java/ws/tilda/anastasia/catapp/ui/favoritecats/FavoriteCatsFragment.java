package ws.tilda.anastasia.catapp.ui.favoritecats;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ws.tilda.anastasia.catapp.data.repository.Repository;
import ws.tilda.anastasia.catapp.databinding.FavoriteCatsBinding;
import ws.tilda.anastasia.catapp.ui.adapters.CatsAdapter;
import ws.tilda.anastasia.catapp.ui.allcats.AllCatsFragment;
import ws.tilda.anastasia.catapp.ui.cat.CatActivity;
import ws.tilda.anastasia.catapp.ui.cat.CatFragment;
import ws.tilda.anastasia.catapp.ui.viewmodels.CustomFactory2;
import ws.tilda.anastasia.catapp.ui.viewmodels.FavoriteCatsViewModel;

public class FavoriteCatsFragment extends AllCatsFragment {


    public static FavoriteCatsFragment newInstance() {
        return new FavoriteCatsFragment();
    }


    private FavoriteCatsViewModel mFavoriteCatsViewModel;
    private CatsAdapter.OnItemClickListener mOnItemClickListener =
            catId -> {
                Intent intent = new Intent(getActivity(), CatActivity.class);
                Bundle args = new Bundle();
                args.putString(CatFragment.CAT_ID_KEY, catId);
                intent.putExtra(CatActivity.CAT_ID_KEY, args);
                startActivity(intent);
            };


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Repository.RepositoryOwner) {
            Repository repository = ((Repository.RepositoryOwner) context).obtainRepository();
            CustomFactory2 customFactory = new CustomFactory2(repository, mOnItemClickListener);
            mFavoriteCatsViewModel = ViewModelProviders.of(this, customFactory).get(FavoriteCatsViewModel.class);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FavoriteCatsBinding binding = FavoriteCatsBinding.inflate(inflater, container, false);
        binding.setVm(mFavoriteCatsViewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

}
