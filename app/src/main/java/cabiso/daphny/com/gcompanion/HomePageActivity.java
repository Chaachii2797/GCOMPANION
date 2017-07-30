package cabiso.daphny.com.gcompanion;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import cabiso.daphny.com.gcompanion.Model.DIYitem;

/**
 * Created by Lenovo on 7/30/2017.
 */

public class HomePageActivity extends AppCompatActivity {

    private List<DIYitem> diyList;
    private ListView lv;
    private DIYListAdapter adapter;
    private ProgressDialog progressDialog;
    //  RecyclerView recyclerView;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private StorageReference mStorageRef;

    //private FirebaseRecyclerAdapter<DIYitem, HomePageActivityViewHolder> mFirebaseAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    public HomePageActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_homepage);

        diyList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list_view_diy);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait loading DIYs.....");
        progressDialog.show();

        database = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("DIY_Details");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                //fetch images from firebase
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DIYitem img = snapshot.getValue(DIYitem.class);
                    diyList.add(img);
                }
                //init adapter
                adapter = new DIYListAdapter(HomePageActivity.this, R.layout.fragment_hp_item, diyList);
                //set adapter for listview
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

        //     recyclerView = (RecyclerView) findViewById(R.id.show_diy_recycler_view);
        //    recyclerView.setLayoutManager(new LinearLayoutManager(HomePageActivity.this));
        //  Toast.makeText(HomePageActivity.this, "Wait! Fetching data....", Toast.LENGTH_SHORT).show();

    }
}

    /*@Override
    public void onStart() {
        super.onStart();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<DIYitem, HomePageActivityViewHolder>
                (DIYitem.class,
                R.layout.fragment_hp_item,
                HomePageActivityViewHolder.class,
                databaseReference.child("DIY_Details")) {

            @Override
            protected void populateViewHolder(final HomePageActivityViewHolder viewHolder, DIYitem model, final int position) {

                viewHolder.Image_URL(model.getImage_URL());
                viewHolder.diyName(model.getDiyName());

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(final View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomePageActivity.this);
                        builder.setMessage("Do you want to delete this data?");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int selectedItem = position;
                                databaseReference.getRef().removeValue();
                                mFirebaseAdapter.notifyItemRemoved(selectedItem);
                               // recyclerView.invalidate();
                                onStart();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.setTitle("Confirm");
                        dialog.show();
                    }
                });


            }
        };

        //recyclerView.setAdapter(mFirebaseAdapter);
    }

    protected static class HomePageActivityViewHolder extends RecyclerView.ViewHolder {

        public TextView diy_name;

        View view1;
        ImageView image_url;

        public HomePageActivityViewHolder(View itemView) {
            super(itemView);

            view1=itemView;

            diy_name = (TextView) view1.findViewById(R.id.fetch_diy_image);
            image_url = (ImageView) view1.findViewById(R.id.fetch_image);

        }

        private void Image_URL(String title){
            Glide.with(itemView.getContext())
                    .load(title)
                    .crossFade()
                    .placeholder(R.drawable.add)
                    .thumbnail(0.1f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(image_url);
        }

        private void diyName(String title){
            diy_name.setText(title);
        }
    }

    private abstract class FirebaseRecyclerAdapter<T, T1> extends RecyclerView.Adapter {
        public FirebaseRecyclerAdapter(Class<T> diYitemClass, int fragment_hp_item, Class<T1> homePageActivityViewHolderClass, DatabaseReference diy_details) {
        }

        protected abstract void populateViewHolder(HomePageActivityViewHolder viewHolder, DIYitem model, int position);


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}*/




