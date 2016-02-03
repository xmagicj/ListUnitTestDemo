package com.xmagicj.android.listunittestdemo;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ListViewAdapter extends BaseAdapter
 * 此Demo展示了一個Icon + title + content 的List
 * Adapter作为数据与容器的桥梁,核心方法为getView()
 *
 * @author Mumu
 */
public class ListViewAdapter extends BaseAdapter {
    /**
     * LayoutInflater
     */
    private LayoutInflater mInflater;
    /**
     * Icon1 For Display
     */
    private Bitmap mIcon1;
    /**
     * Icon2 For Display
     */
    private Bitmap mIcon2;
    /**
     * selectedIndex
     */
    private int selectedIndex;

    public ListViewAdapter(Context mContext) {
        // Cache the LayoutInflate to avoid asking for a new one each time.
        mInflater = LayoutInflater.from(mContext);

        // Icons bound to the rows.
        mIcon1 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.folder_naruto_swirl);
        mIcon2 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.application_ps);
    }

    @Override
    public int getCount() {
        return ListViewData.DATA.length;
    }

    @Override
    public Object getItem(int position) {
        return ListViewData.DATA[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Core Module:用于返回每个Item显示的View
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // A ViewHolder keeps references to children views to avoid unnecessary
        // calls to findViewById() on each row.
        ViewHolder holder;

        // When convertView is not null, we can reuse it directly, there is no
        // need to reinflate it. We only inflate a new View when the convertView
        // supplied by ListView is null.
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_item, null);

            // Creates a ViewHolder and store references to the two children
            // views we want to bind data to.
            holder = new ViewHolder();
            holder.mTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.mContent = (TextView) convertView.findViewById(R.id.tv_content);
            holder.mIcon = (ImageView) convertView.findViewById(R.id.iv_icon);

            convertView.setTag(holder);
        } else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (ViewHolder) convertView.getTag();
        }

        // Bind the data efficiently with the holder.
        holder.mContent.setText(ListViewData.DATA[position]);
        holder.mTitle.setText(ListViewData.DATA_TITLE[position]);
        holder.mIcon.setImageBitmap((position & 1) == 1 ? mIcon1 : mIcon2);
        if (getSelectedIndex() == position) {
            convertView.setSelected(true);
            convertView.setPressed(true);
            convertView.setBackgroundColor(Color.LTGRAY);
        } else {
            convertView.setSelected(false);
            convertView.setPressed(false);
            convertView.setBackgroundColor(Color.WHITE);
        }
        return convertView;
    }

    /**
     * ViewHolder
     *
     * @author Mumu
     */
    static class ViewHolder {
        TextView mContent;
        TextView mTitle;
        ImageView mIcon;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

}
