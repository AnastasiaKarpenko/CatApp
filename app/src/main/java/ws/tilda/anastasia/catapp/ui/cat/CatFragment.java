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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ws.tilda.anastasia.catapp.R;
import ws.tilda.anastasia.catapp.data.model.Cat;
import ws.tilda.anastasia.catapp.data.repository.Repository;
import ws.tilda.anastasia.catapp.ui.RefreshOwner;
import ws.tilda.anastasia.catapp.ui.Refreshable;

public class CatFragment extends Fragment implements Refreshable {
    public static final String CAT_ID_KEY = "CAT_ID_KEY";


    private RefreshOwner mRefreshOwner;
    private View mErrorView;
    private View mCatView;
    private String mId;
    private Repository mRepository;

    private ImageView mCatPhotoIv;
    private TextView mCatIdTv;
    private CheckBox mFavoriteCb;


    public static CatFragment newInstance(Bundle args)

    {
        CatFragment fragment = new CatFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mRepository = context instanceof Repository.RepositoryOwner ?
                ((Repository.RepositoryOwner) context).obtainRepository() : null;
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
        getSpecificCat();
    }

    private void getSpecificCat() {
        //Implement networking method to get specific cat
    }

    private void bind(Cat cat) {
        Glide.with(mCatPhotoIv.getContext()).load(cat.getUrl())
                .centerCrop()
                .into(mCatPhotoIv);
        mCatIdTv.setText(cat.getId());
    }

    @Override
    public void onDetach() {
        mRepository = null;
        mRefreshOwner = null;
        super.onDetach();
    }
}
