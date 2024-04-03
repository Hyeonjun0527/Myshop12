package myshop12.com.model2.mvc.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

public class ControllerLogAspect {
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[91m";
    private static final String ORANGE = "\u001B[38;5;208m";
    private static final String YELLOW = "\u001B[93m";
    private static final String GREEN = "\u001B[92m";
    private static final String BLUE = "\u001B[94m";
    ///Constructor
    public ControllerLogAspect() {
        System.out.println("\n 생성자 :: Common :: "+this.getClass()+"\n");
    }

    //Around  Advice
    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
        // 메서드 시그니처를 가져옴
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName(); // 메서드 이름

        // 시작 로그 출력
        System.out.print(GREEN);
        System.out.println("[[[[[[로그출력 : " + methodName + " : 시작되었습니다]]]]]]");

        Object obj;
        // 타겟 메서드 실행
        obj = joinPoint.proceed();
        // 종료 로그 출력
        System.out.println("[[[[[[로그출력 : " + methodName + " : 종료되었습니다]]]]]]");

        System.out.print(RESET);
        return obj;
    }
}
