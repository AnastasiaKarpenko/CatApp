package ws.tilda.anastasia.catapp.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import ws.tilda.anastasia.catapp.data.model.MainCat;
import ws.tilda.anastasia.catapp.databinding.CatBinding;
import ws.tilda.anastasia.catapp.ui.viewmodels.CatListItemViewModel;

public class CatsAdapter extends RecyclerView.Adapter<CatsAdapter.AllCatsViewHolder> {


    @NonNull
    private final List<MainCat> mCats;
    private final OnItemClickListener mOnItemClickListener;

    public CatsAdapter(List<MainCat> cats, OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
        mCats = cats;
    }

    @NonNull
    @Override
    public AllCatsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        CatBinding binding = CatBinding.inflate(inflater, viewGroup, false);

        return new AllCatsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCatsViewHolder allCatsViewHolder, int i) {
        MainCat cat = mCats.get(i);
        allCatsViewHolder.bind(cat, mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mCats == null ? 0 : mCats.size();
    }

    public interface OnItemClickListener {
        void onItemClick(String catId);
    }


    public class AllCatsViewHolder extends RecyclerView.ViewHolder {
        private CatBinding mCatBinding;

        AllCatsViewHolder(@NonNull CatBinding binding) {
            super(binding.getRoot());
            mCatBinding = binding;
        }

        void bind(MainCat cat, OnItemClickListener listener) {

            mCatBinding.setCat(new CatListItemViewModel(cat));
            mCatBinding.setOnItemClickListener(mOnItemClickListener);
            mCatBinding.executePendingBindings();
        }


    }
}
