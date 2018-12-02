package ws.tilda.anastasia.catapp.ui.cat;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ws.tilda.anastasia.catapp.R;
import ws.tilda.anastasia.catapp.data.api.ApiService;
import ws.tilda.anastasia.catapp.data.model.Cat;
import ws.tilda.anastasia.catapp.ui.RefreshOwner;
import ws.tilda.anastasia.catapp.ui.Refreshable;

public class CatFragment extends Fragment implements Refreshable {
    public static final String CAT_ID_KEY = "CAT_ID_KEY";

    private RefreshOwner mRefreshOwner;
    private View mErrorView;
    private View mCatView;
    private String mId;
    private Disposable mDisposable;

    private ImageView mCatPhotoIv;
    private TextView mCatIdTv;
    private CheckBox mFavoriteCb;


    public static CatFragment newInstance(Bundle args) {
        CatFragment fragment = new CatFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mRefreshOwner = context instanceof RefreshOwner ? (RefreshOwner) context : null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mErrorView = view.findViewById(R.id.errorView);
        mCatView = view.findViewById(R.id.view_cat);

        mCatPhotoIv = view.findViewById(R.id.cat_image);
        mCatIdTv = view.findViewById(R.id.cat_id);
        mFavoriteCb = view.findViewById(R.id.favorite_checkbox);

        mFavoriteCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //Logic of making the cat favorite and vice versa
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            mId = getArguments().getString(CAT_ID_KEY);
        }

        if (getActivity() != null) {
            getActivity().setTitle("Cat id: " + mId);
        }

        mCatView.setVisibility(View.VISIBLE);

        onRefreshData();
    }

    @Override
    public void onRefreshData() {
        getCat();
    }

    private void getCat() {
        mDisposable = ApiService.getApiService().getCat(mId, "full", true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mRefreshOwner.setRefreshState(true))
                .doFinally(() -> mRefreshOwner.setRefreshState(false))
                .subscribe(
                        response -> {
                            mErrorView.setVisibility(View.GONE);
                            mCatView.setVisibility(View.VISIBLE);
                            bind(response);
                        },
                        throwable -> {
                            mErrorView.setVisibility(View.VISIBLE);
                            mCatView.setVisibility(View.GONE);
                        });
    }

    private void bind(Cat cat) {
        Glide.with(mCatPhotoIv.getContext()).load(cat.getUrl())
                .centerCrop()
                .into(mCatPhotoIv);
        mCatIdTv.setText("Cat id: " + cat.getId());
    }

    @Override
    public void onDetach() {
        mRefreshOwner = null;
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        super.onDetach();
    }
}
