package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.ReaderType;
import com.mobileclient.service.ReaderTypeService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class ReaderTypeDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明读者类型编号控件
	private TextView TV_readerTypeId;
	// 声明读者类型控件
	private TextView TV_readerTypeName;
	// 声明可借阅数目控件
	private TextView TV_number;
	/* 要保存的读者类型信息 */
	ReaderType readerType = new ReaderType(); 
	/* 读者类型管理业务逻辑层 */
	private ReaderTypeService readerTypeService = new ReaderTypeService();
	private int readerTypeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.readertype_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看读者类型详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_readerTypeId = (TextView) findViewById(R.id.TV_readerTypeId);
		TV_readerTypeName = (TextView) findViewById(R.id.TV_readerTypeName);
		TV_number = (TextView) findViewById(R.id.TV_number);
		Bundle extras = this.getIntent().getExtras();
		readerTypeId = extras.getInt("readerTypeId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ReaderTypeDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    readerType = readerTypeService.GetReaderType(readerTypeId); 
		this.TV_readerTypeId.setText(readerType.getReaderTypeId() + "");
		this.TV_readerTypeName.setText(readerType.getReaderTypeName());
		this.TV_number.setText(readerType.getNumber() + "");
	} 
}
