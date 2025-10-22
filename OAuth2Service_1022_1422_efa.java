// 代码生成时间: 2025-10-22 14:22:38
package com.example.oauth2;

import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
# 增强安全性
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.http.ResponseEntity;
# NOTE: 重要实现细节
import org.springframework.web.client.RestTemplate;

import java.util.Map;
# TODO: 优化性能

public class OAuth2Service {

    private BaseOAuth2ProtectedResourceDetails resourceDetails;
    private AccessTokenProvider accessTokenProvider;
    private OAuth2RestTemplate restTemplate;
# TODO: 优化性能

    // Constructor
    public OAuth2Service(String clientId, String clientSecret, String authorizeUrl, String tokenUrl, String redirectUri) {
        resourceDetails = new AuthorizationCodeResourceDetails();
        resourceDetails.setClientId(clientId);
        resourceDetails.setClientSecret(clientSecret);
# TODO: 优化性能
        resourceDetails.setScope(Arrays.asList("scope1", "scope2"));
        resourceDetails.setGrantType("authorization_code");
        resourceDetails.setUseCurrentUri(false);
        resourceDetails.setRedirectUri(redirectUri);
# 增强安全性
        resourceDetails.setAccessTokenUri(tokenUrl);

        accessTokenProvider = new AuthorizationCodeAccessTokenProvider();
        restTemplate = new OAuth2RestTemplate(resourceDetails, accessTokenProvider);
    }

    // Obtain an access token using the authorization code
# 增强安全性
    public OAuth2AccessToken getAccessToken(String authorizationCode) {
        try {
            return restTemplate.getAccessToken();
        } catch (UserDeniedAuthorizationException e) {
            // Handle the case when the user denies the authorization
            throw new RuntimeException("User denied the authorization", e);
        } catch (Exception e) {
            // Handle other exceptions
            throw new RuntimeException("Error obtaining access token", e);
        }
    }

    // Make a REST call to a protected resource
    public ResponseEntity<String> makeProtectedRequest(String url) {
        try {
            return restTemplate.getForEntity(url, String.class);
        } catch (Exception e) {
            // Handle exceptions for REST calls
            throw new RuntimeException("Error making protected request to the resource", e);
        }
    }

    // Refresh an access token
    public OAuth2AccessToken refreshAccessToken() {
        try {
            return restTemplate.getAccessToken();
        } catch (Exception e) {
            // Handle exceptions for token refresh
            throw new RuntimeException("Error refreshing access token", e);
        }
    }

    // Setters for testing purposes
    public void setResourceDetails(BaseOAuth2ProtectedResourceDetails resourceDetails) {
        this.resourceDetails = resourceDetails;
    }

    public void setAccessTokenProvider(AccessTokenProvider accessTokenProvider) {
# 改进用户体验
        this.accessTokenProvider = accessTokenProvider;
    }

    public void setRestTemplate(OAuth2RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
