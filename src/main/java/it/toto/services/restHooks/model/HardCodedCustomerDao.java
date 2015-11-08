package it.toto.services.restHooks.model;

import com.google.common.base.Optional;
import org.apache.commons.lang.StringUtils;

/**
 * Created by toto on 08/11/15.
 */
public class HardCodedCustomerDao implements CustomerDao{

    @Override
    public Optional<CustomerEntity> findByUsername(String username) {
        CustomerEntity result = null;
        if (StringUtils.equals(username, "stop-play-minecraft")) {
            result = CustomerEntity.of(
                "stop-play-minecraft",
                "cac3d65e597df4a39840089798677231",
                false,
                true
            );
        }
        return Optional.fromNullable(result);
    }
}
