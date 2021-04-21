package com.example.spacebook;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.spacebook.models.Post;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class cfhomefragment extends Fragment {
    Button cf_backbtn;
    private RecyclerView recyclerViewPosts;
    private List<Post> postList;
    private List<String> followingList;
    RecyclerView recview;
    DatabaseReference reference;
    com.example.spacebook.adapter adapter;
    CircleImageView rImage;

    public cfhomefragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //getChildFragmentManager().beginTransaction().replace(R.id.fragment_container,new cfnotificationfragment()).commit();

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        cf_backbtn = view.findViewById(R.id.cf_back);


        rImage = view.findViewById(R.id.cfimg);
        //String key = reference.child("cfpost").getKey();

        // we will get the default FirebaseDatabase instance
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        // we will get a DatabaseReference for the database root node
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference getImage = databaseReference.child("cfimg");
        //reference = FirebaseDatabase.getInstance().getReference().child("img");



        // Here "image" is the child node value we are getting
        // child node data in the getImage variable

        getImage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


               // String link = dataSnapshot.getValue(String.class);

                //Picasso.get().load(link).into(rImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error Loading Image", Toast.LENGTH_SHORT).show();
            }
        });


        cf_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),MainActivity.class));

            }




        });


            recview=(RecyclerView)view.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("cfpost"), model.class)
                        .build();

        adapter=new adapter(options);
        recview.setAdapter(adapter);


        return view;


    }


    @Override
    public void onStart() {
        super.onStart();

        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    }



