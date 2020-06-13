package com.techelevator;

import java.util.concurrent.TimeUnit;

public class Shakers {

	public Shakers() {
	}

	public void shakeSuccess() throws InterruptedException {
		while (true) {
			TimeUnit.SECONDS.sleep(1);
			
			int random = (int) (Math.random() * 10);
			int time = (int) (Math.random() * 3);
			
			if (random >= 9) {
				TimeUnit.SECONDS.sleep(time);
				System.out.printf("%nScore, you got a free bag of chips!");
				TimeUnit.SECONDS.sleep(2);
				System.out.printf("%n");
				break;
			} else {
				shakeAndKick(random, time);
			}
		}
	}
	
	public void shakeFailure() throws InterruptedException {
		while (true) {
			TimeUnit.SECONDS.sleep(1);

			int random = (int) (Math.random() * 10);
			int time = (int) (Math.random() * 3);
			
			if (random >= 9) {
				TimeUnit.SECONDS.sleep(time);
				System.out.printf("%nNice try, but you're gonna have to do better than that!");
				TimeUnit.SECONDS.sleep(2);
				System.out.printf("%n");
				break;
			} else {
				shakeAndKick(random, time);
			}
		}
	}
	
	public void shakeAndKick(int random, int time) throws InterruptedException {
		switch(random) {
		case 1:
			TimeUnit.SECONDS.sleep(time);
			System.out.printf("%nCrunch%n");
			break;
		case 2:
			TimeUnit.SECONDS.sleep(time);
			System.out.printf("%nCrack%n");
			break;
		case 3:
			TimeUnit.SECONDS.sleep(time);
			System.out.printf("%nSnap%n");
			break;
		case 4:
			TimeUnit.SECONDS.sleep(time);
			System.out.printf("%n~Heavy Breathing~%n");
			break;
		case 5:
			TimeUnit.SECONDS.sleep(time);
			System.out.printf("%n~Sigh~%n");
			break;
		case 6:
			TimeUnit.SECONDS.sleep(time);
			System.out.printf("%nAhhhhhhhh!%n");
			break;
		case 7:
			TimeUnit.SECONDS.sleep(time);
			System.out.printf("%nClunk%n");
			break;
		default:
			TimeUnit.SECONDS.sleep(time);
			System.out.printf("%nBang%n");
			break;
			
		}
	}
	
	

}
