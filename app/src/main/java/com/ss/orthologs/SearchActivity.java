package com.ss.orthologs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.cachapa.expandablelayout.ExpandableLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;


public class SearchActivity extends AppCompatActivity {

    private ExpandableLayout expandablesearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        LinearLayout followConsultButton = findViewById(R.id.search_button);
        followConsultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FollowConsultationActivity.class));
            }
        });

        expandablesearch= findViewById(R.id.expandable_search);

        expandablesearch.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLayout0", "State: " + state);
            }
        });

        AddAdditionalSearch();


        final ImageView details_down_btn = findViewById(R.id.search_details_down);

        details_down_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expandablesearch.isExpanded()) {
                    expandablesearch.collapse();
                    details_down_btn.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.down_white));
                }
                else
                {
                    expandablesearch.expand();
                    details_down_btn.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.up_white));
                }
            }
        });

    }

    private void AddAdditionalSearch()
    {
        LinearLayout.inflate(this, R.layout.widget_advanced_search, expandablesearch);
        LinearLayout lv = findViewById(R.id.search_tag_box);
        lv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), SearchTagsActivity.class), 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
