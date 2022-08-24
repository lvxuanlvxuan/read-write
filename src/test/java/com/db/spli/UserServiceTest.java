package com.db.spli;

import com.db.spli.domain.User;
import com.db.spli.service.UserService;
import com.google.gson.Gson;
import com.keepa.api.backend.KeepaAPI;
import com.keepa.api.backend.helper.KeepaTime;
import com.keepa.api.backend.helper.ProductAnalyzer;
import com.keepa.api.backend.structs.AmazonLocale;
import com.keepa.api.backend.structs.Product;
import com.keepa.api.backend.structs.Request;
import com.keepa.api.backend.structs.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static com.keepa.api.backend.KeepaAPI.ResponseStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testQueryOne() {
        User user1 = userService.queryOneById(1);
        System.out.println(user1.toString());
        User user = new User();
        user.setUserName("xiaolv");
        user.setAddress("shanghai");
        userService.insertOne(user);
    }

    @Test
    public void testInsertOne() {
        User user = new User();
        user.setUserName("xiaolv");
        user.setAddress("shanghai");
        userService.insertOne(user);
    }

    @Test
    public void testKeepa() throws InterruptedException {
        KeepaAPI api = new KeepaAPI("99j1ci7n59rnk4k4llaot1108arv7g00mhmlogl2j3dbabp7bbtspptu5bvorroc");
        Request r = Request.getProductRequest(AmazonLocale.US, 90, null, "B001GZ6QEC");
        api.sendRequest(r);
        api.sendRequest(r)
                .done(result -> {
                    switch (result.status) {
                        case OK:
                            // iterate over received product information
                            for (Product product : result.products){
//                                String s = new Gson().toJson(product);
//                                System.out.println(s);
                                // System.out.println(product);
                                if (product.productType == Product.ProductType.STANDARD.code || product.productType == Product.ProductType.DOWNLOADABLE.code) {
                                    int[][] csv = product.csv;
                                    //get basic data of product and print to stdout
                                    int currentAmazonPrice = ProductAnalyzer.getLast(product.csv[Product.CsvType.AMAZON.index], Product.CsvType.AMAZON);

                                    //check if the product is in stock -1 -> out of stock
                                    if (currentAmazonPrice == -1) {
                                        System.out.println(product.asin + " " + product.title + " is currently out of stock!");
                                    } else {
                                        System.out.println(product.asin + " " + product.title + " Current Amazon Price: " + currentAmazonPrice);
                                    }

                                    // get weighted mean of the last 90 days for Amazon
                                    int weightedMean90days = ProductAnalyzer.calcWeightedMean(product.csv[Product.CsvType.AMAZON.index], KeepaTime.nowMinutes(), 90, Product.CsvType.AMAZON);

                                } else {

                                }
                            }
                            break;
                        default:
                            System.out.println(result);
                    }
                })
                .fail(failure -> System.out.println(failure));

        TimeUnit.SECONDS.sleep(1000);


    }
}
