package au.com.belong.phone.book.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import au.com.belong.phone.book.model.dto.PhoneNumberDTO;
import au.com.belong.phone.book.service.PhoneNumberService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PhoneNumberController.class)
class PhoneNumberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhoneNumberService phoneNumberService;

    @Test
    void shouldReturnEmptyArrayIfPhoneNumbersIsEmpty() throws Exception {
        when(phoneNumberService.getAllPhoneNumbers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/phone-numbers")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("[]"));

        verify(phoneNumberService).getAllPhoneNumbers();
    }

    @Test
    void shouldReturnPhoneNumbersArray() throws Exception {
        List<PhoneNumberDTO> phoneNumberDTOS = List.of(new PhoneNumberDTO(1L, "+61400123456", true));
        when(phoneNumberService.getAllPhoneNumbers()).thenReturn(phoneNumberDTOS);

        mockMvc.perform(get("/api/v1/phone-numbers")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("""
                                [{"id":1,"phoneNumber":"+61400123456","isActivated":true}]"""));

        verify(phoneNumberService).getAllPhoneNumbers();
    }

    @Test
    void shouldInternalServerErrorIfPhoneNumbersThrowsTheSame() throws Exception {
        final String errorMessage = "Error occurred while fetching phone numbers.";
        when(phoneNumberService.getAllPhoneNumbers())
            .thenThrow(new RuntimeException(errorMessage));

        mockMvc.perform(get("/api/v1/phone-numbers")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError())
            .andExpect(content().string(errorMessage));

        verify(phoneNumberService).getAllPhoneNumbers();
    }

}