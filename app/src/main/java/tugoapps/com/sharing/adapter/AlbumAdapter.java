package tugoapps.com.sharing.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tugoapps.com.sharing.R;
import tugoapps.com.sharing.model.Albums;
import tugoapps.com.sharing.model.ItemClickListener;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumViewHolder> {

  Context context;
  List<Albums> albumList;
  ItemClickListener itemClickListener;

  public AlbumAdapter(Context context, List<Albums> albumList, ItemClickListener listener) {
    this.context = context;
    this.albumList = albumList;
    this.itemClickListener = listener;
  }

  @NonNull
  @Override
  public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_albums, viewGroup, false);
    return new AlbumViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull AlbumViewHolder albumViewHolder, int position) {
    albumViewHolder.txt_albumTitle.setText(String.valueOf(albumList.get(position).title));
    albumViewHolder.setItemClickListener(new ItemClickListener() {
      @Override
      public void onClick(View view, int position) {
        itemClickListener.onClick(view, position);
      }
    });
  }

  @Override
  public int getItemCount() {
    return albumList.size();
  }
}
