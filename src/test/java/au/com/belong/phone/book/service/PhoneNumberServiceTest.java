package au.com.belong.phone.book.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import au.com.belong.phone.book.exception.handler.ResourceNotFoundException;
import au.com.belong.phone.book.mapper.PhoneNumberMapper;
import au.com.belong.phone.book.model.dto.CustomerDTO;
import au.com.belong.phone.book.model.dto.PhoneNumberDTO;
import au.com.belong.phone.book.model.entity.Customer;
import au.com.belong.phone.book.model.entity.PhoneNumber;
import au.com.belong.phone.book.repository.CustomerRepository;
import au.com.belong.phone.book.repository.PhoneNumberRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class PhoneNumberServiceTest {

    private PhoneNumberService service;

    @Mock
    private PhoneNumberRepository phoneNumberRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PhoneNumberMapper mapper;

    private Customer customer;

    private PhoneNumber phoneNumber;
    private PhoneNumberDTO phoneNumberDTO;


    @BeforeEach
    void setUp() {
        service = new PhoneNumberService(phoneNumberRepository, customerRepository, mapper);
        customer = new Customer(1L, "Customer 1");
        phoneNumber = new PhoneNumber(1L, "+61400123456", true, customer);

        final CustomerDTO customerDTO = new CustomerDTO(1L, "Customer 1");
        phoneNumberDTO = new PhoneNumberDTO(1L, "+61400123456", true, customerDTO);
    }

    @Test
    void shouldReturnAllPhoneNumbers() {
        when(phoneNumberRepository.findAll()).thenReturn(List.of(phoneNumber));
        when(mapper.phoneNumberToDTO(phoneNumber)).thenReturn(phoneNumberDTO);
        assertEquals(1, service.getAllPhoneNumbers().size());
        verify(phoneNumberRepository).findAll();
        verify(mapper).phoneNumberToDTO(phoneNumber);
    }

    @Test
    void shouldReturnPhoneNumbersWhenCustomerExists() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(phoneNumberRepository.findByCustomerId(1L)).thenReturn(List.of(phoneNumber));
        when(mapper.phoneNumberToDTO(phoneNumber)).thenReturn(phoneNumberDTO);

        List<PhoneNumberDTO> result = service.getPhoneNumbersByCustomer(1L);

        assertEquals(1, result.size());
        assertEquals(phoneNumberDTO, result.get(0));
        verify(customerRepository).findById(1L);
        verify(phoneNumberRepository).findByCustomerId(1L);
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {
        when(customerRepository.findById(999L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
            ResourceNotFoundException.class, () -> service.getPhoneNumbersByCustomer(999L)
        );

        assertEquals("No customer found for ID: 999", ex.getMessage());
        verify(customerRepository).findById(999L);
        verifyNoInteractions(phoneNumberRepository);
    }

}