package myshop12.com.model2.mvc.product.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import myshop12.com.model2.mvc.common.domain.Page;
import myshop12.com.model2.mvc.common.domain.Search;
import myshop12.com.model2.mvc.product.domain.Product;
import myshop12.com.model2.mvc.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/product/json/*")
public class ProductRestController {
    ///Field
    private ProductService productService;
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[91m";
    private static final String ORANGE = "\u001B[38;5;208m";
    private static final String YELLOW = "\u001B[93m";
    private static final String GREEN = "\u001B[92m";
    private static final String BLUE = "\u001B[94m";

    @Autowired
    public void setProductService(@Qualifier("productServiceImpl") ProductService productService) {
        this.productService = productService;
    }

    @Value("${pageUnit}")
    //@Value("#{commonProperties['pageUnit'] ?: 3}")
    int pageUnit;

    @Value("${pageSize}")
    //@Value("#{commonProperties['pageSize'] ?: 2}")
    int pageSize;

    ///Constructor
    public ProductRestController() {
        System.out.println(YELLOW);
        System.out.println("생성자 :: " + this.getClass());
        System.out.println(RESET);
    }

    ///Method
    @PostMapping(value="addProduct", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})//작동됨
    public ResponseEntity<?> addProduct(@RequestPart(required = false) List<MultipartFile> fileList,
                                        @RequestPart(required = false) Product product) throws Exception {
        System.out.println("/addProduct이 시작됩니다..");

        System.out.println(fileList);
        System.out.println(product);
        if (fileList != null) {
            if (fileList.isEmpty()) {
                System.out.println("파일은 업로드 안했습니다.");
                //return ResponseEntity.badRequest().body("Please select a file to upload");
            }
        }
        //스트링으로 바꾸고 리스트를 조인해야해
        List<String> fileNameList = new ArrayList<>();

        if (fileList != null) {
            for (MultipartFile multipartFile : fileList) {
                fileNameList.add(getProductFileName(multipartFile));
            }
        }
        //System.out.println("fileName :: "+fileNameList.toString());
        String fileNames = String.join(",", fileNameList);

        //원래 이렇게 a넣으면 안될거 같은데..?
        product.setProTranCode("a");
        if (!fileNames.isEmpty()) {
            product.setFileName(fileNames);
        }
        //Business Logic
        System.out.println("당연히 prodNo 0이다. product :: " + product);
        productService.addProduct(product);
        //Model 과 View 연결 - 이 부분은 @RequestParam + Model 전략을 쓰면 해야됨
        //model.addAttribute("product", product);
        System.out.println("/addProduct이 끝났습니다..");
        return ResponseEntity.status(HttpStatus.OK).body(product);

    }//end of addProduct

    @RequestMapping("getProduct/{prodNo}")//작동됨
    public ResponseEntity<?> getProduct(HttpServletRequest request,
                                        HttpServletResponse response,
                                        @PathVariable String prodNo,
                                        @RequestParam(value = "menu", required = false) String menu) throws Exception {

        System.out.println("/getProduct이 시작됩니다..");
        //Business Logic
        Product product = productService.getProduct(Integer.parseInt(prodNo));
        //Model 과 View 연결
        // 메서드내에서 참조변수에 도메인을 재할당해버린 경우에는
        // ModelAttribute가 인식을 하지 못한다.
        List<String> fileNames = Arrays.asList(product.getFileName().split(","));//1234,1234,1,

        System.out.println(fileNames.toString());


        Map<String, Object> map = new HashMap<>();
        map.put("menu", menu);
        map.put("product", product);
        map.put("fileNames", fileNames);


        //CookieUtil.addValue(request, response, "history", String.valueOf(product.getProdNo()));

        System.out.println("/getProduct이 끝났습니다..");
        if(menu!=null) {
            if (menu.equals("manage")) {
                System.out.println("forward:/updateProduct.jsp" + "합니다.");
                map.put("navigation", "forward:/product/updateProduct.jsp");
            } else {//search, ok
                map.put("navigation", "forward:/product/getProduct.jsp");
            }//end of else
        }
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }//end of getProduct

    //클라-searchBoundFirst,searchBoundEnd,currentPage,search,menu,products,page
    //currentPage의
    @PostMapping("likeProduct")//작동됨
    public ResponseEntity<?> likeProduct(@RequestBody Search search,
                                         @RequestParam(value = "menu", required = false) String menu,
                                         @RequestParam(value = "searchBoundFirst", required = false) Integer searchBoundFirst,
                                         @RequestParam(value = "searchBoundEnd", required = false) Integer searchBoundEnd,
                                         HttpServletRequest request) throws Exception {
        //search,searchBoundFirst,End는 스스로 안에서 페이지 이동할때의 경우임.


        //@ModelAttribute("products") ArrayList<Product> products

        System.out.println("/likeProduct이 시작됩니다..");
        //바인딩 : 클라-searchBoundFirst, searchBoundEnd > search도메인

        if (searchBoundFirst == null || searchBoundEnd == null) {
            searchBoundFirst = 0;
            searchBoundEnd = 0;
        }
        //바인딩 : 클라-searchBoundFirst, searchBoundEnd > search도메인
        if (!(searchBoundFirst == 0) || !(searchBoundEnd == 0)) {
            search.setSearchBoundFirst(searchBoundFirst);
            search.setSearchBoundEnd(searchBoundEnd);
//                System.out.println("searchBound[0]"+searchBound[0]);
//                System.out.println("searchBound[1]"+searchBound[1]);
        }

        //바인딩 : 클라-currentPage > search도메인 > page도메인
        int currentPage = 1;
        //경로 1,2,3,4로 들어왔을경우
        if (search.getCurrentPage() != 0) {
            currentPage = search.getCurrentPage();
        }

        search.setCurrentPage(currentPage);//current의 default Value를 1로 설정했음.
        search.setPageSize(pageSize);

        //createLike에서 가져옴
        Map<String, Object> createLikeData = (Map<String, Object>) request.getAttribute("createLikeData");

        //menu,currentPage,products
        List<Product> products = (List<Product>) (createLikeData.get("products"));

        int productsSize = products != null ? products.size() : 0;

        Page page = new Page(
                currentPage,
                productsSize,
                pageUnit,
                pageSize);

        System.out.println("ListProductAction ::" + page);
        System.out.println("products :: " + products);

        //Model 과 View 연결

        Map<String, Object> map = new HashMap<>();

        map.put("totalCount", productsSize);
        map.put("products", products);
        map.put("search", search);
        map.put("resultPage", page);

        System.out.println("/likeProduct이 끝났습니다..");

        //네비게이션
        if (menu != null) {
            map.put("navigation", "forward:/product/likeProduct.jsp");
        } else {
            System.out.println("forward:/product/getProduct.jsp" + "합니다.");
            map.put("navigation", "forward:/product/getProduct.jsp");
        }//end of else

        return ResponseEntity.status(HttpStatus.OK).body(map);
    }//end of likeProduct

    //클라-searchBoundFirst,searchBoundEnd,search,menu,products,page
    @RequestMapping("listProduct")
    public ResponseEntity<Map <String,Object>> listProduct(@RequestBody(required = false) Search search,
                                        @RequestParam(value = "searchBoundFirst", required = false) Integer searchBoundFirst,
                                        @RequestParam(value = "searchBoundEnd", required = false) Integer searchBoundEnd,
                                        @RequestParam(value = "menu", required = false) String menu,
                                        //image는 이미지로 볼거냐 이거다. 이미지파일 보내는거 아님.
                                        @RequestParam(value = "image", required = false) String image
                                        ) throws Exception {
        System.out.println("/listProduct이 시작됩니다..");
        System.out.println("searchBound :: " + searchBoundFirst + " " + searchBoundEnd);
        System.out.println("searchType :: " + search.getSearchType());
//        if(search.getSearchType() == null) {
//            search.setSearchType("1");
//        }

        if (searchBoundFirst == null && searchBoundEnd == null) {
            searchBoundFirst = 0;
            searchBoundEnd = 0;
        }
        //바인딩 : 클라-searchBoundFirst, searchBoundEnd > search도메인
        if (!(searchBoundFirst == 0) || !(searchBoundEnd == 0)) {
            search.setSearchBoundFirst(searchBoundFirst);
            search.setSearchBoundEnd(searchBoundEnd);
//                System.out.println("searchBound[0]"+searchBound[0]);
//                System.out.println("searchBound[1]"+searchBound[1]);
        }


        //바인딩 : 클라-currentPage > search도메인 > page도메인
        int currentPage = 1;
        //경로 1,2,3,4로 들어왔을경우
        if (search.getCurrentPage() != 0) {
            currentPage = search.getCurrentPage();
        }

        search.setCurrentPage(currentPage);
        search.setPageSize(pageSize);
        System.out.println("search");
        System.out.println(search);
        Map<String, Object> productMap = productService.getProductList(search);//Like와 다른 부분
        List<Product> list = (List<Product>) productMap.get("list");
        List<List<String>> fileNameListList = new ArrayList<>();

        Optional.ofNullable(image)
                .filter(img -> img.equals("ok"))
                .ifPresent(img -> {
                    Optional.ofNullable(list)//Optional<List<Product>>
                            .ifPresent(lst -> lst.stream()
                                    .flatMap(product -> Optional.ofNullable(product.getFileName())
                                            .map(fileName -> fileName.split(","))
                                            .stream())
                                    .forEach(array -> Optional.ofNullable(fileNameListList)
                                            .ifPresent(fListList -> fListList.add(Arrays.asList(array)))));

                    fileNameListList.forEach(fileNameList -> System.out.println("fileNameList :: " + fileNameList));
                });

        Page page = new Page(
                currentPage,
                (Integer) productMap.get("totalCount"),
                pageUnit,
                pageSize);

        System.out.println("ListProductAction ::" + page);
        System.out.println("list :: " + list);
        System.out.println("search :: " + search);


        //Model 과 View 연결
        //model.addAttribute("products", products);
        //model.addAttribute("search", search);

        Map<String, Object> map = new HashMap<>();


        map.put("search",search);
//        map.put("totalCount", productMap.get("totalCount"));
        map.put("list", list);
        map.put("resultPage", page);
        map.put("menu", menu);

        Optional.ofNullable(image)
                .filter(img -> img.equals("ok"))
                .ifPresent(img -> map.put("fileNameListList", fileNameListList));


        System.out.println("/listProduct이 끝났습니다..");

        return ResponseEntity.status(HttpStatus.OK).body(map);
        //end of else
    }//end of listProduct
    @RequestMapping("listProductImg")//작동됨
    public ResponseEntity<?> listProductImg(@RequestBody Search search,
                                         @RequestParam(value = "searchBoundFirst", required = false) Integer searchBoundFirst,
                                         @RequestParam(value = "searchBoundEnd", required = false) Integer searchBoundEnd,
                                         @RequestParam(value = "menu", required = false) String menu) throws Exception {
        System.out.println("/listProduct이 시작됩니다..");
        System.out.println("searchBound :: " + searchBoundFirst + " " + searchBoundEnd);
        System.out.println("searchType :: " + search.getSearchType());
//        if(search.getSearchType() == null) {
//            search.setSearchType("1");
//        }
        //하나라도 널이면 널 아니게...
        if (searchBoundFirst == null && searchBoundEnd == null) {
            searchBoundFirst = 0;
            searchBoundEnd = 0;
        }
        //바인딩 : 클라-searchBoundFirst, searchBoundEnd가 뭐라도 있으면 search도메인
        if (!(searchBoundFirst == 0) || !(searchBoundEnd == 0)) {
            search.setSearchBoundFirst(searchBoundFirst);
            search.setSearchBoundEnd(searchBoundEnd);
//                System.out.println("searchBound[0]"+searchBound[0]);
//                System.out.println("searchBound[1]"+searchBound[1]);
        }

        System.out.println("search.getCurrentPage()"+search.getCurrentPage());
        if(search.getCurrentPage()==0){
            search.setCurrentPage(1);
            System.out.println("CurrentPage가 1이 됐다.");
        }
        //바인딩 : 클라-currentPage -> search도메인 -> page도메인
        int currentPage = search.getCurrentPage() + 1;

        search.setCurrentPage(currentPage);
        search.setPageSize(pageSize);

        Map<String, Object> productMap = productService.getProductList(search);//Like와 다른 부분

        Page page = new Page(
                currentPage,
                (Integer) productMap.get("totalCount"),
                pageUnit,
                pageSize);

        System.out.println("ListProductAction ::" + page);
        System.out.println("products :: " + productMap.get("list"));
        System.out.println("search :: " + search);

        //Model 과 View 연결
        //model.addAttribute("search", search);
//        model.addAttribute("totalCount", productMap.get("totalCount"));
//        model.addAttribute("list", productMap.get("list"));
//        model.addAttribute("resultPage", page);
//        model.addAttribute("menu", menu);

        Map<String, Object> map = new HashMap<>();
        map.put("products", productMap.get("list"));
        map.put("totalCount", productMap.get("totalCount"));
        map.put("resultPage", page);
        map.put("menu", menu);
        map.put("search", search);


        System.out.println("/listProduct이 끝났습니다..");

        //네비게이션
        if (menu != null) {
            System.out.println("forward:/product/listProduct.jsp" + "합니다.");
            map.put("navigation", "forward:/product/listProduct.jsp");
        } else {
            System.out.println("forward:/product/getProduct.jsp" + "합니다.");
            map.put("navigation", "forward:/product/getProduct.jsp");
        }//end of else

        return ResponseEntity.status(HttpStatus.OK).body(map);
    }//end of listProduct

    //prodNo,menu,currentPage,
    @RequestMapping("/setLikeProduct")//
    public ResponseEntity<?> SetLikeProduct(HttpServletRequest request,
                                            HttpServletResponse response,
                                            @RequestParam("prodNo") int prodNo,
                                            @RequestParam("menu") String menu,
                                            @RequestParam("currentPage") int currentPage,
                                            @CookieValue(name = "like", required = false) String likeCookie) throws Exception {
        System.out.println("/////////////////////////////////////////////////////////////setLikeProduct이 시작됩니다..");


        //Business Logic
        Product product = productService.getProduct(prodNo);

        //쿠키디버깅 ;
        if (likeCookie != null && likeCookie.startsWith(";")) {
            likeCookie = likeCookie.substring(1);
        }


        System.out.println("setLikeProduct::likeCookie :: " + likeCookie);
        String[] likeCookies = null;

        if (likeCookie != null) {
            likeCookies = likeCookie.split(";");
        } // IF

        System.out.println("setLikeProduct::likeCookies::" + Arrays.toString(likeCookies));

        boolean result = true;
        if (likeCookies != null) {
            for (String element : likeCookies) {
                if (element.equals(String.valueOf(product.getProdNo()))) {//같은게 하나라도 있으면 false
                    result = false;
                } // IF
            } // FOR
        }
        if (result) {//같은게 없으면 true
//            CookieUtil.addValue(request, response, "like", String.valueOf(product.getProdNo()));
        }

        currentPage = 1;
        Search search = new Search();
        search.setCurrentPage(currentPage);
        search.setPageSize(pageSize);

        Map<String, Object> productMap = productService.getProductList(search);//Like와 다른 부분

        Page page = new Page(
                currentPage,
                (Integer) productMap.get("totalCount"),
                pageUnit,
                pageSize);

        Map<String, Object> map = Map.of(
                "currentPage", currentPage,
                "search", search,
                "totalCount", productMap.get("totalCount"),
                "list", productMap.get("list"),
                "resultPage", page,
                "menu", menu,
                "navigation", "product/listProduct.jsp");

        System.out.println("//////////////////////////////////////////////////////////////setLikeProduct이 끝났습니다..");
        System.out.println("forward:/product/listProduct?menu=search" + "합니다.");
        //어차피 찜리스트는 관리자는 실행할 수 없도록 해놓았음.
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }//end of SetLikeProduct

    @PostMapping(value ="/updateProduct", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateProduct(
            @RequestPart(value="fileList",required = false) List<MultipartFile> fileList,
            @RequestPart(value="product",required = false) Product product) throws Exception {
        System.out.println("/updateProduct 이 시작됩니다.");

        if (fileList == null || fileList.isEmpty()) {

            System.out.println("파일이 없습니다.");
        }

        List<String> fileNameList = new ArrayList<>();

        if (fileList != null) {
            for (MultipartFile file : fileList) {
                fileNameList.add(getProductFileName(file));

            }
        }
        System.out.println("fileNameList :: " + fileNameList);
        //Business Logic
        product = productService.getProduct(product.getProdNo());

        product.setFileName(String.join(",", fileNameList));
        product.setManuDate(product.getManuDate().replaceAll("-", ""));

        System.out.println("업데이트 완료 :: " + productService.updateProduct(product));
        Map<String, Object> map = new HashMap<>();
        if (fileList != null){
             map = Map.of(
                    "fileNameList", fileNameList,
                    "product",product,
                    "navigation", "product/getProduct");
        }else{
             map = Map.of(
                     "product",product,
                    "navigation", "product/getProduct");
        }
        //Model 과 View 연결
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }//end of updateProduct

    //prodNo,
    @GetMapping("/updateProduct")
    public ResponseEntity<?> updateProductView(@RequestBody Product product) throws Exception {

        System.out.println("/updateProductView" + "이 시작됩니다..");
        product = productService.getProduct(product.getProdNo());

        // Model 과 View 연결
        Map<String, Object> map = Map.of(
                "product", product,
                "navigation", "product/updateProduct.jsp");

        return ResponseEntity.status(HttpStatus.OK).body(map);

    }

    @PostMapping("/autoComplete")
    public ResponseEntity<?> autoComplete(@RequestBody Search search) throws Exception{

        search.setCurrentPage(1);
        search.setPageSize(2147483647);
        //키워드와 컨디션값이 들어올것임.
        System.out.println("/autoComplete이 시작됩니다..");
        System.out.println("search :: "+search);
        //Business Logic
        Map<String,Object> productMap = productService.getProductList(search);
        List<Product> productList = (List<Product>)productMap.get("list");

        System.out.println(productList);
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    public String getProductFileName(MultipartFile file) throws Exception {
        ;

        String uploadDir = "C:/Users/osuma/git/Myshop/Myshop08/src/main/webapp/images/uploadFiles/";
        String fileName = file.getOriginalFilename();


        System.out.println("uploadDir" + uploadDir);
        System.out.println("fileName" + fileName);

        Path path = Paths.get(uploadDir + fileName);

        FileCopyUtils.copy(file.getBytes(), new File(path.toString()));
//      Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

}
