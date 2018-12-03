package ws.tilda.anastasia.catapp.ui.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ws.tilda.anastasia.catapp.data.api.ApiService;
import ws.tilda.anastasia.catapp.data.model.Cat;
import ws.tilda.anastasia.catapp.data.model.MainCat;
import ws.tilda.anastasia.catapp.data.repository.Repository;
import ws.tilda.anastasia.catapp.ui.adapters.CatsAdapter;

public class AllCatsViewModel extends ViewModel {
    private Disposable mDisposable;
    private Repository mRepository;
    private CatsAdapter.OnItemClickListener mOnItemClickListener;

    private MutableLiveData<Boolean> mIsErrorVisible = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    private LiveData<List<MainCat>> mCats;
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = () -> {
        updateAllCats();
    };

    public AllCatsViewModel(Repository repository, CatsAdapter.OnItemClickListener onItemClickListener) {
        mRepository = repository;
        mOnItemClickListener = onItemClickListener;
        mCats = mRepository.getAllCatsLive();
    }

    private void updateAllCats() {
        mDisposable = ApiService.getApiService().getAllCats("small", "DESC", 0, 20)
                .doOnSubscribe(disposable -> mIsLoading.postValue(true))
                .doFinally(() -> mIsLoading.postValue(false))
                .doOnSuccess(response -> mIsErrorVisible.postValue(false))
                .subscribeOn(Schedulers.io())
                .subscribe(
                        response -> mRepository.insertCats(catsToMainCats(response)),
                        throwable -> {
                            mIsErrorVisible.postValue(mCats.getValue().size() == 0 ||
                                    mCats.getValue() == null);
                            Log.d("loadAllCats() ERROR: ", throwable.getMessage());
                        });
    }

    private List<MainCat> catsToMainCats(List<Cat> cats) {
        List<MainCat> mainCats = new ArrayList<>();
        for (Cat cat : cats) {
            MainCat mainCat = new MainCat();
            mainCat.setCatId(cat.getId());
            mainCat.setPhotoUrl(cat.getUrl());
            mainCats.add(mainCat);
        }
        return mainCats;
    }

    @Override
    public void onCleared() {
        mRepository = null;
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    public CatsAdapter.OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return mOnRefreshListener;
    }

    public MutableLiveData<Boolean> getIsErrorVisible() {
        return mIsErrorVisible;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return mIsLoading;
    }

    public LiveData<List<MainCat>> getCats() {
        return mCats;
    }
}
