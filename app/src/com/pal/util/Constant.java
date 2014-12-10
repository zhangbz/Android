package com.pal.util;

import android.os.Environment;

public class Constant {
	  public static final int HOME = 1;
	  public static final int CLASSIFY = 2;
	  public static final int MORE = 5;
	  public static final int SEARCH = 3;
	  public static final int SHOPCAR = 4;
	  public static int SHOPCAR_NUM = 0;
	  public static int defaultIndex;
	  public static int selectedHome;
	  public static String selectedNum;
	  public static final String SDPATH;
	  public static String ACTIVE_UID;
	  public final static int SUCCESS = 1;
	  public final static int NET_FAILED = 2;

	  
	  static
	  {
	    int i = 0;
	    defaultIndex = 1;
	    selectedHome = i;
	    selectedNum = "0";
	    SDPATH = Environment.getExternalStorageDirectory().getPath();
	    ACTIVE_UID = "uid";
	    SHOPCAR_NUM = i;
	  }
}
