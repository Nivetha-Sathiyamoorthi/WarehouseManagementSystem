package com.wipro.wms.util;

public class InvalidItemException extends Exception {
	
	public String toString() {
		return "InvalidException: Item does not exist in warehouse";
	}

}
