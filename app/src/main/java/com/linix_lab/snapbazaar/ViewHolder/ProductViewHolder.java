package com.linix_lab.snapbazaar.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.linix_lab.snapbazaar.Interface.ItemClickListener;
import com.linix_lab.snapbazaar.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName, txtProductDescription, txtProductPrice;
    public ImageView imageView;
    public ItemClickListener listener;


    public ProductViewHolder(View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.product_image);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description);
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);

    }

    public void setItemClickListener(ItemClickListener listner){
        {
            this.listener=listner;
        }
    }

    @Override
    public void onClick(View v) {

        listener.onClick(v, getAdapterPosition(),false);
    }
}
