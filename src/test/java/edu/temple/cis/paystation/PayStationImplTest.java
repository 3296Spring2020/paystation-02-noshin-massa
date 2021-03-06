/**
 * Testcases for the Pay Station system.
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

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import java.util.*;

public class PayStationImplTest {

    PayStation ps;
    
    @Before
    public void setup() {
        ps = new PayStationImpl();
    }

    /**
     * Entering 5 cents should make the display report 2 minutes parking time.
     * 
     * @throws IllegalCoinException
     */
    @Test
    public void shouldDisplay2MinFor5Cents()
            throws IllegalCoinException {
        ps.addPayment(5);
        assertEquals("Should display 2 min for 5 cents",
                2, ps.readDisplay());
    }

    /**
     * Entering 25 cents should make the display report 10 minutes parking time.
     * 
     * @throws IllegalCoinException
     */
    @Test
    public void shouldDisplay10MinFor25Cents() throws IllegalCoinException {
        ps.addPayment(25);
        assertEquals("Should display 10 min for 25 cents",
                10, ps.readDisplay());
    }

    /**
     * Verify that illegal coin values are rejected.
     * 
     * @throws IllegalCoinException
     */
    @Test(expected = IllegalCoinException.class)
    public void shouldRejectIllegalCoin() throws IllegalCoinException {
        ps.addPayment(17);
    }

    /**
     * Entering 10 and 25 cents should be valid and return 14 minutes parking
     * 
     * @throws IllegalCoinException
     */
    @Test
    public void shouldDisplay14MinFor10And25Cents()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(25);
        assertEquals("Should display 14 min for 10+25 cents",
                14, ps.readDisplay());
    }

    /**
     * Buy should return a valid receipt of the proper amount of parking time
     * 
     * @throws IllegalCoinException
     */
    @Test
    public void shouldReturnCorrectReceiptWhenBuy()
            throws IllegalCoinException {
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(25);
        Receipt receipt;
        receipt = ps.buy();
        assertNotNull("Receipt reference cannot be null",
                receipt);
        assertEquals("Receipt value must be 16 min.",
                16, receipt.value());
    }

    /**
     * Buy for 100 cents and verify the receipt
     * 
     * @throws IllegalCoinException
     */
    @Test
    public void shouldReturnReceiptWhenBuy100c()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(25);
        ps.addPayment(25);

        Receipt receipt;
        receipt = ps.buy();
        assertEquals(40, receipt.value());
    }

    /**
     * Verify that the pay station is cleared after a buy scenario
     * 
     * @throws IllegalCoinException
     */
    @Test
    public void shouldClearAfterBuy()
            throws IllegalCoinException {
        ps.addPayment(25);
        ps.buy(); // I do not care about the result
        // verify that the display reads 0
        assertEquals("Display should have been cleared",
                0, ps.readDisplay());
        // verify that a following buy scenario behaves properly
        ps.addPayment(10);
        ps.addPayment(25);
        assertEquals("Next add payment should display correct time",
                14, ps.readDisplay());
        Receipt r = ps.buy();
        assertEquals("Next buy should return valid receipt",
                14, r.value());
        assertEquals("Again, display should be cleared",
                0, ps.readDisplay());
    }

    /**
     * Verify that cancel clears the pay station
     * 
     * @throws IllegalCoinException
     */
    @Test
    public void shouldClearAfterCancel()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.cancel();
        assertEquals("Cancel should clear display",
                0, ps.readDisplay());
        ps.addPayment(25);
        assertEquals("Insert after cancel should work",
                10, ps.readDisplay());
    }
    
    
    /**
     * Verify that a call to empty() correctly returns the machine's profits
     * 
     * Test #1 in list
     * 
     * @see edu.temple.cis.paystation.PayStation#empty()
     * @throws IllegalCoinException 
     */
    @Test
    public void callToEmptyReturnsProfits() throws IllegalCoinException {
        //in case something else was happening
        ps.cancel();
        ps.empty();
        //start test
        ps.addPayment(10);
        ps.buy();
        assertEquals("Paystation should've made 10", 10, ps.empty());
    }
    
    /**
     * Verify that a call to cancel doesn't result in incorrect profits
     * 
     * Test #2 in list
     * 
     * @throws IllegalCoinException
     */
    @Test
    public void callToCancelDoesntAddToProfitsReturnedByEmpty() throws IllegalCoinException {
        //in case something is in progress 
        ps.cancel();
        ps.empty();
        //start test
        ps.addPayment(25);
        ps.buy();
        ps.addPayment(25);
        ps.cancel();
        int profits = ps.empty();
        assertEquals("Profits weren't properly calculated after cancel of purchase", 25, profits);
    }
    
    /**
     * Verify call to empty empties the profit
     * 
     * Test #3 in list
     * 
     * @see edu.temple.cis.paystation.PayStation#empty()
     */
    @Test
    public void callToEmptyClearsProfits() {
        ps.cancel();    //in case something else was in progress
        ps.empty();
        assertEquals("Paystation should've been emptied", 0, ps.readDisplay());
    }
    
    /**
     * Verify a call to cancel returns at least one coin that was entered
     * 
     * Test #4 in list
     * 
     * @throws IllegalCoinException
     */
    @Test
    public void callToCancelReturnsOneCoinEntered() throws IllegalCoinException {
        ps.cancel();
        ps.buy();   //reset ps
        ps.addPayment(25);
        Map<Integer, Integer> m = ps.cancel();
        assertEquals("Cancel should return coin entered.", 1, m.get(25).intValue());
    }
    
     /**
     * Verify that cancel clears the map
     * 
     * @throws IllegalCoinException
     */
    @Test
    public void clearMapOnCancel() throws IllegalCoinException {
        Map<Integer, Integer> map;
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(25);
        ps.cancel();
        map = ps.cancel();
        int size;
        size = map.size();
        assertEquals("Map has no keys after cancel.", 0,size);
    }
    
    /**
     * Verify that buying time clears the map
     * 
     * @throws IllegalCoinException
     */
    @Test
    public void clearMapOnBuy() throws IllegalCoinException {
        Map<Integer, Integer> map;
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(25);
        ps.buy();
        map = ps.cancel();
        int mapSize;
        mapSize = map.size();
        assertEquals("Map has no keys after buying time.", 0, mapSize);
    }
    
    
    /**
     * Verify that the map doesn't contain a key for a coin not entered
     */
    @Test
    public void NoCoinKey () throws IllegalCoinException {
         ps.addPayment(10);
         ps.addPayment(5);
         Map<Integer, Integer> ret = ps.cancel();
        assertTrue("Does not contain quarter ", !ret.containsKey(25));         
    }
    
    /**
     * Verify that the map contains a mixture of coins
     */
    @Test
    public void MixtureOfCoins() throws IllegalCoinException {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(10, 2);
        map.put(5,1);
        
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(5);
       
        Map<Integer, Integer> ret = ps.cancel();
        
        assertEquals("Maps are equal", ret, map);
        
        
    }
    
}
