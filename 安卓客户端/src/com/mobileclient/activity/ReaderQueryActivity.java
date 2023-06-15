package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Reader;
import com.mobileclient.domain.ReaderType;
import com.mobileclient.service.ReaderTypeService;

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
public class ReaderQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明读者编号输入框
	private EditText ET_readerNo;
	// 声明读者类型下拉框
	private Spinner spinner_readerType;
	private ArrayAdapter<String> readerType_adapter;
	private static  String[] readerType_ShowText  = null;
	private List<ReaderType> readerTypeList = null; 
	/*读者类型管理业务逻辑层*/
	private ReaderTypeService readerTypeService = new ReaderTypeService();
	// 声明姓名输入框
	private EditText ET_readerName;
	// 读者生日控件
	private DatePicker dp_birthday;
	private CheckBox cb_birthday;
	// 声明联系电话输入框
	private EditText ET_telephone;
	/*查询过滤条件保存到这个对象中*/
	private Reader queryConditionReader = new Reader();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.reader_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置读者查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_readerNo = (EditText) findViewById(R.id.ET_readerNo);
		spinner_readerType = (Spinner) findViewById(R.id.Spinner_readerType);
		// 获取所有的读者类型
		try {
			readerTypeList = readerTypeService.QueryReaderType(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int readerTypeCount = readerTypeList.size();
		readerType_ShowText = new String[readerTypeCount+1];
		readerType_ShowText[0] = "不限制";
		for(int i=1;i<=readerTypeCount;i++) { 
			readerType_ShowText[i] = readerTypeList.get(i-1).getReaderTypeName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		readerType_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, readerType_ShowText);
		// 设置读者类型下拉列表的风格
		readerType_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_readerType.setAdapter(readerType_adapter);
		// 添加事件Spinner事件监听
		spinner_readerType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionReader.setReaderType(readerTypeList.get(arg2-1).getReaderTypeId()); 
				else
					queryConditionReader.setReaderType(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_readerType.setVisibility(View.VISIBLE);
		ET_readerName = (EditText) findViewById(R.id.ET_readerName);
		dp_birthday = (DatePicker) findViewById(R.id.dp_birthday);
		cb_birthday = (CheckBox) findViewById(R.id.cb_birthday);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionReader.setReaderNo(ET_readerNo.getText().toString());
					queryConditionReader.setReaderName(ET_readerName.getText().toString());
					if(cb_birthday.isChecked()) {
						/*获取读者生日*/
						Date birthday = new Date(dp_birthday.getYear()-1900,dp_birthday.getMonth(),dp_birthday.getDayOfMonth());
						queryConditionReader.setBirthday(new Timestamp(birthday.getTime()));
					} else {
						queryConditionReader.setBirthday(null);
					} 
					queryConditionReader.setTelephone(ET_telephone.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionReader", queryConditionReader);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
