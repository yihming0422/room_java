package com.example.words;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.words.databinding.FragmentAddBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment {
    // private Button buttonSubmit;
    // private EditText editTextEnglish, editTextChinese;
    private WordViewModel wordViewModel;

    private FragmentAddBinding fragmentAddBinding;

    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentAddBinding = FragmentAddBinding.inflate(inflater);
        return fragmentAddBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = requireActivity();
        wordViewModel = ViewModelProviders.of(activity).get(WordViewModel.class);

        fragmentAddBinding.buttonSubmit.setEnabled(false);
        fragmentAddBinding.editTextEnglish.requestFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(fragmentAddBinding.editTextEnglish, 0);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String english = fragmentAddBinding.editTextEnglish.getText().toString().trim();
                String chinese = fragmentAddBinding.editTextChinese.getText().toString().trim();
                fragmentAddBinding.buttonSubmit.setEnabled(!english.isEmpty() && !chinese.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        fragmentAddBinding.editTextEnglish.addTextChangedListener(textWatcher);
        fragmentAddBinding.editTextChinese.addTextChangedListener(textWatcher);
        fragmentAddBinding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String english = fragmentAddBinding.editTextEnglish.getText().toString().trim();
                String chinese = fragmentAddBinding.editTextChinese.getText().toString().trim();
                Word word = new Word(english, chinese);
                wordViewModel.insertWords(word);
                NavController navController = Navigation.findNavController(v);
                navController.navigateUp();
                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
    }
}
