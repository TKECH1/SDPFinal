import java.util.ArrayList;
import java.util.List;



class ShoppingCart {
    private static ShoppingCart instance;
    private List<Product> cartItems = new ArrayList<>();

    private ShoppingCart() {

    }

    public static synchronized ShoppingCart getInstance() {
        if (instance == null) {
            instance = new ShoppingCart();
        }
        return instance;
    }

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


    int countOccurrencesInCart(Product product) {
        int count = 0;
        for (Product item : cartItems) {
            if (item.equals(product)) {
                count++;
            }
        }
        return count;
    }

    public List<Product> getCartItems() {
        return cartItems;
    }


    public void viewCart() {
        if (cartItems.isEmpty()) {
            System.out.println("The cart is empty.");
        } else {
            System.out.println("Products in the cart:");
            for (Product product : cartItems) {
                System.out.println("- " + product.getName());
            }
        }
    }

    public void clearCart() {
        cartItems.clear();
    }
}
