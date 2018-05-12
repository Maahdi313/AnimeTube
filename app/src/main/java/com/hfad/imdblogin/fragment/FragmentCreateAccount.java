package com.hfad.imdblogin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hfad.imdblogin.R;
import com.hfad.imdblogin.activities.ActivityHomepage;

import android.support.v4.app.Fragment;
import android.widget.Button;

public class FragmentCreateAccount extends Fragment implements View.OnClickListener{


    private Button BtnCreateAccount;

    public FragmentCreateAccount()
    {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account, container, false);

        BtnCreateAccount = view.findViewById(R.id.btn_create_account);
        BtnCreateAccount.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btn_create_account:
                ActivityHomepage.fragmentManager.beginTransaction().replace(R.id.fragment_container, new FragmentRegister()).
                        addToBackStack(null).commit();
                break;
        }
    }
}
