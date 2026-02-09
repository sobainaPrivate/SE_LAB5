package com.example.listycitylab3;
//androidx.fragment.app.DialogFragment

import androidx.appcompat.app.AlertDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment
{
    interface AddCityDialogListener {
        void addCity(City city);
        void editCity(City city, int position);
    }
    private AddCityDialogListener listener;
    private static final String ARG_CITY_NAME = "city_name";
    private static final String ARG_PROVINCE = "province";
    private static final String ARG_POSITION = "position";

    private int position = -1;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    public static AddCityFragment newInstance(City city, int position) {
        AddCityFragment fragment = new AddCityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CITY_NAME, city.getName());
        args.putString(ARG_PROVINCE, city.getProvince());
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);
        String cityNameArg = null;
        String provinceArg = null;

        if (getArguments() != null) {
            cityNameArg = getArguments().getString(ARG_CITY_NAME);
            provinceArg = getArguments().getString(ARG_PROVINCE);
            position = getArguments().getInt(ARG_POSITION, -1);
        }
        if (cityNameArg != null) {
            editCityName.setText(cityNameArg);
            editProvinceName.setText(provinceArg);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add a city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    if (position == -1) {
                        listener.addCity(new City(cityName, provinceName));
                    } else {
                        listener.editCity(new City(cityName, provinceName), position);
                    }

                })
                .create();
    }
}


