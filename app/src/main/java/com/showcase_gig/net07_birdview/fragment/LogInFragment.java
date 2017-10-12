package com.showcase_gig.net07_birdview.fragment;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.showcase_gig.net07_birdview.R;

public class LogInFragment extends Fragment {

    private View mView;
    private EditText userNameEdit;
    private EditText passWordEdit;

    public static LogInFragment newInstance() {
        LogInFragment fragment = new LogInFragment();
        return fragment;
    }

    public LogInFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_log_in, container, false);

        userNameEdit = (EditText) mView.findViewById(R.id.login_user_name);
        passWordEdit = (EditText) mView.findViewById(R.id.login_pass_word);

        setLogInButton();

        setSignUpButton();
        return mView;
    }

    private void setLogInButton() {
        mView.findViewById(R.id.login_do_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameEdit.getText().toString();
                String passWord = passWordEdit.getText().toString();

                if(userName.isEmpty() || passWord.isEmpty()) {
                    Toast.makeText(getActivity(), "入力してくださいよ", Toast.LENGTH_SHORT).show();
                    return;
                }

                doLogIn(userName, passWord);
            }
        });
    }

    private void doLogIn(String userName, String passWord) {
    }

    private void setSignUpButton() {
        mView.findViewById(R.id.login_sign_up_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.login_container, SignUpFragment.newInstance());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

}