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

public class ShopListDetailActivity extends AppCompatActivity {

    private DocumentReference mDocRef;
    private DocumentSnapshot mDocSnapshot;
    private TextView mItemTextView;
    private TextView mAldiTextView;
    private TextView mLidlTextView;
    private TextView mDunnesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mItemTextView = findViewById(R.id.detail_item);
        mAldiTextView = findViewById(R.id.detail_aldi_price);
        mLidlTextView = findViewById(R.id.detail_lidl_price);
        mDunnesTextView = findViewById(R.id.detail_dunnes_price);

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
                    mAldiTextView.setText((String)documentSnapshot.get(Constants.KEY_ALDI));
                    mLidlTextView.setText((String)documentSnapshot.get(Constants.KEY_LIDL));
                    mDunnesTextView.setText((String)documentSnapshot.get(Constants.KEY_DUNNES));
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
        final TextView aldiEditText = view.findViewById(R.id.dialog_aldi_edittext);
        final TextView lidlEditText = view.findViewById(R.id.dialog_lidl_edittext);
        final TextView dunnesEditText = view.findViewById(R.id.dialog_dunnes_edittext);
        itemEditText.setText((String)mDocSnapshot.get(Constants.KEY_ITEM));
        aldiEditText.setText((String)mDocSnapshot.get(Constants.KEY_ALDI));
        lidlEditText.setText((String)mDocSnapshot.get(Constants.KEY_LIDL));
        dunnesEditText.setText((String)mDocSnapshot.get(Constants.KEY_DUNNES));
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Map<String, Object> sl = new HashMap<>();
                sl.put(Constants.KEY_ITEM, itemEditText.getText().toString());
                sl.put(Constants.KEY_ALDI, aldiEditText.getText().toString());
                sl.put(Constants.KEY_LIDL, lidlEditText.getText().toString());
                sl.put(Constants.KEY_DUNNES, dunnesEditText.getText().toString());
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
