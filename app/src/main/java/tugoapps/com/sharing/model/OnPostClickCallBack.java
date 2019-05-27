package tugoapps.com.sharing.model;

import android.view.View;

public interface OnPostClickCallBack {
  void onClick(View view, int position);

  void onLongClick(View view, int position);
}
