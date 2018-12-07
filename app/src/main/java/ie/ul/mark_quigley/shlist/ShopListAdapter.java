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

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ShopListViewHolder>{



    private List<DocumentSnapshot> mShopListSnapshots = new ArrayList<>();

    public ShopListAdapter() {
        CollectionReference shoplistRef = FirebaseFirestore.getInstance().collection(Constants.COLLECTION_PATH);

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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shoplist_itemview, parent, false);
        return new ShopListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopListViewHolder shopListViewHolder, int i) {
        DocumentSnapshot ds = mShopListSnapshots.get(i);
        String item = (String)ds.get(Constants.KEY_ITEM);
        Double quantity = (Double)ds.get(Constants.KEY_QUANTITY);
        Double aldi = (Double)ds.get(Constants.KEY_ALDI);
        Double lidl = (Double)ds.get(Constants.KEY_LIDL);
        Double dunnes = (Double)ds.get(Constants.KEY_DUNNES);


        shopListViewHolder.mItemTextView.setText(item);
      //  shopListViewHolder.mQtyTestView.setText(Double.toString(quantity));
        shopListViewHolder.mAldiTextView.setText(aldi.toString());
        shopListViewHolder.mLidlTextView.setText(lidl.toString());
        shopListViewHolder.mDunnesTextView.setText(dunnes.toString());
        shopListViewHolder.mQtyTestView.setText(quantity.toString());


    }


    @Override
    public int getItemCount() {
        return mShopListSnapshots.size();
    }

    class ShopListViewHolder extends RecyclerView.ViewHolder {
        private TextView mItemTextView;
        private TextView mQtyTestView;
        private TextView mAldiTextView;
        private TextView mLidlTextView;
        private TextView mDunnesTextView;

        public ShopListViewHolder(@NonNull View itemView) {
            super(itemView);
            //mItemTextView = itemView.findViewById(R.id.itemview_quote);
//            should like like this
            mItemTextView = itemView.findViewById(R.id.itemview_item);
            mQtyTestView = itemView.findViewById(R.id.itemview_quantity);
            mAldiTextView = itemView.findViewById(R.id.itemview_aldi);
            mLidlTextView = itemView.findViewById(R.id.itemview_lidl);
            mDunnesTextView = itemView.findViewById(R.id.itemview_dunnes);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DocumentSnapshot ds = mShopListSnapshots.get(getAdapterPosition());

                    Context c = view.getContext();
                    Intent intent = new Intent(c, ShopListDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_DOC_ID, ds.getId());
                    c.startActivity(intent);
                }
            });
        }
    }
}
