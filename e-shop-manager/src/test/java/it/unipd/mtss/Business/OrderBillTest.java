////////////////////////////////////////////////////////////////////
// [Mattia] [Zanellato] [1222398]
// [Federico] [Marchi] [1224445]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.Business;

import it.unipd.mtss.Business.Exception.BillException;
import it.unipd.mtss.Model.EItem;
import it.unipd.mtss.Model.ItemType;
import it.unipd.mtss.Model.User;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class OrderBillTest {
    OrderBill orderBill;
    List<EItem> list;
    User user;

    @Before
    public void setup() {
        orderBill = new OrderBill();
        list = new ArrayList<>();
        user = new User(1, new Date(05 / 05 / 2000), "Mario", "Rossi");
    }



    /**
     * Test function getOrderPrice()
     */
    @Test
    public void testOrderPrice() throws BillException {
        list = Arrays.asList(
                new EItem(ItemType.Mouse, "Mouse1", 13.00),
                new EItem(ItemType.Processor, "Processor1", 9.40),
                new EItem(ItemType.Motherboard, "Motherboard1", 34.00),
                new EItem(ItemType.Keyboard, "Keyboard1", 22.00),
                new EItem(ItemType.Mouse, "Mouse2", 28.90),
                new EItem(ItemType.Motherboard, "Motherboard2", 11.00),
                new EItem(ItemType.Motherboard, "Motherboard2", 19.00));
        double sum = 13.00 + 9.40 + 34.00 + 22.00 + 28.90 + 11.00 + 19.00;
        double orderPrice = orderBill.getOrderPrice(list, user);
        assertEquals(sum, orderPrice, 0.001);
    }

    @Test (expected = BillException.class)
    public void testEmptyOrder() throws BillException {
        double orderPrice = orderBill.getOrderPrice(list, user);
    }

    @Test
    public void testMoreThanFiveProcessorOrder() throws BillException {
        list = Arrays.asList(
                new EItem(ItemType.Processor, "Processor1", 13.00),
                new EItem(ItemType.Processor, "Processor2", 9.40),
                new EItem(ItemType.Processor, "Processor3", 34.00),
                new EItem(ItemType.Processor, "Processor4", 22.00),
                new EItem(ItemType.Processor, "Processor5", 28.90));
        double sum = 13.00 + (9.40/2) + 34.00 + 22.00 + 28.90;
        double orderPrice = orderBill.getOrderPrice(list, user);
        assertEquals(sum, orderPrice, 0.001);
    }

    @Test
    public void testMoreThanTenMouseOrder() throws BillException {
        list = Arrays.asList(
                new EItem(ItemType.Mouse, "Mouse1", 13.00),
                new EItem(ItemType.Mouse, "Mouse2", 16.40),
                new EItem(ItemType.Mouse, "Mouse3", 34.00),
                new EItem(ItemType.Mouse, "Mouse4", 22.00),
                new EItem(ItemType.Mouse, "Mouse5", 28.90),
                new EItem(ItemType.Mouse, "Mouse6", 11.30),
                new EItem(ItemType.Mouse, "Mouse7", 31.20),
                new EItem(ItemType.Mouse, "Mouse8", 22.00),
                new EItem(ItemType.Mouse, "Mouse9", 25.90),
                new EItem(ItemType.Mouse, "Mouse10", 35.30));
        double sum = 13.00 + 16.40 + 34.00 + 22.00 + 28.90 + (11.30 * 0) + 31.20 + 22.00 + 25.90 + 35.30;
        double orderPrice = orderBill.getOrderPrice(list, user);
        assertEquals(sum, orderPrice, 0.001);
    }

    @Test
    public void testEqualKeyboardsAndMouses() throws BillException {
        list = Arrays.asList(
                new EItem(ItemType.Keyboard, "Keyboard1", 22.60),
                new EItem(ItemType.Keyboard, "Keyboard2", 19.20),
                new EItem(ItemType.Keyboard, "Keyboard3", 21.10),
                new EItem(ItemType.Mouse, "Mouse1", 33.00),
                new EItem(ItemType.Mouse, "Mouse2", 41.40),
                new EItem(ItemType.Mouse, "Mouse3", 34.00),
                new EItem(ItemType.Motherboard, "Motherboard1", 15.00));
        double sum = 22.60 + 19.20 + 21.10 + 33.00 + 41.40 + 34.00 + (15.00 * 0);
        double lessExp = orderBill.getOrderPrice(list, user);
        assertEquals(sum, lessExp, 0.001);
    }

    @Test
    public void testDifferentKeyboardsAndMouses()  throws BillException {
        list = Arrays.asList(
                new EItem(ItemType.Keyboard, "Keyboard1", 22.60),
                new EItem(ItemType.Keyboard, "Keyboard2", 19.20),
                new EItem(ItemType.Keyboard, "Keyboard3", 21.10),
                new EItem(ItemType.Mouse, "Mouse1", 33.00),
                new EItem(ItemType.Mouse, "Mouse2", 41.40),
                new EItem(ItemType.Motherboard, "Motherboard1", 15.00));
        double sum = 22.60 + 19.20 + 21.10 + 33.00 + 41.40 + 15.00;
        double lessExp = orderBill.getOrderPrice(list, user);
        assertEquals(sum, lessExp, 0.001);
    }



    /**
     * Test function getPriceOfLessExpensiveItem()
     */
    @Test
    public void testLessExpensiveItem() {
        list = Arrays.asList(
                new EItem(ItemType.Processor, "Processor1", 12.60),
                new EItem(ItemType.Processor, "Processor2", 9.20),
                new EItem(ItemType.Processor, "Processor3", 31.10),
                new EItem(ItemType.Processor, "Processor4", 21.80),
                new EItem(ItemType.Processor, "Processor5", 23.80));
        double lessExp = orderBill.getPriceOfLessExpensiveItem(list, ItemType.Processor, 5);
        assertEquals(9.20, lessExp, 0.001);
    }

    @Test
    public void testLessExpensiveItemInListWithNotEnoughItem() {
        list = Arrays.asList(
                new EItem(ItemType.Processor, "Processor1", 12.60),
                new EItem(ItemType.Processor, "Processor2", 9.20),
                new EItem(ItemType.Processor, "Processor3", 31.10),
                new EItem(ItemType.Processor, "Processor4", 21.80));
        double lessExp = orderBill.getPriceOfLessExpensiveItem(list, ItemType.Processor, 5);
        assertEquals(0, lessExp, 0.1);
    }

    @Test
    public void testLessExpensiveItemWithDifferentType() {
        list = Arrays.asList(
                new EItem(ItemType.Processor, "Processor1", 12.60),
                new EItem(ItemType.Processor, "Processor2", 9.20),
                new EItem(ItemType.Processor, "Processor3", 31.10),
                new EItem(ItemType.Processor, "Processor4", 21.80),
                new EItem(ItemType.Processor, "Processor5", 23.80));
        double lessExp = orderBill.getPriceOfLessExpensiveItem(list, ItemType.Mouse, 5);
        assertEquals(0, lessExp, 0.1);
    }

    

    /**
     * Test function getLessExpensiveItemWithEqualKeyboardsAndMouses()
     */
    @Test
    public void testGetLessExpensiveItemWithEqualKeyboardsAndMouses() {
        list = Arrays.asList(
                new EItem(ItemType.Keyboard, "Keyboard1", 22.60),
                new EItem(ItemType.Keyboard, "Keyboard2", 19.20),
                new EItem(ItemType.Keyboard, "Keyboard3", 21.10),
                new EItem(ItemType.Mouse, "Mouse1", 33.00),
                new EItem(ItemType.Mouse, "Mouse2", 41.40),
                new EItem(ItemType.Mouse, "Mouse3", 34.00),
                new EItem(ItemType.Motherboard, "Motherboard1", 15.00));
        double lessExp = orderBill.getLessExpensiveItemWithEqualKeyboardsAndMouses(list);
        assertEquals(15.00, lessExp, 0.001);
    }

    @Test
    public void testGetLessExpensiveItemWithDifferentKeyboardsAndMouses() {
        list = Arrays.asList(
                new EItem(ItemType.Keyboard, "Keyboard1", 22.60),
                new EItem(ItemType.Keyboard, "Keyboard2", 19.20),
                new EItem(ItemType.Keyboard, "Keyboard3", 21.10),
                new EItem(ItemType.Mouse, "Mouse1", 33.00),
                new EItem(ItemType.Mouse, "Mouse2", 41.40),
                new EItem(ItemType.Motherboard, "Motherboard1", 15.00));
        double lessExp = orderBill.getLessExpensiveItemWithEqualKeyboardsAndMouses(list);
        assertEquals(0, lessExp, 0.1);
    }
}