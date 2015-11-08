package it.toto.services.restHooks;

import com.google.common.base.Optional;
import it.toto.services.restHooks.model.CustomerDao;
import it.toto.services.restHooks.model.CustomerEntity;
import org.apache.commons.lang.BooleanUtils;

import javax.inject.Inject;

/**
 * Created by toto on 08/11/15.
 */
public class UserProfile {

    private CustomerDao lightUserDao;

    @Inject
    public UserProfile(CustomerDao lightUserDao){

        this.lightUserDao = lightUserDao;
    }

    public Optional<UserType> resolve(String username) {

        final Optional<CustomerEntity> customerOptional = lightUserDao.findByUsername(username);
        if(customerOptional.isPresent()){

            final CustomerEntity customer = customerOptional.get();

            if (BooleanUtils.isTrue(customer.getAdmin())) {
                return Optional.of(UserType.ADMIN);
            } else if (BooleanUtils.isTrue(customer.getCustomer())) {
                return Optional.of(UserType.CUSTOMER);
            } {
                return Optional.of(UserType.LIGHT);
            }

        }
        return Optional.absent();
    }
}
