package com.example.Fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.GroupBuying.R;
import com.example.JsonData.CirclesBean;
import com.example.JsonData.ConfigInfo;
import com.example.JsonData.ConfigResult;
import com.example.JsonData.InfoBean;
import com.example.JsonData.MerchantBean;
import com.example.JsonData.ResultBean;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.warmtel.expandtab.ExpandPopTabView;
import com.warmtel.expandtab.KeyValueBean;
import com.warmtel.expandtab.PopOneListView;
import com.warmtel.expandtab.PopTwoListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class NearFragment extends Fragment {

    private ImageView image_near;
    private ExpandPopTabView expandPopTabView;
    private ListView mlistView;
    private MerchantAdapter merchantAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_near, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        image_near = (ImageView) getView().findViewById(R.id.near_logo);
        expandPopTabView = (ExpandPopTabView) getView().findViewById(R.id.near_expandpoptabview);
        mlistView = (ListView) getView().findViewById(R.id.merchant_listview);

        merchantAdapter = new MerchantAdapter(getActivity());
        mlistView.setAdapter(merchantAdapter);

        setExpandPopTabViewData();
        setListViewData();
    }

    //    設置二級菜單
    public void setExpandPopTabViewData() {
        String httpUrl = "http://www.warmtel.com:8080/configs";
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                try {
                    Log.e("tag","====="+params);
                    return getDataByConnectNet(params[0]);
                } catch (IOException e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s!=null) {
                    Gson gson = new Gson();
                    ConfigResult configResult = gson.fromJson(s, ConfigResult.class);
                    ConfigInfo info = configResult.getInfo();

                    addItem(expandPopTabView, info.getDistanceKey(), "", "距离");
                    addItem(expandPopTabView, info.getIndustryKey(), "", "行业");
                    addItem(expandPopTabView, info.getSortKey(), "", "排序");

                    List<KeyValueBean> mParentLists = new ArrayList<>();//父区域
                    List<ArrayList<KeyValueBean>> mChildrenListLists = new ArrayList<>();//子区域

                    for (CirclesBean circlesBean : info.getAreaKey()) {
                        KeyValueBean keyValueBean = new KeyValueBean();
                        keyValueBean.setKey(circlesBean.getKey());
                        keyValueBean.setValue(circlesBean.getValue());
                        mParentLists.add(keyValueBean);
                        mChildrenListLists.add((ArrayList<KeyValueBean>) circlesBean.getCircles());
                    }
                    addItem(expandPopTabView, mParentLists, mChildrenListLists, "武侯区", "磨子桥", "区域");
                }
            }
        }.execute(httpUrl);
    }

    public void addItem(ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        PopOneListView popOneListView = new PopOneListView(getActivity());
        popOneListView.setDefaultSelectByValue(defaultSelect);
        popOneListView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {

            @Override
            public void getValue(String key, String value) {
                Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();
            }
        });
        expandTabView.addItemToExpandTab(defaultShowText, popOneListView);
    }

    public void addItem(ExpandPopTabView expandTabView, List<KeyValueBean> parentLists,
                        List<ArrayList<KeyValueBean>> childrenListLists, String defaultParentSelect, String defaultChildSelect, String defaultShowText) {
        PopTwoListView popTwoListView = new PopTwoListView(getActivity());
        popTwoListView.setDefaultSelectByValue(defaultParentSelect, defaultChildSelect);
        popTwoListView.setCallBackAndData(expandTabView, parentLists, childrenListLists, new PopTwoListView.OnSelectListener() {
            @Override
            public void getValue(String showText, String parentKey, String childrenKey) {
                Toast.makeText(getActivity(), showText, Toast.LENGTH_SHORT).show();
            }
        });
        expandTabView.addItemToExpandTab(defaultShowText, popTwoListView);
    }

    public String getDataByConnectNet(String httpUrl) throws IOException {
        URL url = new URL(httpUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(8000);
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = connection.getInputStream();
            return readStrFromInputStream(inputStream);
        } else {
            return null;
        }
    }

    public String readStrFromInputStream(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        is.close();
        return sb.toString();
    }

    //    設置列表的數據
    public void setListViewData() {
        final String httpUrl = "http://www.warmtel.com:8080/around";
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                try {
                    return getDataByConnectNet(params[0]);

                } catch (IOException e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                ArrayList<MerchantBean> merchantLists = JsonToMerchantList(s);
                if(merchantLists!=null) {
                    merchantAdapter.setListData(merchantLists);
                }}
        }.execute(httpUrl);
    }

    public ArrayList<MerchantBean> JsonToMerchantList(String message) {
        ArrayList<MerchantBean> mlists = new ArrayList<>();
        ArrayList<MerchantBean> merchantKey = null;
        if(message!=null) {
            Gson gson = new Gson();
            ResultBean resultBean = gson.fromJson(message, ResultBean.class);
            InfoBean info = resultBean.getInfo();
            merchantKey = (ArrayList<MerchantBean>) info.getMerchantKey();
        }
            return merchantKey;

    }

    public class MerchantAdapter extends BaseAdapter {
        private ArrayList<MerchantBean> merchantlist = new ArrayList<>();
        private LayoutInflater layoutInflater;
        private Context context;

        public MerchantAdapter(Context context) {
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
        }

        public void setListData(ArrayList<MerchantBean> list) {
            this.merchantlist = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return merchantlist.size();
        }

        @Override
        public Object getItem(int position) {
            return merchantlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v;
            final ViewHolder holder;
            if (convertView == null) {
                v = layoutInflater.inflate(R.layout.near_listview_item, null);

                holder = new ViewHolder();
                holder.nameTxt = (TextView) v.findViewById(R.id.list_name);
                holder.couponTxt = (TextView) v.findViewById(R.id.list_coupon);
                holder.locationTxt = (TextView) v.findViewById(R.id.list_location);
                holder.iconImg = (ImageView) v.findViewById(R.id.list_icon);
                holder.distanceTxt = (TextView) v.findViewById(R.id.list_distance);
                holder.ticketImg = (ImageView) v.findViewById(R.id.list_ticket);
                holder.groupImg = (ImageView) v.findViewById(R.id.list_group);
                holder.cardImg = (ImageView) v.findViewById(R.id.list_card);

                v.setTag(holder);
            } else {
                v = convertView;
                holder = (ViewHolder) v.getTag();
            }
            MerchantBean merchantBean = (MerchantBean) getItem(position);

            Picasso.with(context).load(merchantBean.getPicUrl()).into(holder.iconImg);
            holder.nameTxt.setText(merchantBean.getName());
            holder.couponTxt.setText(merchantBean.getCoupon());
            holder.locationTxt.setText(merchantBean.getLocation());
            holder.distanceTxt.setText(merchantBean.getDistance());


            if (merchantBean.getGroupType().equalsIgnoreCase("YES")) {
                holder.groupImg.setVisibility(View.VISIBLE);
            } else {
                holder.groupImg.setVisibility(View.GONE);
            }
            if (merchantBean.getCouponType().equalsIgnoreCase("YES")) {
                holder.ticketImg.setVisibility(View.VISIBLE);
            } else {
                holder.ticketImg.setVisibility(View.GONE);
            }
            if (merchantBean.getCardType().equalsIgnoreCase("YES")) {
                holder.cardImg.setVisibility(View.VISIBLE);
            } else {
                holder.cardImg.setVisibility(View.GONE);
            }
            return v;
        }
    }

    public class ViewHolder {
        ImageView iconImg;
        TextView nameTxt;
        TextView couponTxt;
        TextView locationTxt;
        TextView distanceTxt;
        ImageView ticketImg;
        ImageView groupImg;
        ImageView cardImg;
    }
}