package au.com.belong.phone.book.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CustomerDTO(Long id, String customerName) {
}
