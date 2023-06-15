package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Book;
import com.mobileclient.domain.BookType;
import com.mobileclient.service.BookTypeService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class BookQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明图书条形码输入框
	private EditText ET_barcode;
	// 声明图书类别下拉框
	private Spinner spinner_bookType;
	private ArrayAdapter<String> bookType_adapter;
	private static  String[] bookType_ShowText  = null;
	private List<BookType> bookTypeList = null; 
	/*图书类型管理业务逻辑层*/
	private BookTypeService bookTypeService = new BookTypeService();
	// 声明图书名称输入框
	private EditText ET_bookName;
	// 出版日期控件
	private DatePicker dp_publishDate;
	private CheckBox cb_publishDate;
	/*查询过滤条件保存到这个对象中*/
	private Book queryConditionBook = new Book();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.book_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置图书查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_barcode = (EditText) findViewById(R.id.ET_barcode);
		spinner_bookType = (Spinner) findViewById(R.id.Spinner_bookType);
		// 获取所有的图书类型
		try {
			bookTypeList = bookTypeService.QueryBookType(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int bookTypeCount = bookTypeList.size();
		bookType_ShowText = new String[bookTypeCount+1];
		bookType_ShowText[0] = "不限制";
		for(int i=1;i<=bookTypeCount;i++) { 
			bookType_ShowText[i] = bookTypeList.get(i-1).getBookTypeName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		bookType_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, bookType_ShowText);
		// 设置图书类别下拉列表的风格
		bookType_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_bookType.setAdapter(bookType_adapter);
		// 添加事件Spinner事件监听
		spinner_bookType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionBook.setBookType(bookTypeList.get(arg2-1).getBookTypeId()); 
				else
					queryConditionBook.setBookType(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_bookType.setVisibility(View.VISIBLE);
		ET_bookName = (EditText) findViewById(R.id.ET_bookName);
		dp_publishDate = (DatePicker) findViewById(R.id.dp_publishDate);
		cb_publishDate = (CheckBox) findViewById(R.id.cb_publishDate);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionBook.setBarcode(ET_barcode.getText().toString());
					queryConditionBook.setBookName(ET_bookName.getText().toString());
					if(cb_publishDate.isChecked()) {
						/*获取出版日期*/
						Date publishDate = new Date(dp_publishDate.getYear()-1900,dp_publishDate.getMonth(),dp_publishDate.getDayOfMonth());
						queryConditionBook.setPublishDate(new Timestamp(publishDate.getTime()));
					} else {
						queryConditionBook.setPublishDate(null);
					} 
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionBook", queryConditionBook);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
