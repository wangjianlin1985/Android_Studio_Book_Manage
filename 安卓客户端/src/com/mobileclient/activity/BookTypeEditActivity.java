package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.BookType;
import com.mobileclient.service.BookTypeService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class BookTypeEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明图书类别TextView
	private TextView TV_bookTypeId;
	// 声明类别名称输入框
	private EditText ET_bookTypeName;
	// 声明可借阅天数输入框
	private EditText ET_days;
	protected String carmera_path;
	/*要保存的图书类型信息*/
	BookType bookType = new BookType();
	/*图书类型管理业务逻辑层*/
	private BookTypeService bookTypeService = new BookTypeService();

	private int bookTypeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.booktype_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑图书类型信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_bookTypeId = (TextView) findViewById(R.id.TV_bookTypeId);
		ET_bookTypeName = (EditText) findViewById(R.id.ET_bookTypeName);
		ET_days = (EditText) findViewById(R.id.ET_days);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		bookTypeId = extras.getInt("bookTypeId");
		/*单击修改图书类型按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取类别名称*/ 
					if(ET_bookTypeName.getText().toString().equals("")) {
						Toast.makeText(BookTypeEditActivity.this, "类别名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_bookTypeName.setFocusable(true);
						ET_bookTypeName.requestFocus();
						return;	
					}
					bookType.setBookTypeName(ET_bookTypeName.getText().toString());
					/*验证获取可借阅天数*/ 
					if(ET_days.getText().toString().equals("")) {
						Toast.makeText(BookTypeEditActivity.this, "可借阅天数输入不能为空!", Toast.LENGTH_LONG).show();
						ET_days.setFocusable(true);
						ET_days.requestFocus();
						return;	
					}
					bookType.setDays(Integer.parseInt(ET_days.getText().toString()));
					/*调用业务逻辑层上传图书类型信息*/
					BookTypeEditActivity.this.setTitle("正在更新图书类型信息，稍等...");
					String result = bookTypeService.UpdateBookType(bookType);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    bookType = bookTypeService.GetBookType(bookTypeId);
		this.TV_bookTypeId.setText(bookTypeId+"");
		this.ET_bookTypeName.setText(bookType.getBookTypeName());
		this.ET_days.setText(bookType.getDays() + "");
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
