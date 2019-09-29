package com.linix_lab.snapbazaar;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.linix_lab.snapbazaar.Model.Feedback;

import java.util.ArrayList;
import java.util.List;

public class AdminFeeddbackActivity extends AppCompatActivity {

    private ListView listView;
    DatabaseReference databaseReference;
    private List<Feedback> feedbackList;
    private FeedbackAdapter feedbackAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_feeddback);

        listView=(ListView)findViewById(R.id.fdlv);

        databaseReference= FirebaseDatabase.getInstance().getReference("Feedback");
        feedbackList=new ArrayList<>();

        feedbackAdapter= new FeedbackAdapter(AdminFeeddbackActivity.this,feedbackList);



    }

    @Override
    protected void onStart() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                feedbackList.clear();

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Feedback feedback=dataSnapshot1.getValue(Feedback.class);
                    feedbackList.add(feedback);
                }
                listView.setAdapter(feedbackAdapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        super.onStart();
    }
}
