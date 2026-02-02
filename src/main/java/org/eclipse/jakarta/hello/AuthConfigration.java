package org.eclipse.jakarta.hello;

import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.enterprise.context.ApplicationScoped;

@BasicAuthenticationMechanismDefinition(realmName = "DarijaRealm")
@ApplicationScoped
public class AuthConfig {
    // This activates the security mechanism for the whole app
}