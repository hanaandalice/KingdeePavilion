package com.xilada.xldutils.adapter;

import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xilada.xldutils.view.utils.ViewHolder;

/**
 * JSONArray型数据的适配器
 * @author sinata
 *
 */
public abstract class JSONArrayAdapter extends BaseAdapter{
	
	protected Context mContext;
	protected JSONArray mArray;
	protected LayoutInflater mInflater;
    protected int mLayoutID;    // 布局资源ID

	public JSONArrayAdapter(Context context,JSONArray array,int layoutID){
		 mContext = context;
		 mInflater = LayoutInflater.from(context);
		 mArray = array;
		 mLayoutID = layoutID;
	}
	
	@Override
	public int getCount() {
		if (mArray!=null) return mArray.length(); 
		return 0;
	}

	@Override
	public JSONObject getItem(int position) {
		if (mArray!=null) return mArray.optJSONObject(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) convertView = mInflater.inflate(mLayoutID, null);

        ViewHolder holder = new ViewHolder(convertView);
        JSONObject object=getItem(position);
        if (object!=null) {
        	onBind(position, getItem(position), holder);
		}
       
        return convertView;
	}
	
	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}
	
	public void resetData(JSONArray array){
		this.mArray=array;
	}
	
	/**
     * 绑定数据
     */
    protected abstract void onBind(int position, JSONObject json, ViewHolder holder);
}
