package au.com.belong.phone.book.service;

import au.com.belong.phone.book.exception.handler.ResourceNotFoundException;
import au.com.belong.phone.book.mapper.PhoneNumberMapper;
import au.com.belong.phone.book.model.dto.PhoneNumberDTO;
import au.com.belong.phone.book.model.dto.PhoneNumberWithCustomerDTO;
import au.com.belong.phone.book.model.entity.Customer;
import au.com.belong.phone.book.model.entity.PhoneNumber;
import au.com.belong.phone.book.repository.CustomerRepository;
import au.com.belong.phone.book.repository.PhoneNumberRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;

    private final CustomerRepository customerRepository;

    private final PhoneNumberMapper phoneNumberMapper;

    public PhoneNumberService(PhoneNumberRepository phoneNumberRepository,
        CustomerRepository customerRepository,
        PhoneNumberMapper phoneNumberMapper) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.customerRepository = customerRepository;
        this.phoneNumberMapper = phoneNumberMapper;
    }

    public List<PhoneNumberWithCustomerDTO> getAllPhoneNumbers() {
        return phoneNumberRepository
            .findAll()
            .stream()
            .map(phoneNumberMapper::toPhoneNumberWithCustomerDTO)
            .toList();
    }

    public List<PhoneNumberDTO> getPhoneNumbersByCustomer(Long customerId) {
        final Optional<Customer> customer = customerRepository.findById(customerId);

        if (customer.isEmpty()) {
            throw new ResourceNotFoundException("No customer found for ID: " + customerId);
        }

        return phoneNumberRepository
            .findByCustomerId(customerId)
            .stream()
            .map(phoneNumberMapper::toPhoneNumberDTO)
            .toList();
    }

    public PhoneNumberDTO patchPhoneNumber(Long customerId, Long phoneNumberId, PhoneNumberDTO phoneNumberDTO) {
        final PhoneNumber phoneNumber = phoneNumberRepository.findByIdAndCustomerId(customerId, phoneNumberId)
            .orElseThrow(() -> new ResourceNotFoundException(
                String.format("Phone Number not found with Customer ID: %d and Phone Number ID: %d",
                    customerId, phoneNumberId)
            ));

        if (phoneNumberDTO.isActivated() != null) {
            phoneNumber.setIsActivated(phoneNumberDTO.isActivated());
        }

        final PhoneNumber updated = phoneNumberRepository.save(phoneNumber);
        return phoneNumberMapper.toPhoneNumberDTO(updated);
    }

}
