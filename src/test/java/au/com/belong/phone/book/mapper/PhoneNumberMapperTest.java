package au.com.belong.phone.book.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import au.com.belong.phone.book.model.dto.PhoneNumberDTO;
import au.com.belong.phone.book.model.dto.PhoneNumberWithCustomerDTO;
import au.com.belong.phone.book.model.entity.Customer;
import au.com.belong.phone.book.model.entity.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PhoneNumberMapperTest {

    private PhoneNumberMapper mapper;

    private PhoneNumber phoneNumber;

    private PhoneNumberDTO phoneNumberDTO;
    private PhoneNumberWithCustomerDTO phoneNumberWithCustomerDTO;

    @BeforeEach
    void setUp() {
        mapper = new PhoneNumberMapperImpl();
        final Customer customer = new Customer(1L, "Customer 1");
        phoneNumber = new PhoneNumber(1L, "+61400123456", true, customer);
    }

    @Test
    void shouldReturnNonNullPhoneNumberDTO() {
        phoneNumberDTO = mapper.toPhoneNumberDTO(phoneNumber);
        assertNotNull(phoneNumberDTO);
    }

    @Test
    void shouldMapCorrectPhoneNumber() {
        phoneNumberDTO = mapper.toPhoneNumberDTO(phoneNumber);
        assertEquals(phoneNumber.getContactNumber(), phoneNumberDTO.contactNumber());
    }

    @Test
    void shouldMapCorrectIsActivated() {
        phoneNumberDTO = mapper.toPhoneNumberDTO(phoneNumber);
        assertEquals(phoneNumber.getIsActivated(), phoneNumberDTO.isActivated());
    }

    @Test
    void shouldReturnNullPhoneNumberDTOIfPhoneNumberIsNull() {
        phoneNumberDTO = mapper.toPhoneNumberDTO(null);
        assertNull(phoneNumberDTO);
    }

    @Test
    void shouldReturnNonNullCustomerDTO() {
        phoneNumberWithCustomerDTO = mapper.toPhoneNumberWithCustomerDTO(phoneNumber);
        assertNotNull(phoneNumberWithCustomerDTO.customerDTO());
    }

    @Test
    void shouldReturnNullCustomerDTOIfCustomerIsNull() {
        phoneNumberWithCustomerDTO = mapper.toPhoneNumberWithCustomerDTO(new PhoneNumber(1L, "+61400123456", true, null));
        assertNull(phoneNumberWithCustomerDTO.customerDTO());
    }

    @Test
    void shouldReturnNullPhoneNumberWithCustomerDTO() {
        phoneNumberWithCustomerDTO = mapper.toPhoneNumberWithCustomerDTO(null);
        assertNull(phoneNumberWithCustomerDTO);
    }


}