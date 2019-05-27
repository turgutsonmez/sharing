package tugoapps.com.sharing.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tugoapps.com.sharing.R;
import tugoapps.com.sharing.model.ItemClickListener;
import tugoapps.com.sharing.model.Users;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

  Context context;
  List<Users> usersList;
  ItemClickListener itemClickListener;


  public UserAdapter(Context context, List<Users> usersList, ItemClickListener listener) {
    this.context = context;
    this.itemClickListener = listener;
    this.usersList = usersList;
  }

  @NonNull
  @Override
  public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_users, viewGroup, false);
    return new UserViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int position) {
    userViewHolder.txt_userName.setText(String.valueOf(usersList.get(position).name));
    userViewHolder.txt_phoneNumber.setText(String.valueOf(usersList.get(position).phoneNumber));
    userViewHolder.setItemClickListener(new ItemClickListener() {
      @Override
      public void onClick(View view, int position) {
        itemClickListener.onClick(view, position);
      }
    });
  }

  @Override
  public int getItemCount() {
    return usersList.size();
  }


}
