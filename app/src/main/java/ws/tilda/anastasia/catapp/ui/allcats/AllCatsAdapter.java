package ws.tilda.anastasia.catapp.ui.allcats;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ws.tilda.anastasia.catapp.R;
import ws.tilda.anastasia.catapp.data.model.Cat;

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
        View view = inflater.inflate(R.layout.list_item_cat_layout, viewGroup, false);
        return new AllCatsViewHolder(view);
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
        ImageView mCatPhoto;

        AllCatsViewHolder(@NonNull View itemView) {
            super(itemView);
            mCatPhoto = itemView.findViewById(R.id.cat_photo_iv);
        }

        void bind(Cat cat, OnItemClickListener listener) {
            String url = cat.getUrl();

//        Tried to implement the logic of loading the images, but it does not work. Need to review later
//        Bitmap photo = getBitmap(url);
//        mCatPhoto.setImageBitmap(photo);


            //Decided to use Glide for now
            Glide.with(mCatPhoto.getContext()).load(url)
                    .centerCrop()
                    .into(mCatPhoto);

            if (listener != null) {
            itemView.setOnClickListener(v -> listener.onItemClick(
                    cat.getId()
            ));
        }
    }

        private Bitmap getBitmap(String url) {
            try {
                InputStream is = (InputStream) new URL(url).getContent();
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                is.close();
                return bitmap;

            } catch (Exception e) {
                return null;
            }
        }

    }
}
