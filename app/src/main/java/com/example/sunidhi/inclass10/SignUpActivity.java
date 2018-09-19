package com.example.sunidhi.inclass10;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpActivity extends AppCompatActivity {

    ArrayList<SignUpClass> signUpClassArrayList= new ArrayList();
    String TOKEN_NAME;

    String fname, lname, email, password, repeatPassword;
    private final OkHttpClient client = new OkHttpClient();
    Button buttonSignUp, buttonCancel;
    EditText editTextFirstName, editTextLastName, editTextEmail1, editTextPassword, editTextRepeatPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_up );

        setTitle( "Sign Up" );

        editTextFirstName = findViewById( R.id.editTextFirstName );
        editTextLastName = findViewById( R.id.editTextLastName );
        editTextEmail1 = findViewById( R.id.editTextEmail1 );
        editTextPassword = findViewById( R.id.editTextPassword );
        editTextRepeatPassword = findViewById( R.id.editTextRepeatPassword );
        buttonCancel = findViewById( R.id.buttonCancel );
        buttonSignUp = findViewById( R.id.buttonSignUp );

        buttonCancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( SignUpActivity.this, MainActivity.class);
                startActivity( intent );
            }
        } );

        buttonSignUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname = editTextFirstName.getText().toString();
                lname = editTextLastName.getText().toString();
                email = editTextEmail1.getText().toString();
                password = editTextPassword.getText().toString();
                repeatPassword = editTextRepeatPassword.getText().toString();


                performSignUp(fname, lname, email, password);
            }
        } );
    }

    public void performSignUp(String fname, String lname, String email, String password)
    {
        RequestBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("password",password)
                .add("fname",fname)
                .add("lname",lname)
                .build();
        Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/signup")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                SignUpClass signUpClass = new SignUpClass();
                String str = response.body().string();
                Log.d("demo", "res = " +str);
                Gson gson = new Gson();
                signUpClass = gson.fromJson(str,SignUpClass.class);
                signUpClassArrayList.add( signUpClass );

                String token = signUpClass.getToken();
                saveToken(token);
                Log.d( "demo", "ArrayList = " +signUpClassArrayList.toString() );
            }
            });
        }

        public void saveToken(String token)
        {
            SharedPreferences.Editor editor = getSharedPreferences(TOKEN_NAME, MODE_PRIVATE).edit();
            editor.putString("token1", token);
            editor.apply();
            editor.commit();
        }

}
