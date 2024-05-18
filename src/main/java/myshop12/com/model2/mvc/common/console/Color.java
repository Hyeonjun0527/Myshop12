package myshop12.com.model2.mvc.common.console;

/*
enum 클래스는 고정된 인스턴스 집합을 가지고 있다.

 */
public enum Color {
    //인스턴스들을 정의+초기화 하도록 한다.
    //enum의 인스턴스들이다.
    //열거형 상수라고도 한다.
    RESET("\u001B[0m"),
    RED("\u001B[91m"),
    ORANGE("\u001B[38;5;208m"),
    YELLOW("\u001B[93m"),
    GREEN("\u001B[92m"),
    BLUE("\u001B[94m");

    //enum의 필드이다.
    //또한 enum의 각 인스턴스의 필드이다.
    private final String code;


    //이 생성자에 맞춰서 위에서 정의한 인스턴스들이 초기화됨(내부의 필드에 값을 넣어줌)
    Color(String code) {
        this.code = code;
    }

    //Color.RED.getCode()하면 "\u001B[91m"이 반환됨
    public String getCode() {
        return code;
    }
    //Color.RED하면 "\u001B[91m"이 반환됨
    @Override
    public String toString() {
        return code;
    }
}

/*
* (11)RED ORANGE YELLOW GREEN BLUE RESET이라는 인스턴스 만들거야 (20)그 안에 code라는 필드 만들거야
* (24)생성자 호출할때 code값을 받도록 하고, 받은 값을 필드에 할당할거야.
* (34)인스턴스를 호출하면 그 인스턴스의 code라는 필드값을 리턴할거야.
* */