package com.example.appdev.appdev2018.activities;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.example.appdev.appdev2018.R;
import com.example.appdev.appdev2018.adapters.AlbumsAdapter;
import com.example.appdev.appdev2018.pojos.Album;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GenresActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;
    private static final String TAG = "GenresActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genres);

        recyclerView = findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(this, albumList);
        db = FirebaseFirestore.getInstance();


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();

    }

    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {
        // Fetch records

//        final int[] covers = new int[]{
//                R.drawable.ic_launcher_background,
//                R.drawable.ic_launcher_background,
//                R.drawable.ic_launcher_background,
//                R.drawable.ic_launcher_background,
//                R.drawable.ic_launcher_background,
//                R.drawable.ic_launcher_background,
//                R.drawable.ic_launcher_background,
//                R.drawable.ic_launcher_background};

        db.collection(DB_list_of_genres)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("arel", String.valueOf(R.drawable.ic_launcher_background));
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("arel", document.getString("name"));
                                Album a = new Album(document.getString("name"), document.getLong("number of songs").intValue(), Integer.valueOf(document.getString("thumbnailID")),document.getId());
                                albumList.add(a);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


//        Album a = new Album("OPM", 13, covers[0]);
//        albumList.add(a);
//
//        a = new Album("POP", 8, covers[1]);
//        albumList.add(a);
//
//        a = new Album("ROMANCE", 11, covers[2]);
//        albumList.add(a);
//
//        a = new Album("PARTY", 12, covers[3]);
//        albumList.add(a);
//
//        a = new Album("R&B", 14, covers[4]);
//        albumList.add(a);
//
//        a = new Album("HIP HOP", 1, covers[5]);
//        albumList.add(a);
//
//        a = new Album("KPOP", 11, covers[6]);
//        albumList.add(a);
//
//        a = new Album("ROCK", 14, covers[7]);
//        albumList.add(a);


    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
