package com.wipro.wms.service;
import java.util.ArrayList;
import com.wipro.wms.entity.*;
import com.wipro.wms.util.*;

public class WarehouseService {
	 ArrayList<Item> items;
	    ArrayList<Location> locations;
	    ArrayList<WarehouseTransaction> transactions;
public WarehouseService(
	    		ArrayList<Item> items,
	            ArrayList<Location> locations,
	           ArrayList<WarehouseTransaction> transactions) {

	        this.items = items;
	        this.locations = locations;
	        this.transactions = transactions;
	    }
public void validateItem(String itemId) throws InvalidItemException {
	        boolean found = false;

	        for (Item i : items) {
	            if (i.getItemId().equals(itemId)) {
	                found = true;
	                break;
	            }
	        }
           if (!found) {
	            throw new InvalidItemException();
	        }
	    }
public void validateLocation(String locationId) throws InvalidLocationException {
	        boolean found = false;
            for (Location l : locations) {
	            if (l.getLocationId().equals(locationId)) {
	                found = true;
	                break;
	            }
	        }

	        if (!found) {
	            throw new InvalidLocationException();
	        }
	    }
 public WarehouseTransaction inbound(String itemId, String locationId, int qty)
	            throws Exception {

	        validateItem(itemId);
	        validateLocation(locationId);

	        Item item = null;
	        Location location = null;

	        for (Item i : items) {
	            if (i.getItemId().equals(itemId)) {
	                item = i;
	            }
	        }

	        for (Location l : locations) {
	            if (l.getLocationId().equals(locationId)) {
	                location = l;
	            }
	        }
	        if (location.getCurrentLoad() + qty > location.getCapacity()) {
	            throw new TransactionException();
	        }
          item.setTotalQuantity(item.getTotalQuantity() + qty);
	        location.setCurrentLoad(location.getCurrentLoad() + qty);String transactionId = "T" + (transactions.size() + 1);
	        String date = "2026-01-21";
       WarehouseTransaction wt =new WarehouseTransaction(transactionId, itemId, locationId,
	 "INBOUND", qty, date);
                 transactions.add(wt);
	             return wt;
	    }
 public WarehouseTransaction outbound(String itemId, String locationId, int qty)
	            throws Exception {
            validateItem(itemId);
	        validateLocation(locationId);
            Item item = null;
	        Location location = null;
            for (Item i : items) {
	            if (i.getItemId().equals(itemId)) {
	                item = i;
	            }
	        }
           for (Location l : locations) {
	            if (l.getLocationId().equals(locationId)) {
	                location = l;
	            }
	        }
	        if (item.getTotalQuantity() < qty || location.getCurrentLoad() < qty) {
	            throw new InsufficientStockException();
	        }
            item.setTotalQuantity(item.getTotalQuantity() - qty);
	        location.setCurrentLoad(location.getCurrentLoad() - qty);
           String transactionId = "T" + (transactions.size() + 1);
	        String date = "2026-01-21";
               WarehouseTransaction wt =
	            new WarehouseTransaction(transactionId, itemId, locationId,
	            "OUTBOUND", qty, date);
                         transactions.add(wt);
	                  return wt;
	    }
 public void printStockSummary() {
	        for (Item i : items) {
	            System.out.println(i.getItemName() + " : " + i.getTotalQuantity());
	        }
	    }
 public void printLocationSummary() {
	        for (Location l : locations) {
	            System.out.println(l.getDescription() + " | Load: " + l.getCurrentLoad() + "/" + l.getCapacity());
	        }
	    }
public void printItemHistory(String itemId) {
	        for (WarehouseTransaction t : transactions) {
	            if (t.getItemId().equals(itemId)) {
	                System.out.println(t.getTransactionType() +   " " + t.getQuantity() + " on " + t.getDate());
	            }
	        }
	    }

}
