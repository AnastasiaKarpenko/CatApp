package ws.tilda.anastasia.catapp.ui.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import ws.tilda.anastasia.catapp.data.repository.Repository;
import ws.tilda.anastasia.catapp.ui.adapters.CatsAdapter;

public class CustomFactory2 extends ViewModelProvider.NewInstanceFactory {
    private Repository mRepository;
    private CatsAdapter.OnItemClickListener mOnItemClickListener;

    public CustomFactory2(Repository repository, CatsAdapter.OnItemClickListener onItemClickListener) {
        mRepository = repository;
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new FavoriteCatsViewModel(mRepository, mOnItemClickListener);
    }
}
