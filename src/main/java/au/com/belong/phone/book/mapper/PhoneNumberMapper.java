package au.com.belong.phone.book.mapper;

import au.com.belong.phone.book.model.dto.PhoneNumberDTO;
import au.com.belong.phone.book.model.dto.PhoneNumberWithCustomerDTO;
import au.com.belong.phone.book.model.entity.PhoneNumber;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PhoneNumberMapper {

    PhoneNumberDTO toPhoneNumberDTO(PhoneNumber phoneNumber);

    @Mapping(target = "customerDTO", source = "customer")
    PhoneNumberWithCustomerDTO toPhoneNumberWithCustomerDTO(PhoneNumber phoneNumber);
}
