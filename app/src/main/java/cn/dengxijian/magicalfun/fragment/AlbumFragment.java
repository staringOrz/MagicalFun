package cn.dengxijian.magicalfun.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

import cn.dengxijian.magicalfun.R;
import cn.dengxijian.magicalfun.activity.ModelActivity;
import cn.dengxijian.magicalfun.adapter.AlbumAdapter;
import cn.dengxijian.magicalfun.database.news.DBManager;
import cn.dengxijian.magicalfun.entity.music.AlbumInfo;
import cn.dengxijian.magicalfun.util.MyMusicUtil;

/**
 * Created by dengxijian on 2019/3/9.
 */

public class AlbumFragment extends Fragment {

    private static final String TAG = "AlbumFragment";
    private RecyclerView recyclerView;
    private AlbumAdapter adapter;
    private ArrayList<AlbumInfo> albumInfoList = new ArrayList<>();
    private DBManager dbManager;
    private Context mContext;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    public void onResume() {
        super.onResume();
        albumInfoList.clear();
        albumInfoList.addAll(MyMusicUtil.groupByAlbum((ArrayList)dbManager.getAllMusicFromMusicTable()));
        Log.d(TAG, "onResume: albumInfoList.size() = "+albumInfoList.size());
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_singer,container,false);
        dbManager = DBManager.getInstance(getContext());
        recyclerView = (RecyclerView)view.findViewById(R.id.singer_recycler_view);
        adapter = new AlbumAdapter(getContext(),albumInfoList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new AlbumAdapter.OnItemClickListener() {
            @Override
            public void onDeleteMenuClick(View content, int position) {

            }

            @Override
            public void onContentClick(View content, int position) {
                Intent intent = new Intent(mContext,ModelActivity.class);
                intent.putExtra(ModelActivity.KEY_TITLE,albumInfoList.get(position).getName());
                intent.putExtra(ModelActivity.KEY_TYPE, ModelActivity.ALBUM_TYPE);
                mContext.startActivity(intent);
            }
        });
        return view;
    }
}
