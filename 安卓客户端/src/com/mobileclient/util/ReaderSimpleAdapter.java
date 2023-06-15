package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.ReaderTypeService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class ReaderSimpleAdapter extends SimpleAdapter { 
	/*��Ҫ�󶨵Ŀؼ���Դid*/
    private int[] mTo;
    /*map���Ϲؼ�������*/
    private String[] mFrom;
/*��Ҫ�󶨵�����*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //ͼƬ�첽���������,���ڴ滺����ļ�����
    private SyncImageLoader syncImageLoader;

    public ReaderSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*��һ��װ�����viewʱ=null,���½�һ������inflate��Ⱦһ��view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.reader_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*�󶨸�view�����ؼ�*/
	  holder.tv_readerNo = (TextView)convertView.findViewById(R.id.tv_readerNo);
	  holder.tv_readerType = (TextView)convertView.findViewById(R.id.tv_readerType);
	  holder.tv_readerName = (TextView)convertView.findViewById(R.id.tv_readerName);
	  holder.iv_photo = (ImageView)convertView.findViewById(R.id.iv_photo);
	  /*���ø����ؼ���չʾ����*/
	  holder.tv_readerNo.setText("���߱�ţ�" + mData.get(position).get("readerNo").toString());
	  holder.tv_readerType.setText("�������ͣ�" + (new ReaderTypeService()).GetReaderType(Integer.parseInt(mData.get(position).get("readerType").toString())).getReaderTypeName());
	  holder.tv_readerName.setText("������" + mData.get(position).get("readerName").toString());
	  holder.iv_photo.setImageResource(R.drawable.default_photo);
	  ImageLoadListener photoLoadListener = new ImageLoadListener(mListView,R.id.iv_photo);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("photo"),photoLoadListener);  
	  /*�����޸ĺõ�view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_readerNo;
    	TextView tv_readerType;
    	TextView tv_readerName;
    	ImageView iv_photo;
    }
} 
