package com.github.totoCastaldi.services;

import com.github.totoCastaldi.restServer.UserType;
import com.github.totoCastaldi.restServer.authorization.AuthorizationResult;
import com.github.totoCastaldi.restServer.authorization.BasicAuthorization;
import com.google.common.base.Optional;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;

/**
 * Created by toto on 29/11/15.
 */
@Slf4j
public class MashapeCredentialResource implements BasicAuthorization {

    @Override
    public AuthorizationResult checkCredential(String username, String password) {
        try {
            HttpResponse<JsonNode> response = Unirest.get("https://toto-credential-v1.p.mashape.com/user/" + username + "/" + password)
                    .header("X-Mashape-Key", "FCKFcesv9PmshjpunUVhQVx88GI7p1HDYROjsnTmJ45NAYWEnd")
                    .asJson();

            final JsonNode body = response.getBody();

            log.info("authentication {} {}", response.getStatus(),  body.toString());

            if (response.getStatus() == HttpStatus.SC_OK) {
                return AuthorizationResult.passed();
            } else {
                return AuthorizationResult.notPassed("manage your credential here http://toto-castaldi.github.io/rest-switch-resource/");
            }
        } catch (UnirestException e) {
            log.error(StringUtils.EMPTY, e);
            return AuthorizationResult.notPassed("manage your credential here http://toto-castaldi.github.io/rest-switch-resource/");
        }
    }
}
