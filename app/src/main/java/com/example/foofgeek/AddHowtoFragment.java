package com.example.foofgeek;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class AddHowtoFragment extends Fragment {

    InputMethodManager imm;
    EditText eidtHowto;
    public AddHowtoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add_howto, container, false);
        setRetainInstance(true);

        //hidding keybord
        imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
        eidtHowto = view.findViewById(R.id.txt_ADD_Howto);
        ConstraintLayout root = view.findViewById(R.id.root_Add_howto);

        root.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                imm.hideSoftInputFromWindow(eidtHowto.getWindowToken(), 0);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

}
