package it.toto.services.restHooks.request;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import it.toto.services.restHooks.ApiValidation;
import it.toto.services.restHooks.model.CustomerDao;
import it.toto.services.restHooks.model.CustomerEntity;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.glassfish.jersey.internal.util.Base64;

/**
 * Created by toto on 08/11/15.
 */
@Slf4j
@ToString
public class BasicAuthorizationRequest implements AuthorizationRequest {

    @Getter
    private final String username;
    @Getter
    private final String request;
    @Getter
    private final boolean passed;

    public interface Factory {

        BasicAuthorizationRequest create(@Assisted("md5Credentials") String md5Credentials);

    }

    @AssistedInject
    public BasicAuthorizationRequest(
            CustomerDao CustomerDao,
            @Assisted("md5Credentials") String md5Credentials) {
        this.request = md5Credentials;
        String credentialDecode = Base64.decodeAsString(md5Credentials);
        log.debug("credential {}", credentialDecode);
        String[] credentials = StringUtils.splitByWholeSeparator(credentialDecode, ":");
        if (credentials != null && credentials.length > 1) {
            this.username = credentials[0];
            final String password = credentials[1];
            final Optional<CustomerEntity> lightUserEntityOptional = CustomerDao.findByUsername(this.username);
            if (lightUserEntityOptional.isPresent() && ApiValidation.validCustomerPassword(lightUserEntityOptional.get(), password)) {
                this.passed = true;
            } else {
                this.passed = false;
            }
        } else {
            this.username = null;
            this.passed = false;
        }
    }

}
