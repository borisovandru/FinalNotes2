package com.android.FinalNotes.ui.info;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.google.android.material.snackbar.Snackbar;
import com.android.FinalNotes.R;

public class InfoFragment extends Fragment {

    public static final String TAG = "InfoFragment";

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getChildFragmentManager().setFragmentResultListener(AlertDialogFragment.RESULT, getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull  String requestKey, @NonNull  Bundle result) {
                Snackbar.make(getView(), "Positive", Snackbar.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.dialog_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogFragment();
            }
        });


        view.findViewById(R.id.alert_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        view.findViewById(R.id.list_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListDialog();
            }
        });

        view.findViewById(R.id.single_choice_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingleChoiceDialog();
            }
        });

        view.findViewById(R.id.multi_choice_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMultiChoiceDialog();
            }
        });

        view.findViewById(R.id.custom_view_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomViewDialog();
            }
        });

        view.findViewById(R.id.alert_dialog_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogFragment();
            }
        });

    }

    private void showDialogFragment() {
        CustomDialogFragment.newInstance()
                .show(getChildFragmentManager(), CustomDialogFragment.TAG);
    }

    private void showAlertDialogFragment() {
        AlertDialogFragment.newInstance(R.string.list_title)
                .show(getChildFragmentManager(), AlertDialogFragment.TAG);
    }

    private void showCustomViewDialog() {

        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit, null, false);

        EditText editText = view.findViewById(R.id.edit_name);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setTitle(R.string.notes_title)
                .setView(view)
                .setPositiveButton(R.string.select, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Snackbar.make(getView(), editText.getText(), Snackbar.LENGTH_SHORT).show();
                    }
                });

        builder.show();

    }

    private void showMultiChoiceDialog() {
        String[] items = getResources().getStringArray(R.array.options);

        boolean[] checked = new boolean[items.length];

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setMultiChoiceItems(items, checked, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checked[which] = isChecked;
                    }
                })
                .setPositiveButton(R.string.select, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        StringBuilder strBuilder = new StringBuilder();

                        for (int i = 0; i < checked.length; i++) {
                            if (checked[i]) {
                                strBuilder.append(items[i]);
                                strBuilder.append(',');
                            }
                        }

                        Snackbar.make(getView(), strBuilder, Snackbar.LENGTH_SHORT).show();
                    }
                });

        builder.show();

    }

    private void showSingleChoiceDialog() {
        String[] items = getResources().getStringArray(R.array.options);

        final int[] selected = {-1};

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setSingleChoiceItems(items, selected[0], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selected[0] = which;
                    }
                })
                .setPositiveButton(R.string.select, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (selected[0] != -1) {
                            Snackbar.make(getView(), items[selected[0]], Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });

        builder.show();

    }

    private void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setTitle(R.string.alert_dialog_title)
                .setMessage(R.string.alert_dialog_message)
                .setIcon(R.drawable.ic_clear)
                .setCancelable(false)
                .setPositiveButton(R.string.btn_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Snackbar.make(getView(), "Positive", Snackbar.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.btn_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Snackbar.make(getView(), "Negative", Snackbar.LENGTH_SHORT).show();

                    }
                })
                .setNeutralButton(R.string.btn_neutral, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Snackbar.make(getView(), "Neutral", Snackbar.LENGTH_SHORT).show();
                    }
                });

        builder.show();
    }

    private void showListDialog() {
        String[] items = getResources().getStringArray(R.array.options);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setTitle(R.string.list_title)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Snackbar.make(getView(), items[which], Snackbar.LENGTH_SHORT).show();
                    }
                });

        builder.show();
    }

}
