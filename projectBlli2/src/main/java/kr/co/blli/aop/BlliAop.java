package kr.co.blli.aop;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class BlliAop {
	/*@Around("execution(public * kr.co.blli.model.scheduler.*Scheduler.logList(..))")
	public void checkScheduler(ProceedingJoinPoint point) throws Throwable{
		Logger logger = Logger.getLogger(getClass());
		ArrayList<String> logList = (ArrayList<String>)point.getArgs()[0];
		point.proceed();
		ArrayList<String> logList = (ArrayList<String>)point.proceed();
		for(int i=0;i<logList.size();i++){
			logger.info(logList.get(i));
		}
	}*/
}