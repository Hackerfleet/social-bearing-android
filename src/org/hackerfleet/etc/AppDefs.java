package org.hackerfleet.etc;

import org.hackerfleet.R;

import android.util.SparseArray;

/**
 * Application Definitions / xonfig
 *
 * @author ligi
 */
public class AppDefs {
  public final static String TAG = "SOCIALBEARING";
  public final static String BASE_URL = "http://192.168.2.180";
  public final static String POST_URL = BASE_URL.concat("/v1/bearing");

  public final static SparseArray<BuoyDefinition> buoyDefinitions = new SparseArray<BuoyDefinition>() {{
    put(R.id.north_btn, new BuoyDefinition(R.drawable.spar_north, "north"));
    put(R.id.south_btn, new BuoyDefinition(R.drawable.spar_south, "south"));
    put(R.id.east_btn, new BuoyDefinition(R.drawable.spar_east, "east"));
    put(R.id.west_btn, new BuoyDefinition(R.drawable.spar_west, "west"));
    put(R.id.center_btn, new BuoyDefinition(R.drawable.pillar_single, "center"));
  }};

}
