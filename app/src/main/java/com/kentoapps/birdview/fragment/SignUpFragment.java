package com.kentoapps.birdview.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.kentoapps.birdview.R;

public class SignUpFragment extends Fragment {

    private View mView;
    private EditText userNameEdit;
    private EditText passWordEdit;

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        return fragment;
    }

    public SignUpFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        userNameEdit = (EditText) mView.findViewById(R.id.signup_user_name);
        passWordEdit = (EditText) mView.findViewById(R.id.signup_pass_word);

        setSignUpButton();

        return mView;
    }

    private void setSignUpButton() {
        mView.findViewById(R.id.signup_do_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameEdit.getText().toString();
                String passWord = passWordEdit.getText().toString();

                if (userName.isEmpty() || passWord.isEmpty()) {
                    Toast.makeText(getActivity(), "入力してくださいよ", Toast.LENGTH_SHORT).show();
                    return;
                }

                doSignUp(userName, passWord);
            }
        });
    }

    private void doSignUp(String userName, String passWord) {
//        ParseUser user = new ParseUser();
//        user.setUsername(userName);
//        user.setPassword(passWord);
//        user.signUpInBackground(new SignUpCallback() {
//            @Override
//            public void done(ParseException e) {
//                if(e == null) {
//                    Intent intent = new Intent(getActivity(), MainActivity.class);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(getActivity(), "新規登録失敗", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
}