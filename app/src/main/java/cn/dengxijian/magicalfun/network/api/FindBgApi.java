package cn.dengxijian.magicalfun.network.api;


import cn.dengxijian.magicalfun.entity.FindBg;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;



public interface FindBgApi {
    @GET("HPImageArchive.aspx")
    Observable<FindBg> getFindBg(@Query("format") String format, @Query("idx") int idx, @Query("n") int n);
}
