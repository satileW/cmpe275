package edu.sjsu.cmpe275.aop;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.sjsu.cmpe275.aop.exceptions.AccessDeniedExeption;
import edu.sjsu.cmpe275.aop.exceptions.NetworkException;

public class App {
	public static void main(String[] args) {
        /***
         * Following is a dummy implementation of App to demonstrate bean creation with Application context.
         * You may make changes to suit your need, but this file is NOT part of the submission.
         */

    	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
        ProfileService profileService = (ProfileService) ctx.getBean("profileService");

        try {
        	profileService.shareProfile("Alice", "Alice", "Bob");
            profileService.readProfile("Bob", "Alice");
            profileService.unshareProfile("Alice", "Bob");
            profileService.unshareProfile("Alice", "Bob");
            profileService.shareProfile("Bob", "Alice", "Carl");
            profileService.readProfile("Bob", "Alice");
        } catch (AccessDeniedExeption e) {
        	System.err.println("Access Denied");
			// TODO: handle exception
		}catch (NetworkException e) {
			// TODO: handle exception
			System.err.println("Network Denied");
		}catch (Exception e) {
            e.printStackTrace();
        }
        ctx.close();
    }
}