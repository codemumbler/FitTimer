package io.github.codemumbler.fittimer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

public class DurationDialogFragment extends DialogFragment {

    private DurationDialogListener listener;

    public interface DurationDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.duration_dialog_title);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.duration_dialog, null))
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (listener != null) {
                            listener.onDialogPositiveClick(DurationDialogFragment.this);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DurationDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public void setListener(DurationDialogListener listener) {
        this.listener = listener;
    }
}
