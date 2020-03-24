package ru.itis.repository;

import ru.itis.models.Address;

import java.util.List;

public interface AddressesRepository extends CrudRepository<Long, Address> {
    List<Address> findByOwnerId(Long ownerId);
}
