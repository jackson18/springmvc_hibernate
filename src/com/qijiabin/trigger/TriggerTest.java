package com.qijiabin.trigger;

import org.springframework.stereotype.Service;


@Service
public class TriggerTest {

	public void execute() {
		System.out.println("现在开始job......");
	}
}
