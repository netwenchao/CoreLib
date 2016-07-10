package cn.dragon2.common.adapters;

import java.util.List;

import cn.dragon2.common.R;
import cn.dragon2.common.entity.MyAppsInfo;
import cn.dragon2.common.util.StringUtils;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAppInfoListAdapter  extends BaseAdapter implements OnClickListener{	
		public class MyAppInfoHolder{		
			public TextView AppName;
			public TextView AppDesc;
			public TextView AppUrl;
			public ImageView AppIcon;			

			public MyAppInfoHolder(View v){
				AppName=(TextView)v.findViewById(R.id.appName);
				AppDesc=(TextView)v.findViewById(R.id.appDesc);
				AppIcon=(ImageView)v.findViewById(R.id.appIcon);
				AppUrl=(TextView)v.findViewById(R.id.appUrl);
			}
		}

		private Context mContext;
		private List<MyAppsInfo> listData;
		private LayoutInflater inflater;
		
		public MyAppInfoListAdapter(Context ctx,List<MyAppsInfo> data) {
			mContext=ctx;
			listData=data;
			inflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listData.size();
		}

		@Override
		public MyAppsInfo getItem(int position) {
			if(position>-1 && position<listData.size())
				return listData.get(position);
			return null;
		}

		@Override
		public long getItemId(int position) {
			MyAppsInfo info=getItem(position);		
			return info!=null?info.Id:-1;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v=convertView;
			if(v!=null && v.getTag()!=null) return v;
			MyAppInfoHolder itemHolder;
			v=(v==null)?inflater.inflate(R.layout.abs_app_item,null):v;
			itemHolder=(MyAppInfoHolder)v.getTag();
			itemHolder=(itemHolder==null)?new MyAppInfoHolder(v):itemHolder;
			
			MyAppsInfo info=listData.get(position);
			itemHolder.AppName.setText(info.AppName);
			itemHolder.AppDesc.setText(info.AppDesc);
			itemHolder.AppIcon.setImageBitmap(info.getAppIconBitMap(mContext));
			itemHolder.AppUrl.setOnClickListener(this);
			itemHolder.AppUrl.setTag(info.AppUrl);
			itemHolder.AppUrl.setVisibility(StringUtils.isBlank(info.AppUrl)?View.GONE:View.VISIBLE);
			return v;
		}

		@Override
		public void onClick(View v) {
			Object tagObject=v.getTag();
			if(tagObject!=null){
				Uri url=Uri.parse(tagObject.toString());
				Intent actView=new Intent(Intent.ACTION_VIEW,url);
				mContext.startActivity(actView);
			}
		}
}
