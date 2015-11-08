package it.toto.services.restHooks.filter;

import it.toto.services.restHooks.ApiHeaderUtils;
import it.toto.services.restHooks.UserType;
import it.toto.services.restHooks.annotation.UserProfileCustomer;
import it.toto.services.restHooks.response.ApiResponse;
import it.toto.services.restHooks.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by toto on 08/11/15.
 */
@Slf4j
@UserProfileCustomer
public class ProfileCustomerAbortRequestFilter extends BaseProfileAbortRequestFilter {

    @Inject
    public ProfileCustomerAbortRequestFilter(
            ApiHeaderUtils apiHeaderUtils,
            ApiResponse apiResponse,
            HttpServletRequest httpRequest
    ) {
        super(
                apiHeaderUtils,
                apiResponse,
                httpRequest,
                UserType.CUSTOMER,
                ErrorResponse.DetailsCode.PROFILE_NOT_CUSTOMER
        );
    }

}

