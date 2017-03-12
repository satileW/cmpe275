package edu.sjsu.cmpe275.aop.aspect;
import org.aspectj.lang.annotation.Aspect;  // if needed

import edu.sjsu.cmpe275.aop.exceptions.NetworkException;

import org.aspectj.lang.annotation.Around;  // if needed
import org.aspectj.lang.ProceedingJoinPoint; // if needed

@Aspect
public class RetryAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     * @throws Throwable 
     */

	   private static final int MAX_RETRY_TIMES = 2;
	    /***
	     * Following is a dummy implementation of this aspect.
	     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
	     */

	   @Around("execution(public void edu.sjsu.cmpe275.aop.ProfileService.*(..))")
	   public void dummyAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		 // System.out.printf("Prior to the executuion of the metohd %s\n", joinPoint.getSignature().getName());
		 // Can do sth. here, equals before advice

	      int currentRetryTimes = 0;
	      while (true){
	          try {
	             joinPoint.proceed();
	             //no exception, return around check, continue main operation
	             return;
	          }catch (NetworkException e){
	             //catch exception, judge the numbers of exceptions, if reach 3, throw network exception
	             if(currentRetryTimes == MAX_RETRY_TIMES){
	                throw e;
	             }
	             currentRetryTimes++;
	          }
	       }
	   }
}