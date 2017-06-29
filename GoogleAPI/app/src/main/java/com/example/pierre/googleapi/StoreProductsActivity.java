package com.example.pierre.googleapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class StoreProductsActivity extends AppCompatActivity {

    ListView mListView;

    ImageButton wish;
    private String[] products = new String[]{
            "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_products);
        getSupportActionBar().hide();

        wish =(ImageButton)findViewById(R.id.wish);
        wish.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    Intent intent = new Intent(StoreProductsActivity.this, WishListActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mListView = (ListView) findViewById(R.id.listProducts);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(StoreProductsActivity.this,
                android.R.layout.simple_list_item_1, products);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                MapsActivity.notificationCountCart++;
                Toast.makeText(StoreProductsActivity.this, "Item added to wish list", Toast.LENGTH_SHORT).show();

                String message = "Text I want to share.";
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);

                startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));
            }
        });
    }
}
