package com.example.ratingreview;

import Model.ListRating;
import Model.Rating;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private static final String REQUIRED = "Required";
    private EditText editText;
    private RatingBar ratingBar,listrate;
    private  Button getRating;

    private TextView textcomment ,textid;
//a list to store all the artist from firebase database
    List<Rating> ratingList;
    ListView listViewArtists;
    //our database reference object
    DatabaseReference databaseRatings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textcomment=findViewById(R.id.textcomment);
        textid=findViewById(R.id.textid);
         getRating = findViewById(R.id.getRating);
        ratingBar = findViewById(R.id.rating);
        editText=findViewById(R.id.commentbox);
        listViewArtists = (ListView) findViewById(R.id.listViewArtists);
        listrate=findViewById(R.id.listrate);
        //list to store artists
        ratingList= new ArrayList<>();
        //getting the reference of artists node
        databaseRatings = FirebaseDatabase.getInstance().getReference("ratingList");

        getRating.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String rating = "Rating is :" + ratingBar.getRating();
                Toast.makeText(MainActivity.this, rating, Toast.LENGTH_LONG).show();
                basicReadWrite();
            }
        });
    }


    @Override
    protected void onStart() {
        try {
            super.onStart();
            //attaching value event listener
            databaseRatings.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //clearing the previous artist list
                    ratingList.clear();
                    //iterating through all the nodes
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        //getting artist
                        Rating artist = postSnapshot.getValue(Rating.class);
                        //adding artist to the list
                        ratingList.add(artist);
//                        textid.append(artist.getId());
//                        textid.append("\n");
//                        listrate.setRating(Float.parseFloat(artist.getRatestar()));
//                        textcomment.append(artist.getComment());
//                        textcomment.append("\n");
                    }

                    //creating adapter
                    ListRating artistAdapter = new ListRating(MainActivity.this,ratingList );
                    //attaching adapter to the listview
                    listViewArtists.setAdapter(artistAdapter);

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        catch (Exception ex)
        {
        throw ex;
        }

    }
    public void basicReadWrite() {
try {
    final String comment = editText.getText().toString().trim();
    final String star = String.valueOf(ratingBar.getRating());;
    if (TextUtils.isEmpty(comment)) {
        editText.setError(REQUIRED);
        return;
    }
    //for disable multiple time submit click
    setEditingEnabled(false);
    Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();
    //getting a unique id using push().getKey() method
    String id = databaseRatings.push().getKey();
    Rating rating=new Rating(id,comment,star);
    //upload your class Property to Firebase database
    databaseRatings.child(id).setValue(rating);
    editText.setText("");//empty Edit text Value
    Toast.makeText(this, "Review  added", Toast.LENGTH_LONG).show();//displaying a success toast
}
catch (Exception ex) {
    Log.d(TAG, "Value is: " + ex);
}
}
    private void setEditingEnabled(boolean enabled) {
        editText.setEnabled(enabled);
        //mBodyField.setEnabled(enabled);
        if (enabled) {
            getRating.isShown();
        } else {
            getRating.isShown();
        }
    }
}