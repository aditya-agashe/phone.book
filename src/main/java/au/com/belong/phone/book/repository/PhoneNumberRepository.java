package au.com.belong.phone.book.repository;

import au.com.belong.phone.book.model.entity.PhoneNumber;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {

    @Query("SELECT p FROM PhoneNumber p WHERE p.customer.id = :customerId")
    List<PhoneNumber> findByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT p FROM PhoneNumber p WHERE p.id = :phoneNumberId AND p.customer.id = :customerId")
    Optional<PhoneNumber> findByIdAndCustomerId(
        @Param("customerId") Long customerId,
        @Param("phoneNumberId") Long phoneNumberId);
}
