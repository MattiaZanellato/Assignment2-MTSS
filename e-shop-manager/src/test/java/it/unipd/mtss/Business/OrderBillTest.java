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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class OrderBillTest {
    OrderBill orderBill;
    List<EItem> list;
    User user;

    @Before
    public void setup() {
        list = new ArrayList<>();
        user = new User(1, LocalDate.parse("2000-05-05"), "Mario", "Rossi");
        String str = "2000-05-05 18:19";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        orderBill = new OrderBill(dateTime, user);
    }


    /**
     * Test Function getDateTime()
     */
    @Test 
    public void testGetDateTime() {
        String str = "2000-05-05 18:19";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

        orderBill = new OrderBill(dateTime, user);

        assertEquals(dateTime, orderBill.getDateTime());
    }



    /**
     * Test Function getRemainedGifted() e setRemainedGifted()
     */
    @Test 
    public void testGetRemainedGifted() {
        orderBill.setRemainedGifted(5);

        assertEquals(5, orderBill.getRemainedGifted());
    }



    /**
     * Test Function getIsGifted() e setIsGifted()
     */
    @Test 
    public void testIsGiftedTrue() {
        orderBill.setGifted(true);

        assertEquals(true, orderBill.getGifted());
    }

    @Test 
    public void testIsGiftedFlase() {
        orderBill.setGifted(false);

        assertEquals(false, orderBill.getGifted());
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

    @Test(expected = BillException.class)
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
        double sum = 13.00 + (9.40 / 2) + 34.00 + 22.00 + 28.90;

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

        double orderPrice = orderBill.getOrderPrice(list, user);

        assertEquals(sum, orderPrice, 0.001);
    }

    @Test
    public void testDifferentKeyboardsAndMouses() throws BillException {
        list = Arrays.asList(
                new EItem(ItemType.Keyboard, "Keyboard1", 22.60),
                new EItem(ItemType.Keyboard, "Keyboard2", 19.20),
                new EItem(ItemType.Keyboard, "Keyboard3", 21.10),
                new EItem(ItemType.Mouse, "Mouse1", 33.00),
                new EItem(ItemType.Mouse, "Mouse2", 41.40),
                new EItem(ItemType.Motherboard, "Motherboard1", 15.00));
        double sum = 22.60 + 19.20 + 21.10 + 33.00 + 41.40 + 15.00;

        double orderPrice = orderBill.getOrderPrice(list, user);

        assertEquals(sum, orderPrice, 0.001);
    }

    @Test
    public void testMoreThan1000EurosBill() throws BillException {
        list = Arrays.asList(
                new EItem(ItemType.Keyboard, "Keyboard1", 150.60),
                new EItem(ItemType.Keyboard, "Keyboard2", 220.20),
                new EItem(ItemType.Keyboard, "Keyboard3", 110.10),
                new EItem(ItemType.Mouse, "Mouse1", 260.00),
                new EItem(ItemType.Mouse, "Mouse2", 250.40),
                new EItem(ItemType.Motherboard, "Motherboard1", 180.00));
        double sum = (150.60 + 220.20 + 110.10 + 260.00 + 250.40 + 180.00) - 117.13;

        double orderPrice = orderBill.getOrderPrice(list, user);

        assertEquals(sum, orderPrice, 0.001);
    }

    @Test(expected = BillException.class)
    public void testMoreThan30Items() throws BillException {
        for (int i = 0; i < 31; i++) {
            list.add(new EItem(ItemType.Keyboard, "Keyboard" + (i + 1), 150.60));
        }

        double orderPrice = orderBill.getOrderPrice(list, user);
    }

    @Test
    public void testLessThan10EurosBill() throws BillException {
        list = Arrays.asList(
                new EItem(ItemType.Keyboard, "Keyboard1", 5.50),
                new EItem(ItemType.Keyboard, "Keyboard2", 3.30));
        double sum = 5.50 + 3.30 + 2;

        double orderPrice = orderBill.getOrderPrice(list, user);

        assertEquals(sum, orderPrice, 0.001);
    }

    @Test
    public void testMax10OrderGifted() throws BillException {
        list = Arrays.asList(
                new EItem(ItemType.Mouse, "Mouse1", 13.00),
                new EItem(ItemType.Processor, "Processor1", 9.40),
                new EItem(ItemType.Motherboard, "Motherboard1", 34.00),
                new EItem(ItemType.Keyboard, "Keyboard1", 22.00),
                new EItem(ItemType.Mouse, "Mouse2", 28.90),
                new EItem(ItemType.Motherboard, "Motherboard2", 11.00),
                new EItem(ItemType.Motherboard, "Motherboard2", 19.00));
        User user;
        OrderBill order;
        int count = 0;

        for (int i = 0; i < 100; i++) {
            user = new User(i, LocalDate.parse("2006-05-05"), "Mario" + i, "Rossi" + i);
            String str = "2000-05-05 18:19";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
            order = new OrderBill(dateTime, user);
            if (order.getOrderPrice(list, user) == 0) {
                count++;
            }
        }

        assert count <= 10;
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



    /**
     * Test Function isGifted()
     */
    @Test
    public void testOrderIsGiftedTrue() {
        user = new User(1, LocalDate.parse("2006-05-05"), "Mario", "Rossi");

        boolean verify = orderBill.isGifted(user, 0);

        assertEquals(true, verify);
    }

    @Test
    public void testOrderIsGiftedFalseForHour() {
        user = new User(1, LocalDate.parse("2006-05-05"), "Mario", "Rossi");
        String str = "2000-05-05 15:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        orderBill = new OrderBill(dateTime, user);

        boolean verify = orderBill.isGifted(user, 0);

        assertEquals(false, verify);
    }

    @Test
    public void testOrderIsGiftedFalseForBirthDate() {
        user = new User(1, LocalDate.parse("2000-05-05"), "Mario", "Rossi");

        boolean verify = orderBill.isGifted(user, 0);

        assertEquals(false, verify);
    }

    @Test
    public void testOrderIsGiftedFalseForRandomProbabilityOfGift() {
        user = new User(1, LocalDate.parse("2006-05-05"), "Mario", "Rossi");

        boolean verify = orderBill.isGifted(user, 5);

        assertEquals(false, verify);
    }

    @Test
    public void testOrderIsGiftedFalseForProbabilityOfGift() {
        user = new User(1, LocalDate.parse("2006-05-05"), "Mario", "Rossi");
        orderBill.setRemainedGifted(0);

        boolean verify = orderBill.isGifted(user, 0);

        assertEquals(false, verify);
    }
}