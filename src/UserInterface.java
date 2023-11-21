import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class UserInterface {
    private final ShoppingCart shoppingCart;
    private final PriceStockManager priceStockManager;
    private final ProductDatabase productDatabase;
    private DeliveryAdapter deliveryAdapter;
    private final Observer userNotifier;
    private double userBudget = 0.0;

    public UserInterface(ShoppingCart shoppingCart, PriceStockManager priceStockManager, ProductDatabase productDatabase, Observer userNotifier) {
        this.shoppingCart = shoppingCart;
        this.priceStockManager = priceStockManager;
        this.productDatabase = productDatabase;
        this.deliveryAdapter = new DeliveryAdapter(DeliveryServiceFactory.createDeliveryService("postoffice"));
        this.userNotifier = userNotifier;
        this.priceStockManager.addObserver(this.userNotifier);

    }

    public void displayMainMenu() {
        System.out.println("Welcome to the E-Commerce System!");
        System.out.println("1. View Products");
        System.out.println("2. Search Products");
        System.out.println("3. View Cart");
        System.out.println("4. Checkout");
        System.out.println("5. Remove Cart");
        System.out.println("6. Delivery");
        System.out.println("7. Exit");
    }

    public void setInitialBudget() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your budget: $");
        userBudget = scanner.nextDouble();
    }

    private boolean hasSufficientFunds(double totalAmount) {
        return userBudget >= totalAmount;
    }

    private void deductAmountFromBudget(double amount) {
        userBudget -= amount;
    }
    public void handleUserChoice(int choice) {
        switch (choice) {
            case 1:
                viewProducts();
                break;
            case 2:
                searchProducts();
                break;
            case 3:
                viewCart();
                break;
            case 4:
                checkout();
                break;
            case 5:
                removeItemsFromCart();
                break;
            case 6:
                deliveryAdapter = chooseDeliveryAdapter();
                System.out.println("Delivery service set to: " + deliveryAdapter.getClass().getSimpleName());
                break;
            case 7:
                System.out.println("Exiting the E-Commerce System. Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please enter a valid option.");
        }
    }


    private void viewProducts() {
        List<Product> products = productDatabase.getAllProducts();
        System.out.println("Products available:");

        int index = 1;
        for (Product product : products) {
            System.out.println(index + ". " + product.getName() + " | Price: $" + product.getPrice() + " | Stock: " + product.getStock());
            index++;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of the product to buy (0 to cancel): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice > 0 && choice <= products.size()) {
            Product selectedProduct = products.get(choice - 1);


            if (selectedProduct.getStock() > 0) {
                System.out.print("Enter the quantity to buy (available: " + selectedProduct.getStock() + "): ");
                int quantityToBuy = scanner.nextInt();
                scanner.nextLine();

                if (quantityToBuy > 0 && quantityToBuy <= selectedProduct.getStock()) {
                    customizeProduct(selectedProduct);
                    addToCart(selectedProduct, quantityToBuy);
                    System.out.println(quantityToBuy + " " + selectedProduct.getName() + "(s) added to the cart.");
                } else {
                    System.out.println("Invalid quantity. No product added to the cart.");
                }
            } else {
                System.out.println("Sorry, the selected product is out of stock.");
            }
        } else {
            System.out.println("Invalid choice. No product added to the cart.");
        }
    }




    private void searchProducts() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter product name to search: ");
        String productName = scanner.nextLine();

        List<Product> searchResults = productDatabase.searchProducts(productName);

        if (searchResults != null && !searchResults.isEmpty()) {
            System.out.println("Search results for product: " + productName);
            int index = 1;
            for (Product product : searchResults) {
                System.out.println(index + ". " + product.getName() + " | Price: $" + product.getPrice() + " | Stock: " + product.getStock());
                index++;
            }

            System.out.print("Enter the number of the product to add to the cart (0 to cancel): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice > 0 && choice <= searchResults.size()) {
                Product selectedProduct = searchResults.get(choice - 1);
                System.out.print("Enter the quantity to add to the cart: ");
                int quantity = scanner.nextInt();
                scanner.nextLine();
                addToCart(selectedProduct, quantity);
                System.out.println(quantity + " " + selectedProduct.getName() + "(s) added to the cart.");
            } else {
                System.out.println("Invalid choice. No product added to the cart.");
            }
        } else {
            System.out.println("No results found for product: " + productName);
        }
    }


    private void addToCart(Product product, int quantityToAdd) {
        for (int i = 0; i < quantityToAdd; i++) {
            shoppingCart.addToCart(product);
            product.addObserver(userNotifier);
        }
    }




    private void viewCart() {
        shoppingCart.viewCart();
    }

    private void removeItemsFromCart() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of the product to remove from the cart (0 to cancel): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        List<Product> cartItems = shoppingCart.getCartItems();

        if (choice > 0 && choice <= cartItems.size()) {
            Product selectedProduct = cartItems.get(choice - 1);

            System.out.print("Enter the quantity to remove (currently in cart: "
                    + shoppingCart.countOccurrencesInCart(selectedProduct) + "): ");
            int quantityToRemove = scanner.nextInt();
            scanner.nextLine();

            shoppingCart.removeFromCart(selectedProduct, quantityToRemove);
        } else {
            System.out.println("Invalid choice. No product removed from the cart.");
        }
    }


    private void checkout() {
        List<Product> cartItems = shoppingCart.getCartItems();

        if (cartItems.isEmpty()) {
            System.out.println("The cart is empty. Nothing to checkout.");
        } else {
            System.out.println("Products in the cart:");
            for (Product product : cartItems) {
                System.out.println("- " + product.getName() + " | Price: $" + product.getPrice());
            }


            PaymentStrategy paymentStrategy = getPaymentMethod();
            double totalAmount = calculateTotalAmount(cartItems);

            for (Product product : cartItems) {
                int updatedStock = product.getStock() - 1;
                product.setStock(updatedStock);
                productDatabase.updateStock(product);
            }


            for (Product ignored : cartItems) {
                deliveryAdapter.deliver("User Address");
            }


            for (Product product : cartItems) {
                if (product.getStock() == 1) {
                    product.setDiscount(0.1);
                }
            }




            if (!hasSufficientFunds(totalAmount)) {
                System.out.println("Sorry, you don't have enough funds. Please remove items from the cart or choose a different payment method.");
                return;
            }
            paymentStrategy.pay(totalAmount);
            deductAmountFromBudget(totalAmount);


            shoppingCart.clearCart();
            System.out.println("Payment Method: " + paymentStrategy.getClass().getSimpleName());
            System.out.println("Remaining Budget: $" + userBudget);
        }
    }

    private DeliveryService chooseDeliveryService() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a delivery service:");
        System.out.println("1. Post Office");
        System.out.println("2. Courier");

        int choice;
        do {
            System.out.print("Enter the number of your delivery service: ");
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 2);

        switch (choice) {
            case 1:
                return DeliveryServiceFactory.createDeliveryService("postoffice");
            case 2:
                return DeliveryServiceFactory.createDeliveryService("courier");
            default:
                throw new IllegalArgumentException("Unknown Delivery Service");
        }
    }
    private DeliveryAdapter chooseDeliveryAdapter() {
        DeliveryService deliveryService = chooseDeliveryService();
        return new DeliveryAdapter(deliveryService);
    }
    private double calculateTotalAmount(List<Product> cartItems) {
        double totalAmount = 0.0;
        for (Product product : cartItems) {
            totalAmount += product.getPrice();
        }
        return totalAmount;
    }


    private PaymentStrategy getPaymentMethod() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a payment method:");
        System.out.println("1. Credit Card");
        System.out.println("2. Kaspi.kz");
        System.out.println("3. Cryptocurrency");

        int choice;
        do {
            System.out.print("Enter the number of your payment method: ");
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 3);

        switch (choice) {
            case 1:
                return PaymentStrategyFactory.createCreditCardPaymentStrategy();
            case 2:
                return PaymentStrategyFactory.createKaspiPaymentStrategy();
            case 3:
                return PaymentStrategyFactory.createCryptoCurrencyPaymentStrategy();


            default:
                throw new IllegalArgumentException("Unknown Payment Method");
        }
    }

    private void customizeProduct(Product product) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you want to customize the product?");
        System.out.println("1. Gift Wrapping");
        System.out.println("2. Engraving");
        System.out.println("3. Skip customization");

        int customizationChoice = scanner.nextInt();
        scanner.nextLine();

        switch (customizationChoice) {
            case 1:

                ProductAttribute giftWrapping = new GiftWrapping("Standard");
                product.addAttribute(giftWrapping);
                break;
            case 2:

                ProductAttribute engraving = new Engraving("Basic");
                product.addAttribute(engraving);
                break;

        }

        System.out.println("Product customization:");
        List<ProductAttribute> attributes = product.getAttributes();
        for (ProductAttribute attribute : attributes) {
            System.out.println("- " + attribute.getDescription());
            attribute.customize();
        }
    }








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

        if (authenticationManager.authenticateUser(authUsername, authPassword)) {
            System.out.println("Authentication successful. You can now proceed with shopping.");
        } else {
            System.out.println("Authentication failed. Please check your credentials.");
            return;
        }

        ShoppingCart shoppingCart = ShoppingCart.getInstance();
        PriceStockManager priceStockManager;
        ProductDatabase productDatabase = new ProductDatabase();
        Observer userNotifier = new UserNotifier();
        List<Observer> observers = new ArrayList<>();
        observers.add(userNotifier);
        priceStockManager = new PriceStockManager(observers);

        UserInterface userInterface = new UserInterface(shoppingCart, priceStockManager, productDatabase, userNotifier);
        userInterface.setInitialBudget();
        while (true) {
            userInterface.displayMainMenu();
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            userInterface.handleUserChoice(choice);
            priceStockManager.updatePricesAndStocks();

        }
    }
}
