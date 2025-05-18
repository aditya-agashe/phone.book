package au.com.belong.phone.book.controller;

import au.com.belong.phone.book.exception.handler.ErrorResponse;
import au.com.belong.phone.book.model.dto.PhoneNumberDTO;
import au.com.belong.phone.book.model.dto.PhoneNumberWithCustomerDTO;
import au.com.belong.phone.book.service.PhoneNumberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Phone Numbers", description = "Operations related to phone numbers")
public class PhoneNumberController {

    private final PhoneNumberService phoneNumberService;

    public PhoneNumberController(PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
    }

    @Operation(summary = "Get all phone numbers", responses = {
        @ApiResponse(responseCode = "200", description = "Get all phone numbers with customer details",
            content = @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = PhoneNumberWithCustomerDTO.class))))
    })
    @GetMapping("/phone-numbers")
    public List<PhoneNumberWithCustomerDTO> getAllPhoneNumbers() {
        return phoneNumberService.getAllPhoneNumbers();
    }

    @Operation(summary = "Get all phone numbers for a specific customer", responses = {
        @ApiResponse(responseCode = "200", description = "Phone numbers belonging to the customer",
            content = @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = PhoneNumberDTO.class)))),
        @ApiResponse(responseCode = "404", description = "Customer not found",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/customers/{customerId}/phone-numbers")
    public List<PhoneNumberDTO> getPhoneNumbersByCustomer(
        @Parameter(description = "ID of the customer", required = true)
        @PathVariable Long customerId) {
        return phoneNumberService.getPhoneNumbersByCustomer(customerId);
    }

    @Operation(
        summary = "Partially update a customer's phone number, for now just manage isActivated",
        description = "Updates one or more fields of a phone number that belongs to the specified customer. "
            + "Fields not provided in the request body will remain unchanged. Activate or Deactivate the phone number.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Phone number activated or deactivated successfully",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PhoneNumberDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad Request - The request body is malformed or contains invalid data",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Not Found - The combination of customerId and phoneNumberId does not exist",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            )
        }
    )
    @PatchMapping("/customers/{customerId}/phone-numbers/{phoneNumberId}")
    public ResponseEntity<PhoneNumberDTO> patchPhoneNumber(
        @Parameter(description = "ID of the customer", required = true)
        @PathVariable Long customerId,
        @Parameter(description = "ID of the phone number to update", required = true)
        @PathVariable Long phoneNumberId,
        @RequestBody PhoneNumberDTO phoneNumberDTO) {
        final PhoneNumberDTO updated = phoneNumberService.patchPhoneNumber(customerId, phoneNumberId, phoneNumberDTO);
        return ResponseEntity.ok(updated);
    }
}