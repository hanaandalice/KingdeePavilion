package com.xilada.xldutils.tool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.UUID;

/**
 * Created by zhy on 15/11/6.
 */
public class ImageUtils
{
    /**
     * 根据InputStream获取图片实际的宽度和高度
     *
     * @param imageStream
     * @return
     */
    public static ImageSize getImageSize(InputStream imageStream)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(imageStream, null, options);
        return new ImageSize(options.outWidth, options.outHeight);
    }

    public static class ImageSize
    {
        int width;
        int height;

        public ImageSize()
        {
        }

        public ImageSize(int width, int height)
        {
            this.width = width;
            this.height = height;
        }

        @Override
        public String toString()
        {
            return "ImageSize{" +
                    "width=" + width +
                    ", height=" + height +
                    '}';
        }
    }

    public static int calculateInSampleSize(ImageSize srcSize, ImageSize targetSize)
    {
        // 源图片的宽度
        int width = srcSize.width;
        int height = srcSize.height;
        int inSampleSize = 1;

        int reqWidth = targetSize.width;
        int reqHeight = targetSize.height;

        if (width > reqWidth && height > reqHeight)
        {
            // 计算出实际宽度和目标宽度的比率
            int widthRatio = Math.round((float) width / (float) reqWidth);
            int heightRatio = Math.round((float) height / (float) reqHeight);
            inSampleSize = Math.max(widthRatio, heightRatio);
        }
        return inSampleSize;
    }

    /**
     * 根据ImageView获适当的压缩的宽和高
     *
     * @param view
     * @return
     */
    public static ImageSize getImageViewSize(View view)
    {

        ImageSize imageSize = new ImageSize();

        imageSize.width = getExpectWidth(view);
        imageSize.height = getExpectHeight(view);

        return imageSize;
    }

    /**
     * 根据view获得期望的高度
     *
     * @param view
     * @return
     */
    private static int getExpectHeight(View view)
    {

        int height = 0;
        if (view == null) return 0;

        final ViewGroup.LayoutParams params = view.getLayoutParams();
        //如果是WRAP_CONTENT，此时图片还没加载，getWidth根本无效
        if (params != null && params.height != ViewGroup.LayoutParams.WRAP_CONTENT)
        {
            height = view.getWidth(); // 获得实际的宽度
        }
        if (height <= 0 && params != null)
        {
            height = params.height; // 获得布局文件中的声明的宽度
        }

        if (height <= 0)
        {
            height = getImageViewFieldValue(view, "mMaxHeight");// 获得设置的最大的宽度
        }

        //如果宽度还是没有获取到，憋大招，使用屏幕的宽度
        if (height <= 0)
        {
            DisplayMetrics displayMetrics = view.getContext().getResources()
                    .getDisplayMetrics();
            height = displayMetrics.heightPixels;
        }

        return height;
    }

    /**
     * 根据view获得期望的宽度
     *
     * @param view
     * @return
     */
    private static int getExpectWidth(View view)
    {
        int width = 0;
        if (view == null) return 0;

        final ViewGroup.LayoutParams params = view.getLayoutParams();
        //如果是WRAP_CONTENT，此时图片还没加载，getWidth根本无效
        if (params != null && params.width != ViewGroup.LayoutParams.WRAP_CONTENT)
        {
            width = view.getWidth(); // 获得实际的宽度
        }
        if (width <= 0 && params != null)
        {
            width = params.width; // 获得布局文件中的声明的宽度
        }

        if (width <= 0)

        {
            width = getImageViewFieldValue(view, "mMaxWidth");// 获得设置的最大的宽度
        }
        //如果宽度还是没有获取到，憋大招，使用屏幕的宽度
        if (width <= 0)

        {
            DisplayMetrics displayMetrics = view.getContext().getResources()
                    .getDisplayMetrics();
            width = displayMetrics.widthPixels;
        }

        return width;
    }

    /**
     * 通过反射获取imageview的某个属性值
     *
     * @param object
     * @param fieldName
     * @return
     */
    private static int getImageViewFieldValue(Object object, String fieldName)
    {
        int value = 0;
        try
        {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = field.getInt(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE)
            {
                value = fieldValue;
            }
        } catch (Exception e){

        }
        return value;
    }
    /**
     * 根据宽度等比例缩放图片
     * @param defaultBitmap
     * @param targetWidth
     * @return
     */
    public static Bitmap resizeImageByWidth(Bitmap defaultBitmap,int targetWidth) {
        int rawWidth = defaultBitmap.getWidth();
        int rawHeight = defaultBitmap.getHeight();
        float targetHeight = targetWidth * (float) rawHeight / (float) rawWidth;
        float scaleWidth = targetWidth / (float) rawWidth;
        float scaleHeight = targetHeight / (float) rawHeight;
        Matrix localMatrix = new Matrix();
        localMatrix.postScale(scaleHeight, scaleWidth);
        return Bitmap.createBitmap(defaultBitmap, 0, 0, rawWidth, rawHeight,localMatrix, true);
    }
    /**
     * 压缩图片--100k以内
     * @param path
     * @return
     * @throws IOException
     */
    public static Bitmap revitionImageSize(String path) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        Bitmap resizeBitmap = null;
        while (true) {
            if ((options.outWidth >> i <= 600)
                    && (options.outHeight >> i <= 600)) {
                in = new BufferedInputStream(
                        new FileInputStream(new File(path)));
                //修改参数
                options.inSampleSize = (int) Math.pow(1.5D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);

                break;
            }
            i += 1;
        }

//        int bitmapWidth = bitmap.getWidth();
//        int bitmapHeight = bitmap.getHeight();
//        // 缩放图片的尺寸
//        float scaleWidth = (float) width / bitmapWidth;
//        float scaleHeight = (float) width / bitmapHeight;
//        Matrix matrix = new Matrix();
//        matrix.postScale(scaleWidth, scaleHeight);
//        // 产生缩放后的Bitmap对象
//        resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, false);
        return bitmap;
    }
    public static String saveImage(Bitmap bmp) throws Exception {
        //图片地址文件夹
        String sdcardPathDir = android.os.Environment.getExternalStorageDirectory().getPath() + "/mmhdc/photo/feedback/";
        // 获取系统时间 然后将图片保存至指定的文件夹
        String  address= UUID.randomUUID().toString();
        String pathiamge=sdcardPathDir+address+".jpg";//图片地址
        String state = android.os.Environment.getExternalStorageState();
        if (!state.equals(android.os.Environment.MEDIA_MOUNTED))
        {
            Log.i("image_tag", "SD Card is not mounted,It is  " + state + ".");
        }
        File directory = new File(pathiamge).getParentFile();
        if (!directory.exists() && !directory.mkdirs()) {
            Log.i("image_tag", "Path to file could not be created");
        }
        File file = new File(pathiamge);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fos = new FileOutputStream(file);
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        fos.flush();
        fos.close();
        return pathiamge;
    }
}
