package com.ss.orthologs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PreviousVisitsActivity extends AppCompatActivity {
    private RecyclerView recyclerView1;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private final String[] values = new String[]{"16-Oct-2019", "14-Nov-2019", "15-Dec-2020", "16-Oct-2019", "14-Nov-2019", "15-Dec-2020", "16-Oct-2019", "14-Nov-2019", "15-Dec-2020", "14-Nov-2019", "15-Dec-2020", "16-Oct-2019", "14-Nov-2019", "15-Dec-2020"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_visits);
        Toolbar myToolbar = findViewById(R.id.my_toolbar1);
        setSupportActionBar(myToolbar);


        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }

        recyclerView1 = findViewById(R.id.images_recycler1);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView1.setHasFixedSize(true);

        // use a linear layout manager
        //layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        //recyclerView1.setLayoutManager(layoutManager);

        layoutManager = new GridLayoutManager(this, 3);
        recyclerView1.setLayoutManager(layoutManager);

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView1.setItemAnimator(itemAnimator);

        // specify an adapter (see also next example)
        mAdapter = new ImagesAdapter(this, list, new ImageRecyclerTouchListner(this));
        recyclerView1.setAdapter(mAdapter);

    }

    private class ImageRecyclerTouchListner implements ImagesAdapter.OnItemClickListener {

        private Context ctx;
        ImageRecyclerTouchListner(Context ctx)
        {
            this.ctx = ctx;
        }
        @Override
        public void onItemClick(View view, int position) {
            Toast.makeText(ctx, "helloe "+ values[position], Toast.LENGTH_LONG).show();
            ctx.startActivity(new Intent(getApplicationContext(), ImageViewerActivity.class));
        }
    }
}
