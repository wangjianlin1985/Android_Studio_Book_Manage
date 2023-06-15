package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Book;
import com.mobileclient.service.BookService;
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
public class BookDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// ����ͼ��������ؼ�
	private TextView TV_barcode;
	// ����ͼ�����ؼ�
	private TextView TV_bookType;
	// ����ͼ�����ƿؼ�
	private TextView TV_bookName;
	// ����ͼ��ͼƬͼƬ��
	private ImageView iv_bookPhoto;
	// ����ͼ��۸�ؼ�
	private TextView TV_price;
	// �������ؼ�
	private TextView TV_count;
	// �����������ڿؼ�
	private TextView TV_publishDate;
	// ����������ؼ�
	private TextView TV_publish;
	// ����ͼ����ؼ�
	private TextView TV_introduction;
	/* Ҫ�����ͼ����Ϣ */
	Book book = new Book(); 
	/* ͼ�����ҵ���߼��� */
	private BookService bookService = new BookService();
	private BookTypeService bookTypeService = new BookTypeService();
	private String barcode;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.book_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴ͼ������");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_barcode = (TextView) findViewById(R.id.TV_barcode);
		TV_bookType = (TextView) findViewById(R.id.TV_bookType);
		TV_bookName = (TextView) findViewById(R.id.TV_bookName);
		iv_bookPhoto = (ImageView) findViewById(R.id.iv_bookPhoto); 
		TV_price = (TextView) findViewById(R.id.TV_price);
		TV_count = (TextView) findViewById(R.id.TV_count);
		TV_publishDate = (TextView) findViewById(R.id.TV_publishDate);
		TV_publish = (TextView) findViewById(R.id.TV_publish);
		TV_introduction = (TextView) findViewById(R.id.TV_introduction);
		Bundle extras = this.getIntent().getExtras();
		barcode = extras.getString("barcode");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BookDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    book = bookService.GetBook(barcode); 
		this.TV_barcode.setText(book.getBarcode());
		BookType bookType = bookTypeService.GetBookType(book.getBookType());
		this.TV_bookType.setText(bookType.getBookTypeName());
		this.TV_bookName.setText(book.getBookName());
		byte[] bookPhoto_data = null;
		try {
			// ��ȡͼƬ����
			bookPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + book.getBookPhoto());
			Bitmap bookPhoto = BitmapFactory.decodeByteArray(bookPhoto_data, 0,bookPhoto_data.length);
			this.iv_bookPhoto.setImageBitmap(bookPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_price.setText(book.getPrice() + "");
		this.TV_count.setText(book.getCount() + "");
		Date publishDate = new Date(book.getPublishDate().getTime());
		String publishDateStr = (publishDate.getYear() + 1900) + "-" + (publishDate.getMonth()+1) + "-" + publishDate.getDate();
		this.TV_publishDate.setText(publishDateStr);
		this.TV_publish.setText(book.getPublish());
		this.TV_introduction.setText(book.getIntroduction());
	} 
}
