package com.example.yallah_project;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener  {
    private Button registerButton ;
    private Button loginbutton ;

    private EditText registerName ;
    private EditText registerEmail ;
    private EditText registerPassword ;
    private EditText registerAge ;
    private RadioGroup registerGender ;
    private TextView registerError ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register) ;
        registerButton =findViewById(R.id.registerButton2) ;
        registerButton.setOnClickListener(this) ;
        loginbutton =findViewById(R.id.loginButton2) ;
        loginbutton.setOnClickListener(this) ;
        registerName =findViewById(R.id.registerName) ;
         registerEmail = findViewById(R.id.registerEmail);
         registerPassword = findViewById(R.id.registerPassword);
         registerAge = findViewById(R.id.registerAge);
         registerAge.setOnClickListener(this);
         registerGender = findViewById(R.id.registerGender) ;
         registerError = findViewById(R.id.registerErrors) ;

    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.registerAge) {
            Calendar calendar = Calendar.getInstance() ;
            int annee = calendar.get(Calendar.YEAR) ;
            int mois = calendar.get(Calendar.MONTH) ;
            int jour = calendar.get(Calendar.DAY_OF_MONTH) ;

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    registerAge.setText(dayOfMonth +"/" +(month  + 1 ) + "/" + year ) ;
                }
            } , annee , mois , jour ) ;

            datePickerDialog.show();
        }

        if ( v.getId() == R.id.registerButton2){
           if ( !registerEmail.getText().toString().isEmpty() &&
                !registerName.getText().toString().isEmpty() &&
                !registerPassword.getText().toString().isEmpty() &&
                !registerAge.getText().toString().isEmpty() )
           {
                 saveUser() ;

           }
           else {
               registerError.setText("Error in your resgistartion !");
           }
        }

        if ( v.getId() == R.id.loginButton2){
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }


    }

    public void saveUser() {
        User user = new User();
        user.setName(registerName.getText().toString());
        user.setEmail(registerEmail.getText().toString());
        user.setPassword(registerPassword.getText().toString());
        user.setAge(registerAge.getText().toString());

        UserDbHelper dbHelper = new UserDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserDbHelper.COLUMN_NAME, user.getName());
        values.put(UserDbHelper.COLUMN_EMAIL, user.getEmail());
        values.put(UserDbHelper.COLUMN_PASSWORD, user.getPassword());
        // values.put(UserDbHelper.COLUMN_AGE, user.getAge());
        Log.i("register",user.getName())  ;

        long newRowId = db.insert(UserDbHelper.TABLE_NAME, null, values);



    }



























}