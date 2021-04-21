package com.example.spacebook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostActivity extends AppCompatActivity {
    Button cf_backbtn, cfsubmit;
    EditText cftopic, cfcontent,cfprice;
    DatabaseReference reference;
    FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
    TextView funding;


    private static final int PICK_IMAGE = 1;
    Button upload, choose;
    TextView alert;

    ArrayList<Uri> FileList = new ArrayList<Uri>();
    private Uri FileUri;
    private ProgressDialog progressDialog;
    private int upload_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        cfsubmit = findViewById(R.id.cfsubmit);
        cf_backbtn = findViewById(R.id.cf_back);
        cftopic = findViewById(R.id.topic);
        cfcontent = findViewById(R.id.content);
        funding = findViewById(R.id.funding);
        cfprice = findViewById(R.id.initialprice);


        alert = findViewById(R.id.alert);
        choose = findViewById(R.id.chooser);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("File Uploading Please Wait...........");

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, PICK_IMAGE);

            }
        });

        cf_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), cf_main.class));

            }
        });


        cfsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressLint("SetTextI18n")
            public void onClick(View v) {


                reference = FirebaseDatabase.getInstance().getReference();
                String key = reference.child("cfpost").push().getKey();


                String topic = cftopic.getText().toString().trim();
                String content = cfcontent.getText().toString().trim();
                String userid = mFirebaseUser.getUid();
                String fund = funding.getText().toString().trim();
                String price = cfprice.getText().toString().trim();



                progressDialog.show();
                alert.setText("If Loading Takes too long please Press the button again");

                StorageReference ImageFolder = FirebaseStorage.getInstance().getReference().child("FileFolder");


                for (upload_count = 0; upload_count < FileList.size(); upload_count++) {


                    Uri IndividualFile = FileList.get(upload_count);
                    final StorageReference ImageName = ImageFolder.child("Image" + IndividualFile.getLastPathSegment());


                    ImageName.putFile(IndividualFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {


                                    String url = String.valueOf(uri);
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("cfimg").child(key);



                                    HashMap<String, String> hashMap = new HashMap<>();

                                    hashMap.put("img", url);

                                    databaseReference.push().setValue(hashMap);


                                    progressDialog.dismiss();
                                    alert.setText("File Uploaded Successfully");
                                    upload.setVisibility(View.GONE);
                                    FileList.clear();

                                    if (TextUtils.isEmpty(cftopic.getText().toString())) {
                                        cftopic.setError("Please enter your email!");


                                    } else if (TextUtils.isEmpty(cfcontent.getText().toString())) {
                                        cfcontent.setError("Please enter your email!");

                                    } else if (TextUtils.isEmpty(cfprice.getText().toString())) {
                                        cfprice.setError("Please enter your email!");

                                    } else {

                                        cftopic.setError(null);
                                        cfcontent.setError(null);
                                        cfprice.setError(null);

                                        cfpost cfpost = new cfpost(topic, content, userid, fund,price);


                                        Map<String, Object> postValues = cfpost.toMap();
                                        Map<String, Object> childUpdates = new HashMap<>();

                                        childUpdates.put("/cfpost/" + key, postValues);

                                        reference.updateChildren(childUpdates);
                                        startActivity(new Intent(getApplicationContext(), cf_main.class));
                                    }


                                }
                            });


                        }
                    });


                }


            }
        });
    }





    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE) {

            if (resultCode == RESULT_OK) {

                if (data.getClipData() != null) {


                    int countClipData = data.getClipData().getItemCount();


                    int currentImageSelect = 0;

                    while (currentImageSelect < countClipData) {


                        FileUri = data.getClipData().getItemAt(currentImageSelect).getUri();

                        FileList.add(FileUri);

                        currentImageSelect = currentImageSelect + 1;


                    }

                    alert.setVisibility(View.VISIBLE);
                    alert.setText("You Have Selected " + FileList.size() + " Images");
                    choose.setVisibility(View.GONE);


                } else {


                    Toast.makeText(this, "Please Select Multiple File", Toast.LENGTH_SHORT).show();
                }


            }


        }



    }


}







