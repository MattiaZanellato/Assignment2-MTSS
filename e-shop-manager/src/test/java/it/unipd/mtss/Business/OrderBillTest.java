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

    @Test
    public void testOrderPrice() throws BillException {
        list = Arrays.asList(
                new EItem(ItemType.Mouse, "Mouse1", 13.00),
                new EItem(ItemType.Processor, "Processor1", 9.40),
                new EItem(ItemType.Motherboard, "Motherboard1", 34.00),
                new EItem(ItemType.Keyboard, "Keyboard1", 22.00),
                new EItem(ItemType.Mouse, "Mouse2", 28.90),
                new EItem(ItemType.Motherboard, "Motherboard2", 11.00),
                new EItem(ItemType.Keyboard, "Keyboard2", 19.00));
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
                new EItem(ItemType.Processor, "Processor5", 28.90),
                new EItem(ItemType.Mouse, "Mouse1", 35.30),
                new EItem(ItemType.Keyboard, "Keyboard1", 46.20));
        double sum = 13.00 + (9.40/2) + 34.00 + 22.00 + 28.90 + 35.30 + 46.20;
        double orderPrice = orderBill.getOrderPrice(list, user);
        assertEquals(sum, orderPrice, 0.001);
    }
}