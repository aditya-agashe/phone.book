package au.com.belong.phone.book.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import au.com.belong.phone.book.model.dto.PhoneNumberDTO;
import au.com.belong.phone.book.model.entity.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PhoneNumberMapperTest {

    private PhoneNumberMapper mapper;

    private PhoneNumber phoneNumber;

    private PhoneNumberDTO phoneNumberDTO;

    @BeforeEach
    void setUp() {
        mapper = new PhoneNumberMapperImpl();
        phoneNumber = new PhoneNumber(1L, "+61400123456", true);
    }

    @Test
    void shouldNotReturnNullDTO() {
        phoneNumberDTO = mapper.phoneNumberToDTO(phoneNumber);
        assertNotNull(phoneNumberDTO);
    }

    @Test
    void shouldMapCorrectPhoneNumber() {
        phoneNumberDTO = mapper.phoneNumberToDTO(phoneNumber);
        assertEquals(phoneNumber.getPhoneNumber(), phoneNumberDTO.phoneNumber());
    }

    @Test
    void shouldMapCorrectId() {
        phoneNumberDTO = mapper.phoneNumberToDTO(phoneNumber);
        assertEquals(phoneNumber.getIsActivated(), phoneNumberDTO.isActivated());
    }

    @Test
    void shouldReturnNullDTOIfPhoneNumberIsNull() {
        phoneNumberDTO = mapper.phoneNumberToDTO(null);
        assertNull(phoneNumberDTO);
    }


}