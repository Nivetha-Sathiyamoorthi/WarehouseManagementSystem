package com.wipro.wms.util;

public class InvalidLocationException extends Exception {
	public String toString() {
        return "InvalidLocationException: Location is invalid or does not exist";
    }

}
