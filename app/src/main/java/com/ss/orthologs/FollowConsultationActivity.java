package com.ss.orthologs;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;


public class FollowConsultationActivity extends AppCompatActivity {

    private ExpandableLayout expandableLayout0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followconsultation);
        Log.e("FollowCon","Hello");

        Toolbar myToolbar = findViewById(R.id.my_toolbar1);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        ab.setSubtitle("Followup Consultation");



        expandableLayout0 = findViewById(R.id.expandable_layout_0);

        expandableLayout0.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLayout0", "State: " + state);
            }
        });

        AddPatientDetails();


        final ImageView details_down_btn = findViewById(R.id.followup_details_down);
        final TextView collapse_subtitle = findViewById(R.id.collapse_subtitle);

        details_down_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expandableLayout0.isExpanded()) {
                    expandableLayout0.collapse();
                    details_down_btn.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.down_white));
                    collapse_subtitle.setText("Press down for more details");
                }
                else
                {
                    expandableLayout0.expand();
                    details_down_btn.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.up_white));
                    collapse_subtitle.setText("");
                }
            }
        });


        final ListView listview = (ListView) findViewById(R.id.visit_dates_list);
        String[] values = new String[] { "16-Oct-2019","14-Nov-2019", "15-Dec-2020" };

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final VisitsListAdapter adapter = new VisitsListAdapter(this, list);
        listview.setAdapter(adapter);

        VisitsListClickListener vl = new VisitsListClickListener(this);
        listview.setOnItemClickListener(vl);

    }



    private void AddPatientDetails()
    {
        LinearLayout.inflate(this, R.layout.widget_followup_dropdown, expandableLayout0);
        CheckBox cb = findViewById(R.id.checkBoxdiab);
        cb.setChecked(true);
    }

    private class VisitsListClickListener implements AdapterView.OnItemClickListener
    {
        private Context ctx;
        VisitsListClickListener(Context ctx)
        {
            this.ctx = ctx;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final String item = (String) parent.getItemAtPosition(position);
            String s = (String)parent.getItemAtPosition(position);
            ctx.startActivity(new Intent(getApplicationContext(), PreviousVisitsActivity.class));
        }
    }
}
