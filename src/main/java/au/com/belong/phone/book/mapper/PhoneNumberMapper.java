package au.com.belong.phone.book.mapper;

import au.com.belong.phone.book.model.dto.PhoneNumberDTO;
import au.com.belong.phone.book.model.entity.PhoneNumber;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PhoneNumberMapper {

    @Mapping(target = "customerDTO", source = "customer")
    PhoneNumberDTO phoneNumberToDTO(PhoneNumber phoneNumber);
}
