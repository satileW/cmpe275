package edu.sjsu.cmpe275.aop.aspect;

import org.springframework.beans.factory.annotation.Autowired;  // if needed

import edu.sjsu.cmpe275.aop.ProfileService;
import edu.sjsu.cmpe275.aop.exceptions.AccessDeniedExeption;
import edu.sjsu.cmpe275.aop.Profile;
import org.aspectj.lang.annotation.Aspect;  // if needed

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.aspectj.lang.JoinPoint;  // if needed
import org.aspectj.lang.annotation.After;  // if needed
import org.aspectj.lang.annotation.Before;  // if needed


@Aspect
public class AuthorizationAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advises as needed.
     */
	HashMap<String, HashSet<String>> shareList = new HashMap<String, HashSet<String>>();
	@Autowired public ProfileService profileService;
	@Before("execution(public void edu.sjsu.cmpe275.aop.ProfileService.shareProfile(..))")
	public void shareAuthortication(JoinPoint joinPoint) throws AccessDeniedExeption {
		Object[] args = joinPoint.getArgs();
		String userId = (String)args[0];
		String fileUserId = (String)args[1];
		if(userId.equals(fileUserId)){
			return ;
		}else if(shareList.containsKey(fileUserId)&&shareList.get(fileUserId).contains(userId)){
			return;
		}
		throw new AccessDeniedExeption("shareProfile error");
	}
	@After("execution(public void edu.sjsu.cmpe275.aop.ProfileService.shareProfile(..))")
	private void successShare(JoinPoint joinPoint){
		Object[] args = joinPoint.getArgs();
		String fileUserId = (String)args[1];
		String targetId = (String)args[2];
		HashSet<String> sharedUsers = null;
		if(shareList.containsKey(fileUserId)){
			sharedUsers = shareList.get(fileUserId);
		}else{
			sharedUsers = new HashSet<String>();
		}
		sharedUsers.add(targetId);
		shareList.put(fileUserId, sharedUsers);
	}
	@Before("execution(public void edu.sjsu.cmpe275.aop.ProfileService.unshareProfile(..))")
	public void unshareAuthortication(JoinPoint joinPoint) {
		//System.out.printf("before unshare %s\n", joinPoint.getSignature().getName());
		Object[] args = joinPoint.getArgs();
		String userId = (String)args[0];
		String targetUserId = (String)args[1];
		if(shareList.containsKey(userId)&&
				shareList.get(userId).contains(targetUserId)){
			return;
		}
		throw new AccessDeniedExeption("can not unshare");
	}
	@After("execution(public void edu.sjsu.cmpe275.aop.ProfileService.unshareProfile(..))")
	public void successUnshare(JoinPoint joinPoint) {
		//System.out.printf("after unshare %s\n", joinPoint.getSignature().getName());
		Object[] args = joinPoint.getArgs();
		String userId = (String)args[0];
		String targetUserId = (String)args[1];
		shareList.get(userId).remove(targetUserId);
	}
	
	@Before("execution(* edu.sjsu.cmpe275.aop.ProfileService.readProfile(..))")
	public void readfileAuthortication(JoinPoint joinPoint){
		Object[] args = joinPoint.getArgs();
		String userId = (String)args[0];
		String fileUserId = (String)args[1];
		if(userId.equals(fileUserId)||
				(shareList.containsKey(fileUserId)&&shareList.get(fileUserId).contains(userId))){
			System.out.println("Before ReadFile Authortication: "+userId +" can read file of "+ fileUserId);
			return ;
		}else{
			throw new
		AccessDeniedExeption("you don't have access to file"+fileUserId);
		}
	}
	
}
