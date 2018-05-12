package com.hfad.imdblogin.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hfad.imdblogin.R;
import com.hfad.imdblogin.activities.ActivityHomepage;
import com.hfad.imdblogin.model.User;

public class FragmentRegister extends Fragment{


    //Defining edit texts
    private EditText EtUsername;
    private EditText EtFirstname;
    private EditText EtLastname;
    private EditText EtPassword;
    private EditText EtSecondPassword;

    private Button BtnRegisterUser;
    private Button BtnCancel;

    public FragmentRegister()
    {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        EtUsername = view.findViewById(R.id.et_username);
        EtFirstname = view.findViewById(R.id.et_firstname);
        EtLastname = view.findViewById(R.id.et_lastname);
        EtPassword = view.findViewById(R.id.et_password);
        EtSecondPassword = view.findViewById(R.id.et_second_password);

        BtnCancel = view.findViewById(R.id.btn_cancel);
        BtnRegisterUser = view.findViewById(R.id.btn_register);

        BtnRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = EtUsername.getText().toString();
                String firstname = EtFirstname.getText().toString();
                String lastname = EtLastname.getText().toString();
                String password = EtPassword.getText().toString();
                String second_password = EtSecondPassword.getText().toString();

                User user = new User();
                user.setUserName(username);
                user.setFirstName(firstname);
                user.setLastName(lastname);
                user.setPassWord(password);
                user.setRePassWord(second_password);

                ActivityHomepage.appDatabase.daoAccess().addUser(user);
                Toast.makeText(getActivity(), "Account successfully created", Toast.LENGTH_SHORT).show();

                EtUsername.setText("");
                EtFirstname.setText("");
                EtLastname.setText("");
                EtPassword.setText("");
                EtSecondPassword.setText("");
            }
        });

        return view;

    }
}
