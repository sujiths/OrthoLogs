package com.ss.orthologs;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

public class SearchTagsActivity extends AppCompatActivity {
    Context context = null;
    SearchListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchtags);
        Toolbar myToolbar = findViewById(R.id.my_toolbar1);
        setSupportActionBar(myToolbar);

        context = this;

        ActionBar ab = getSupportActionBar();

        ImageView iv = findViewById(R.id.back_health);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });

        myToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d(SearchTagsActivity.class.getName(), "Menu  item id : " + item.getItemId() + " --- " + R.menu.actions);
                if (item.getItemId() == R.id.action_refresh) {
                    // get prompts.xml view
                    LayoutInflater li = LayoutInflater.from(context);
                    View promptsView = li.inflate(R.layout.add_search_tag_prompt, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Dialog_Alert);

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);

                    final EditText userInput = (EditText) promptsView
                            .findViewById(R.id.searchTag);

                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            writeToSearchTagFile(userInput.getText().toString());
                                            refreshList();
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }
                return false;
            }
        });

        final ListView listview = (ListView) findViewById(R.id.search_tag_list);
        String[] values = new String[]{"ACL Tear", "Osteoarthritis", "Lateral Epicondylitis"};

        final List<String> searchTags = readSearchTags();

        adapter = new SearchListAdapter(this, searchTags);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.teal, null));

                String s = (String) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();


            }

        });

    }

    private void refreshList() {
        adapter.clear();
        adapter.addAll(readSearchTags());
        adapter.notifyDataSetChanged();
    }

    private void writeToSearchTagFile(String tag) {
        File testFile = new File(this.getExternalFilesDir(null), "SearchTag.txt");
        if (!testFile.exists()) {
            try {
                testFile.createNewFile();
            } catch (IOException e) {
                Log.e(SearchTagsActivity.class.getName(), "Exception  : ", e);
            }
        }
        PrintWriter writer = null;
        try {
            // Adds a line to the file
            writer = new PrintWriter(new FileWriter(testFile, true /*append*/));
            writer.println(tag);
            writer.close();
        } catch (IOException e) {
            Log.e(SearchTagsActivity.class.getName(), "Exception  : ", e);
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (Exception e) {
                Log.e(SearchTagsActivity.class.getName(), "Exception  : ", e);
            }
        }
    }

    private List<String> readSearchTags() {
        List<String> searchTags = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File(this.getExternalFilesDir(null), "SearchTag.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                searchTags.add(line);
            }
        } catch (Exception e) {
            Log.e(SearchTagsActivity.class.getName(), "Exception  : ", e);
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (Exception e) {
                Log.e(SearchTagsActivity.class.getName(), "Exception  : ", e);
            }
        }
        return searchTags;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions, menu);
        return true;
    }
}
