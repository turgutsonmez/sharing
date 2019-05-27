package tugoapps.com.sharing.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import tugoapps.com.sharing.R;
import tugoapps.com.sharing.Retrofit.IMyAPI;
import tugoapps.com.sharing.Retrofit.RetrofitClient;
import tugoapps.com.sharing.adapter.UserAdapter;
import tugoapps.com.sharing.model.ItemClickListener;
import tugoapps.com.sharing.model.Users;

public class UserActivity extends AppCompatActivity {

  public ProgressDialog mProgressDialog;
  IMyAPI myAPI;
  RecyclerView recyclerView;
  SharedPreferences pref;
  CompositeDisposable compositeDisposable = new CompositeDisposable();
  String userID;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
    userID = pref.getString("prefUserId", "");

    //init api
    Retrofit retrofit = RetrofitClient.getInstance();
    myAPI = retrofit.create(IMyAPI.class);

    //View
    recyclerView = findViewById(R.id.recyclerview);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    if (userID.isEmpty()) {
      showProgressDialog("Uygulamaya ilk kez giriş yapıyorsun", "Herhangi bir yere dokun");
    } else {
      Intent intent = new Intent(UserActivity.this, PostAndAlbumActivity.class);
      startActivity(intent);
      overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left );
      finish();
    }

    fetchData();
  }

  private void fetchData() {
    compositeDisposable.add(myAPI.getUsers()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new Consumer<List<Users>>() {
        @Override
        public void accept(List<Users> users) throws Exception {
          displayData(users);
        }
      }));
  }

  private void displayData(final List<Users> users) {
    UserAdapter userAdapter = new UserAdapter(this, users, new ItemClickListener() {
      @Override
      public void onClick(View view, int position) {
        SharedPreferences.Editor editor2 = pref.edit();
        editor2.putString("prefUserId", String.valueOf(users.get(position).id));
        editor2.apply();
        Intent intent = new Intent(UserActivity.this, PostAndAlbumActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left );
        finish();
      }
    });
    recyclerView.setAdapter(userAdapter);

  }

  @Override
  protected void onStop() {
    compositeDisposable.clear();
    super.onStop();
  }


  public void showProgressDialog(String title, String message) {
    if (mProgressDialog == null) {
      mProgressDialog = new ProgressDialog(this);
      mProgressDialog.setTitle(title);
      mProgressDialog.setMessage(message);
    }
    mProgressDialog.show();
  }

  public void hideProgressDialog() {
    if (mProgressDialog != null && mProgressDialog.isShowing()) {
      mProgressDialog.dismiss();
    }
  }

  @Override
  public void finish() {
    super.finish();
    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right );
  }
}
