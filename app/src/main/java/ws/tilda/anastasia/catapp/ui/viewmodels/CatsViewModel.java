package ws.tilda.anastasia.catapp.ui.viewmodels;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
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

public class CatsViewModel {
    private Disposable mDisposable;
    private Repository mRepository;
    private CatsAdapter.OnItemClickListener mOnItemClickListener;

    private ObservableBoolean mIsErrorVisible = new ObservableBoolean(false);
    private ObservableBoolean mIsLoading = new ObservableBoolean(false);
    private ObservableArrayList<MainCat> mCats = new ObservableArrayList<>();
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = () -> loadAllCats();

    public CatsViewModel(Repository repository, CatsAdapter.OnItemClickListener onItemClickListener) {
        mRepository = repository;
        mOnItemClickListener = onItemClickListener;
    }


    public void loadAllCats() {
        mDisposable = ApiService.getApiService().getAllCats("small", "DESC", 0, 10)
                .doOnSuccess(response -> mRepository.insertCats(getMainCats(response)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mIsLoading.set(true))
                .doFinally(() -> mIsLoading.set(false))
                .subscribe(
                        response -> {
                            mIsErrorVisible.set(false);
                            mCats.addAll(getMainCats(response));

                        },
                        throwable -> {
                            mIsErrorVisible.set(false);
                            Log.d("loadAllCats() ERROR: ", throwable.getMessage());
                        });
    }

    private List<MainCat> getMainCats(List<Cat> cats) {
        List<MainCat> mainCats = new ArrayList<>();
        for (Cat cat : cats) {
            MainCat mainCat = new MainCat();
            mainCat.setCatId(cat.getId());
            mainCat.setPhotoUrl(cat.getUrl());
            mainCats.add(mainCat);
        }
        return mainCats;
    }

    public void dispatchDetach() {
        mRepository = null;
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    public CatsAdapter.OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public ObservableBoolean getIsErrorVisible() {
        return mIsErrorVisible;
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public ObservableArrayList<MainCat> getCats() {
        return mCats;
    }

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return mOnRefreshListener;
    }
}
