package au.com.belong.phone.book.mapper;

import au.com.belong.phone.book.model.dto.PhoneNumberDTO;
import au.com.belong.phone.book.model.entity.PhoneNumber;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhoneNumberMapper {

    PhoneNumberDTO phoneNumberToDTO(PhoneNumber phoneNumber);
}
