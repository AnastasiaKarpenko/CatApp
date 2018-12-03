package ws.tilda.anastasia.catapp.ui.allcats;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ws.tilda.anastasia.catapp.data.repository.Repository;
import ws.tilda.anastasia.catapp.databinding.CatsBinding;
import ws.tilda.anastasia.catapp.ui.adapters.CatsAdapter;
import ws.tilda.anastasia.catapp.ui.cat.CatActivity;
import ws.tilda.anastasia.catapp.ui.cat.CatFragment;
import ws.tilda.anastasia.catapp.ui.viewmodels.AllCatsViewModel;
import ws.tilda.anastasia.catapp.ui.viewmodels.CustomFactory;

public class AllCatsFragment extends Fragment {

    private AllCatsViewModel mAllCatsViewModel;
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

            CustomFactory customFactory = new CustomFactory(repository, mOnItemClickListener);
            mAllCatsViewModel = ViewModelProviders.of(this, customFactory).get(AllCatsViewModel.class);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CatsBinding binding = CatsBinding.inflate(inflater, container, false);
        binding.setVm(mAllCatsViewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

}
