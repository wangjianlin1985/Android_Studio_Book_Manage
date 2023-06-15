package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.BookType;
import com.mobileclient.service.BookTypeService;
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
public class BookTypeDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// ����ͼ�����ؼ�
	private TextView TV_bookTypeId;
	// ����������ƿؼ�
	private TextView TV_bookTypeName;
	// �����ɽ��������ؼ�
	private TextView TV_days;
	/* Ҫ�����ͼ��������Ϣ */
	BookType bookType = new BookType(); 
	/* ͼ�����͹���ҵ���߼��� */
	private BookTypeService bookTypeService = new BookTypeService();
	private int bookTypeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.booktype_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴ͼ����������");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_bookTypeId = (TextView) findViewById(R.id.TV_bookTypeId);
		TV_bookTypeName = (TextView) findViewById(R.id.TV_bookTypeName);
		TV_days = (TextView) findViewById(R.id.TV_days);
		Bundle extras = this.getIntent().getExtras();
		bookTypeId = extras.getInt("bookTypeId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BookTypeDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    bookType = bookTypeService.GetBookType(bookTypeId); 
		this.TV_bookTypeId.setText(bookType.getBookTypeId() + "");
		this.TV_bookTypeName.setText(bookType.getBookTypeName());
		this.TV_days.setText(bookType.getDays() + "");
	} 
}
