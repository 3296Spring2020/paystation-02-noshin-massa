/**
 * Implementation of the pay station.
 *
 * Responsibilities:
 *
 * 1) Accept payment; 
 * 2) Calculate parking time based on payment; 
 * 3) Know earning, parking time bought; 
 * 4) Issue receipts; 
 * 5) Handle buy and cancel events.
 *
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 *
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */

package edu.temple.cis.paystation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of the pay station.
 *
 * Responsibilities:
 *
 * 1) Accept payment; 
 * 2) Calculate parking time based on payment; 
 * 3) Know earning, parking time bought; 
 * 4) Issue receipts; 
 * 5) Handle buy and cancel events.
 *
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 *
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */
 public class PayStationImpl implements PayStation {
    
    private int insertedSoFar;
    private int timeBought;
    private int totalProfits;
    private int nickels, dimes, quarters;
    private Map<Integer, Integer> map = new HashMap<>();
    
    private HashSet<Integer> legalCoins = new HashSet();
    
    public PayStationImpl(){
        initLegalCoins();
    }
    
    private void initLegalCoins(){
        legalCoins.add(25);
        legalCoins.add(10);
        legalCoins.add(5);
    }
    

    

    @Override
    public void addPayment(int coinValue)
            throws IllegalCoinException {
        if(!legalCoins.contains(coinValue)){
            throw new IllegalCoinException("Invalid coin: " + coinValue);
        }
        if(!map.containsKey(coinValue)){
            map.put(coinValue, 1);
        } else {
            map.put(coinValue, map.get(coinValue) + 1);
        }
        insertedSoFar += coinValue;
        timeBought = insertedSoFar / 5 * 2;
    }

    @Override
    public int readDisplay() {
        return timeBought;
    }

    @Override
    public Receipt buy() {
        Receipt r = new ReceiptImpl(timeBought);
        totalProfits += insertedSoFar;
        reset();
        return r;
    }

    
    
    @Override
    public Map <Integer,Integer> cancel() {
        Map<Integer,Integer> mapCopy = new HashMap<>();
        mapCopy.putAll(map);  
        reset(); 
        return mapCopy;
        
    }
    
    private void reset() {
        timeBought = insertedSoFar = 0;
        nickels = 0;
        dimes = 0;
        quarters = 0; 
        map.clear(); 
    }
    
    @Override
    public int empty(){
        int earned = totalProfits;
        totalProfits = 0;
        reset();
        return earned;
    }
}