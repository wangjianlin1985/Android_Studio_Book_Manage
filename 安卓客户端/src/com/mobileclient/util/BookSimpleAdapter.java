package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.BookTypeService;
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

public class BookSimpleAdapter extends SimpleAdapter { 
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

    public BookSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.book_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*�󶨸�view�����ؼ�*/
	  holder.tv_barcode = (TextView)convertView.findViewById(R.id.tv_barcode);
	  holder.tv_bookType = (TextView)convertView.findViewById(R.id.tv_bookType);
	  holder.tv_bookName = (TextView)convertView.findViewById(R.id.tv_bookName);
	  holder.iv_bookPhoto = (ImageView)convertView.findViewById(R.id.iv_bookPhoto);
	  holder.tv_publishDate = (TextView)convertView.findViewById(R.id.tv_publishDate);
	  /*���ø����ؼ���չʾ����*/
	  holder.tv_barcode.setText("ͼ�������룺" + mData.get(position).get("barcode").toString());
	  holder.tv_bookType.setText("ͼ�����" + (new BookTypeService()).GetBookType(Integer.parseInt(mData.get(position).get("bookType").toString())).getBookTypeName());
	  holder.tv_bookName.setText("ͼ�����ƣ�" + mData.get(position).get("bookName").toString());
	  holder.iv_bookPhoto.setImageResource(R.drawable.default_photo);
	  ImageLoadListener bookPhotoLoadListener = new ImageLoadListener(mListView,R.id.iv_bookPhoto);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("bookPhoto"),bookPhotoLoadListener);  
	  try {holder.tv_publishDate.setText("�������ڣ�" + mData.get(position).get("publishDate").toString().substring(0, 10));} catch(Exception ex){}
	  /*�����޸ĺõ�view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_barcode;
    	TextView tv_bookType;
    	TextView tv_bookName;
    	ImageView iv_bookPhoto;
    	TextView tv_publishDate;
    }
} 
