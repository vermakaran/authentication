package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match

    EditText etEmail, etPass;
    Button btLogin;
    TextView tvReg;
    private FirebaseAuth auth;
    FirebaseUser user;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Login() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etEmail = view.findViewById(R.id.etEmail);
        etPass = view.findViewById(R.id.etPass);
        btLogin = view.findViewById(R.id.btLogin);
        tvReg = view.findViewById(R.id.tvReg);

        btLogin.setOnClickListener(this);
        tvReg.setOnClickListener(this);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btLogin) {
            if (TextUtils.isEmpty(etEmail.getText().toString())) {
                etEmail.setError("Enter Email");
                etEmail.requestFocus();
            } else if (TextUtils.isEmpty(etPass.getText().toString())) {
                etPass.setError("password ");
                etPass.requestFocus();
            } else {
                if (etPass.getText().toString().length() < 6) {
                    etPass.setError("sixdigit");
                    etPass.requestFocus();
                } else {
                    String email = etEmail.getText().toString();
                    String Pass = etPass.getText().toString();
                }

            }
        } else if (id == R.id.tvReg) {
            NavController navController = Navigation.findNavController(getActivity(), R.id.fragment);
            navController.navigate(R.id.register);

        }
    }

    @Override
    public void onStart() {
        super.onStart();



user=auth.getCurrentUser();
if (user!=null)
{
    updateUI(user);
    Toast.makeText(getActivity().getApplicationContext(),"logged in already",Toast.LENGTH_LONG).show();
}
    }

    public void loginUser(String email, String pass) {
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    user = auth.getCurrentUser();
                    Toast.makeText(getActivity().getApplicationContext(), "Login Successful", Toast.LENGTH_LONG);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Login unSuccessful", Toast.LENGTH_LONG);
                }
            }
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public void updateUI(FirebaseUser user)
    {
        NavController navController=Navigation.findNavController(getActivity(),R.id.fragment);
Bundle b=new Bundle();
b.putParcelable("user",user);
navController.navigate(R.id.home);



    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
