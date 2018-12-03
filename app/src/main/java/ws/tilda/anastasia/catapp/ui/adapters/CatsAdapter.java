package ws.tilda.anastasia.catapp.ui.adapters;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import ws.tilda.anastasia.catapp.data.model.Cat;
import ws.tilda.anastasia.catapp.data.model.MainCat;
import ws.tilda.anastasia.catapp.databinding.CatBinding;
import ws.tilda.anastasia.catapp.ui.viewmodels.CatListItemViewModel;

public class CatsAdapter extends PagedListAdapter<MainCat, CatsAdapter.AllCatsViewHolder> {


    @NonNull
    private final OnItemClickListener mOnItemClickListener;

    public CatsAdapter(OnItemClickListener onItemClickListener) {
        super(CALLBACK);
        mOnItemClickListener = onItemClickListener;
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
        MainCat cat = getItem(i);
        if (cat != null) {
            allCatsViewHolder.bind(cat, mOnItemClickListener);
        }

    }

    private static final DiffUtil.ItemCallback<MainCat> CALLBACK = new DiffUtil.ItemCallback<MainCat>() {
        @Override
        public boolean areItemsTheSame(@NonNull MainCat oldItem, @NonNull MainCat newItem) {
            return oldItem.getCatId() == newItem.getCatId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull MainCat oldItem, @NonNull MainCat newItem) {
            return oldItem.equals(newItem);
        }
    };


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
            mCatBinding.setOnItemClickListener(listener);
            mCatBinding.executePendingBindings();
        }

    }
}
