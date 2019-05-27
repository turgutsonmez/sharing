package tugoapps.com.sharing.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import tugoapps.com.sharing.R;
import tugoapps.com.sharing.model.ItemClickListener;

public class AlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

  TextView txt_albumTitle;
  ItemClickListener itemClickListener;

  public AlbumViewHolder(@NonNull View itemView) {
    super(itemView);
    txt_albumTitle = itemView.findViewById(R.id.txt_albumTitle);
    itemView.setOnClickListener(this);
  }

  public void setItemClickListener(ItemClickListener listener) {
    this.itemClickListener = listener;
  }


  @Override
  public void onClick(View v) {
    itemClickListener.onClick(v, getAdapterPosition());
  }
}
