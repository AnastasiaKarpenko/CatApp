package ws.tilda.anastasia.catapp.ui.viewmodels;

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
import ws.tilda.anastasia.catapp.data.model.FavoriteCat;
import ws.tilda.anastasia.catapp.data.model.MainCat;
import ws.tilda.anastasia.catapp.data.repository.Repository;
import ws.tilda.anastasia.catapp.ui.adapters.CatsAdapter;

public class CatsViewModel extends ViewModel {
    private Disposable mDisposable;
    private Repository mRepository;
    private CatsAdapter.OnItemClickListener mOnItemClickListener;

    private MutableLiveData<Boolean> mIsFavCatsEmpty = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsErrorVisible = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    private MutableLiveData<List<MainCat>> mCats = new MutableLiveData<>();
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = () -> {
        loadAllCats();
    };

    public CatsViewModel(Repository repository, CatsAdapter.OnItemClickListener onItemClickListener) {
        mRepository = repository;
        mOnItemClickListener = onItemClickListener;
        mCats.setValue(new ArrayList<>());
    }


    public void loadAllCats() {
        mDisposable = ApiService.getApiService().getAllCats("small", "DESC", 0, 20)
                .doOnSuccess(response -> mRepository.insertCats(catsToMainCats(response)))
                .doOnError(throwable -> {
                    mCats.postValue(mRepository.getAllCats());
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mIsLoading.postValue(true))
                .doFinally(() -> mIsLoading.postValue(false))
                .subscribe(
                        response -> {
                            mIsErrorVisible.postValue(false);
                            mCats.postValue(catsToMainCats(response));

                        },
                        throwable -> {
                            mIsErrorVisible.postValue(false);
                            Log.d("loadAllCats() ERROR: ", throwable.getMessage());
                        });
    }

    public void loadFavoriteCats() {
        mDisposable = ApiService.getApiService().getFavoriteCats()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mIsLoading.postValue(true))
                .doFinally(() -> mIsLoading.postValue(false))
                .subscribe(
                        response -> {
                            mIsErrorVisible.postValue(false);
                            if (response != null && !response.isEmpty()) {
                                //mCats.clear();
                                mCats.postValue(favoriteCatsToMainCats(response));
                            } else {
                                mIsFavCatsEmpty.postValue(true);
                            }
                        },
                        throwable -> {
                            mIsErrorVisible.postValue(false);
                            Log.d("loadFavoriteCats():ERR ", throwable.getMessage());
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

    private List<MainCat> favoriteCatsToMainCats(List<FavoriteCat> cats) {
        List<MainCat> mainCats = new ArrayList<>();
        for (FavoriteCat cat : cats) {
            MainCat mainCat = new MainCat();
            mainCat.setCatId(cat.getImage().getId());
            mainCat.setPhotoUrl(cat.getImage().getUrl());
            mainCat.setFavoriteId(cat.getId());
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

    public MutableLiveData<Boolean> getIsFavCatsEmpty() {
        return mIsFavCatsEmpty;
    }

    public MutableLiveData<Boolean> getIsErrorVisible() {
        return mIsErrorVisible;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return mIsLoading;
    }

    public MutableLiveData<List<MainCat>> getCats() {
        return mCats;
    }
}
