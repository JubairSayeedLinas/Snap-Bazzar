package com.linix_lab.snapbazaar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PaymentActivity extends AppCompatActivity {

    private Button checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        checkout=(Button)findViewById(R.id.checkout);



        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PaymentActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
