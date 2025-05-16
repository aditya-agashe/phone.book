package au.com.belong.phone.book.service;

import au.com.belong.phone.book.mapper.PhoneNumberMapper;
import au.com.belong.phone.book.model.dto.PhoneNumberDTO;
import au.com.belong.phone.book.repository.PhoneNumberRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;

    private final PhoneNumberMapper phoneNumberMapper;

    public PhoneNumberService(PhoneNumberRepository phoneNumberRepository, PhoneNumberMapper phoneNumberMapper) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.phoneNumberMapper = phoneNumberMapper;
    }

    public List<PhoneNumberDTO> getAllPhoneNumbers() {
        return phoneNumberRepository
            .findAll()
            .stream()
            .map(phoneNumberMapper::phoneNumberToDTO)
            .toList();
    }

}
