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
	// �������ذ�ť
	private Button btnReturn;
	// �����������ͱ�ſؼ�
	private TextView TV_readerTypeId;
	// �����������Ϳؼ�
	private TextView TV_readerTypeName;
	// �����ɽ�����Ŀ�ؼ�
	private TextView TV_number;
	/* Ҫ����Ķ���������Ϣ */
	ReaderType readerType = new ReaderType(); 
	/* �������͹���ҵ���߼��� */
	private ReaderTypeService readerTypeService = new ReaderTypeService();
	private int readerTypeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.readertype_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴������������");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
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
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    readerType = readerTypeService.GetReaderType(readerTypeId); 
		this.TV_readerTypeId.setText(readerType.getReaderTypeId() + "");
		this.TV_readerTypeName.setText(readerType.getReaderTypeName());
		this.TV_number.setText(readerType.getNumber() + "");
	} 
}
