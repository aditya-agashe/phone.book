package au.com.belong.phone.book.controller;

import au.com.belong.phone.book.model.dto.PhoneNumberDTO;
import au.com.belong.phone.book.service.PhoneNumberService;
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
public class PhoneNumberController {

    private final PhoneNumberService phoneNumberService;

    public PhoneNumberController(PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
    }

    @GetMapping("/phone-numbers")
    public List<PhoneNumberDTO> getAllPhoneNumbers() {
        return phoneNumberService.getAllPhoneNumbers();
    }

    @GetMapping("/customers/{customerId}/phone-numbers")
    public List<PhoneNumberDTO> getPhoneNumbersByCustomer(@PathVariable Long customerId) {
        return phoneNumberService.getPhoneNumbersByCustomer(customerId);
    }

    @PatchMapping("/customers/{customerId}/phone-numbers/{phoneNumberId}")
    public ResponseEntity<PhoneNumberDTO> patchPhoneNumber(
        @PathVariable Long customerId, @PathVariable Long phoneNumberId,
        @RequestBody PhoneNumberDTO phoneNumberDTO) {
        final PhoneNumberDTO updated = phoneNumberService.patchPhoneNumber(customerId, phoneNumberId, phoneNumberDTO);
        return ResponseEntity.ok(updated);
    }
}