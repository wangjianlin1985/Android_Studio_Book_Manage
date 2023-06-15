package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.BookService;
import com.mobileclient.service.ReaderService;
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

public class LoanInfoSimpleAdapter extends SimpleAdapter { 
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

    public LoanInfoSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.loaninfo_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*�󶨸�view�����ؼ�*/
	  holder.tv_book = (TextView)convertView.findViewById(R.id.tv_book);
	  holder.tv_reader = (TextView)convertView.findViewById(R.id.tv_reader);
	  holder.tv_borrowDate = (TextView)convertView.findViewById(R.id.tv_borrowDate);
	  holder.tv_returnDate = (TextView)convertView.findViewById(R.id.tv_returnDate);
	  /*���ø����ؼ���չʾ����*/
	  holder.tv_book.setText("ͼ�飺" + (new BookService()).GetBook(mData.get(position).get("book").toString()).getBookName());
	  holder.tv_reader.setText("���ߣ�" + (new ReaderService()).GetReader(mData.get(position).get("reader").toString()).getReaderName());
	  try {holder.tv_borrowDate.setText("�������ڣ�" + mData.get(position).get("borrowDate").toString().substring(0, 10));} catch(Exception ex){}
	  try {holder.tv_returnDate.setText("�黹���ڣ�" + mData.get(position).get("returnDate").toString().substring(0, 10));} catch(Exception ex){}
	  /*�����޸ĺõ�view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_book;
    	TextView tv_reader;
    	TextView tv_borrowDate;
    	TextView tv_returnDate;
    }
} 
