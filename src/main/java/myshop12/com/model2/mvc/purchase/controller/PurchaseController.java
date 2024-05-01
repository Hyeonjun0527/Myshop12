package myshop12.com.model2.mvc.purchase.controller;


import javax.servlet.http.HttpSession;
import myshop12.com.model2.mvc.common.domain.Page;
import myshop12.com.model2.mvc.common.domain.Search;
import myshop12.com.model2.mvc.common.util.CommonUtil;
import myshop12.com.model2.mvc.product.service.ProductService;
import myshop12.com.model2.mvc.purchase.domain.Purchase;
import myshop12.com.model2.mvc.purchase.domain.PurchaseDetail;
import myshop12.com.model2.mvc.purchase.dto.AddPurchaseRequestDTO;
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
    public ModelAndView addPurchase(@ModelAttribute("purchase") AddPurchaseRequestDTO addPurchaseRequestDTO,
                                    @RequestParam("typeCount") List<Integer> typeCountList,
                                    @RequestParam("prodNo") List<Integer> prodNoList,
                                    HttpSession session) throws Exception {
        System.out.println("/addPurchase");
        User user = (User)session.getAttribute("user");
        addPurchaseRequestDTO.setBuyer(user);

        List<Product> productList = new ArrayList<>();

        System.out.println("purchase값 ::"+addPurchaseRequestDTO);

        System.out.println("prodNoList :: "+prodNoList);
        System.out.println("typeCountList :: " + typeCountList);


        for (int prodNo : prodNoList) {
            Product product = productService.getProduct(prodNo);
            productList.add(product);
        }

        addPurchaseRequestDTO.setTypeCountList(typeCountList);
        List<PurchaseDetail> purchaseDetailList = PurchaseUtil.createPurchaseDetailList(addPurchaseRequestDTO,productList);
        addPurchaseRequestDTO.setPurchaseDetailList(purchaseDetailList);

        System.out.println("prodNO값 :: "+prodNoList);
        System.out.println("Product값 :: "+productList);
        System.out.println("PurchaseDetail값 :: "+purchaseDetailList);
        System.out.println("Purchase값 :: "+addPurchaseRequestDTO);
        System.out.println("detailNo tranNo 0인게 맞음");
        purchaseService.addPurchase(addPurchaseRequestDTO);

        return new ModelAndView(
                "forward:/purchase/getPurchase.jsp"
                , Collections.singletonMap("purchase", addPurchaseRequestDTO));

    }
//    @PostMapping("/addPurchaseList")

//    @RequestMapping(value = "/addPurchase", method = RequestMethod.GET)
    @GetMapping(value = "/addPurchase")
    public ModelAndView addPurchaseView(@RequestParam("prodNo") List<Integer> prodNoList
                                                    ) throws Exception {
        System.out.println("PurchaseController/addPurchaseView");
        System.out.println(prodNoList);

        Map<String, Object> model = new HashMap<>();
        List<Product> productList = new ArrayList<>();
        for (int element : prodNoList) {
            Product product = productService.getProduct(element);
            System.out.println(product);
            productList.add(product);
        }
        model.put("productList", productList);
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
        //count 5개 = 5개의 쿼리 4~6RowNum
        System.out.println("search.getStartRowNum()::"+search.getStartRowNum());
        System.out.println("search.getEndRowNum()::"+search.getEndRowNum());
        System.out.println("여기서 search가 "+search);


        Map<String,Object> materials = new HashMap<>();
        materials.put("search", search);
        materials.put("user", user);

        System.out.println("ListPurchase ::"+materials);

        Optional<Map<String, Object>> optionalMap = Optional.ofNullable(purchaseService.getPurchaseList(materials));

        System.out.println("map값 :: " + optionalMap);//

        //널이였을때 대체동작
        Map<String, Object> map = optionalMap.orElseGet(() -> {
            Map<String, Object> emptyMap = new HashMap<>();
            emptyMap.put("count", 0);
            emptyMap.put("list", new ArrayList<Purchase>());
            return emptyMap;
        });

        final Map<String, Object> finalMap = map;
        map = Optional.ofNullable(user.getRole())
                .map(role -> {
                    if (role.equals("user")) {
                        System.out.println("ListPurchaseAction :: 유저");
                        return finalMap;
                    } else if(role.equals("admin")){
                        System.out.println("ListPurchaseAction :: 관리자");
                        try {
                            Map<String,Object> material = new HashMap<>();
                            material.put("search", search);
                            return purchaseService.getSaleList(material);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    } else{
                        return finalMap;
                    }
                })
                .orElse(map);

        List<Purchase> purchaseList = (List<Purchase>) map.get("list");
        int totalCount = Optional.ofNullable(purchaseList)
                .map(List::size)
                .orElse(0);
        System.out.println("totalCount");
        System.out.println(totalCount);
        System.out.println("purchaseList");
        System.out.println(purchaseList);
        Page resultPage = new Page(currentPage, totalCount, pageUnit, pageSize);
        System.out.println("ListPurchaseAction ::" + resultPage);

        Map<String, Object> model = new HashMap<>();
        model.put("totalCount", totalCount);
        model.put("list", purchaseList);
        model.put("resultPage", resultPage);

        System.out.println("listPurchase :: 가 끝났습니다..");

        //포워드하므로 헤더,바디,쿼리파라미터가 유지된다.
        return new ModelAndView("forward:/purchase/listPurchase.jsp", model);
    }

    @PostMapping("/updatePurchase")
    public ModelAndView updatePurchase(@RequestParam("tranNo") int tranNo,
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
    public ModelAndView updateTranCode(@RequestParam("tranNo") int tranNo,
                                       @RequestParam("navigationPage") String navigationPage,
                                       @RequestParam("menu") String menu) throws Exception {
        System.out.println("/updateTranCode가 시작됩니다...");

        Purchase purchase = purchaseService.getPurchase(tranNo);
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

        if(navigationPage.equals("listPurchase")){
            if(menu.equals("search")) {
                System.out.println("redirect:/listPurchase?menu=search"+"합니다.");
                viewName =  "redirect:/purchase/listPurchase?menu=search";
            }else if(menu.equals("manage")){
                System.out.println("redirect:/listPurchase?menu=manage"+"합니다.");
                viewName = "redirect:/purchase/listPurchase?menu=manage";
            }
        }

        return new ModelAndView(viewName, map);

    }


}
