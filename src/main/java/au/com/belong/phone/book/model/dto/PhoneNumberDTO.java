package au.com.belong.phone.book.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PhoneNumberDTO(Long id,
                             String phoneNumber,
                             // @NotNull(message = "isActivated must not be null")
                             Boolean isActivated,
                             @JsonProperty("customer") CustomerDTO customerDTO) {
}
