package com.linix_lab.snapbazaar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.linix_lab.snapbazaar.Model.Feedback;

public class FeedbackActivity extends AppCompatActivity {
    private Button send;
    EditText fdPhone,fdProduct,fdBack;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        fdPhone=(EditText)findViewById(R.id.fdphone);
        fdProduct=(EditText)findViewById(R.id.fdproduct);
        fdBack=(EditText)findViewById(R.id.fdback);
        send=(Button)findViewById(R.id.fdsend);

        databaseReference= FirebaseDatabase.getInstance().getReference("Feedback");


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                Intent intent = new Intent(FeedbackActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveData() {

        String fphone=fdPhone.getText().toString().trim();
        String fproduct=fdProduct.getText().toString().trim();
        String fback=fdBack.getText().toString().trim();

        String key=databaseReference.push().getKey();
        Feedback feedback=new Feedback(fphone,fproduct,fback);
        databaseReference.child(key).setValue(feedback);

    }
}
