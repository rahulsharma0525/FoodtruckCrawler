package com.finder.request;

import com.finder.request.service.ServiceImpl;

public class FoodTruckFinder {

	public static void main(String[] args) {
		ServiceImpl finder = new ServiceImpl();
		finder.process();
  }
	
}
