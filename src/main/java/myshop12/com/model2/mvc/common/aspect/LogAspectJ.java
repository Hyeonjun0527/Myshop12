package myshop12.com.model2.mvc.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/*
 * FileName : PojoAspectJ.java
 *	:: XML 에 선언적으로 aspect 의 적용   
  */
@Aspect
@Component
public class LogAspectJ {
	///Field
	private static final String RESET = "\u001B[0m";
	private static final String RED = "\u001B[91m";
	private static final String ORANGE = "\u001B[38;5;208m";
	private static final String YELLOW = "\u001B[93m";
	private static final String GREEN = "\u001B[92m";
	private static final String BLUE = "\u001B[94m";
	///Constructor
	public LogAspectJ() {
		System.out.println("\n 생성자 :: Common :: "+this.getClass()+"\n");		
	}
	
	//Around  Advice
	@Around("execution(* myshop12.com.model2.mvc.*.service.Impl.*Impl(..))")
	public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
			
		System.out.print(RED);
		System.out.println("[Around before] 타겟객체.메서드 :"+
													joinPoint.getTarget().getClass().getName() +"."+
													joinPoint.getSignature().getName());
		if(joinPoint.getArgs().length !=0){
			System.out.println("[Around before]method에 전달되는 인자 : "+ joinPoint.getArgs()[0]);
		}
		//==> 타겟 객체의 Method 를 호출 하는 부분 
		Object obj = joinPoint.proceed();

		System.out.println("[Around after] 타겟 객체return value  : "+obj);
		System.out.print(RESET);
		
		return obj;
	}
	
}//end of class