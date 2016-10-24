package com.dsz.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.dsz.addmsg.R;
import com.dsz.bean.SelectItemBean;
import com.dsz.utils.ScalableImageView;

import java.util.List;

/**
 * the adpater for selecting base tools item.
 */
public class ModelListViewAdapter extends BaseAdapter {
    private static final String TAG = "Cloudy/SelectAdapter";

    private Context mContext = null;
    private List<SelectItemBean> mData = null;
    private Dialog dialog;


    ///
    private int screenWidth;
    private int screenHeight;
    private int bitmapWidth;
    private int bitmapHeight;
    private float baseScale;
    private float originalScale;
    //

    public ModelListViewAdapter(Context Context, List<SelectItemBean> Data) {
        mContext = Context;
        mData = Data;
        create();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(SelectItemBean itemBean) {
        mData.add(itemBean);
    }

    public void remove(SelectItemBean itemBean) {
        mData.remove(itemBean);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView: position: " + position);
        View view = null;
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.model_listview, null);
            viewHolder.iconView = (ImageView) view.findViewById(R.id.add_model_icon_imageView);
            viewHolder.actionView = (TextView) view.findViewById(R.id.add_model_txt_textview);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.iconView.setImageDrawable(((SelectItemBean) getItem(position)).getIcon());
        viewHolder.actionView.setText(((SelectItemBean) getItem(position)).getActionName());

        viewHolder.iconView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "dadada", Toast.LENGTH_SHORT).show();

                show();

            }
        });

        return view;
    }

    class ViewHolder {
//        public ImageView iconView;
        public ImageView iconView;
        public TextView actionView;
    }


    private void show() {
        dialog.show();
    }

    private void create() {
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.image_dialog);
        initImageView();
    }

    private void initImageView() {
        ScalableImageView image = (ScalableImageView) dialog.findViewById(R.id.image);
//        Display viewss = dialog.getWindow().getWindowManager().getDefaultDisplay();


        screenWidth =     1080;
        screenHeight =   1920;


        Log.d(TAG,screenWidth + "和");
        Log.d(TAG,screenHeight + "和");
        final Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.move_right_01);
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();


        // 计算缩放比，因为如果图片的尺寸超过屏幕，那么就会自动匹配到屏幕的尺寸去显示。
        // 那么，我们就不知道图片实际上在屏幕上显示的宽高，所以先计算需要全部显示的缩放比，
        // 在去计算图片显示时候的实际宽高，然后，才好进行下一步的缩放。
        // 要不然，会导致缩小和放大没效果，或者内存泄漏等等
        float scaleX = screenWidth / (float) bitmapWidth;
        float scaleY = screenHeight / (float) bitmapHeight;
//        float scaleX =  (float) (bitmapWidth*(screenWidth/bitmapWidth));
//        float scaleY =  (float)(bitmapWidth*(screenHeight/bitmapWidth));;
        baseScale = Math.min(scaleX, scaleY);// 获得缩放比例最大的那个缩放比，即scaleX和scaleY中小的那个
        originalScale = baseScale;

        final Matrix matrix = new Matrix();
        matrix.setScale(originalScale, originalScale);
        // 关于setScale和preScale和postScale的区别以后再说
        // matrix.preScale(originalScale, originalScale);
        // matrix.postScale(originalScale, originalScale);
//        Bitmap bitmap2 = Bitmap
//                .createBitmap(bitmap, 0, 0, screenWidth, screenHeight, matrix, false);
  Bitmap bitmap2 = Bitmap
                .createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, false);


//        image.setImageResource(R.drawable.a);
        image.setImageBitmap(bitmap2);
        image.setOnClickListener(new ScalableImageView.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
    }


}
