package ie.ul.mark_quigley.shlist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import java.math.*;
//import java.lang.Integer;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

//    private View headerView;
//    private ListView lvItensShoppingList;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_add:
                showAddDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        ShopListAdapter shopListAdapter = new ShopListAdapter();
        recyclerView.setAdapter(shopListAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aldiListDetailActivity();
            }
        });
    }



    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.shoplist_dialog, null, false);
        builder.setView(view);
        // Opening window in the app
        builder.setTitle("Main Add item");
        
        final TextView itemEditText = view.findViewById(R.id.dialog_item_edittext);
        final TextView quantityEditText = view.findViewById(R.id.dialog_quantity_edittext);
        final TextView aldiEditText = view.findViewById(R.id.dialog_aldi_edittext);
        final TextView lidlEditText = view.findViewById(R.id.dialog_lidl_edittext);
        final TextView dunnesEditText = view.findViewById(R.id.dialog_dunnes_edittext);
//        final double aldiPrice = Double.parseDouble(String.valueOf(aldiEditText));
//        final double lidiPrice = Double.parseDouble(String.valueOf(lidlEditText));// need to do this for all doubles.
//        final double dunnesPrice = Double.parseDouble(String.valueOf(dunnesEditText));
//        final int quantity = Integer.parseInt(String.valueOf(quantityEditText));

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Map<String, Object> sl = new HashMap<>();
                sl.put(Constants.KEY_ITEM, itemEditText.getText().toString());
//                sl.put(Constants.KEY_QUANTITY, quantity);
//                //sl.put(Constants.KEY_ALDI, aldiEditText.getText().toString());// put all prices into db as a double
//                sl.put(Constants.KEY_ALDI, aldiPrice);
//                sl.put(Constants.KEY_LIDL, lidiPrice);
//                sl.put(Constants.KEY_DUNNES, dunnesPrice);
//                sl.put(Constants.KEY_CREATED, new Date());
//                //s1.put(Constants.KEY_TEST_PRICE, aldiPrice);


//              sl.put(Constants.KEY_ITEM, itemEditText.getText().toString());
                sl.put(Constants.KEY_QUANTITY, quantityEditText.getText().toString());
                sl.put(Constants.KEY_ALDI, aldiEditText.getText().toString());
                sl.put(Constants.KEY_LIDL, lidlEditText.getText().toString());
                sl.put(Constants.KEY_DUNNES, dunnesEditText.getText().toString());
                sl.put(Constants.KEY_CREATED, new Date());
                FirebaseFirestore.getInstance().collection(Constants.COLLECTION_PATH).add(sl);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);

        builder.create().show();
    }


    public void aldiListDetailActivity () {
        Intent intent = new Intent(this, AldiListDetailActivity.class);
        startActivity(intent);


    }




}
