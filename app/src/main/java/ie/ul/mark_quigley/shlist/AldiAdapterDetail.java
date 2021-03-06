package ie.ul.mark_quigley.shlist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AldiAdapterDetail extends AppCompatActivity {

    private DocumentReference mDocRef;
    private DocumentSnapshot mDocSnapshot;
    private TextView mItemTextView;
    private TextView mQuantityTextView;
    private TextView mAldiTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aldi_list_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mItemTextView = findViewById(R.id.detail_item);
        mQuantityTextView = findViewById(R.id.detail_quantity);
        mAldiTextView = findViewById(R.id.detail_aldi_price);


        String docId = getIntent().getStringExtra(Constants.EXTRA_DOC_ID);


        mDocRef = FirebaseFirestore.getInstance()
                .collection(Constants.COLLECTION_PATH).document(docId);
        mDocRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(Constants.TAG, "Listen failed");
                    return;
                }
                if (documentSnapshot.exists()) {
                    mDocSnapshot = documentSnapshot;
                    mItemTextView.setText((String)documentSnapshot.get(Constants.KEY_ITEM));
                    mQuantityTextView.setText((String)documentSnapshot.get(Constants.KEY_QUANTITY));
                    mAldiTextView.setText((String)documentSnapshot.get(Constants.KEY_ALDI));
                }
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog();
            }
        });
    }



    private void showEditDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.shoplist_dialog, null, false);
        builder.setView(view);
        builder.setTitle("Edit this item");
        final TextView itemEditText = view.findViewById(R.id.dialog_item_edittext);
        final TextView quantityEditText = view.findViewById(R.id.dialog_quantity_edittext);
        final TextView aldiEditText = view.findViewById(R.id.dialog_aldi_edittext);

        itemEditText.setText((String)mDocSnapshot.get(Constants.KEY_ITEM));
        quantityEditText.setText((String)mDocSnapshot.get(Constants.KEY_QUANTITY));
        aldiEditText.setText((String)mDocSnapshot.get(Constants.KEY_ALDI));

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {



            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Map<String, Object> sl = new HashMap<>();
                sl.put(Constants.KEY_ITEM, itemEditText.getText().toString());
                sl.put(Constants.KEY_QUANTITY, quantityEditText.getText().toString());
                sl.put(Constants.KEY_ALDI, aldiEditText.getText().toString());
                sl.put(Constants.KEY_CREATED, new Date());
                mDocRef.update(sl);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                mDocRef.delete();
                finish();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }




}
