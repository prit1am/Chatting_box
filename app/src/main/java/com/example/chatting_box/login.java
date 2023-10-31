package com.example.chatting_box;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;


public class login extends AppCompatActivity {

    CountryCodePicker countryCodePicker;
    EditText phoneInput;

    Button sendOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phoneInput =findViewById(R.id.editTextPhone2);
        countryCodePicker=findViewById(R.id.phonecode);
        sendOtp=findViewById(R.id.button);


        countryCodePicker.registerCarrierNumberEditText(phoneInput);




        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!countryCodePicker.isValidFullNumber()){
                    phoneInput.setError("Phone number not valid");
                    return;
                }
                Intent intent=new Intent(login.this , otp.class);
                intent.putExtra("phone",countryCodePicker.getFullNumberWithPlus());
                startActivity(intent);
            }
        });




    }
}