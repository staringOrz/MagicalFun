package cn.dengxijian.magicalfun.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.dengxijian.magicalfun.R;
import cn.dengxijian.magicalfun.activity.LastMyloveActivity;
import cn.dengxijian.magicalfun.activity.LocalMusicActivity;
import cn.dengxijian.magicalfun.activity.base.BaseActivity;
import cn.dengxijian.magicalfun.adapter.HomeListViewAdapter;
import cn.dengxijian.magicalfun.database.news.DBManager;
import cn.dengxijian.magicalfun.entity.music.PlayListInfo;
import cn.dengxijian.magicalfun.fragment.base.BaseFragment;
import cn.dengxijian.magicalfun.service.MusicPlayerService;
import cn.dengxijian.magicalfun.util.Constant;


/**
 * @author dengxijian
 * @date 2019/3/9
 */
public class MusicFragment extends BaseFragment {

    private View mContextView;
    private PlayBarFragment mPlayBarFragment;
    private FragmentManager mFragmentManager;

    /**
     * DB
     */
    private DBManager dbManager;

    /**
     * view 控件
     */
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ImageView navHeadIv;
    private LinearLayout localMusicLl;
    private LinearLayout lastPlayLl;
    private LinearLayout myLoveLl;
    private LinearLayout myListTitleLl;
    private TextView localMusicCountTv;
    private TextView lastPlayCountTv;
    private TextView myLoveCountTv;
    private TextView myPLCountTv;
    private ImageView myPLArrowIv;
    private ImageView myPLAddIv;
    private ListView listView;

    private HomeListViewAdapter adapter;
    private List<PlayListInfo> playListInfos;
    private BaseActivity mContext;
    private int count;
    private boolean isOpenMyPL = false; //标识我的歌单列表打开状态
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getBaseActivity();
        dbManager = DBManager.getInstance(mContext);
        mContextView = inflater.inflate(R.layout.activity_home, container, false);
        mFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (mPlayBarFragment == null) {
            mPlayBarFragment = PlayBarFragment.newInstance();
            fragmentTransaction.add(R.id.fragment_playbar, mPlayBarFragment).commit();
        }else {
            fragmentTransaction.show(mPlayBarFragment).commit();
        }
        return mContextView;
    }

    @Override
    public int getLayoutRes() {
        return 0;
    }

    @Override
    protected void managerArguments() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        appCompatActivity.getSupportActionBar().setTitle("听听音乐");

        localMusicLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,LocalMusicActivity.class);
                startActivity(intent);
            }
        });

        lastPlayLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,LastMyloveActivity.class);
                intent.putExtra(Constant.LABEL,Constant.LABEL_LAST);
                startActivity(intent);
            }
        });

        myLoveLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,LastMyloveActivity.class);
                intent.putExtra(Constant.LABEL,Constant.LABEL_MYLOVE);
                startActivity(intent);
            }
        });

        playListInfos = dbManager.getMyPlayList();
        adapter = new HomeListViewAdapter(playListInfos,mContext,dbManager);

        adapter.setOnupdateDataListListener(new HomeListViewAdapter.UpdateDataListListener() {
            @Override
            public void updateDataList() {
                updatePlaylistCount();
            }
        });
        listView.setAdapter(adapter);
        myPLAddIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加歌单
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_create_playlist,null);
                final EditText playlistEt = (EditText)view.findViewById(R.id.dialog_playlist_name_et);
                builder.setView(view);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = playlistEt.getText().toString();
                        if (TextUtils.isEmpty(name)) {
                            Toast.makeText(mContext,"请输入歌单名",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        dbManager.createPlaylist(name);
                        dialog.dismiss();
                        adapter.updateDataList();
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();//配置好后再builder show
            }
        });
        myListTitleLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //展现我的歌单
                if (isOpenMyPL){
                    isOpenMyPL = false;
                    myPLArrowIv.setImageResource(R.drawable.arrow_right);
                    listView.setVisibility(View.GONE);
                }else {
                    isOpenMyPL = true;
                    myPLArrowIv.setImageResource(R.drawable.arrow_down);
                    listView.setVisibility(View.VISIBLE);
                    playListInfos = dbManager.getMyPlayList();
                    adapter = new HomeListViewAdapter(playListInfos,mContext,dbManager);
                    listView.setAdapter(adapter);
                }
            }
        });

        Intent startIntent = new Intent(getActivity(),MusicPlayerService.class);
        getActivity().startService(startIntent);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    public void initView() {
        toolbar = getActivity().findViewById(R.id.home_activity_toolbar);
        localMusicLl = (LinearLayout) getActivity().findViewById(R.id.home_local_music_ll);
        lastPlayLl = (LinearLayout) getActivity().findViewById(R.id.home_recently_music_ll);
        myLoveLl = (LinearLayout) getActivity().findViewById(R.id.home_my_love_music_ll);
        myListTitleLl = (LinearLayout) getActivity().findViewById(R.id.home_my_list_title_ll);
        listView = (ListView)getActivity().findViewById(R.id.home_my_list_lv);
        localMusicCountTv = (TextView) getActivity().findViewById(R.id.home_local_music_count_tv);
        lastPlayCountTv = (TextView) getActivity().findViewById(R.id.home_recently_music_count_tv);
        myLoveCountTv = (TextView) getActivity().findViewById(R.id.home_my_love_music_count_tv);
        myPLCountTv = (TextView) getActivity().findViewById(R.id.home_my_list_count_tv);
        myPLArrowIv = (ImageView) getActivity().findViewById(R.id.home_my_pl_arror_iv);
        myPLAddIv = (ImageView) getActivity().findViewById(R.id.home_my_pl_add_iv);

    }

    @Override
    public void onResume() {
        super.onResume();
        count = dbManager.getMusicCount(Constant.LIST_ALLMUSIC);
        localMusicCountTv.setText(count + "");
        count = dbManager.getMusicCount(Constant.LIST_LASTPLAY);
        lastPlayCountTv.setText(count + "");
        count = dbManager.getMusicCount(Constant.LIST_MYLOVE);
        myLoveCountTv.setText(count + "");
        count = dbManager.getMusicCount(Constant.LIST_MYPLAY);
        myPLCountTv.setText("(" + count + ")");
        if(adapter.updateDataListListener == null ){
            adapter.setOnupdateDataListListener(new HomeListViewAdapter.UpdateDataListListener() {
                @Override
                public void updateDataList() {
                    updatePlaylistCount();
                }
            });
        }
        adapter.updateDataList();
    }


    public void updatePlaylistCount(){
        count = dbManager.getMusicCount(Constant.LIST_MYPLAY);
        myPLCountTv.setText("(" + count + ")");
    }
}
