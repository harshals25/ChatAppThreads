package com.example.sunidhi.inclass10;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//group 30
public class MainActivity extends AppCompatActivity {

    private final OkHttpClient client = new OkHttpClient();
    Button buttonLogin, buttonSignUp;
    EditText editTextEmail, editTextPassword;
    String email, password;
    String TOKEN_NAME;
    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        setTitle( "Chat Rooms" );

        buttonLogin = findViewById( R.id.buttonLogin );
        buttonSignUp = findViewById( R.id.buttonSignUp );
        editTextEmail = findViewById( R.id.editTextEmail );
        editTextPassword = findViewById( R.id.editTextPassword );

        buttonLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();
                performLogin( email, password );

//                Log.d( "demo", "Main ACtivity token = " + token );
//                if (token!= null){
//                    runOnUiThread(new Runnable(){
//                        @Override
//                        public void run(){
//                            Intent intent = new Intent( MainActivity.this, ThreadsActivity.class );
//                            startActivity( intent );
//                        }
//                    });
//                }
//                else{
//                    Toast.makeText( MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT ).show();
//                }
            }
        } );

        buttonSignUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        } );



    }
    @Override
    protected void onStop() {
        super.onStop();
        removeToken();
    }


    public void performLogin (String email, String password) {

        RequestBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("password",password)
                .build();
        Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/login")
                .post(formBody)
                .build();

        client.newCall(request).enqueue( new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText( MainActivity.this, "Login unsuccessful", Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();

                Gson gson = new Gson();
                TokenResponse tokenResponse = gson.fromJson(str,TokenResponse.class);
//                token = tokenResponse.getToken();
//                saveToken( token );
//                Log.d( "demo", "token = " +token );

                 token = getToken1();
                Log.d( "demo", "token = " + getToken1());

            }
        } );

        if (!token.matches( "" ) ){
            runOnUiThread(new Runnable(){
                @Override
                public void run(){
                    Intent intent = new Intent( MainActivity.this, ThreadsActivity.class );
                    startActivity( intent );
                }
            });

        }
        else{
            Toast.makeText( this, "Vhdefsyck,jsd", Toast.LENGTH_SHORT ).show();
        }
    }


    public void saveToken(String token)
    {
        SharedPreferences.Editor editor = getSharedPreferences(TOKEN_NAME, MODE_PRIVATE).edit();
        editor.putString("token", token);
        editor.apply();
        editor.commit();
    }


    public String getToken1()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(TOKEN_NAME,MODE_PRIVATE);
        return sharedPreferences.getString("token","");
    }

    public void removeToken () {

        SharedPreferences.Editor editor = getSharedPreferences(TOKEN_NAME, MODE_PRIVATE).edit();
        editor.putString("token", "");
        editor.apply();
    }
}
