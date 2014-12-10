package com.pal.vo;

import java.util.HashMap;
import com.pal.parser.BaseParser;
import android.content.Context;

public class RequestVo {
	public int requestUrl;
	public Context context;
	public HashMap<String,String> requestDataMap;
	public BaseParser<?> jsonParser;
}
