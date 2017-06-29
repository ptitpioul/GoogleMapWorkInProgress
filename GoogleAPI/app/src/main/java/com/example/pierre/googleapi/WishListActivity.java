package com.example.pierre.googleapi;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class WishListActivity extends AppCompatActivity {
    private static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wish_list);

        getSupportActionBar().hide();
        mContext = WishListActivity.this;



        //Show cart layout based on items
        setCartLayout();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(mContext);

        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerView.setAdapter(new WishListActivity.SimpleStringRecyclerViewAdapter(recyclerView));
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<WishListActivity.SimpleStringRecyclerViewAdapter.ViewHolder> {

        private RecyclerView mRecyclerView;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final LinearLayout mLayoutItem, mLayoutRemove ;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item_desc);
                mLayoutRemove = (LinearLayout) view.findViewById(R.id.layout_action1);

            }
        }

        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView) {

            mRecyclerView = recyclerView;
        }

        @Override
        public WishListActivity.SimpleStringRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_wishlist_item, parent, false);
            return new WishListActivity.SimpleStringRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onViewRecycled(WishListActivity.SimpleStringRecyclerViewAdapter.ViewHolder holder) {

        }

        @Override
        public void onBindViewHolder(final WishListActivity.SimpleStringRecyclerViewAdapter.ViewHolder holder, final int position) {


            holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            //Set click action
            holder.mLayoutRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notifyDataSetChanged();
                    //Decrease notification count
                    MapsActivity.notificationCountCart--;

                }
            });

            //Set click action

        }

        @Override
        public int getItemCount() {
            return MapsActivity.notificationCountCart;
        }
    }

    protected void setCartLayout(){
        LinearLayout layoutCartItems = (LinearLayout) findViewById(R.id.layout_items);



        if(MapsActivity.notificationCountCart >0){

            layoutCartItems.setVisibility(View.VISIBLE);

        }else {

            layoutCartItems.setVisibility(View.GONE);



        }
    }
}
