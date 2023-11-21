import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


interface Observer {
    void update(Product product);
}


class PriceStockManager {
    private List<Product> products = new ArrayList<>();
    private List<Observer> observers;

    private Set<Product> discountedProducts = new HashSet<>();

    public PriceStockManager(List<Observer> observers) {
        this.observers = observers;
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }




    public void updatePricesAndStocks() {

        notifyObservers();
    }


    private void notifyObservers() {
        for (Product product : products) {
            if (product.getStock() == 0 && !discountedProducts.contains(product)) {
                double discountedPrice = product.getPrice() * 0.9;
                product.setDiscount(0.1);
                product.setPrice(discountedPrice);
                discountedProducts.add(product);
            }
        }

        for (Observer observer : observers) {
            // Notify the observer about all products once
            for (Product product : products) {
                observer.update(product);
            }
        }
    }
}

class UserNotifier implements Observer {
    @Override
    public void update(Product product) {
        if (product.getStock() == 1) {
            System.out.println("Attention! Only 1 unit of " + product.getName() + " left. Special discount applied!");

            double discountedPrice = product.getPrice() * 0.9;
            System.out.println("Discounted Price: $" + discountedPrice);

        }
    }
}