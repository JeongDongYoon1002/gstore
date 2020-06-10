package gachon.mp.gstore;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

public class GetApi {

    private String apiKey="eb10e06504d3495db11fa551c6872d6b";
    private int Total = 572888;

    public ArrayList<Store> getAllXmlData () {
        ArrayList<Store> allStores = new ArrayList<>();

        for(int i=1 ; i <= 3 ; i++){
            allStores.addAll(getXmlData(1000, i));
        }
        return allStores;
    }

    public ArrayList<Store> getXmlData(int pSize, int pIndex){
        StringBuffer buffer=new StringBuffer();
        ArrayList<Store> stores = new ArrayList<>();

        String queryUrl="https://openapi.gg.go.kr/RegionMnyFacltStus?KEY=" + apiKey + "&pSize=" + pSize + "&pIndex=" + pIndex; // api 요청 주소

//        if(!requestSigun.equals("")){
//            queryUrl = queryUrl + "&SIGUN_NM=" + requestSigun;
//        }
//        if(!requestName.equals("")){
//            queryUrl = queryUrl + "&CMPNM_NM=" + requestName;
//        }
//        if(!requestAddr.equals("")){
//            queryUrl = queryUrl + "&REFINE_LOTNO_ADDR=" + requestAddr;
//        }
//        if(!requestRoadAddr.equals("")){
//            queryUrl = queryUrl + "&REFINE_ROADNM_ADDR=" + requestRoadAddr;
//        }


        try{
            URL url= new URL(queryUrl); //문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            String name=null, lat=null, longt=null, sigun=null, type=null, addr=null, roadAddr=null, tel=null, zip=null;

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();//xml파싱을 위한
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();
            while( eventType != XmlPullParser.END_DOCUMENT ){
                
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//테그 이름 얻어오기
                        if(tag.equals("row")){ // 객체 태그 시작
                            name=null;
                            lat=null;
                            longt=null;
                            sigun=null;
                            type=null;
                            addr=null;
                            roadAddr=null;
                            tel=null;
                            zip=null;
                        }// 첫번째 검색결과
                        else if(tag.equals("CMPNM_NM")){
                            buffer.append("상호명 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            name = xpp.getText();
                            buffer.append("\n");
                        }
                        else if(tag.equals("REFINE_WGS84_LAT")){
                            buffer.append("위도 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            lat = xpp.getText();
                            buffer.append("\n");
                        }
                        else if(tag.equals("REFINE_WGS84_LOGT")){
                            buffer.append("경도 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            longt = xpp.getText();
                            buffer.append("\n");
                        }
                        else if(tag.equals("SIGUN_NM")){
                            buffer.append("시군명 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            sigun = xpp.getText();
                            buffer.append("\n");
                        }
                        else if(tag.equals("INDUTYPE_NM")){
                            buffer.append("업종명 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            type = xpp.getText();
                            buffer.append("\n");
                        }
                        else if(tag.equals("REFINE_LOTNO_ADDR")){
                            buffer.append("지번주소 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            addr = xpp.getText();
                            buffer.append("\n");
                        }
                        else if(tag.equals("REFINE_ROADNM_ADDR")){
                            buffer.append("도로명주소. : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            roadAddr = xpp.getText();
                            buffer.append("\n");
                        }
                        else if(tag.equals("TELNO")){
                            buffer.append("전화번호 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            tel = xpp.getText();
                            buffer.append("\n");
                        }
                        else if(tag.equals("REFINE_ZIP_CD")){
                            buffer.append("우편번호 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            zip = xpp.getText();
                            buffer.append("\n");
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기

                        if(tag.equals("row")){
                            stores.add(new Store(name, lat, longt, sigun, type, addr, roadAddr, tel, zip));
                            buffer.append("\n\n");
                        }
                        break;
                }

                eventType= xpp.next();
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        buffer.append("파싱 끝\n");
        return stores;//StringBuffer 문자열 객체 반환

    }//getXmlData method....
}
