package ws.tilda.anastasia.catapp.ui.allcats;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import ws.tilda.anastasia.catapp.data.model.Cat;
import ws.tilda.anastasia.catapp.databinding.CatBinding;

public class AllCatsAdapter extends RecyclerView.Adapter<AllCatsAdapter.AllCatsViewHolder> {


    @NonNull
    private final List<Cat> mCats = new ArrayList<>();
    private final OnItemClickListener mOnItemClickListener;

    public AllCatsAdapter(OnItemClickListener onItemClickListener) {
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
        Cat cat = mCats.get(i);
        allCatsViewHolder.bind(cat, mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mCats.size();
    }


    void addData(List<Cat> data, boolean isRefreshed) {
        if (isRefreshed) {
            mCats.clear();
        }

        mCats.addAll(data);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(String catId);
    }


    class AllCatsViewHolder extends RecyclerView.ViewHolder {
        private CatBinding mCatBinding;
        ImageView mCatPhoto;

        AllCatsViewHolder(@NonNull CatBinding binding) {
            super(binding.getRoot());
            mCatBinding = binding;

        }

        void bind(Cat cat, OnItemClickListener listener) {

            mCatBinding.setCat(new CatListItemViewModel(cat));
            mCatBinding.setOnItemClickListener(mOnItemClickListener);
            mCatBinding.executePendingBindings();

//            //Decided to use Glide for now
//            Glide.with(mCatPhoto.getContext()).load(url)
//                    .centerCrop()
//                    .into(mCatPhoto);
//
//            if (listener != null) {
//                itemView.setOnClickListener(v -> listener.onItemClick(
//                        cat.getId()
//                ));
//            }
        }
    }
}
