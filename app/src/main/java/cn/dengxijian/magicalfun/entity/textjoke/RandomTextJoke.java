package cn.dengxijian.magicalfun.entity.textjoke;

import java.util.List;



public class RandomTextJoke extends BaseJokeBean{


    private List<TextJokeBean> result;

    public List<TextJokeBean> getResult() {
        return result;
    }

    public void setResult(List<TextJokeBean> result) {
        this.result = result;
    }

}
