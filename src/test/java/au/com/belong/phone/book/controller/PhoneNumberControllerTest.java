package au.com.belong.phone.book.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import au.com.belong.phone.book.exception.handler.ResourceNotFoundException;
import au.com.belong.phone.book.model.dto.CustomerDTO;
import au.com.belong.phone.book.model.dto.PhoneNumberDTO;
import au.com.belong.phone.book.service.PhoneNumberService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
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
        final CustomerDTO customerDTO = new CustomerDTO(1L, "Customer 1");
        List<PhoneNumberDTO> phoneNumberDTOS = List.of(new PhoneNumberDTO(1L, "+61400123456", true, customerDTO));
        when(phoneNumberService.getAllPhoneNumbers()).thenReturn(phoneNumberDTOS);

        final String expectedJson = """
            [
              {
                "id": 1,
                "phoneNumber": "+61400123456",
                "isActivated": true,
                "customer": {
                  "id": 1,
                  "customerName": "Customer 1"
                }
              }
            ]
            """;

        final String actualJson = mockMvc.perform(get("/api/v1/phone-numbers")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        JSONAssert.assertEquals(expectedJson, actualJson, true);
        verify(phoneNumberService).getAllPhoneNumbers();
    }

    @Test
    void shouldInternalServerErrorIfPhoneNumbersThrowsTheSame() throws Exception {
        final String expectedJson = """
            {
              "message": "Error occurred while fetching phone numbers."
            }
        """;
        final String errorMessage = "Error occurred while fetching phone numbers.";
        when(phoneNumberService.getAllPhoneNumbers())
            .thenThrow(new RuntimeException(errorMessage));

        final String actualJson = mockMvc.perform(get("/api/v1/phone-numbers")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError())
            .andReturn()
            .getResponse()
            .getContentAsString();
        JSONAssert.assertEquals(expectedJson, actualJson, true);
        verify(phoneNumberService).getAllPhoneNumbers();
    }

    @Test
    void shouldReturnEmptyArrayIfCustomerPhoneNumbersIsEmpty() throws Exception {
        when(phoneNumberService.getPhoneNumbersByCustomer(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/customers/1/phone-numbers")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("[]"));

        verify(phoneNumberService).getPhoneNumbersByCustomer(1L);
    }

    @Test
    void shouldReturnCustomerPhoneNumbersArray() throws Exception {
        final CustomerDTO customerDTO = new CustomerDTO(1L, "Customer 1");
        List<PhoneNumberDTO> phoneNumberDTOS = List.of(new PhoneNumberDTO(1L, "+61400123456", true, customerDTO));
        when(phoneNumberService.getPhoneNumbersByCustomer(1L)).thenReturn(phoneNumberDTOS);

        final String expectedJson = """
            [
              {
                "id": 1,
                "phoneNumber": "+61400123456",
                "isActivated": true,
                "customer": {
                  "id": 1,
                  "customerName": "Customer 1"
                }
              }
            ]
            """;

        final String actualJson = mockMvc.perform(get("/api/v1/customers/1/phone-numbers")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        JSONAssert.assertEquals(expectedJson, actualJson, true);
        verify(phoneNumberService).getPhoneNumbersByCustomer(1L);
    }

    @Test
    void shouldNotFoundErrorIfCustomerPhoneNumbersThrowsTheSame() throws Exception {
        final String expectedJson = """
            {
              "message": "No customer found for ID: 1"
            }
        """;
        final String errorMessage = "No customer found for ID: 1";
        when(phoneNumberService.getPhoneNumbersByCustomer(1L))
            .thenThrow(new ResourceNotFoundException(errorMessage));

        final String actualJson = mockMvc.perform(get("/api/v1/customers/1/phone-numbers")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn()
            .getResponse()
            .getContentAsString();
        JSONAssert.assertEquals(expectedJson, actualJson, true);
        verify(phoneNumberService).getPhoneNumbersByCustomer(1L);
    }

}