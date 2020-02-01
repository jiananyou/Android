package com.xh189050936.weathercast.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class NowWeather implements Weather {
    private String cloud;
    private String cond_code;
    private String cond_txt;
    private String fl;
    private String hum;
    private String pcpn;
    private String pres;
    private String tmp;
    private String vis;
    private String wind_deg;
    private String wind_dir;
    private String wind_sc;
    private String wind_spd;

    @Override
    public void parseJSON(JSONObject now) throws JSONException {
        this.setCloud(now.getString("cloud"));
        this.setCond_code(now.getString("cond_code"));
        this.setCond_txt(now.getString("cond_txt"));
        this.setFl(now.getString("fl"));
        this.setHum(now.getString("hum"));
        this.setPcpn(now.getString("pcpn"));
        this.setPres(now.getString("pres"));
        this.setTmp(now.getString("tmp"));
        this.setVis(now.getString("vis"));
        this.setWind_deg(now.getString("wind_deg"));
        this.setWind_dir(now.getString("wind_dir"));
        this.setWind_sc(now.getString("wind_sc"));
        this.setWind_spd(now.getString("wind_spd"));
    }

    @Override
    public String toString() {
        return "NowWeather{" +
                "cloud='" + cloud + '\'' +
                ", cond_code='" + cond_code + '\'' +
                ", cond_txt='" + cond_txt + '\'' +
                ", fl='" + fl + '\'' +
                ", hum='" + hum + '\'' +
                ", pcpn='" + pcpn + '\'' +
                ", pres='" + pres + '\'' +
                ", tmp='" + tmp + '\'' +
                ", vis='" + vis + '\'' +
                ", wind_deg='" + wind_deg + '\'' +
                ", wind_dir='" + wind_dir + '\'' +
                ", wind_sc='" + wind_sc + '\'' +
                ", wind_spd='" + wind_spd + '\'' +
                '}';
    }

    public String getCloud() {
        return cloud;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    public String getCond_code() {
        return cond_code;
    }

    public void setCond_code(String cond_code) {
        this.cond_code = cond_code;
    }

    public String getCond_txt() {
        return cond_txt;
    }

    public void setCond_txt(String cond_txt) {
        this.cond_txt = cond_txt;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getPcpn() {
        return pcpn;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
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
}
