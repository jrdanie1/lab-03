package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {

    // Callback interface to send result back
    interface EditCityDialogListener {
        void editCity(City city, int position);
    }

    private EditCityDialogListener listener;

    // Argument keys for passing data
    private static final String ARG_NAME = "name";
    private static final String ARG_PROVINCE = "province";
    private static final String ARG_POSITION = "position";

    // Factory method to create new instance with arguments
    public static EditCityFragment newInstance(City city, int position) {
        EditCityFragment fragment = new EditCityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, city.getName());
        args.putString(ARG_PROVINCE, city.getProvince());
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);

        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        // Load existing city values
        Bundle args = getArguments();
        int position = -1;
        if (args != null) {
            editCityName.setText(args.getString(ARG_NAME));
            editProvinceName.setText(args.getString(ARG_PROVINCE));
            position = args.getInt(ARG_POSITION, -1);
        }

        final int finalPosition = position;

        return new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle("Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    if (listener != null && finalPosition != -1) {
                        listener.editCity(new City(cityName, provinceName), finalPosition);
                    }
                })
                .create();
    }
}
