package sysu.zyb.panellisttest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sysu.zyb.panellistlibrary.PanelListAdapter;
import sysu.zyb.panellistlibrary.PanelListLayout;

/**
 * <pre>
 *     author : zyb
 *     e-mail : hbdxzyb@hotmail.com
 *     time   : 2017/05/23
 *     desc   : 整个页面的Adapter，内部使用了两个子Adapter
 *              开发者可自行定义两个子Adapter
 *     version: 1.0
 * </pre>
 */

public class MyPanelListAdapter extends PanelListAdapter {

    private Context context;

    private ListView lv_content;
    private int contentResourceId;
    private List<Map<String, String>> contentList = new ArrayList<>();

    /**
     * constructor
     *
     * @param context 上下文
     * @param pl_root 根布局（PanelListLayout）
     * @param lv_content content部分的布局（ListView）
     * @param contentResourceId content 部分的 item 布局
     * @param contentList content 部分的数据
     */
    public MyPanelListAdapter(Context context, PanelListLayout pl_root, ListView lv_content,
                              int contentResourceId, List<Map<String,String>> contentList) {
        super(context, pl_root, lv_content);
        this.context = context;
        this.lv_content = lv_content;
        this.contentResourceId = contentResourceId;
        this.contentList = contentList;
    }

    /**
     * 1. 自行编写Adapter并进行setAdapter
     * 2. 调用父类方法进行同步控制
     */
    @Override
    public void initAdapter() {
        setTitle("test");//设置表的标题
        setTitleHeight(100);//设置表标题的高
        setTitleWidth(150);//设置表标题的宽
        setRowDataList(getRowDataList());//设置横向表头的内容

        setSwipeRefreshEnabled(false);

        // set自己写的contentAdapter
        ContentAdapter contentAdapter = new ContentAdapter(context,contentResourceId,contentList);
        lv_content.setAdapter(contentAdapter);

        setOnRefreshListener(new CustomRefreshListener());//需要在调用父类的方法之前设置监听

        setInitPosition(88);//指定初始位置。该方法public，可在Activity中调用

        super.initAdapter();//一定要在设置完后再调用父类的方法
    }


    /**
     * 刷新数据
     *
     * 虽然支持自定义OnRefreshListener来设置下拉刷新的监听，但是推荐重写父类的该方法来实现刷新逻辑
     */
//    @Override
//    public void refreshData(){
//        Log.d("ybz", "refreshData: in custom adapter");
//    }

    /**
     * 也可以自定义OnRefreshListener，然后再上面的{@link #initAdapter()}中调用setOnRefreshListener()
     */
    private class CustomRefreshListener implements SwipeRefreshLayout.OnRefreshListener{
        @Override
        public void onRefresh() {
            Log.d("ybz", "onRefresh:  custom listener");
            getSwipeRefreshLayout().setRefreshing(false);
        }
    }

    /**
     * 重写父类的该方法，返回数据的个数即可
     *
     * @return size of content
     */
    @Override
    protected int getCount() {
        return contentList.size();
    }

    /** 生成一份横向表头的内容
     *
     * @return List<String>
     */
    private List<String> getRowDataList(){
        List<String> rowDataList = new ArrayList<>();
        rowDataList.add("row1");
        rowDataList.add("row2");
        rowDataList.add("row3");
        rowDataList.add("row4");
        rowDataList.add("row5");
        rowDataList.add("row6");
        rowDataList.add("row7");
        return rowDataList;
    }

    /**
     * content部分的adapter
     *
     * 这里可以自由发挥，和普通的 ListView 的 Adapter 没区别
     */
    private class ContentAdapter extends ArrayAdapter {

        private List<Map<String, String>> contentList;
        private int resourceId;

        ContentAdapter(Context context, int resourceId, List<Map<String, String>> contentList) {
            super(context, resourceId);
            this.contentList = contentList;
            this.resourceId = resourceId;
        }

        @Override
        public int getCount() {
            return contentList.size();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final Map<String, String> data = contentList.get(position);
            View view;
            ViewHolder viewHolder;

            if (convertView == null) {
                view = LayoutInflater.from(parent.getContext()).inflate(resourceId, parent, false);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.tv_01.setText(data.get("1"));
            viewHolder.tv_02.setText(data.get("2"));
            viewHolder.tv_03.setText(data.get("3"));
            viewHolder.tv_04.setText(data.get("4"));
            viewHolder.tv_05.setText(data.get("5"));
            viewHolder.tv_06.setText(data.get("6"));
            viewHolder.tv_07.setText(data.get("7"));

            if (lv_content.isItemChecked(position)){
                view.setBackgroundColor(context.getResources().getColor(R.color.colorSelected));
            } else {
                view.setBackgroundColor(context.getResources().getColor(R.color.colorDeselected));
            }

            return view;
        }

        private class ViewHolder {
            TextView tv_01;
            TextView tv_02;
            TextView tv_03;
            TextView tv_04;
            TextView tv_05;
            TextView tv_06;
            TextView tv_07;

            ViewHolder(View itemView) {
                tv_01 = (TextView) itemView.findViewById(R.id.id_tv_01);
                tv_02 = (TextView) itemView.findViewById(R.id.id_tv_02);
                tv_03 = (TextView) itemView.findViewById(R.id.id_tv_03);
                tv_04 = (TextView) itemView.findViewById(R.id.id_tv_04);
                tv_05 = (TextView) itemView.findViewById(R.id.id_tv_05);
                tv_06 = (TextView) itemView.findViewById(R.id.id_tv_06);
                tv_07 = (TextView) itemView.findViewById(R.id.id_tv_07);
            }
        }
    }
}
