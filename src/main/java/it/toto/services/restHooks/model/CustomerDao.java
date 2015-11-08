package it.toto.services.restHooks.model;

import com.google.common.base.Optional;

/**
 * Created by toto on 08/11/15.
 */
public interface CustomerDao {
    Optional<CustomerEntity> findByUsername(String username);
}
