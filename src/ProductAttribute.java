import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

interface ProductAttribute {
    String getDescription();
    void customize();
}


class GiftWrapping implements ProductAttribute {
    private String description;

    public GiftWrapping(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return "Gift Wrapping: " + description;
    }

    @Override
    public void customize() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter gift wrapping color: ");
        String color = scanner.nextLine();
        System.out.println("Gift wrapping color set to: " + color);
    }
}

class Engraving implements ProductAttribute {
    private String description;

    public Engraving(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return "Engraving: " + description;
    }

    @Override
    public void customize() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter engraving text: ");
        String text = scanner.nextLine();
        System.out.println("Engraving text set to: " + text);
    }
}




class Product {
    private String name;
    private String description;
    private double price;
    private int stock;
    private List<ProductAttribute> attributes;
    private double weight;
    private double discount;
    private List<Observer> observers = new ArrayList<>();


    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    public Product(String name, String description, double price, int stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.attributes = new ArrayList<>();

    }


    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (this.stock != stock) {
            this.stock = stock;
            notifyObservers();
        }
    }


    public void setDiscount(double discount) {
        if (this.discount != discount) {
            this.discount = discount;
            notifyObservers();
        }
    }


    public void addAttribute(ProductAttribute attribute) {
        attributes.add(attribute);
    }

    public List<ProductAttribute> getAttributes() {
        return new ArrayList<>(attributes);
    }

    public double getWeight() {
        return weight;
    }


    public void setPrice(double price) {
        if (this.price != price) {
            this.price = price;
            notifyObservers();
        }
    }
}

