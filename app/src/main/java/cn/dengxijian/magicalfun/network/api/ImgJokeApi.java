package cn.dengxijian.magicalfun.network.api;


import cn.dengxijian.magicalfun.entity.textjoke.NewTextJokeBean;
import cn.dengxijian.magicalfun.entity.textjoke.RandomTextJoke;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/*******************************************************************
 * * * * *   * * * *   *     *       Created by OCN.Yang
 * *     *   *         * *   *       Time:2017/3/3 15:24.
 * *     *   *         *   * *       Email address:ocnyang@gmail.com
 * * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public interface ImgJokeApi {
    @GET("joke/img/text.from")
    Observable<NewTextJokeBean> getNewTextJokeJoke(@Query("key") String appkey,
                                                   @Query("page") int pno,
                                                   @Query("pagesize") int ps);

    @GET("joke/randJoke.php")
    Observable<RandomTextJoke> getRandomTextJoke(@Query("key") String key, @Query("type") String type);
}
