package org.hackerfleet.etc;

import org.hackerfleet.R;

import android.util.SparseArray;
/**
 * Application Definitions
 * 
 * @author ligi
 *
 */
public class AppDefs {
	public final static String TAG="SOCIALBEARING";
	
	public final static SparseArray<BuoyDefinition> foo=new SparseArray<BuoyDefinition>() {{
		put(R.id.north_btn,new BuoyDefinition(R.drawable.spar_north,"north"));
		put(R.id.south_btn,new BuoyDefinition(R.drawable.spar_south,"south"));
		put(R.id.east_btn,new BuoyDefinition(R.drawable.spar_east,"east"));
		put(R.id.west_btn,new BuoyDefinition(R.drawable.spar_west,"west"));
		put(R.id.center_btn,new BuoyDefinition(R.drawable.pillar_single,"center"));
	}};
		
}
