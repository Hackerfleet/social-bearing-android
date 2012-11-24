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
  //public final static String BASE_URL = "http://192.168.2.180";
  public final static String BASE_URL = "http://192.168.2.181";
  public final static String POST_URL = BASE_URL.concat("/v1/bearing");

  public final static SparseArray<BuoyDefinition> buoyDefinitions = new SparseArray<BuoyDefinition>() {{
    put(R.id.north_btn, new BuoyDefinition(R.drawable.spar_north, "north"));
    put(R.id.south_btn, new BuoyDefinition(R.drawable.spar_south, "south"));
    put(R.id.east_btn, new BuoyDefinition(R.drawable.spar_east, "east"));
    put(R.id.west_btn, new BuoyDefinition(R.drawable.spar_west, "west"));
    put(R.id.center_btn, new BuoyDefinition(R.drawable.pillar_single, "center"));
    
    put(R.id.specialpp_conical, new BuoyDefinition(R.drawable.specialpp_conical, "specialpp_conical"));
    put(R.id.specialpp_forbidden, new BuoyDefinition(R.drawable.specialpp_forbidden, "specialpp_forbidden"));
    put(R.id.specialpp_safe_water, new BuoyDefinition(R.drawable.specialpp_safe_water, "specialpp_safe_water"));
    put(R.id.specialpp_fateral, new BuoyDefinition(R.drawable.specialpp_fateral, "specialpp_fateral"));
    put(R.id.specialpp_lateral, new BuoyDefinition(R.drawable.specialpp_lateral, "specialpp_lateral"));
    put(R.id.specialpp_prefered_starbord, new BuoyDefinition(R.drawable.specialpp_starboard_prefered, "specialpp_prefered_starbord"));
    //put(R.id.spe, new BuoyDefinition(R.drawable.specialpp_port_prefered, "specialpp_prefered_port"));

    
    //specialpp_forbidden
    //specialpp_safe water
    //specialpp_fateral
    //specialpp_lateral
    
    // specialpp_prefered_starbord
    // specialpp_prefered port
    
    
  }};

}
