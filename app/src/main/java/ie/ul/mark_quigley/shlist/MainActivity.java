package ie.ul.mark_quigley.shlist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                showAddDialog();
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
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Map<String, Object> sl = new HashMap<>();
                sl.put(Constants.KEY_ITEM, itemEditText.getText().toString());
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
}