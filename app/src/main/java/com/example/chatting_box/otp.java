package com.example.chatting_box;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class otp extends AppCompatActivity {

    String phoneNumber;
    EditText otpinput;
    Button next;

    TextView resend;
    Long timeout=30L;

     String codeVerify;
    PhoneAuthProvider.ForceResendingToken resendingToken;

    FirebaseAuth mAuth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        otpinput=findViewById(R.id.editTextNumber);
        next=findViewById(R.id.button2);
        resend=findViewById(R.id.textView4);


        phoneNumber=getIntent().getExtras().getString("phone");
        Toast.makeText(getApplicationContext(),phoneNumber,Toast.LENGTH_LONG).show();

        sendOtp(phoneNumber,false );

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enterdOtp=otpinput.getText().toString();
                PhoneAuthCredential credential= PhoneAuthProvider.getCredential(codeVerify,enterdOtp);

                signIn(credential);

            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendOtp(phoneNumber,true);

            }
        });


    }

    void sendOtp(String phoneNumber,boolean isResend){
        startResendTimer();

        PhoneAuthOptions.Builder builder =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(timeout, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
                        {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential ) {
                                signIn(phoneAuthCredential);

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                final String code = e.getMessage();
                                Toast.makeText(getApplicationContext(),code,Toast.LENGTH_LONG).show();
                                otpinput.setText(code);


                            }


                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);

                                codeVerify=s;
                                resendingToken=forceResendingToken;
                                Toast.makeText(getApplicationContext(),"OTP sent successfully",Toast.LENGTH_LONG).show();

                            }
                        });
        if(isResend){
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        }
        else{
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }

    }

    void signIn(PhoneAuthCredential phoneAuthCredential){


        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if(task.isSuccessful()){
                    Intent intent=new Intent(otp.this,loginuser.class);
                    intent.putExtra("phone",phoneNumber);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"OTP Verification Failed",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    void startResendTimer(){
        resend.setEnabled(false);

        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                timeout--;
                resend.setText("Resend OTP in"+timeout+"seconds");

                if(timeout<=0){
                    timeout=30L;
                    timer.cancel();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            resend.setEnabled(true);
                        }
                    });
                }

            }
        },0,1000);
    }


}