package myshop12.com.model2.mvc.common.util;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class CookieUtil {

    ///Field
    //Constructor
    //Method
    //쿠키 키 밸류 정해서 쿠키에 값을 추가해주는놈 "history" prodName
    //쿠키를 완전히 바꾸려면
    public static void addValue(HttpServletRequest request, HttpServletResponse response, String cookieKey, String cookieValue) throws UnsupportedEncodingException{

        //쿠키 설정
        Cookie clientCookies[] = request.getCookies();
        System.out.println(clientCookies);

        //이름이 @@인 쿠키 있는지 확인
        boolean checkHistory=false;
        for (Cookie tempCookie : clientCookies) {
            if(tempCookie.getName().equals(cookieKey)){
                checkHistory=true;
            }//end of if
        }//end of for

        //[없으면 만들고] [있으면 추가한다]
        if(checkHistory==false) {
            System.out.println("쿠키가 없다. 그러니 새로운 쿠키 생성");
            Cookie newCookie = new Cookie(cookieKey, cookieValue);
            newCookie.setPath("/");
            response.addCookie(newCookie);
        }
        else{//쿠키가 있으니 추가하자
            System.out.println("쿠키가 있다. 그러니 추가하겠다.");
            for (Cookie tempCookie : clientCookies) {
                System.out.println("쿠키명" + tempCookie.getName());
                String decodedTempCookieValue = URLDecoder.decode(tempCookie.getValue(),"EUC-KR");
                System.out.println("쿠키값" + decodedTempCookieValue);
                //파라미터의 Key와 쿠키속의 Key가 같은가? 즉, like가 있냐? history가 있냐?
                if (tempCookie.getName().equals(cookieKey)) {

                    if (decodedTempCookieValue.startsWith(";")) {
                        decodedTempCookieValue = decodedTempCookieValue.substring(1);
                    }
                    String[] list = decodedTempCookieValue.split(";");

                    boolean result = false;//[같은게 있어 > true] [없어 > false]

                    for (String listElement : list) {
                        if (listElement.equals(cookieValue)) {
                            result = true;
                        }
                    }
                    //쿠키의 Value를 파싱하고 파라미터 value가 같은게 있는가?
                    if (!result) {//파라미터의 키와 쿠키의 키에 따른 데이터들 중에 같은게 없으면 추가한다. 즉, 중복이 아니면 추가한다.

                        String cookieString;
                        if (tempCookie.getValue().equals("")) {//처음이면 10000
                            cookieString = cookieValue;
                        } else {//처음이 아니면 10000;10001
                            cookieString = decodedTempCookieValue + ";" + cookieValue;
                            System.out.println(cookieValue+"에 prodNo를 추가하였습니다.");
                        }
                        Cookie cookie = new Cookie(cookieKey, URLEncoder.encode(cookieString, "EUC-KR"));
                        cookie.setPath("/");
                        response.addCookie(cookie);
                        System.out.println("추가한 쿠키 : " + URLEncoder.encode(cookieString, "EUC-KR"));
                    }
                }
            } // end of for
        }//end of else
    }//end of addValue

}//end of class
