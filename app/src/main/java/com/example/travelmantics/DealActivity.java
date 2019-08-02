package com.example.travelmantics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DealActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    EditText etTitle;
    EditText etDescription;
    EditText etPrice;
    private TravelDeal deal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);


        FirebaseUtil.openFbReference("traveldeals");
//        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
//        mDatabaseReference = mFirebaseDatabase.getReference().child("traveldeals");
        mDatabaseReference = FirebaseUtil.mDatabaseReference;
        etTitle = (EditText) findViewById(R.id.et_title);
        etDescription = (EditText) findViewById(R.id.et_description);
        etPrice = (EditText) findViewById(R.id.et_price);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save:
                saveDeal();
                Toast.makeText(this, R.string.deal_saved, Toast.LENGTH_SHORT).show();
                clean();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void saveDeal(){
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        String price = etPrice.getText().toString();

        TravelDeal deal = new TravelDeal(title, description, price, "");
        mDatabaseReference.push().setValue(deal);
    }

    private void clean (){

        etTitle.setText("");
        etDescription.setText("");
        etPrice.setText("");
        etTitle.requestFocus();
    }
}
