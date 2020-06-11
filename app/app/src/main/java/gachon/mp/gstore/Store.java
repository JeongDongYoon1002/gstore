package gachon.mp.gstore;

import androidx.annotation.NonNull;

public class Store {
    private String name = null;
    private String lat = null;
    private String longt = null;
    private String sigun = null;
    private String type = null;
    private String addr = null;
    private String roadAddr = null;
    private String tel = null;
    private String zip = null;

    public Store(String name, String lat, String longt, String sigun, String type, String addr, String roadAddr, String tel, String zip){
        this.name = name;
        this.lat = lat;
        this.longt = longt;
        this.sigun = sigun;
        this.type = type;
        this.addr = addr;
        this.roadAddr = roadAddr;
        this.tel = tel;
        this.zip = zip;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getName() + "\n" + this.getTel() + "\n" + this.getRoadAddr();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongt() {
        return longt;
    }

    public void setLongt(String longt) {
        this.longt = longt;
    }

    public String getSigun() {
        return sigun;
    }

    public void setSigun(String sigun) {
        this.sigun = sigun;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getRoadAddr() {
        return roadAddr;
    }

    public void setRoadAddr(String roadAddr) {
        this.roadAddr = roadAddr;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
