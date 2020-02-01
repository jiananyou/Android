package com.xh189050936.weathercast.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class DailyWeather implements Weather {
    private String cond_code_d;
    private String cond_code_n;
    private String cond_txt_d;
    private String cond_txt_n;
    private String date;
    private String hum;
    private String mr;
    private String ms;
    private String pcpn;
    private String pop;
    private String pres;
    private String sr;
    private String ss;
    private String tmp_max;
    private String tmp_min;
    private String uv_index;
    private String vis;
    private String wind_deg;
    private String wind_dir;
    private String wind_sc;
    private String wind_spd;

    // 返回json数据中没有的属性
    private String week;

    @Override
    public void parseJSON(JSONObject json) throws JSONException {
        this.setCond_code_d(json.getString("cond_code_d"));
        this.setCond_code_n(json.getString("cond_code_n"));
        this.setCond_txt_d(json.getString("cond_txt_d"));
        this.setCond_txt_n(json.getString("cond_txt_n"));
        this.setDate(json.getString("date"));
        this.setHum(json.getString("hum"));
        this.setMr(json.getString("mr"));
        this.setMs(json.getString("ms"));
        this.setPcpn(json.getString("pcpn"));
        this.setPop(json.getString("pop"));
        this.setPres(json.getString("pres"));
        this.setSr(json.getString("sr"));
        this.setSs(json.getString("ss"));
        this.setTmp_max(json.getString("tmp_max"));
        this.setTmp_min(json.getString("tmp_min"));
        this.setUv_index(json.getString("uv_index"));
        this.setVis(json.getString("vis"));
        this.setWind_deg(json.getString("wind_deg"));
        this.setWind_dir(json.getString("wind_dir"));
        this.setWind_sc(json.getString("wind_sc"));
        this.setWind_spd(json.getString("wind_spd"));
    }

    @Override
    public String toString() {
        return "DailyWeather{" +
                "cond_code_d='" + cond_code_d + '\'' +
                ", cond_code_n='" + cond_code_n + '\'' +
                ", cond_txt_d='" + cond_txt_d + '\'' +
                ", cond_txt_n='" + cond_txt_n + '\'' +
                ", date='" + date + '\'' +
                ", hum='" + hum + '\'' +
                ", mr='" + mr + '\'' +
                ", ms='" + ms + '\'' +
                ", pcpn='" + pcpn + '\'' +
                ", pop='" + pop + '\'' +
                ", pres='" + pres + '\'' +
                ", sr='" + sr + '\'' +
                ", ss='" + ss + '\'' +
                ", tmp_max='" + tmp_max + '\'' +
                ", tmp_min='" + tmp_min + '\'' +
                ", uv_index='" + uv_index + '\'' +
                ", vis='" + vis + '\'' +
                ", wind_deg='" + wind_deg + '\'' +
                ", wind_dir='" + wind_dir + '\'' +
                ", wind_sc='" + wind_sc + '\'' +
                ", wind_spd='" + wind_spd + '\'' +
                "}\n";
    }

    public String getCond_code_d() {
        return cond_code_d;
    }

    public void setCond_code_d(String cond_code_d) {
        this.cond_code_d = cond_code_d;
    }

    public String getCond_code_n() {
        return cond_code_n;
    }

    public void setCond_code_n(String cond_code_n) {
        this.cond_code_n = cond_code_n;
    }

    public String getCond_txt_d() {
        return cond_txt_d;
    }

    public void setCond_txt_d(String cond_txt_d) {
        this.cond_txt_d = cond_txt_d;
    }

    public String getCond_txt_n() {
        return cond_txt_n;
    }

    public void setCond_txt_n(String cond_txt_n) {
        this.cond_txt_n = cond_txt_n;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getMr() {
        return mr;
    }

    public void setMr(String mr) {
        this.mr = mr;
    }

    public String getMs() {
        return ms;
    }

    public void setMs(String ms) {
        this.ms = ms;
    }

    public String getPcpn() {
        return pcpn;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getSr() {
        return sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getSs() {
        return ss;
    }

    public void setSs(String ss) {
        this.ss = ss;
    }

    public String getTmp_max() {
        return tmp_max;
    }

    public void setTmp_max(String tmp_max) {
        this.tmp_max = tmp_max;
    }

    public String getTmp_min() {
        return tmp_min;
    }

    public void setTmp_min(String tmp_min) {
        this.tmp_min = tmp_min;
    }

    public String getUv_index() {
        return uv_index;
    }

    public void setUv_index(String uv_index) {
        this.uv_index = uv_index;
    }

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public String getWind_deg() {
        return wind_deg;
    }

    public void setWind_deg(String wind_deg) {
        this.wind_deg = wind_deg;
    }

    public String getWind_dir() {
        return wind_dir;
    }

    public void setWind_dir(String wind_dir) {
        this.wind_dir = wind_dir;
    }

    public String getWind_sc() {
        return wind_sc;
    }

    public void setWind_sc(String wind_sc) {
        this.wind_sc = wind_sc;
    }

    public String getWind_spd() {
        return wind_spd;
    }

    public void setWind_spd(String wind_spd) {
        this.wind_spd = wind_spd;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
