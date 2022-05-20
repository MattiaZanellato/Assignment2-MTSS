package it.unipd.mtss.Business;

import it.unipd.mtss.Business.Exception.BillException;
import it.unipd.mtss.Model.EItem;
import it.unipd.mtss.Model.User;

import java.util.List;

public class OrderBill implements Bill {

    @Override
    public double getOrderPrice(List<EItem> itemsOrdered, User user) throws BillException {
        double sum = 0;
        for (EItem item : itemsOrdered) {
             sum += item.getPrice();
        }
        if (sum != 0) return sum;
        else throw new BillException("Empty bill");
    }
}
