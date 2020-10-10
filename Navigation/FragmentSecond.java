package com.janfranco.navigationexample.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.janfranco.navigationexample.R;

public class SecondFragment extends Fragment {

    private int age;

    public SecondFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null)
            age = SecondFragmentArgs.fromBundle(getArguments()).getAge();

        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button goToFirstFragment = view.findViewById(R.id.go_to_first_fragment_button);
        goToFirstFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFirstFragment(v);
            }
        });

        TextView ageText = view.findViewById(R.id.age_text_view);
        ageText.setText(String.valueOf(age));
    }

    private void goToFirstFragment(View view) {
        NavDirections action = SecondFragmentDirections.actionSecondFragmentToFirstFragment();
        Navigation.findNavController(view).navigate(action);
    }

}
