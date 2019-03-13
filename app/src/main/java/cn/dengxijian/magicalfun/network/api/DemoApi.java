package cn.dengxijian.magicalfun.network.api;


import cn.dengxijian.magicalfun.entity.GankBeautyResult;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;



public interface DemoApi {
    @GET("data/福利/{number}/{page}")
    Observable<GankBeautyResult> getBeauties(@Path("number") int number, @Path("page") int page);
}
