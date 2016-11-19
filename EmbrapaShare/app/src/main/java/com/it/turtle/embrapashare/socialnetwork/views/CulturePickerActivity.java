package com.it.turtle.embrapashare.socialnetwork.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.it.turtle.embrapashare.R;
import com.it.turtle.embrapashare.socialnetwork.controller.FirebaseManager;
import com.it.turtle.embrapashare.socialnetwork.controller.FirebaseMap;
import com.it.turtle.embrapashare.socialnetwork.models.Culture;
import com.it.turtle.embrapashare.socialnetwork.views.adapter.CutureAdapter;

import java.util.ArrayList;
import java.util.List;

public class CulturePickerActivity extends AppCompatActivity {

    private CutureAdapter cultureAdapter;
    private ListView culturesListView;

    private FirebaseManager firebaseManager;

    List<Culture> cultureList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_picker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseManager = new FirebaseManager(FirebaseMap.CULTURES, Culture.class);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddCultureActivity.class));
            }
        });

        initView();

        firebaseManager.setOnDataChangedListener(new FirebaseManager.OnDataChangedListener() {
            @Override
            public void onDatabaseUpdate(DataSnapshot dataSnapshot) {
                cultureList = convertList(firebaseManager.getChildrenList());

                cultureList = convertList(firebaseManager.getChildrenList());

                if (cultureList != null) {
                    cultureAdapter = new CutureAdapter(CulturePickerActivity.this, cultureList);

                    culturesListView.setAdapter(cultureAdapter);
                } else {
                    Log.d("Vinicius", ">>>>>>>>>>>>>>>>>>>>>>>>>> lista nula!!!!");
                }

                culturesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        openCulture(i);
                    }
                });
            }
        });
    }

    private void initView () {
        culturesListView = (ListView) findViewById(R.id.culturesListView);
    }

    private List<Culture> convertList (List<Object> sourceList) {
        List<Culture> outPut = new ArrayList<>();
        for (Object object : sourceList) {
            outPut.add((Culture) object);
        }

        return outPut;
    }

    private void openCulture(int number) {
        switch (number) {
            case 1:
                startActivity(new Intent(getApplicationContext(), TomateActivity.class));
                break;
            case 2:
                startActivity(new Intent(getApplicationContext(), MelanciaActivity.class));
                break;
            case 3:
                startActivity(new Intent(getApplicationContext(), CafeActivity.class));
                break;
        }

    }
}
