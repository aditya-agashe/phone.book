package au.com.belong.phone.book.service;

import au.com.belong.phone.book.exception.handler.ResourceNotFoundException;
import au.com.belong.phone.book.mapper.PhoneNumberMapper;
import au.com.belong.phone.book.model.dto.PhoneNumberDTO;
import au.com.belong.phone.book.model.entity.Customer;
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

    public List<PhoneNumberDTO> getAllPhoneNumbers() {
        return phoneNumberRepository
            .findAll()
            .stream()
            .map(phoneNumberMapper::phoneNumberToDTO)
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
            .map(phoneNumberMapper::phoneNumberToDTO)
            .toList();
    }
}
