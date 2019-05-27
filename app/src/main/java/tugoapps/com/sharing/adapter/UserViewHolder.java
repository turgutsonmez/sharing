package tugoapps.com.sharing.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import tugoapps.com.sharing.R;
import tugoapps.com.sharing.model.ItemClickListener;
import tugoapps.com.sharing.model.Users;

public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
  TextView txt_userName, txt_phoneNumber;
  ItemClickListener itemClickListener;

  public UserViewHolder(@NonNull final View itemView) {
    super(itemView);
    txt_userName = itemView.findViewById(R.id.txt_userName);
    txt_phoneNumber = itemView.findViewById(R.id.txt_phoneNumber);
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
