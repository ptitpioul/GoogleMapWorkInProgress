package com.example.pierre.googleapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    RelativeLayout food;
    RelativeLayout media;
    RelativeLayout clothes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        food = (RelativeLayout) findViewById(R.id.food);
        // When we creating a button and if we expect that to use for event handling we have to set the listener
        food.setOnClickListener(this);
        media = (RelativeLayout) findViewById(R.id.media);
        media.setOnClickListener(this);
        clothes = (RelativeLayout) findViewById(R.id.clothes);
        clothes.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {

            case R.id.food:
                Toast.makeText(this, "Food section", Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("type", "convenience_store");

                break;
            case R.id.media:
                Toast.makeText(this, "Media section", Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("type", "electronics_store");
                break;
            case R.id.clothes:
                Toast.makeText(this, "Clothes section", Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("type", "clothing_store");
                break;

            default:
                // You never use this
                intent = new Intent(getApplicationContext(), MapsActivity.class);
                break;
        }


        startActivity(intent);
    }
}
