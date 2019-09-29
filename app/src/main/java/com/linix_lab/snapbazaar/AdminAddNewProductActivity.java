package com.linix_lab.snapbazaar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.linix_lab.snapbazaar.Prevalent.Prevalent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewProductActivity extends AppCompatActivity {

    private String categoryName,Description,Price,pName, saveCurrentDate, saveCurrentTime;
    private Button addNewProductBtn;
    private ImageView inputPrductImage;
    private EditText inputProductName, inputProductDescription,inputProductPrice;
    private static final int GalleryPick=1;
    private Uri ImageUri;
    private String productRandomKey, downloadImageUrl;
    private StorageReference ProductImagesRef;
    private DatabaseReference ProductsRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        //fetch the data from category
        categoryName=getIntent().getExtras().get("category").toString();
        ProductImagesRef= FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductsRef=FirebaseDatabase.getInstance().getReference().child("Products");

        addNewProductBtn = (Button) findViewById(R.id.add_new_product);
        inputPrductImage = (ImageView) findViewById(R.id.select_product_image);
        inputProductName = (EditText) findViewById(R.id.product_name);
        inputProductDescription = (EditText) findViewById(R.id.product_description);
        inputProductPrice = (EditText) findViewById(R.id.product_price);
        loadingBar= new ProgressDialog(this);

        inputPrductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });


        addNewProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateProductData();
            }
        });

    }


    //input images from gallery
    private void OpenGallery() {

        Intent galleryIntent=new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/");
        startActivityForResult(galleryIntent,GalleryPick);

        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GalleryPick && resultCode==RESULT_OK &&data!=null){
            ImageUri=data.getData();
            inputPrductImage.setImageURI(ImageUri);
        }
    }
    private void validateProductData(){

        Description=inputProductDescription.getText().toString();
        Price=inputProductPrice.getText().toString();
        pName=inputProductName.getText().toString();
        
        if(ImageUri==null){
            Toast.makeText(this, "Product image is mandatory", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Description)){
            Toast.makeText(this, "Please add product description", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Price)){
            Toast.makeText(this, "Please add product price", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pName)){
            Toast.makeText(this, "Please add product name", Toast.LENGTH_SHORT).show();
        }
        else{
            storeProductInfo();
        }

    }
    private void storeProductInfo(){

        loadingBar.setTitle("Add new product");
        loadingBar.setMessage("Admin, Please wait, while we are adding new product");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar= Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calendar.getTime());

        productRandomKey=saveCurrentDate + saveCurrentTime;


        final StorageReference filePath= ProductImagesRef.child(ImageUri.getLastPathSegment()+productRandomKey+".jpg");

        final UploadTask uploadTask=filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message= e.toString();
                Toast.makeText(AdminAddNewProductActivity.this, "Error"+message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(AdminAddNewProductActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();

                Task<Uri>urlTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();


                        }
                        downloadImageUrl=filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){

                            downloadImageUrl=task.getResult().toString();

                            Toast.makeText(AdminAddNewProductActivity.this, "Product image url saved successfully", Toast.LENGTH_SHORT).show();

                            saveProductInfoDatabase();
                        }
                    }
                });

            }
        });


    }

    private void saveProductInfoDatabase() {
        HashMap<String, Object> productMap=new HashMap<>();
        productMap.put("pid",productRandomKey);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("description",Description);
        productMap.put("image",downloadImageUrl);
        productMap.put("category",categoryName);
        productMap.put("price",Price);
        productMap.put("pname",pName);


        ProductsRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            Intent intent = new Intent(AdminAddNewProductActivity.this, AdminCategoryActivity.class);
                            startActivity(intent);
                            loadingBar.dismiss();
                            Toast.makeText(AdminAddNewProductActivity.this, "Product is added Succesfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            String message=task.getException().toString();

                            loadingBar.dismiss();

                            Toast.makeText(AdminAddNewProductActivity.this, "Error"+message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
