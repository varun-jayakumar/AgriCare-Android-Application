package com.example.agricare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity implements ValueEventListener {
    private TextView Humidity,Temprature,Moisture,threshold;
    private RadioButton motorON, motorOFF,motorAUTO;
    private EditText th;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference=firebaseDatabase.getReference();
    private DatabaseReference mHumidityReference= mRootReference.child("humidity");
    private DatabaseReference mTemperatureReference= mRootReference.child("temprature");
    private DatabaseReference mMoistureReference= mRootReference.child("moisture");
    private DatabaseReference mMotorStateReference= mRootReference.child("motorState");
    private DatabaseReference mthresholdReference= mRootReference.child("threshold");



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
Humidity =(TextView)findViewById(R.id.textView);
Temprature =(TextView)findViewById(R.id.textView2);
Moisture =(TextView)findViewById(R.id.textView3);
threshold =(TextView)findViewById(R.id.textView12);
motorON=(RadioButton)findViewById(R.id.radioButton);
motorOFF=(RadioButton)findViewById(R.id.radioButton2);
motorAUTO=(RadioButton)findViewById(R.id.radioButton3);
th=(EditText) findViewById(R.id.blank);

    }
    public void onRadioButtonClickedOFF(View view)
    {
        mMotorStateReference.setValue("0");
        motorAUTO.setChecked(false);
        motorON.setChecked(false);
        motorOFF.setChecked(true);
    }
    public void onRadioButtonClickedON(View view)
    {
        mMotorStateReference.setValue("1");
        motorAUTO.setChecked(false);
        motorON.setChecked(true);
        motorOFF.setChecked(false);
    }
    public void onRadioButtonClickedAUTO(View view)
    {
        mMotorStateReference.setValue("2");
        motorAUTO.setChecked(true);
        motorON.setChecked(false);
        motorOFF.setChecked(false);
    }
    public void onUpdateButtonClicked(View view) {
          String thre=th.getText().toString();
          mthresholdReference.setValue(thre);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
       if(dataSnapshot.getValue(String.class)!=null)
       {
           String key=dataSnapshot.getKey();
           if(key.equals("humidity")){
               String hu = dataSnapshot.getValue(String.class);
               Humidity.setText(hu);
             }
          else if(key.equals("temprature")){
               String te = dataSnapshot.getValue(String.class);
               Temprature.setText(te);
           }
          else if(key.equals("moisture")){
               String mo = dataSnapshot.getValue(String.class);
               Moisture.setText(mo);
           }

           else if(key.equals("threshold")){
               String state = dataSnapshot.getValue(String.class);
               String th = dataSnapshot.getValue(String.class);
               threshold.setText(th);
           }
       }
    }


    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }

    @Override
    public void onStart(){
        super.onStart();
        mHumidityReference.addValueEventListener(this);
        mTemperatureReference.addValueEventListener(this);
        mMoistureReference.addValueEventListener(this);
        mthresholdReference.addValueEventListener(this);
    }



    //for submitting
    // string heading = headingInput.getText().toString();
    //mHeadingReference.setValue(heading);
}