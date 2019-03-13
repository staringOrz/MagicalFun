package cn.dengxijian.magicalfun.entity.textjoke;




public class RefreshJokeStyleEvent {
    private int JokeStyle;

    public RefreshJokeStyleEvent(int jokeStyle) {
        JokeStyle = jokeStyle;
    }

    public int getJokeStyle() {
        return JokeStyle;
    }

    public void setJokeStyle(int jokeStyle) {
        JokeStyle = jokeStyle;
    }
}
