package kg.kloop.android.zvonilka.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;

import kg.kloop.android.zvonilka.R;

public class SortingDialogFragment extends AppCompatDialogFragment {

    private static final String TAG = SortingDialogFragment.class.getSimpleName();

    public interface SortingDialogListener {
        void onDialogPositiveClick(AppCompatDialogFragment dialog);
        void onDialogNegativeClick(AppCompatDialogFragment dialog);
    }

    SortingDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_sort_clients, null);

        final AutoCompleteTextView cityATV = dialogView.findViewById(R.id.city_sorting_dialog_autocomplete_text_view);
        final AutoCompleteTextView interestATV = dialogView.findViewById(R.id.interest_sorting_dialog_autocomplete_text_view);
        cityATV.setThreshold(0);
        cityATV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityATV.showDropDown();
            }
        });
        interestATV.setThreshold(0);
        interestATV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interestATV.showDropDown();
            }
        });
        ArrayList<String> interestsArrayList = new ArrayList<>();
        interestsArrayList.add("Метод Stabiliti");
        interestsArrayList.add("Ломи-ломи");
        ArrayList<String> citiesArrayList = new ArrayList<>();
        citiesArrayList.add("Алматы");
        citiesArrayList.add("Астана");
        setAdapter(cityATV, citiesArrayList);
        setAdapter(interestATV, interestsArrayList);



        builder.setView(dialogView)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onDialogPositiveClick(SortingDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onDialogNegativeClick(SortingDialogFragment.this);
                    }
                });
        return builder.create();
    }

    private void setAdapter(AutoCompleteTextView autoCompleteTextView, ArrayList<String> itemsArrayList) {
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, itemsArrayList);
        autoCompleteTextView.setAdapter(arrayAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (SortingDialogListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}
