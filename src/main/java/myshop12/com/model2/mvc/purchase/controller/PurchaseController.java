package myshop12.com.model2.mvc.purchase.controller;


import javax.servlet.http.HttpSession;
import myshop12.com.model2.mvc.common.domain.Page;
import myshop12.com.model2.mvc.common.domain.Search;
import myshop12.com.model2.mvc.common.util.CommonUtil;
import myshop12.com.model2.mvc.product.service.ProductService;
import myshop12.com.model2.mvc.purchase.domain.Purchase;
import myshop12.com.model2.mvc.purchase.domain.PurchaseDetail;
import myshop12.com.model2.mvc.purchase.service.PurchaseService;
import myshop12.com.model2.mvc.user.domain.User;
import myshop12.com.model2.mvc.product.domain.Product;
import myshop12.com.model2.mvc.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {

    ///Field
    @Autowired
    @Qualifier("purchaseServiceImpl")
    private PurchaseService purchaseService;
    @Autowired
    @Qualifier("productServiceImpl")
    private ProductService productService;
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[91m";
    private static final String ORANGE = "\u001B[38;5;208m";
    private static final String YELLOW = "\u001B[93m";
    private static final String GREEN = "\u001B[92m";
    private static final String BLUE = "\u001B[94m";

    @Autowired
    public void setPurchaseService(@Qualifier("purchaseServiceImpl") PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @Value("${pageUnit}")
    //@Value("#{commonProperties['pageUnit'] ?: 3}")
    int pageUnit;

    @Value("${pageSize}")
    //@Value("#{commonProperties['pageSize'] ?: 2}")
    int pageSize;

    ///Constructor
    public PurchaseController() {
        System.out.println(BLUE);
        System.out.println("\n 생성자 :: "+this.getClass()+"\n");
        System.out.println(RESET);
    }

    ///Method
    @PostMapping("/addPurchase")
    public ModelAndView addPurchase(@ModelAttribute("purchase") Purchase purchase,
                                    @ModelAttribute("purchaseDetail") PurchaseDetail purchaseDetail,
                                    @RequestParam("prodNo") int prodNo,
                                    HttpSession session) throws Exception {
        System.out.println("/addPurchase");
        System.out.println("purchase값 ::"+purchase);

        User user = (User)session.getAttribute("user");

        Product product = productService.getProduct(prodNo);
        purchase.setBuyer(user);
        purchase.setPurchaseProd(product);
        purchase.setTranCode("b");

        System.out.println("prodNO값 :: "+prodNo);
        System.out.println("Product값 :: "+product);
        System.out.println("Purchase값 :: "+purchase);
        purchaseService.addPurchase(purchase);

        return new ModelAndView(
                "forward:/purchase/getPurchase.jsp"
                , Collections.singletonMap("purchase", purchase));

    }
    @GetMapping("/addPurchase")
    public ModelAndView addPurchaseView(@RequestParam("prodNo") int prodNo) throws Exception {
        System.out.println("/addPurchaseView");

        Map<String, Object> model = new HashMap<>();
        model.put("product", productService.getProduct(prodNo));

        return new ModelAndView(
                "forward:/purchase/addPurchaseView.jsp",
                model);
    }

    @RequestMapping("/getPurchase")
    public ModelAndView getPurchase(@RequestParam("tranNo") int tranNo,
                                    @RequestParam("menu") String menu) throws Exception {
        System.out.println("/getPurchase");

        Purchase purchase = purchaseService.getPurchase(tranNo);

        Map<String,Object> model = new HashMap<>();
        model.put("purchase", purchase);
        model.put("menu", menu);

        String viewName = "forward:/purchase/getPurchase.jsp";
        if(menu.equals("ok")){
            viewName = "forward:/purchase/getPurchase.jsp";
        }else{
            viewName = "forward:/purchase/updatePurchase";

        }
        return new ModelAndView(viewName, model);
    }
    @RequestMapping("/listPurchase")
    public ModelAndView listPurchase(HttpSession session,
                                     @ModelAttribute("search") Search search,
                                     @ModelAttribute("purchase") Purchase purchase,
                                     @RequestParam("menu") String menu
                                     ) throws Exception {
        System.out.println("/listPurchase 시작합니다...");
        User user = (User) session.getAttribute("user");


        int currentPage = 1;

        if(search.getCurrentPage() != 0){
            currentPage = search.getCurrentPage();
        }

        search.setCurrentPage(currentPage);
        search.setPageSize(pageSize);
        System.out.println("search.getStartRowNum()::"+search.getStartRowNum());
        System.out.println("search.getEndRowNum()::"+search.getEndRowNum());
        System.out.println("여기서 search가 "+search);


        Map<String,Object> materials = new HashMap<>();
        materials.put("search", search);
        materials.put("user", user);

        System.out.println("ListPurchase ::"+materials);

        Optional<Map<String, Object>> optionalMap = Optional.ofNullable(purchaseService.getPurchaseList(materials));

        System.out.println("map값 :: " + optionalMap);

        //널이였을때 대체동작
        Map<String, Object> map = optionalMap.orElseGet(() -> {
            Map<String, Object> emptyMap = new HashMap<>();
            emptyMap.put("count", 0);
            emptyMap.put("purchase", new Purchase());
            return emptyMap;
        });

        Map<String, Object> finalMap = map;
        map = Optional.ofNullable(user.getRole())
                .map(role -> {
                    if (role.equals("user")) {
                        System.out.println("ListPurchaseAction :: 유저");
                        return finalMap;
                    } else {
                        System.out.println("ListPurchaseAction :: 관리자");
//                        return purchaseService.getSaleList(search);
                        return null;
                    }
                })
                .orElse(map);

        int totalCount = (Integer) map.getOrDefault("count", 0);
        List<Purchase> purchaseList = (List<Purchase>) map.get("list");

        Page resultPage = new Page(currentPage, totalCount, pageUnit, pageSize);
        System.out.println("ListPurchaseAction ::" + resultPage);

        Map<String, Object> model = new HashMap<>();
        model.put("totalCount", totalCount);
        model.put("list", purchaseList);
        model.put("resultPage", resultPage);

        System.out.println("listPurchase :: 가 끝났습니다..");

        return new ModelAndView("forward:/purchase/listPurchase.jsp", model);
    }

    @PostMapping("/updatePurchase")
    public ModelAndView updatePurchase(@RequestParam("tranNo") int tranNo,
                                       @RequestParam("prodNo") int prodNo,
                                       @ModelAttribute("purchase") Purchase lastPurchase) throws Exception {
        System.out.println("/updatePurchase가 시작됩니다...");

        Purchase purchase = purchaseService.getPurchase(tranNo);

        //TranCode,product,user는 고치지 않아도 된다. 디비에서 그대로 가져와서
        purchase.setPaymentOption(lastPurchase.getPaymentOption());
        purchase.setReceiverName(lastPurchase.getReceiverName());
        purchase.setReceiverPhone(lastPurchase.getReceiverPhone());
        purchase.setDivyAddr(lastPurchase.getDivyAddr());
        purchase.setDivyRequest(lastPurchase.getDivyRequest());
        purchase.setDivyDate(lastPurchase.getDivyDate());

        purchase = purchaseService.updatePurchase(purchase);
        System.out.println("업데이트 완료 :: " + purchase);

        System.out.println("/updatePurchase가 끝났습니다...");

        Map<String,Object> map = new HashMap<>();
        map.put("purchase", purchase);
        map.put("menu", "ok");
        map.put("tranNo", tranNo);


        return new ModelAndView(
                "redirect:/purchase/getPurchase",
                map);

    }
    @GetMapping("/updatePurchase")
    public ModelAndView updatePurchaseView(@ModelAttribute("purchase") Purchase purchase,
                                           @RequestParam("menu") String menu) throws Exception {
        System.out.println("/updatePurchaseView가 시작됩니다...");


        System.out.println("updatePurchaseView가 끝났습니다...");
        return new ModelAndView(
                "forward:/product/updateProduct.jsp",
                Collections.singletonMap("purchase", purchase));
    }
    @RequestMapping("/updateTranCode")
    public ModelAndView updateTranCode(@RequestParam("prodNo") int prodNo,
                                       @RequestParam("navigationPage") String navigationPage,
                                       @RequestParam("menu") String menu) throws Exception {
        System.out.println("/updateTranCode가 시작됩니다...");

        Purchase purchase = purchaseService.getPurchaseProdNo(prodNo);
        System.out.println("updateTranCode :: 여기서 " + purchase);

        if (CommonUtil.null2str(purchase.getTranCode()).equals("b")) {
            System.out.println("b실행됨");
            purchase.setTranCode("c");

        } else if (purchase.getTranCode().trim().equals("c")) {
            System.out.println("c실행됨");
            purchase.setTranCode("d");
        }

        purchaseService.updateTranCode(purchase);

        String viewName = "";
        Map<String,Object> map = new HashMap<>();
        map.put("menu", menu);

        System.out.println("updateTranCode가 끝났습니다...");

        if(navigationPage.equals("listProduct")) {
            if(menu.equals("manage")) {
                System.out.println( "redirect:/purchase/listProduct?menu=manage"+"합니다.");
                viewName = "redirect:/product/listProduct?menu=manage";
            }
        }else if(navigationPage.equals("listPurchase")){

            if(menu.equals("search")) {
                System.out.println("redirect:/listPurchase?menu=search"+"합니다.");
                viewName =  "redirect:/purchase/listPurchase?menu=search";
            }
        }else {

        }

        return new ModelAndView(viewName, map);

    }


}
