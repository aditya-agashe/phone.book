package au.com.belong.phone.book.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import au.com.belong.phone.book.mapper.PhoneNumberMapper;
import au.com.belong.phone.book.model.dto.PhoneNumberDTO;
import au.com.belong.phone.book.model.entity.PhoneNumber;
import au.com.belong.phone.book.repository.PhoneNumberRepository;
import java.util.List;
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
    private PhoneNumberMapper mapper;

    private PhoneNumber phoneNumber;

    @BeforeEach
    void setUp() {
        service = new PhoneNumberService(phoneNumberRepository, mapper);
        phoneNumber = new PhoneNumber(1L, "+61400123456", true);

        PhoneNumberDTO phoneNumberDTO1 = new PhoneNumberDTO(1L, "+61400123456", true);

        when(phoneNumberRepository.findAll()).thenReturn(List.of(phoneNumber));
        when(mapper.phoneNumberToDTO(phoneNumber)).thenReturn(phoneNumberDTO1);
    }

    @Test
    void shouldReturnNonNullPhoneNumbers() {
        assertEquals(1, service.getAllPhoneNumbers().size());
        verify(phoneNumberRepository).findAll();
        verify(mapper).phoneNumberToDTO(phoneNumber);
    }


}