package com.zhangbz.uibestpractice;

import java.util.List;

import org.w3c.dom.Text;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MsgAdapter extends ArrayAdapter<Msg> {
	
	private int resourceId;
	
	public MsgAdapter(Context context, int textViewResourceId,
			List<Msg> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Msg msg = getItem(position);
		View view = null ;
		ViewHolder viewHolder;
		if(convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.leftLayout = (LinearLayout) view.findViewById(R.id.left_layout);
			viewHolder.rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);
			
		}
		return view;
		
	}

	class ViewHolder {
		LinearLayout leftLayout;
		LinearLayout rightLayout;
		TextView leftMsg;
		TextView rightMsg;
	}

}
