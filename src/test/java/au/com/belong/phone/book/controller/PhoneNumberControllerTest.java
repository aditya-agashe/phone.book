package au.com.belong.phone.book.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import au.com.belong.phone.book.exception.handler.ResourceNotFoundException;
import au.com.belong.phone.book.model.dto.CustomerDTO;
import au.com.belong.phone.book.model.dto.PhoneNumberDTO;
import au.com.belong.phone.book.model.dto.PhoneNumberWithCustomerDTO;
import au.com.belong.phone.book.service.PhoneNumberService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Autowired
    private ObjectMapper objectMapper;

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
    void shouldReturnPhoneNumbersWithCustomerDetailsArray() throws Exception {
        final CustomerDTO customerDTO = new CustomerDTO(1L, "Customer 1");
        List<PhoneNumberWithCustomerDTO> phoneNumberDTOS = List.of(
            new PhoneNumberWithCustomerDTO(1L, "+61400123456", true, customerDTO));
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
    void shouldReturnInternalServerErrorIfPhoneNumbersThrowsTheSame() throws Exception {
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
        List<PhoneNumberDTO> phoneNumberDTOS = List.of(new PhoneNumberDTO(1L, "+61400123456", true));
        when(phoneNumberService.getPhoneNumbersByCustomer(1L)).thenReturn(phoneNumberDTOS);

        final String expectedJson = """
            [
              {
                "id": 1,
                "phoneNumber": "+61400123456",
                "isActivated": true
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
    void shouldReturnNotFoundErrorIfCustomerPhoneNumbersThrowsTheSame() throws Exception {
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

    @Test
    void shouldReturnUpdatedCustomerPhoneNumber() throws Exception {
        final PhoneNumberDTO phoneNumberDTO = new PhoneNumberDTO(1L, "+61400123456", true);
        when(phoneNumberService.patchPhoneNumber(1L, 1L, phoneNumberDTO)).thenReturn(phoneNumberDTO);
        final String expectedJson = """
              {
                "id": 1,
                "phoneNumber": "+61400123456",
                "isActivated": true
              }
            """;

        final String actualJson = mockMvc.perform(patch("/api/v1/customers/1/phone-numbers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(phoneNumberDTO)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        JSONAssert.assertEquals(expectedJson, actualJson, true);
        verify(phoneNumberService).patchPhoneNumber(1L, 1L, phoneNumberDTO);
    }

    @Test
    void shouldReturnNotFoundErrorIfCustomerPhoneNumberCannotBeIdentified() throws Exception {
        final String expectedJson = """
            {
              "message": "Phone Number not found with Customer ID: 1 and Phone Number ID: 2"
            }
        """;
        final String errorMessage = "Phone Number not found with Customer ID: 1 and Phone Number ID: 2";
        final PhoneNumberDTO phoneNumberDTO = new PhoneNumberDTO(1L, "", true);
        when(phoneNumberService.patchPhoneNumber(1L, 2L, phoneNumberDTO))
            .thenThrow(new ResourceNotFoundException(errorMessage));

        final String actualJson = mockMvc.perform(patch("/api/v1/customers/1/phone-numbers/2")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(phoneNumberDTO)))
            .andExpect(status().isNotFound())
            .andReturn()
            .getResponse()
            .getContentAsString();
        JSONAssert.assertEquals(expectedJson, actualJson, true);
        verify(phoneNumberService).patchPhoneNumber(1L, 2L, phoneNumberDTO);
    }

    @Test
    void shouldReturnBadRequestErrorIfThePassedJsonIsMalformed() throws Exception {
        final String content = """
              {
                "id": 1,
                "phoneNumber": "+61400123456",
                "isActivated": "ABCD"
              }
            """;
        final String expectedJson = """
            {
              "message":
              "JSON parse error: Cannot deserialize value of type `java.lang.Boolean` from String \\"ABCD\\": only \\"true\\" or \\"false\\" recognized"
            }
        """;

        final String actualJson = mockMvc.perform(patch("/api/v1/customers/1/phone-numbers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();
        JSONAssert.assertEquals(expectedJson, actualJson, true);
        verifyNoInteractions(phoneNumberService);
    }

}