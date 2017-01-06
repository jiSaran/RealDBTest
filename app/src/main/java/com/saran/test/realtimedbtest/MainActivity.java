package com.saran.test.realtimedbtest;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ftxt,ltxt;
    private Button pushbtn,getbtn;
    private Spinner spinner;

    private final String TAG = "MainActivity";
    private final String REQUIRED = "Required";
    private final  int min = 1;
    private final int max = 10000;
    private DatabaseReference dbref;
    FirebaseDatabase database;
    private List<String> keyList;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ftxt = (EditText)findViewById(R.id.fname);
        ltxt = (EditText)findViewById(R.id.lname);
        pushbtn = (Button)findViewById(R.id.push);
        getbtn = (Button)findViewById(R.id.get);
        spinner = (Spinner)findViewById(R.id.spin);

        pushbtn.setOnClickListener(this);
        getbtn.setOnClickListener(this);
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        dbref = database.getReference();
        dbref.keepSynced(true);

        setSpinner();


    }


    private void submit(){
        final String fname = ftxt.getText().toString().trim();
        final String lname = ltxt.getText().toString().trim();

        if(TextUtils.isEmpty(fname)){
            //Toast.makeText(this,"not ok1",Toast.LENGTH_SHORT).show();
            ftxt.setError(REQUIRED);
        }
        if(TextUtils.isEmpty(lname)){
            //Toast.makeText(this,"not ok2",Toast.LENGTH_SHORT).show();
            ltxt.setError(REQUIRED);
        }
        UserDetails user_details = new UserDetails(fname,lname);
        int random = min + (int)(Math.random()*max);
        User user = new User(random,user_details);

        /*Add new child*/

//        dbref.child("user"+id).setValue(user).addOnCompleteListener(this, new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                Log.d(TAG,"Task is:"+task.isSuccessful());
//                if(!task.isSuccessful()){
//                    Toast.makeText(MainActivity.this,"Push failed",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                id++;
//                Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_SHORT).show();
//            }
//        });


        /*Update existing child*/
        Map<String,Object> map = new HashMap<>();
        map.put("fname",fname);
        map.put("lname",lname);
        dbref.child("user").child("ud").updateChildren(map);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == pushbtn.getId()){
            submit();
        }else if(view.getId() == getbtn.getId()){
            getvalue();
        }
    }

    private void getvalue() {
        String key = dbref.child("user").getKey();
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot shot : dataSnapshot.getChildren()){
                    String key = shot.getKey();
                    Log.d(TAG,"Key : "+key);
                    User o = shot.getValue(User.class);
                    Log.d(TAG,"Key 2 : "+o.getUid());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.d(TAG,"DB key: "+key);
    }

    private void setSpinner(){
        keyList = new ArrayList<>();
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                keyList.clear();
                for (DataSnapshot shot : dataSnapshot.getChildren()){
                    String key = shot.getKey();
                    keyList.add(key);
                }
                id = (int)dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, keyList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }
}
