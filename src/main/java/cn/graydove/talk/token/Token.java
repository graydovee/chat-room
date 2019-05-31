package cn.graydove.talk.token;

import cn.graydove.talk.token.utils.TokenUtils;

import java.util.Objects;

public class Token {
    public static long MaxEffetiveTime = 1000 * 60 * 60;
    private String id;
    private String userJson;
    private String RSAcode;
    private long effetiveTime;

    public Token() {
    }

    public Token(String id, String userJson, String RSAcode, long effetiveTime) {
        this.id = id;
        this.userJson = userJson;
        this.RSAcode = RSAcode;
        this.effetiveTime = effetiveTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(id, token.id) &&
                Objects.equals(userJson, token.userJson) &&
                Objects.equals(RSAcode, token.RSAcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userJson, RSAcode);
    }

    @Override
    public String toString() {
        return "Token{" +
                "id='" + id + '\'' +
                ", user=" + userJson +
                ", RSAcode='" + RSAcode + '\'' +
                ", effetiveTime=" + effetiveTime +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserJson() {
        return userJson;
    }

    public void setUser(String userJson) {
        this.userJson = userJson;
    }

    public String getRSAcode() {
        return RSAcode;
    }

    public void setRSAcode(String RSAcode) {
        this.RSAcode = RSAcode;
    }

    public long getEffetiveTime() {
        return effetiveTime;
    }

    public void setEffetiveTime(long effetiveTime) {
        this.effetiveTime = effetiveTime;
    }

    public String encrypt() {
        return TokenUtils.encrypt(this);
    }
}
