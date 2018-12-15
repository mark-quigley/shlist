package ie.ul.mark_quigley.shlist;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class AldiListDetailActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aldi_list_detail);


        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        AldiAdapter AldiAdapter = new AldiAdapter( );
        recyclerView.setAdapter(AldiAdapter);

       View view = getLayoutInflater().inflate(R.layout.activity_aldi_list_detail, null, false);
        Intent intent = getIntent();
        String mAldiTotalCost = intent.getStringExtra("key1");


        Log.d("aldiSubTotal3", String.valueOf("test"));

       final TextView aldiFinalCostText = (TextView) findViewById(R.id.footer_total_sum);


        aldiFinalCostText.setText("" + AldiAdapter.getmAldiTotalCost());


        Log.d("aldiSubTotal4", String.valueOf("" + AldiAdapter.getmAldiTotalCost()));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lidl();
            }
        });
    }

    public void lidl() {
        Intent intent = new Intent(this, LidlListDetailActivity.class);
        startActivity(intent);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}