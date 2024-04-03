package myshop12.com.model2.mvc.product.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
public class ProductDTO {
    private String fileName;
    private String manuDate;
    private int price;
    private String prodDetail;
    private String prodName;
    private int prodNo;
    private Date regDate;
    private String proTranCode;
    private List<MultipartFile> fileList;

    // 생성자, getter, setter, toString 등은 Lombok이 자동으로 생성해줍니다.
}