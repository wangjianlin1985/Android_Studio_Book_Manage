package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Reader;
import com.mobileclient.service.ReaderService;
import com.mobileclient.domain.ReaderType;
import com.mobileclient.service.ReaderTypeService;
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

public class ReaderSelfEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明读者编号TextView
	private TextView TV_readerNo;
	// 声明登录密码输入框
	private EditText ET_password;
	// 声明读者类型下拉框
	private Spinner spinner_readerType;
	private ArrayAdapter<String> readerType_adapter;
	private static  String[] readerType_ShowText  = null;
	private List<ReaderType> readerTypeList = null;
	/*读者类型管理业务逻辑层*/
	private ReaderTypeService readerTypeService = new ReaderTypeService();
	// 声明姓名输入框
	private EditText ET_readerName;
	// 声明性别输入框
	private EditText ET_sex;
	// 声明读者头像图片框控件
	private ImageView iv_photo;
	private Button btn_photo;
	protected int REQ_CODE_SELECT_IMAGE_photo = 1;
	private int REQ_CODE_CAMERA_photo = 2;
	// 出版读者生日控件
	private DatePicker dp_birthday;
	// 声明联系电话输入框
	private EditText ET_telephone;
	// 声明联系Email输入框
	private EditText ET_email;
	// 声明联系qq输入框
	private EditText ET_qq;
	// 声明读者地址输入框
	private EditText ET_address;
	protected String carmera_path;
	/*要保存的读者信息*/
	Reader reader = new Reader();
	/*读者管理业务逻辑层*/
	private ReaderService readerService = new ReaderService();

	private String readerNo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.reader_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("修改个人信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_readerNo = (TextView) findViewById(R.id.TV_readerNo);
		ET_password = (EditText) findViewById(R.id.ET_password);
		spinner_readerType = (Spinner) findViewById(R.id.Spinner_readerType);
		// 获取所有的读者类型
		try {
			readerTypeList = readerTypeService.QueryReaderType(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int readerTypeCount = readerTypeList.size();
		readerType_ShowText = new String[readerTypeCount];
		for(int i=0;i<readerTypeCount;i++) { 
			readerType_ShowText[i] = readerTypeList.get(i).getReaderTypeName();
		}
		// 将可选内容与ArrayAdapter连接起来
		readerType_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, readerType_ShowText);
		// 设置图书类别下拉列表的风格
		readerType_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_readerType.setAdapter(readerType_adapter);
		// 添加事件Spinner事件监听
		spinner_readerType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				reader.setReaderType(readerTypeList.get(arg2).getReaderTypeId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_readerType.setVisibility(View.VISIBLE);
		ET_readerName = (EditText) findViewById(R.id.ET_readerName);
		ET_sex = (EditText) findViewById(R.id.ET_sex);
		iv_photo = (ImageView) findViewById(R.id.iv_photo);
		/*单击图片显示控件时进行图片的选择*/
		iv_photo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(ReaderSelfEditActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_photo);
			}
		});
		btn_photo = (Button) findViewById(R.id.btn_photo);
		btn_photo.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_photo.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_photo);  
			}
		});
		dp_birthday = (DatePicker)this.findViewById(R.id.dp_birthday);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_email = (EditText) findViewById(R.id.ET_email);
		ET_qq = (EditText) findViewById(R.id.ET_qq);
		ET_address = (EditText) findViewById(R.id.ET_address);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		readerNo = extras.getString("readerNo");
		/*单击修改读者按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取登录密码*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(ReaderSelfEditActivity.this, "登录密码输入不能为空!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					reader.setPassword(ET_password.getText().toString());
					/*验证获取姓名*/ 
					if(ET_readerName.getText().toString().equals("")) {
						Toast.makeText(ReaderSelfEditActivity.this, "姓名输入不能为空!", Toast.LENGTH_LONG).show();
						ET_readerName.setFocusable(true);
						ET_readerName.requestFocus();
						return;	
					}
					reader.setReaderName(ET_readerName.getText().toString());
					/*验证获取性别*/ 
					if(ET_sex.getText().toString().equals("")) {
						Toast.makeText(ReaderSelfEditActivity.this, "性别输入不能为空!", Toast.LENGTH_LONG).show();
						ET_sex.setFocusable(true);
						ET_sex.requestFocus();
						return;	
					}
					reader.setSex(ET_sex.getText().toString());
					if (!reader.getPhoto().startsWith("upload/")) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						ReaderSelfEditActivity.this.setTitle("正在上传图片，稍等...");
						String photo = HttpUtil.uploadFile(reader.getPhoto());
						ReaderSelfEditActivity.this.setTitle("图片上传完毕！");
						reader.setPhoto(photo);
					} 
					/*获取出版日期*/
					Date birthday = new Date(dp_birthday.getYear()-1900,dp_birthday.getMonth(),dp_birthday.getDayOfMonth());
					reader.setBirthday(new Timestamp(birthday.getTime()));
					/*验证获取联系电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(ReaderSelfEditActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					reader.setTelephone(ET_telephone.getText().toString());
					/*验证获取联系Email*/ 
					if(ET_email.getText().toString().equals("")) {
						Toast.makeText(ReaderSelfEditActivity.this, "联系Email输入不能为空!", Toast.LENGTH_LONG).show();
						ET_email.setFocusable(true);
						ET_email.requestFocus();
						return;	
					}
					reader.setEmail(ET_email.getText().toString());
					/*验证获取联系qq*/ 
					if(ET_qq.getText().toString().equals("")) {
						Toast.makeText(ReaderSelfEditActivity.this, "联系qq输入不能为空!", Toast.LENGTH_LONG).show();
						ET_qq.setFocusable(true);
						ET_qq.requestFocus();
						return;	
					}
					reader.setQq(ET_qq.getText().toString());
					/*验证获取读者地址*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(ReaderSelfEditActivity.this, "读者地址输入不能为空!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					reader.setAddress(ET_address.getText().toString());
					/*调用业务逻辑层上传读者信息*/
					ReaderSelfEditActivity.this.setTitle("正在更新读者信息，稍等...");
					String result = readerService.UpdateReader(reader);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					 
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    reader = readerService.GetReader(readerNo);
		this.TV_readerNo.setText(readerNo);
		this.ET_password.setText(reader.getPassword());
		for (int i = 0; i < readerTypeList.size(); i++) {
			if (reader.getReaderType() == readerTypeList.get(i).getReaderTypeId()) {
				this.spinner_readerType.setSelection(i);
				break;
			}
		}
		this.ET_readerName.setText(reader.getReaderName());
		this.ET_sex.setText(reader.getSex());
		byte[] photo_data = null;
		try {
			// 获取图片数据
			photo_data = ImageService.getImage(HttpUtil.BASE_URL + reader.getPhoto());
			Bitmap photo = BitmapFactory.decodeByteArray(photo_data, 0, photo_data.length);
			this.iv_photo.setImageBitmap(photo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Date birthday = new Date(reader.getBirthday().getTime());
		this.dp_birthday.init(birthday.getYear() + 1900,birthday.getMonth(), birthday.getDate(), null);
		this.ET_telephone.setText(reader.getTelephone());
		this.ET_email.setText(reader.getEmail());
		this.ET_qq.setText(reader.getQq());
		this.ET_address.setText(reader.getAddress());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_CAMERA_photo  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_photo.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_photo.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_photo.setImageBitmap(booImageBm);
				this.iv_photo.setScaleType(ScaleType.FIT_CENTER);
				this.reader.setPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_photo && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(filepath, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
			opts.inJustDecodeBounds = false; 
			try { 
				Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
				this.iv_photo.setImageBitmap(bm); 
				this.iv_photo.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			reader.setPhoto(filename); 
		}
	}
}
