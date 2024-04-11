package myshop12.com.model2.mvc.purchase.mapper;

import myshop12.com.model2.mvc.purchase.domain.Purchase;
import myshop12.com.model2.mvc.purchase.dto.AddPurchaseRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PurchaseMapper {

    PurchaseMapper INSTANCE = Mappers.getMapper(PurchaseMapper.class);

    //DTO를 도메인으로 변환
    @Mapping(target = "tranNo", ignore = true)
    Purchase toEntity(AddPurchaseRequestDTO dto);
}
