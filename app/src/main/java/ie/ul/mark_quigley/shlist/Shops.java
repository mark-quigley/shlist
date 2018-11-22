package ie.ul.mark_quigley.shlist;

import java.util.HashMap;
import java.util.Random;

public class Shops {

    private String mName;
    private int mImageResourceId;
    private float mRating;
    private Random random = new Random();

    public static final HashMap<String, Integer> sShopLogo;
    static {
        sShopLogo = new HashMap<>();
        sShopLogo.put("Lidl", R.drawable.lidl);
        sShopLogo.put("Aldi", R.drawable.aldi);
        sShopLogo.put("Dunnes Stores", R.drawable.dunnes);
        sShopLogo.put("Tesco", R.drawable.tesco);

    }




















}
