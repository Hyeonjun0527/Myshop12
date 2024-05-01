package myshop12.com.model2.mvc.purchase.controller;

import myshop12.com.model2.mvc.product.domain.Product;
import myshop12.com.model2.mvc.product.service.ProductService;
import myshop12.com.model2.mvc.purchase.domain.PurchaseDetail;
import myshop12.com.model2.mvc.purchase.dto.AddPurchaseRequestDTO;
import myshop12.com.model2.mvc.purchase.service.PurchaseService;
import myshop12.com.model2.mvc.user.domain.User;
import myshop12.com.model2.mvc.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/purchase/json/*")
public class PurchaseRestController {

    ///Field
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[91m";
    private static final String ORANGE = "\u001B[38;5;208m";
    private static final String YELLOW = "\u001B[93m";
    private static final String GREEN = "\u001B[92m";
    private static final String BLUE = "\u001B[94m";

    @Autowired
    @Qualifier("purchaseServiceImpl")
    private PurchaseService purchaseService;
    @Autowired
    @Qualifier("productServiceImpl")
    private ProductService productService;
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Value("${pageUnit}")
    //@Value("#{commonProperties['pageUnit'] ?: 3}")
    int pageUnit;

    @Value("${pageSize}")
    //@Value("#{commonProperties['pageSize'] ?: 2}")
    int pageSize;

    ///Constructor
    public PurchaseRestController() {
        System.out.println(YELLOW);
        System.out.println("생성자 :: " + this.getClass());
        System.out.println(RESET);
    }

    @PostMapping("/addPurchase")
    public Map<String,Object> addPurchase(@RequestBody AddPurchaseRequestDTO dto,
                                    HttpSession session) throws Exception {
        System.out.println("/addPurchase");
        User user = (User)session.getAttribute("user");
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String name = attributeNames.nextElement();
            System.out.println("session name :: "+name);
        }
        System.out.println(user);
        dto.setBuyer(user);

        List<Product> productList = new ArrayList<>();

        System.out.println("purchase값 ::"+dto);

        System.out.println("prodNoList :: "+ dto.getProdNoList());
        System.out.println("typeCountList :: " + dto.getTypeCountList());


        for (int prodNo : dto.getProdNoList()) {
            Product product = productService.getProduct(prodNo);
            productList.add(product);
        }

        dto.setTypeCountList(dto.getTypeCountList());
        List<PurchaseDetail> purchaseDetailList = PurchaseUtil.createPurchaseDetailList(dto,productList);
        dto.setPurchaseDetailList(purchaseDetailList);

        System.out.println("prodNO값 :: "+ dto.getProdNoList());
        System.out.println("Product값 :: "+productList);
        System.out.println("PurchaseDetail값 :: "+purchaseDetailList);
        System.out.println("디비에 추가되는 Purchase값 :: "+dto);
        System.out.println("detailNo tranNo 0인게 맞음");
        purchaseService.addPurchase(dto);

        return Collections.singletonMap("purchase", dto);

    }
//    @PostMapping("/addPurchaseList")

    //    @RequestMapping(value = "/addPurchase", method = RequestMethod.GET)
    @GetMapping(value = "/addPurchase")
    public Map<String,Object> addPurchaseView(@RequestParam("prodNo") List<Integer> prodNoList) throws Exception {
        System.out.println("PurchaseController/addPurchaseView");
        System.out.println(prodNoList);
        Map<String, Object> map = new HashMap<>();
        List<Product> productList = new ArrayList<>();
        for (int prodNo : prodNoList) {
            Product product = productService.getProduct(prodNo);
            System.out.println(product);
            productList.add(product);
        }
        map.put("productList", productList);
        return map;
    }

}
