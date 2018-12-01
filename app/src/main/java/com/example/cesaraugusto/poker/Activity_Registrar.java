package com.example.cesaraugusto.poker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.google.firebase.database.*;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Activity_Registrar extends AppCompatActivity {


    private EditText userName;
    private EditText userEmail;
    private EditText userPhone;
    private EditText userDni;
    private EditText userLastName;
    private EditText userPassword;
    private Button regButton;
    private TextView userLogin;
    private static final String TAG = "Activity_Registrar";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__registrar);
        setupUIView();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 // Create a new user with a first and last name
                 Map<String, Object> user = new HashMap<>();
                 user.put("Nombre", userName.getText().toString());
                 user.put("Apellido", userLastName.getText().toString());
                 user.put("Celular", userPhone.getText().toString());
                 user.put("Contraseña", userPassword.getText().toString());
                 user.put("DNI", userDni.getText().toString());
                 user.put("E-mail", userEmail.getText().toString());

                 // Add a new document with a generated ID
                 db.collection("users")
                         .add(user)
                         .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                             @Override
                             public void onSuccess(DocumentReference documentReference) {
                                 Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                             }
                         })
                         .addOnFailureListener(new OnFailureListener() {
                             @Override
                             public void onFailure(@NonNull Exception e) {
                                 Log.w(TAG, "Error adding document", e);
                             }
                         });

                /*
                // Create a new user with a first and last name
                Map<String, Object> user = new HashMap<>();
                user.put("Nombre", userName);
                user.put("Apellido", userLastName);
                user.put("Celular", userPhone);
                user.put("Contraseña", userPassword);
                user.put("DNI", userDni);
                user.put("E-mail", userEmail);

                if (validate()){
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();


                    mAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Activity_Registrar.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }

                        }
                    }
                    );
                }
                */
                                         }
                                     }

        );
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                startActivity(new Intent(Activity_Registrar.this, Activity_Login.class));

            }
        });
    }

    private void setupUIView() {
        userName = (EditText)findViewById(R.id.etUserName);
        userLastName = (EditText)findViewById(R.id.etUserLastName);
        userEmail = (EditText)findViewById(R.id.etUserEmail);
        userPassword = (EditText)findViewById(R.id.etUserPassword);
        userPhone = (EditText)findViewById(R.id.etUserPhone);
        userDni = (EditText)findViewById(R.id.etUserDNI);


        regButton = (Button)findViewById(R.id.btnRegistrar);
        userLogin = (TextView)findViewById(R.id.tvUserLogin);
    }


    private Boolean validate(){

        Boolean result = false;

        String name = userName.getText().toString();
        String last = userLastName.getText().toString();
        String email = userEmail.getText().toString();
        String dni = userDni.getText().toString();
        String phone = userPhone.getText().toString();
        String password = userPassword.getText().toString();

        if(name.isEmpty() && last.isEmpty() && email.isEmpty() && dni.isEmpty() && phone.isEmpty() && password.isEmpty()){

            Toast.makeText(this,"rellenar todos los campos",Toast.LENGTH_SHORT).show();


        }else{
            result = true;
        }
        return result;
    }
}
