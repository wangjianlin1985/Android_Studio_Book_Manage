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
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
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
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.reader_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_readerNo = (TextView)convertView.findViewById(R.id.tv_readerNo);
	  holder.tv_readerType = (TextView)convertView.findViewById(R.id.tv_readerType);
	  holder.tv_readerName = (TextView)convertView.findViewById(R.id.tv_readerName);
	  holder.iv_photo = (ImageView)convertView.findViewById(R.id.iv_photo);
	  /*设置各个控件的展示内容*/
	  holder.tv_readerNo.setText("读者编号：" + mData.get(position).get("readerNo").toString());
	  holder.tv_readerType.setText("读者类型：" + (new ReaderTypeService()).GetReaderType(Integer.parseInt(mData.get(position).get("readerType").toString())).getReaderTypeName());
	  holder.tv_readerName.setText("姓名：" + mData.get(position).get("readerName").toString());
	  holder.iv_photo.setImageResource(R.drawable.default_photo);
	  ImageLoadListener photoLoadListener = new ImageLoadListener(mListView,R.id.iv_photo);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("photo"),photoLoadListener);  
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_readerNo;
    	TextView tv_readerType;
    	TextView tv_readerName;
    	ImageView iv_photo;
    }
} 
