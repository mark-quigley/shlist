package ie.ul.mark_quigley.shlist;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DunnesAdapter extends RecyclerView.Adapter<DunnesAdapter.ShopListViewHolder>{

    private List<DocumentSnapshot> mShopListSnapshots = new ArrayList<>();

    public DunnesAdapter() {
        CollectionReference shoplistRef = FirebaseFirestore.getInstance()
                .collection(Constants.COLLECTION_PATH);

        shoplistRef.orderBy(Constants.KEY_CREATED, Query.Direction.DESCENDING).limit(50)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(Constants.TAG, "Listening failed!");
                            return;
                        }
                        mShopListSnapshots = documentSnapshots.getDocuments();
                        notifyDataSetChanged();
                    }
                });
    }

    @NonNull
    @Override
    public ShopListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dunnes_itemview, parent, false);
        return new ShopListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopListViewHolder shopListViewHolder, int i) {
        DocumentSnapshot ds = mShopListSnapshots.get(i);
        String item = (String)ds.get(Constants.KEY_ITEM);
        String quantity = (String)ds.get(Constants.KEY_QUANTITY);
        String aldi = (String)ds.get(Constants.KEY_ALDI);
        String lidl = (String)ds.get(Constants.KEY_LIDL);
        String dunnes = (String)ds.get(Constants.KEY_DUNNES);

        double x;
        double aldiPrice = 0.0;
        double lidlPrice = 0.0;
        double dunnesPrice = 0.0;
//        //final int quantity = Integer.parseInt(quantityEditText.getText().toString();
        aldiPrice = Double.parseDouble(aldi.toString());
        lidlPrice = Double.parseDouble(lidl.toString());
        dunnesPrice = Double.parseDouble(dunnes.toString());

        x = Math.min(Math.min(aldiPrice, lidlPrice), dunnesPrice);

        if (x == dunnesPrice) {
            shopListViewHolder.mItemTextView.setText(item);
            shopListViewHolder.mQtyTestView.setText(quantity);
            shopListViewHolder.mDunnesTextView.setText(aldi);
        }

    }


    @Override
    public int getItemCount() {
        return mShopListSnapshots.size();
    }

    class ShopListViewHolder extends RecyclerView.ViewHolder {
        private TextView mItemTextView;
        private TextView mQtyTestView;
        private TextView mDunnesTextView;


        public ShopListViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemTextView = itemView.findViewById(R.id.itemview_item);
            mQtyTestView = itemView.findViewById(R.id.itemview_quantity);
            mDunnesTextView = itemView.findViewById(R.id.itemview_dunnes);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DocumentSnapshot ds = mShopListSnapshots.get(getAdapterPosition());

                    Context c = view.getContext();
                    Intent intent = new Intent(c, AldiAdapterDetail.class);
                    intent.putExtra(Constants.EXTRA_DOC_ID, ds.getId());
                    c.startActivity(intent);
                }
            });
        }
    }
}

