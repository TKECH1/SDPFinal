
class DeliveryAdapter {
    private DeliveryService deliveryService;

    public DeliveryAdapter(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }


    public void deliver(String address) {

        deliveryService.shipProduct(address);
    }
}


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


class DeliveryServiceFactory {
    public static DeliveryService createDeliveryService(String type) {
        switch (type.toLowerCase()) {
            case "postoffice":
                return new PostOfficeDelivery();
            case "courier":
                return new CourierDelivery();

            default:
                throw new IllegalArgumentException("Invalid delivery service type");
        }
    }
}
