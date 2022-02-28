package com.naqvi.foodmunch.Cafe.CreateCafe;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.naqvi.foodmunch.Helper.ImageUtil;
import com.naqvi.foodmunch.Helper.LoadingDialog;
import com.naqvi.foodmunch.Helper.SharedReference;
import com.naqvi.foodmunch.databinding.ActivityCreateCafeBinding;

import java.util.HashMap;
import java.util.Map;

public class CreateCafeActivity extends AppCompatActivity {
    ActivityCreateCafeBinding binding;
    SharedReference sharedRef;
    LoadingDialog loadingDialog;
    ActivityResultLauncher<Intent> launchActivityForResult;
    String base64String = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        launchActivityForResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        try {
                            Intent data = result.getData();
                            Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                            base64String = ImageUtil.convertToBase64(selectedImage);
                            binding.image.setImageBitmap(selectedImage);
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        binding = ActivityCreateCafeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedRef = new SharedReference(this);
        loadingDialog = new LoadingDialog(this);


        binding.createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCafe(
                        binding.etCafeTitle.getEditText().getText().toString(),
                        binding.etCafeContactNo.getEditText().getText().toString(),
                        binding.etCafeDescription.getEditText().getText().toString()
                );
            }
        });

        binding.uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(CreateCafeActivity.this);
            }
        });
    }

    private void selectImage(Context context) {
        Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        launchActivityForResult.launch(takePicture);

//        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
//
//        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
//        builder.setTitle("Choose Banner Source");
//
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//
//                if (options[item].equals("Take Photo")) {
//                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    launchActivityForResult.launch(takePicture);
//                } else if (options[item].equals("Choose from Gallery")) {
//
//                    Intent gallery = new Intent();
//                    gallery.setType("image/*");
//                    gallery.setAction(Intent.ACTION_GET_CONTENT);
//                    //  startActivityForResult(Intent.createChooser(gallery, "Select Picture"), 1);
//                    launchActivityForResult.launch(gallery);
//
//                } else if (options[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.create().show();
    }


    void addCafe(String name, String phone, String description) {
        loadingDialog.startLoadingDialog();
        Map<String, Object> cafeInfo = new HashMap<>();
        cafeInfo.put("Id", name.replaceAll(" ", "_"));
        cafeInfo.put("CafeName", name);
        cafeInfo.put("Description", description);
        cafeInfo.put("Phone", phone);
        cafeInfo.put("Image",base64String);
        cafeInfo.put("isVerified", "0");

        Log.d("TAG1", "addCafe: " + cafeInfo);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userId = sharedRef.getUser().userId;
        Log.d("TAG1", "addCafe: " + userId);
        DatabaseReference myRef = database.getReference("Cafe").child(userId).child(name.replaceAll(" ", "_"));
        myRef.setValue(cafeInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                loadingDialog.dismissDialog();
                Toast.makeText(CreateCafeActivity.this, "Cafe Registered Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingDialog.dismissDialog();
            }
        });

    }

}