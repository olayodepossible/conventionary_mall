package com.possible.clientapplication;

import com.possible.clientapplication.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@SpringBootApplication
public class ClientApplication implements CommandLineRunner{
    @Lazy
    @Autowired
    Client client;

    private static final String baseUrl = "http://localhost:8080";

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


        log.info("============= Create Vendor ... =============");
        Address vendorAddress = Address.builder()
                .street("2000 North st")
                .country("USA")
                .city("Fairfield")
                .zip("52447")
                .build();
        Vendor vendor1= Vendor.builder()
                .companyAddress(vendorAddress)
                .companyName("Dominos")
                .createdAt(LocalDateTime.now())
                .desc("Tasty. . .")
                .email("vendor@gmail.com")
                .firstName("Mike")
                .lastName("Jones")
                .gender(Gender.MALE)
                .build();

        Vendor saveVendor = restTemplate().postForObject(baseUrl+"/vendor/save", vendor1, Vendor.class);
        log.info("============= RETURNED VENDOR============={}", saveVendor);




        log.info("============= Create Customer ... =============");
        Customer cust1= new Customer();
        cust1.setFirstName("Michale");
        cust1.setLastName("Rezene");
        cust1.setEmail("mike@gmail.com");
        cust1.setPhone("641256646");
        cust1.setGender(Gender.MALE);
        Address address = Address.builder()
                .street("1000 North st")
                .country("USA")
                .city("Fairfield")
                .zip("52557")
                .build();
        cust1.setAddress(address);

        log.info("BEFORE SAVE ********** {}", cust1);

        restTemplate().postForObject(baseUrl+"/customers/save", cust1, Customer.class);
        log.info("============= get Customer number one=============");
        var customers= restTemplate().getForObject(baseUrl+"/customers", Customer[].class);
        List<Customer> customerList = Arrays.asList(Objects.requireNonNull(customers));
        var customer1= customerList.get(0);
        log.info("Customer{}", customer1);



        log.info("=============adding Product: Macbook...=============");
        String id = "";
        if (saveVendor != null) {
            id = saveVendor.getId();
        }
        log.info("=============RETURNED VENDOR ID...============={}\n", saveVendor.getId());
        Product product =  Product.builder()
                .productName("Ban-creamy")
                .productLogo("logo1.jpg")
                .productFlavour(Flavour.BANANA)
                .productPrice(2000.0)
                .vendorId(id)
                .productDescription("Low Sugar")
                .productNumInStock(10)
                .build();
        Product savedProduct1 = client.addProduct(product);

        log.info("=============adding Product: Samsung 21...=============");
        Product product2 =  Product.builder()
                .productName("Choco-Choco")
                .productLogo("logo.jpg")
                .productFlavour(Flavour.CHOCOLATE)
                .productPrice(3000.0)
                .vendorId(id)
                .productDescription("Full Malt")
                .productNumInStock(10)
                .build();
        Product savedProduct2 = client.addProduct(product2);

        log.info("============= Get All Products=============");
        var allProducts= client.getProducts();
        log.info("{}\n", allProducts);
/*
        //edit product
        log.info("============= Edit Product from Macbook to Hp=============");
        Product product1 = allProducts.get(0);
        Product product2 = allProducts.get(1);
        product1.setProductName("Hp");
        product1.setProductPrice(1200.0);
        product1.setProductDescription("125 gb storage 16 RAM");
        product1.setProductNumInStock(5);
        client.modifyProduct(product1, product1.getProductId());
        var editedProduct= client.getProducts();
        log.info("*****Edited Product**********\n{}",editedProduct);
*/




        //Create shopping cart to a customer
        log.info("============= Create cart to Customer1 ...=============");
        restTemplate().postForLocation(baseUrl+"/cart/addCartForACustomer/"+ customer1.getId(), null, ShoppingCart.class);

        //add product(Hp) to cart of customer1
        log.info("============= Put product1 to cart of customer1 .... =============");
        restTemplate().postForLocation(baseUrl+"/cart/addProductToCartWithQuantity/" +customer1.getId()+"/quantity/"+ 4, savedProduct1, Product[].class);
        restTemplate().postForLocation(baseUrl+"/cart/addProductToCartWithQuantity/" +customer1.getId()+"/quantity/"+ 2, savedProduct2, Product[].class);

        // Show the shopping cart of customer1
        log.info("============= Get shopping cart of customer1=============");
        ShoppingCart cart= restTemplate().getForObject(baseUrl+"/cartQuery/getShoppingCart/"+customer1.getId(), ShoppingCart.class);
        log.info("{}",cart);

//        // delete product1(Hp) from cart
//        log.info("============= Deleting product 1(Hp) from cart of customer1..=============");
//        restTemplate().delete(baseUrl+ "/cart/removeProductFromCart/"+customer1.getId()+"/product/"+product2.getProductNumInStock(), Void.class);
//
//        //Change the quantity of one of the products
//        int quantity2= 101;
//        restTemplate().delete(baseUrl+"/cart/removeProductFromCartWithQuantity/"+customer1.getId()+"/product/"+savedProduct1.getProductNumInStock()+"/quantity/"+quantity2, savedProduct1, Product[].class);

//
        log.info("************Retrieve and show the shopping cart****************\n{}",restTemplate().getForObject(baseUrl+"/cartQuery/getShoppingCart/"+customer1.getId(), ShoppingCart.class));
//
//        log.info("============= Checkout and place an order =============");
//
        Order order = restTemplate().postForObject(baseUrl+"/cart/checkout/"+customer1.getId(),null, Order.class);
        log.info("============= Order NUMBER .... ============={}\n", order);
//
//        //Order placed
        log.info("============= Placing an order .... =============");
        restTemplate().postForObject(baseUrl+"/order/placeOrder/orderNumber/"+order.getOrderId()+customer1.getId(), null, Void.class);

//        log.info("============= Getting shopping cart (should be empty) After placing order .... =============");
//        log.info("{}",restTemplate().getForObject(baseUrl+"/cartQuery/getShoppingCart/"+ customer1.getId(), ShoppingCart.class));

        log.info("============= Get all order =============");
        log.info("{}", restTemplate().getForObject(baseUrl+"/order/getOrders", Order[].class));

    }



}
