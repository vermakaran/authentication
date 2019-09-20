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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Register.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Register#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Register extends Fragment {
    EditText etName,etUser,etPassword,etConfirm;
           Button btRegister;
           FirebaseAuth auth;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Register() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Register.
     */
    // TODO: Rename and change types and number of parameters
    public static Register newInstance(String param1, String param2) {
        Register fragment = new Register();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth =FirebaseAuth.getInstance();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etName=view.findViewById(R.id.etName);
        etConfirm=view.findViewById(R.id.etConfirm);
        etPassword=view.findViewById(R.id.etConfirm);
        btRegister=view.findViewById(R.id.btRegister);
        etUser=view.findViewById(R.id.etUser);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(!checkEmptyField()) {
                  if (etPassword.getText().toString().length()<6)
                  {
                      etPassword.setError("Invalid Password!Password should be atleast ");
                  etPassword.requestFocus();
                  }else if(!etPassword.getText().toString().equals(etPassword.getText().toString()))
                  {
                      etPassword.setError("Password missssss match");

                      etPassword.requestFocus();
                  }else
                  {
String name=etName.getText().toString();
String email=etUser.getText().toString();
String pass=etPassword.getText().toString();

createUser(name,email,pass);
                  }
              }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    public boolean checkEmptyField()
    {
        if(TextUtils.isEmpty(etName.getText().toString()))
        {
            etName.setError("name is blank");
            etName.requestFocus();
            return true;

        }else if(TextUtils.isEmpty(etPassword.getText().toString()))
    {
        etPassword.setError("password is blank");
        etPassword.requestFocus();
        return true;

    }else if(TextUtils.isEmpty(etConfirm.getText().toString()))
        {
            etConfirm.setError("password is blank");
            etConfirm.requestFocus();
            return true;

        }else if(TextUtils.isEmpty(etUser.getText().toString()))
        {
            etUser.setError("password is blank");
            etUser.requestFocus();
            return true;

        }
        return false;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }




    public void createUser(final String name,final String email,final String pass)
    {
      auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
           if(task.isSuccessful())
           {
               FirebaseUser user=auth.getCurrentUser();
               FirebaseFirestore db=FirebaseFirestore.getInstance();

               Map<String,Object> usermap= new  HashMap<>();
               usermap.put("name",name);
               usermap.put("email",email);
               db.collection("user").document(user.getUid()).set(usermap).addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {
                       Toast.makeText(getActivity().getApplicationContext(),"lelo",Toast.LENGTH_LONG).show();
                   }
               });
           }
           else {
               System.out.println("from Register"+task.getException());
               Toast.makeText(getActivity().getApplicationContext(),"eerlo",Toast.LENGTH_LONG).show();
           }
          }
      })  ;
FirebaseAuth.getInstance().signOut();
        NavController navController= Navigation.findNavController(getActivity(),R.id.fragment);
        navController.navigate(R.id.login);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
