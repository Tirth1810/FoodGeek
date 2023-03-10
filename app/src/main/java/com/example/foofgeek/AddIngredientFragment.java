package com.example.foofgeek;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class AddIngredientFragment extends Fragment {

    EditText txt_ingredient;
    ListView list_ingredient;
    Button btn_ingredient;

    ArrayList<String> ingredientList;

    InputMethodManager inputMethodManager;

    public AddIngredientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_add_ingredient, container, false);
        setRetainInstance(true);

        //set hiding keybord
        inputMethodManager = (InputMethodManager)this.getActivity().getSystemService(this.getContext().INPUT_METHOD_SERVICE);


        btn_ingredient = view.findViewById(R.id.btn_Add_IngredientAdd);
        txt_ingredient = view.findViewById(R.id.txt_Add_IngredientAdd);

        btn_ingredient.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(txt_ingredient.getText().length() > 0) {
                    ingredientList.add(txt_ingredient.getText().toString());
                    txt_ingredient.setText("");
                    inputMethodManager.hideSoftInputFromWindow(txt_ingredient.getWindowToken(), 0);
                }
                else
                {
                    Toast.makeText(view.getContext(),"Please, Insert your Ingredient", Toast.LENGTH_SHORT).show();
                }
            } });

        ingredientList = new ArrayList<>();
        list_ingredient = view.findViewById(R.id.ListView_Add_Ingredient);
        list_ingredient.setAdapter(new AddIngredientAdapter(this.getContext(), ingredientList, R.layout.fragment_add_ingredientitem));

        list_ingredient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                ingredientList.remove(position);
                list_ingredient.setAdapter(new AddIngredientAdapter(view.getContext(), ingredientList, R.layout.fragment_add_ingredientitem));
            }
        });
        return view;
    }
}
