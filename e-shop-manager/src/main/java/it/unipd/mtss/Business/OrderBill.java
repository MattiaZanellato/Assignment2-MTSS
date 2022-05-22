////////////////////////////////////////////////////////////////////
// [Mattia] [Zanellato] [1222398]
// [Federico] [Marchi] [1224445]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.Business;

import it.unipd.mtss.Business.Exception.BillException;
import it.unipd.mtss.Model.EItem;
import it.unipd.mtss.Model.ItemType;
import it.unipd.mtss.Model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

public class OrderBill implements Bill {
    private static int remainedGifted = 10;

    private LocalDateTime dateTime;
    private boolean gifted;

    public OrderBill(LocalDateTime dateTime, User user) {
        this.dateTime = dateTime;
        int random = (int)(Math.random()*10);
        this.gifted = this.isGifted(user, random);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getRemainedGifted() {
        return remainedGifted;
    }

    public boolean getGifted() {
        return gifted;
    }

    public void setRemainedGifted(int remainedGifted) {
        OrderBill.remainedGifted = remainedGifted;
    }

    public void setGifted(boolean gifted) {
        this.gifted = gifted;
    }

    @Override
    public double getOrderPrice(List<EItem> itemsOrdered, User user) throws BillException {
        double sum = 0;

        if (itemsOrdered.isEmpty()) {
            throw new BillException("Empty Bill");
        }

        // Funzionalità 6
        if (itemsOrdered.size() > 30) {
            throw new BillException("More than 30 items");
        }

        // Funzionalità 8
        if (gifted) {
            return 0;
        }

        // Funzionalità 1
        for (EItem item : itemsOrdered) {
            sum += item.getPrice();
        }

        // Funzionalità 2
        double processorPrice = getPriceOfLessExpensiveItem(itemsOrdered, ItemType.Processor, 5);

        // Funzionalità 3
        double mousePrice = getPriceOfLessExpensiveItem(itemsOrdered, ItemType.Mouse, 10);

        // Funzionalità 4
        double lessExpPrice = getLessExpensiveItemWithEqualKeyboardsAndMouses(itemsOrdered);

        double discountedSum = sum - processorPrice / 2 - mousePrice - lessExpPrice;

        // Funzionalità 5
        if (discountedSum > 1000) {
            return discountedSum * 9 / 10;
        } else if (discountedSum < 10) { // Funzionalità 7
            return discountedSum + 2;
        }

        return discountedSum;
    }

    // Ritorna il prezzo dell'articolo del tipo specificato se il numero di item di
    // quel tipo è maggiore o uguale a n
    public double getPriceOfLessExpensiveItem(List<EItem> itemsOrdered, ItemType type, int n) {
        int count = 0;
        double lessExp = 0;
        for (EItem item : itemsOrdered) {
            if (item.getItemType() == type) {
                count++;
                if (lessExp == 0 || lessExp > item.getPrice()) {
                    lessExp = item.getPrice();
                }
            }
        }
        if (count >= n) {
            return lessExp;
        }
        return 0;
    }

    // Ritorna il prezzo dell'articolo meno caro se il numero di tastiere e mouse
    // sono uguali
    public double getLessExpensiveItemWithEqualKeyboardsAndMouses(List<EItem> itemsOrdered) {
        int countK = 0;
        int countM = 0;
        double lessExp = 0;
        for (EItem item : itemsOrdered) {
            if (item.getItemType() == ItemType.Keyboard) {
                countK++;
            } else if (item.getItemType() == ItemType.Mouse) {
                countM++;
            }
            if (lessExp == 0 || lessExp > item.getPrice()) {
                lessExp = item.getPrice();
            }
        }
        if (countK == countM && countK > 0) {
            return lessExp;
        }
        return 0;
    }

    // Ritorna true se l'ordine è regalato, false altrimenti
    public boolean isGifted(User user, int random) {
        int hour = dateTime.getHour();
        LocalDate dateAdult = LocalDate.now().minus(Period.ofYears(18));
        if ((hour >= 18 && hour < 19) && (user.getBirthDate().isAfter(dateAdult)) && random == 0 && remainedGifted > 0) {
            remainedGifted--;
            return true;
        }
        return false;
    }
}
