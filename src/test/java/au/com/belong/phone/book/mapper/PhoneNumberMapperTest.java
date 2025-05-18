package au.com.belong.phone.book.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import au.com.belong.phone.book.model.dto.PhoneNumberDTO;
import au.com.belong.phone.book.model.entity.Customer;
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
        final Customer customer = new Customer(1L, "Customer 1");
        phoneNumber = new PhoneNumber(1L, "+61400123456", true, customer);
    }

    @Test
    void shouldReturnNonNullPhoneNumberDTO() {
        phoneNumberDTO = mapper.phoneNumberToDTO(phoneNumber);
        assertNotNull(phoneNumberDTO);
    }

    @Test
    void shouldReturnNonNullCustomerDTO() {
        phoneNumberDTO = mapper.phoneNumberToDTO(phoneNumber);
        assertNotNull(phoneNumberDTO.customerDTO());
    }

    @Test
    void shouldMapCorrectPhoneNumber() {
        phoneNumberDTO = mapper.phoneNumberToDTO(phoneNumber);
        assertEquals(phoneNumber.getPhoneNumber(), phoneNumberDTO.phoneNumber());
    }

    @Test
    void shouldMapCorrectIsActivated() {
        phoneNumberDTO = mapper.phoneNumberToDTO(phoneNumber);
        assertEquals(phoneNumber.getIsActivated(), phoneNumberDTO.isActivated());
    }

    @Test
    void shouldReturnNullPhoneNumberDTOIfPhoneNumberIsNull() {
        phoneNumberDTO = mapper.phoneNumberToDTO(null);
        assertNull(phoneNumberDTO);
    }

    @Test
    void shouldReturnNullCustomerDTOIfCustomerIsNull() {
        phoneNumberDTO = mapper.phoneNumberToDTO(new PhoneNumber(1L, "+61400123456", true, null));
        assertNull(phoneNumberDTO.customerDTO());
    }


}