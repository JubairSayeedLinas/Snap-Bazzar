package com.linix_lab.snapbazaar;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.linix_lab.snapbazaar.Model.Feedback;

import java.util.List;

public class FeedbackAdapter extends ArrayAdapter<Feedback>{

    private Activity context;
    private List<Feedback> feedbackList;

    public FeedbackAdapter(Activity context, List<Feedback> feedbackList) {
        super(context, R.layout.feedback_sample_lauout, feedbackList);
        this.context = context;
        this.feedbackList = feedbackList;
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {


        LayoutInflater layoutInflater=context.getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.feedback_sample_lauout,null,true);

        Feedback fFeedback=feedbackList.get(position);

        TextView adfdPhone=view.findViewById(R.id.adfdphone);
        TextView adfdProduct=view.findViewById(R.id.adfdproduct);
        TextView adfdfedback=view.findViewById(R.id.adfdfeedback);

        adfdPhone.setText("Phone: "+fFeedback.getFphone());
        adfdProduct.setText("Product: "+fFeedback.getFproduct());
        adfdfedback.setText("Feedback: "+fFeedback.getFback());

        return view;
    }
}
