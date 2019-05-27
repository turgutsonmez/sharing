package tugoapps.com.sharing.UI;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import tugoapps.com.sharing.R;
import tugoapps.com.sharing.model.Post;
import tugoapps.com.sharing.model.Users;

public class CustomDialog extends AppCompatDialogFragment {

  private EditText dialog_title, dialog_body;
  private CustomDialogListener listener;

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    LayoutInflater layoutInflater = getActivity().getLayoutInflater();
    View view = layoutInflater.inflate(R.layout.layout_dialog, null);
    builder.setView(view)
      .setTitle("Post")
      .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          String title = dialog_title.getText().toString();
          String body = dialog_body.getText().toString();

          listener.applyTexts( title, body);
        }
      })
      .setPositiveButton("Okey", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
      });

    dialog_body = view.findViewById(R.id.dialog_body);
    dialog_title = view.findViewById(R.id.dialog_title);

    return builder.create();
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);

    try {
      listener = (CustomDialogListener) context;
    } catch (ClassCastException e) {
      throw new ClassCastException(context.toString() + "must implement CustomDialogListener");
    }
  }


  public interface CustomDialogListener {
    void applyTexts( String title, String body);
  }
}
