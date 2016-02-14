package kr.co.blli.aop;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class BlliAop {
	@Around("execution(public * kr.co.blli.model.scheduler.*Scheduler.*(..))")
	public ArrayList<String> checkScheduler(ProceedingJoinPoint point) throws Throwable{
		Logger logger = Logger.getLogger(getClass());
		ArrayList<String> logList = (ArrayList<String>)point.proceed();
		for(int i=0;i<logList.size();i++){
			logger.info(logList.get(i));
		}
		return null;
	}
}