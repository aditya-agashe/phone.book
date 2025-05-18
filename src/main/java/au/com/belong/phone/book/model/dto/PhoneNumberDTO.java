package au.com.belong.phone.book.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PhoneNumberDTO(Long id,
                             String phoneNumber,
                             Boolean isActivated,
                             @JsonProperty("customer") CustomerDTO customerDTO) {
}
