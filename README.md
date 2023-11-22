# E-COMMERCE - Final Project

This is the repository for the final project of the "Software Design Patterns" course (SE-2226). 

## Group Members

- Alikhan Sayat
- Adilet Zhumabai
- Anarbekov Agzam

Teaching Assistant: Arailym Bakenova

---

## Project Overview

Project Overview

1) We have created a project about e-commerce. We have pc accessories and components. User can add necessary items to the shopping cart and pay for them. Also, users can authenticate, view all products, search them and add them to their cards, pay for them and see information about delivery. Payment methods are: CreditCard and Kaspi. Also delivery methods: Courier, Post Office delivery. We used a database for storage products of our shop. Also, we did a simple interface in the console for users convenience.

2) Idea of the project was to implement 6 patterns into an e-commerce project. Understand and create a simple program which is going to be the prototype of a computer shop with necessary products, some payments and variation of delivery.

3) Purpose of the project is to combine and make a logical structure of the shop with only important methods which we can see from any shop in the city. Also connect database for storage information and products.

4) The objective of the work: authenticate the user and let him view all products and give him opportunity to add interested products to the shopping cart and give him some variation of payment and delivery methods.

---

## UML Diagram

![UML Diagram](https://github.com/TKECH1/SDPFinal/blob/main/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA1.png)

---

## Features / Design Patterns

1) User Authentication:

- Users can register with a username and password.
```java
public static void main(String[] args) {

        AuthenticationManager authenticationManager = new AuthenticationManager();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the E-Commerce System!");

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        authenticationManager.registerUser(username, password);

        System.out.print("Enter your username for authentication: ");
        String authUsername = scanner.nextLine();
        System.out.print("Enter your password for authentication: ");
        String authPassword = scanner.nextLine();
```
2) Shopping Cart:

- Users have a shopping cart to add and remove products.
```java
public void addToCart(Product product) {
        cartItems.add(product);
        System.out.println(product.getName() + " added to the cart.");

    }


    public void removeFromCart(Product product, int quantityToRemove) {
        int occurrencesInCart = countOccurrencesInCart(product);

        if (quantityToRemove <= 0 || quantityToRemove > occurrencesInCart) {
            System.out.println("Invalid quantity. No product removed from the cart.");
            return;
        }

        for (int i = 0; i < quantityToRemove; i++) {
            cartItems.remove(product);
        }

        System.out.println(quantityToRemove + " " + product.getName() + "(s) removed from the cart.");
    }
```

3) Product Management:

Products have attributes like name, description, price, stock, weight, and discount.

```java
class Product {
    private String name;
    private String description;
    private double price;
    private int stock;
    private List<ProductAttribute> attributes;
    private double weight;
    private double discount;
    private List<Observer> observers = new ArrayList<>();
```

4) Product Customization:

Users can customize products with options like gift wrapping and engraving.
```java
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
```
5) Observer Pattern:

- The Observer pattern is used to notify users about product updates, such as stock changes and discounts.
```java
class UserNotifier implements Observer {
    @Override
    public void update(Product product) {
        if (product.getStock() == 1) {
            System.out.println("Attention! Only 1 unit of " + product.getName() + " left. Special discount applied!");

            double discountedPrice = product.getPrice() * 0.9;
            System.out.println("Discounted Price: $" + discountedPrice);

        }
    }
```

6) Price and Stock Management:

Price and stock of products are managed by the PriceStockManager class.
```java
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
```
7) Payment Strategies:

- Users can choose from different payment methods (Credit Card, Kaspi.kz, Cryptocurrency).
- Payment strategies are implemented using the Strategy pattern.
```java
   class CreditCardPayment implements PaymentStrategy { code }
class KaspiPayment implements PaymentStrategy {code}
class CryptoCurrencyPayment implements PaymentStrategy {code}

```
8) Delivery Services:

- Users can choose between delivery services (Post Office, Courier).
```java
interface DeliveryService {
    void shipProduct(String address);
}


class PostOfficeDelivery implements DeliveryService {


    @Override
    public void shipProduct(String address) {
        System.out.println("Product shipped to " + address + " via Post Office.");
    }
}

class CourierDelivery implements DeliveryService {


    @Override
    public void shipProduct(String address) {

        System.out.println("Product delivered to " + address + " by Courier.");
    }
}
```
9) Product Database:

- -	Products are stored in a database (ProductDatabase class).
-	Users can view all products or search for specificnes
```java
class ProductDatabase {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/sdp";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "maimir2005";

private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

public static void updateStock(Product product) { code }

public static List<Product> getAllProducts() { code }

public List<Product> searchProducts(String productName) { code }
}
```

---

## Conclusion

1) Key points: So we did a good project about a pc shop using all 6 patterns that we have learned. Finally, we had a good experience of teamwork and solving the problems. Also we tried to do well-structured code and had good logic to connect every element with each other.

2) As a result, we have a pc store created by using design patterns with interfaces and connected database. Also notification about discounts and last item done by using observer pattern, creating items using factory pattern and etc. If we speak about faced problems, they were about how to connect database and implement all patterns into a project. In the end, we did it!

3) Future improvement. All projects can be improved and our project also can be better. We will increase our database and add fields about users, add new products, add more info about users, etc. Firstly, try to upload a project to server and refix code after some advantages of users.

---

## Repository

The code for the project is available on GitHub: [SDPFinal](https://github.com/TKECH1/SDPFinal)

---

## References

[APA citation](https://refactoring.guru/design-patterns)https://refactoring.guru/design-patterns
