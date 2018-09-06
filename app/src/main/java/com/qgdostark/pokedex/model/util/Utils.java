package com.qgdostark.pokedex.model.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Marcos on 20/03/18.
 */

public class Utils {

    private static final String DOIS_ZEROS = "00";
    private static final String UM_ZERO = "0";

    // Verificar estado da conexão com a internet
    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static String adicionaZeros(int n) {
        if (n >= 1 && n <= 9)
            return DOIS_ZEROS + n;
        if (n <= 99)
            return UM_ZERO + n;
        return String.valueOf(n);
    }
}

