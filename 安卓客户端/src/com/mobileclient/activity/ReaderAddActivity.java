package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class ReaderAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// �������߱�������
	private EditText ET_readerNo;
	// ������¼���������
	private EditText ET_password;
	// ������������������
	private Spinner spinner_readerType;
	private ArrayAdapter<String> readerType_adapter;
	private static  String[] readerType_ShowText  = null;
	private List<ReaderType> readerTypeList = null;
	/*�������͹���ҵ���߼���*/
	private ReaderTypeService readerTypeService = new ReaderTypeService();
	// �������������
	private EditText ET_readerName;
	// �����Ա������
	private EditText ET_sex;
	// ��������ͷ��ͼƬ��ؼ�
	private ImageView iv_photo;
	private Button btn_photo;
	protected int REQ_CODE_SELECT_IMAGE_photo = 1;
	private int REQ_CODE_CAMERA_photo = 2;
	// ����������տؼ�
	private DatePicker dp_birthday;
	// ������ϵ�绰�����
	private EditText ET_telephone;
	// ������ϵEmail�����
	private EditText ET_email;
	// ������ϵqq�����
	private EditText ET_qq;
	// �������ߵ�ַ�����
	private EditText ET_address;
	protected String carmera_path;
	/*Ҫ����Ķ�����Ϣ*/
	Reader reader = new Reader();
	/*���߹���ҵ���߼���*/
	private ReaderService readerService = new ReaderService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.reader_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("��Ӷ���");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_readerNo = (EditText) findViewById(R.id.ET_readerNo);
		ET_password = (EditText) findViewById(R.id.ET_password);
		spinner_readerType = (Spinner) findViewById(R.id.Spinner_readerType);
		// ��ȡ���еĶ�������
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
		// ����ѡ������ArrayAdapter��������
		readerType_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, readerType_ShowText);
		// ���������б�ķ��
		readerType_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_readerType.setAdapter(readerType_adapter);
		// ����¼�Spinner�¼�����
		spinner_readerType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				reader.setReaderType(readerTypeList.get(arg2).getReaderTypeId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_readerType.setVisibility(View.VISIBLE);
		ET_readerName = (EditText) findViewById(R.id.ET_readerName);
		ET_sex = (EditText) findViewById(R.id.ET_sex);
		iv_photo = (ImageView) findViewById(R.id.iv_photo);
		/*����ͼƬ��ʾ�ؼ�ʱ����ͼƬ��ѡ��*/
		iv_photo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(ReaderAddActivity.this,photoListActivity.class);
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
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*������Ӷ��߰�ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ���߱��*/
					if(ET_readerNo.getText().toString().equals("")) {
						Toast.makeText(ReaderAddActivity.this, "���߱�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_readerNo.setFocusable(true);
						ET_readerNo.requestFocus();
						return;
					}
					reader.setReaderNo(ET_readerNo.getText().toString());
					/*��֤��ȡ��¼����*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(ReaderAddActivity.this, "��¼�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					reader.setPassword(ET_password.getText().toString());
					/*��֤��ȡ����*/ 
					if(ET_readerName.getText().toString().equals("")) {
						Toast.makeText(ReaderAddActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_readerName.setFocusable(true);
						ET_readerName.requestFocus();
						return;	
					}
					reader.setReaderName(ET_readerName.getText().toString());
					/*��֤��ȡ�Ա�*/ 
					if(ET_sex.getText().toString().equals("")) {
						Toast.makeText(ReaderAddActivity.this, "�Ա����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_sex.setFocusable(true);
						ET_sex.requestFocus();
						return;	
					}
					reader.setSex(ET_sex.getText().toString());
					if(reader.getPhoto() != null) {
						//���ͼƬ��ַ��Ϊ�գ�˵���û�ѡ����ͼƬ����ʱ��Ҫ���ӷ������ϴ�ͼƬ
						ReaderAddActivity.this.setTitle("�����ϴ�ͼƬ���Ե�...");
						String photo = HttpUtil.uploadFile(reader.getPhoto());
						ReaderAddActivity.this.setTitle("ͼƬ�ϴ���ϣ�");
						reader.setPhoto(photo);
					} else {
						reader.setPhoto("upload/noimage.jpg");
					}
					/*��ȡ��������*/
					Date birthday = new Date(dp_birthday.getYear()-1900,dp_birthday.getMonth(),dp_birthday.getDayOfMonth());
					reader.setBirthday(new Timestamp(birthday.getTime()));
					/*��֤��ȡ��ϵ�绰*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(ReaderAddActivity.this, "��ϵ�绰���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					reader.setTelephone(ET_telephone.getText().toString());
					/*��֤��ȡ��ϵEmail*/ 
					if(ET_email.getText().toString().equals("")) {
						Toast.makeText(ReaderAddActivity.this, "��ϵEmail���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_email.setFocusable(true);
						ET_email.requestFocus();
						return;	
					}
					reader.setEmail(ET_email.getText().toString());
					/*��֤��ȡ��ϵqq*/ 
					if(ET_qq.getText().toString().equals("")) {
						Toast.makeText(ReaderAddActivity.this, "��ϵqq���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_qq.setFocusable(true);
						ET_qq.requestFocus();
						return;	
					}
					reader.setQq(ET_qq.getText().toString());
					/*��֤��ȡ���ߵ�ַ*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(ReaderAddActivity.this, "���ߵ�ַ���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					reader.setAddress(ET_address.getText().toString());
					/*����ҵ���߼����ϴ�������Ϣ*/
					ReaderAddActivity.this.setTitle("�����ϴ�������Ϣ���Ե�...");
					String result = readerService.AddReader(reader);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
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
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// ������д���ļ� 
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
