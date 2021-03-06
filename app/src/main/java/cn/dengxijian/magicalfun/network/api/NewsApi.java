package cn.dengxijian.magicalfun.network.api;


import cn.dengxijian.magicalfun.entity.news.NewsItem;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/*******************************************************************
 * * * * *   * * * *   *     *       Created by OCN.Yang
 * *     *   *         * *   *       Time:2017/2/17 10:17.
 * *     *   *         *   * *       Email address:ocnyang@gmail.com
 * * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public interface NewsApi {
    @GET("toutiao/index")
    Observable<NewsItem> getNews(@Query("type") String newstype, @Query("key") String appkey);
}
