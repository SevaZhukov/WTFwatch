package com.mrswimmer.shiftwatch.presentation.base;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mrswimmer.shiftwatch.R;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    public NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        injectDependencies();
        //setRetainInstance(true);
        return inflater.inflate(getLayoutID(), container, false);
    }

    protected abstract void injectDependencies();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    public void showDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("ОК",
                        (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void showChoiceDialog(String title, String message, DialogActionCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Да", (dialog, id) -> callback.positiveAction(dialog))
        .setNegativeButton("Нет", (dialog, which) -> callback.negativeAction(dialog));
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void showSnack(String message, View.OnClickListener snackOnClickListener) {
        Snackbar snackbar = Snackbar.make(getView(), message, Snackbar.LENGTH_LONG)
                .setAction("lol", snackOnClickListener);
        snackbar.getView();
        TextView tv = (snackbar.getView()).findViewById(R.id.snackbar_action);
    }

    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    protected abstract int getLayoutID();

    public interface DialogActionCallback {
        void positiveAction(DialogInterface dialog);
        void negativeAction(DialogInterface dialog);
    }
}
