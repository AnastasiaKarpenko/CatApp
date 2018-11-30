package ws.tilda.anastasia.catapp.ui.all;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ws.tilda.anastasia.catapp.R;
import ws.tilda.anastasia.catapp.model.Cat;

public class AllCatsAdapter extends RecyclerView.Adapter<AllCatsAdapter.AllCatsViewHolder> {

    @NonNull
    private final List<Cat> mCats = new ArrayList<>();

    public AllCatsAdapter() {

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
        String url = cat.getUrl();
        Bitmap photo = getBitmap(url);
        allCatsViewHolder.mCatPhoto.setImageBitmap(photo);
    }

    @Override
    public int getItemCount() {
        return mCats.size();
    }

    private Bitmap getBitmap(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Bitmap d = BitmapFactory.decodeStream(is);
            is.close();
            return d;

        } catch (Exception e) {
            return null;
        }
    }

    public void addData(List<Cat> data) {
        mCats.addAll(data);
        notifyDataSetChanged();
    }

    public class AllCatsViewHolder extends RecyclerView.ViewHolder {
        private ImageView mCatPhoto;

        public AllCatsViewHolder(@NonNull View itemView) {
            super(itemView);
            mCatPhoto = itemView.findViewById(R.id.cat_photo_iv);
        }

    }
}
