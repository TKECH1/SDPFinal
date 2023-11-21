import java.util.Scanner;

interface PaymentStrategy {
    void pay(double amount);
}

class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String expiryDate;
    private String cvv;

    public CreditCardPayment(String cardNumber, String expiryDate, String cvv) {

        if (isValidCardNumber(cardNumber)) {
            this.cardNumber = cardNumber;
        } else {

            this.cardNumber = null;
            System.out.println("Invalid credit card number. Please enter a 16-digit card number.");
        }


        if (isValidExpiryDate(expiryDate)) {
            this.expiryDate = expiryDate;
        } else {

            this.expiryDate = null;
            System.out.println("Invalid expiry date. Please enter the date in the format MM/YY.");
        }


        if (isValidCVV(cvv)) {
            this.cvv = cvv;
        } else {

            this.cvv = null;
            System.out.println("Invalid CVV. Please enter a three-digit CVV.");
        }
    }

    private boolean isValidCardNumber(String cardNumber) {

        return cardNumber != null && cardNumber.matches("\\d{16}");
    }

    private boolean isValidExpiryDate(String expiryDate) {

        return expiryDate != null && expiryDate.matches("\\d{2}/\\d{2}");
    }

    private boolean isValidCVV(String cvv) {

        return cvv != null && cvv.matches("\\d{3}");
    }

    @Override
    public void pay(double amount) {
        if (cardNumber == null || expiryDate == null || cvv == null) {
            System.out.println("Payment failed. Please check your card details and try again.");

        }
       else {
            System.out.println("Paid " + amount + " with Credit Card");
        }
    }


}

class KaspiPayment implements PaymentStrategy {
    private String phoneNumber;

    public KaspiPayment(String phoneNumber) {

        if (isValidPhoneNumber(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        } else {

            this.phoneNumber = null;
            System.out.println("Invalid Kaspi.kz phone number. Please enter a number starting with '87' and containing 11 digits.");
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {

        return phoneNumber != null && phoneNumber.matches("87\\d{9}");
    }

    @Override
    public void pay(double amount) {
        if (phoneNumber == null) {
            System.out.println("Payment failed. Please check your Kaspi.kz account details and try again.");

        }

        else {
            System.out.println("Paid " + amount + " with Kaspi.kz");
        }
    }
}


class CryptoCurrencyPayment implements PaymentStrategy {
    private String walletAddress;

    public CryptoCurrencyPayment(String walletAddress) {

        if (isValidBitcoinWalletAddress(walletAddress)) {
            this.walletAddress = walletAddress;
        } else {

            this.walletAddress = null;
            System.out.println("Invalid Bitcoin wallet address. Please check your cryptocurrency wallet details.");
        }
    }

    private boolean isValidBitcoinWalletAddress(String walletAddress) {

        return walletAddress != null && walletAddress.matches("^[13][a-km-zA-HJ-NP-Z1-9]{26,35}$");
    }

    @Override
    public void pay(double amount) {
        if (walletAddress == null) {
            System.out.println("Payment failed. Please check your cryptocurrency wallet details and try again.");
        }
        else {
            System.out.println("Paid " + amount + " with Cryptocurrency");
        }
    }


}




class PaymentStrategyFactory {

    public static PaymentStrategy createCreditCardPaymentStrategy() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Card Number: ");
        String cardNumber = scanner.nextLine();

        System.out.print("Enter Expiry Date (MM/YY): ");
        String expiryDate = scanner.nextLine();

        System.out.print("Enter CVV: ");
        String cvv = scanner.nextLine();

        return new CreditCardPayment(cardNumber, expiryDate, cvv);
    }


    public static PaymentStrategy createKaspiPaymentStrategy() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Kaspi.kz Phone Number: ");
        String phoneNumber = scanner.nextLine();

        return new KaspiPayment(phoneNumber);
    }



    public static PaymentStrategy createCryptoCurrencyPaymentStrategy() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Cryptocurrency Wallet Address: ");
        String walletAddress = scanner.nextLine();

        return new CryptoCurrencyPayment(walletAddress);
    }



}





