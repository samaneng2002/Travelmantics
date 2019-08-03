package com.example.travelmantics;

import android.content.Intent;
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

        Intent intent = getIntent();
        TravelDeal deal = (TravelDeal) intent.getSerializableExtra("Deal");

        if (deal== null){
            deal = new TravelDeal();
        }
        this.deal = deal;
        etTitle.setText(deal.getTitle());
        etDescription.setText(deal.getDescription());
        etPrice.setText(deal.getPrice());

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
                backToList();
                return true;
            case R.id.delete_menu:
                deleteDeal();
                Toast.makeText(this, "Deal Deleted", Toast.LENGTH_LONG).show();
                backToList();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void saveDeal(){
        deal.setTitle(etTitle.getText().toString());
        deal.setDescription(etDescription.getText().toString());
        deal.setPrice(etPrice.getText().toString());
        if (deal.getId() == null){
            mDatabaseReference.push().setValue(deal);

        }
        else {
            mDatabaseReference.child(deal.getId()).setValue(deal);
        }

    }

    private void deleteDeal(){
        if (deal == null) {
            Toast.makeText(this, "Please save the deal before deleting", Toast.LENGTH_SHORT).show();
            return;
        }
        mDatabaseReference.child(deal.getId()).removeValue();
    }

    private void backToList() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    private void clean (){

        etTitle.setText("");
        etDescription.setText("");
        etPrice.setText("");
        etTitle.requestFocus();
    }
}
