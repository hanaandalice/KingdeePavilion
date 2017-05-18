package com.xilada.xldutils.activitys;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.xilada.xldutils.BuildConfig;
import com.xilada.xldutils.R;
import com.xilada.xldutils.utils.DialogUtils;
import com.xilada.xldutils.utils.PermissionManager;
import com.xilada.xldutils.utils.Toast;
import com.xilada.xldutils.utils.Utils;
import com.xilada.xldutils.xldUtils;

import java.io.File;
import java.util.Locale;

/**
 * 选择拍照或相册等dialog
 * @author sinata
 *
 */
public class SelectPhotoDialog extends DialogActivity implements OnClickListener{

	private final int CODE_PERMISSION = 10;
	public static final String DATA = "path";
	private File tempFile;//临时文件
	//	private String fileName;//图片文件名称
	@Override
	protected int exitAnim() {
		return 0;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.take_photo_popupwindow);
		getWindow().setGravity(Gravity.BOTTOM);
		getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		PermissionManager.request(this, Manifest.permission.WRITE_EXTERNAL_STORAGE,"访问本地存储",CODE_PERMISSION);
		if (!TextUtils.isEmpty(getIntent().getStringExtra("cramre"))) {
			goCramre();
		}
		initView();
	}
	//初始化视图
	private void initView() {
		Button btnTakePhoto = (Button) findViewById(android.R.id.button1);
		Button btnSelectPhoto = (Button) findViewById(android.R.id.button2);
		Button btnCancel = (Button) findViewById(android.R.id.button3);
		btnTakePhoto.setOnClickListener(this);
		btnSelectPhoto.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode==RESULT_OK) {
			switch (requestCode) {
				case 0:
					if (tempFile!=null && tempFile.exists()) {
						Intent intent=new Intent();
						intent.putExtra(DATA, tempFile.getAbsolutePath());
						setResult(RESULT_OK,intent);
						finish();
						overridePendingTransition(0, 0);
					}
					break;

				case 1:
					if (data!=null) {
						Uri uri = data.getData();
						if (uri != null) {
							String path = Utils.getUrlPath(this, uri);
							if (path != null) {
								int typeIndex = path.lastIndexOf(".");
								if (typeIndex != -1) {
									String fileType = path.substring(typeIndex + 1).toLowerCase(Locale.CHINA);
									//某些设备选择图片是可以选择一些非图片的文件。然后发送出去或出错。这里简单的通过匹配后缀名来判断是否是图片文件
									//如果是图片文件则发送。反之给出提示
									if (fileType.equals("jpg") || fileType.equals("gif")
											|| fileType.equals("png") || fileType.equals("jpeg")
											|| fileType.equals("bmp") || fileType.equals("wbmp")
											|| fileType.equals("ico") || fileType.equals("jpe")) {
										Intent intent=new Intent();
										intent.putExtra(DATA, path);
										setResult(RESULT_OK,intent);
										finish();
										overridePendingTransition(0, 0);
//			                        	cropImage(path);
//			                        	BitmapUtil.getInstance(this).loadImage(iv_image, path);
									}else {
										Toast.create(this).show("无法识别的图片类型！");
									}
								}else {
									Toast.create(this).show("无法识别的图片类型！");
								}
							}else {
								Toast.create(this).show("无法识别的图片类型或路径！");
							}
						}else {
							Toast.create(this).show("无法识别的图片类型！");
						}
					}
					break;
			}
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == CODE_PERMISSION){
			if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
					&&grantResults[0] == PackageManager.PERMISSION_GRANTED){
				//用户同意使用write
			}else{
				//用户不同意，向用户展示该权限作用
				if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
					DialogUtils.createNoticeDialog(this, "请注意", "本应用需要使用访问本地存储权限，否则无法正常使用！", "确定", "取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					}, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});

					return;
				}
				finish();
			}
		}
	}

	private static final String TAG = "SelectPhotoDialog";
	final private int REQUEST_CODE_ASK_PERMISSIONS = 124;//
	@TargetApi(Build.VERSION_CODES.M)
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			//拍照
			case android.R.id.button1: {
				//检测路径是否存在，不存在就创建
//			File file=new File(xldUtils.PICDIR);
//			if (!file.exists()) {
//				file.mkdirs();
//			}
//			file=null;
				goCramre();
			}
			break;
			// 相册选取
			case android.R.id.button2: {

				Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// 调用android的图库
				intent.setType("image/*");
				startActivityForResult(intent, 1);
			}
			break;
			//按钮以外区域
//		case R.id.view_outside:
			// 取消
			case android.R.id.button3:
				//直接调用返回键事件
				onBackPressed();
				break;
		}
	}
	private void goCramre() {
		if (Integer.parseInt(Build.VERSION.SDK)>=23  ) {
			int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.CAMERA);
			if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
				// 弹窗询问 ，让用户自己判断
				requestPermissions(new String[] {Manifest.permission.CAMERA},REQUEST_CODE_ASK_PERMISSIONS);
				return;
			}
		}
		xldUtils.initFilePath();
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		String fileName = System.currentTimeMillis() + ".jpg";
		tempFile = new File(xldUtils.PICDIR, fileName);
		Uri u;
		if (Build.VERSION.SDK_INT >=24){
			u =FileProvider.getUriForFile(this, "com.zipingfang.greenhouse.fileprovider", tempFile);
		}else{
			u= Uri.fromFile(tempFile);
		}
		intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
		startActivityForResult(intent, 0);
	}

	@Override
	public void onBackPressed() {
		if (!TextUtils.isEmpty(getIntent().getStringExtra("cramre"))) {
			finish();
		}else{
			super.onBackPressed();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}
}
