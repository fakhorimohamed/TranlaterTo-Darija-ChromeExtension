package org.eclipse.jakarta.hello;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import java.util.Set;

@ApplicationScoped
public class InMemoryIdentityStore implements IdentityStore {

    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential userCredential = (UsernamePasswordCredential) credential;
            
            String username = userCredential.getCaller();
            String password = userCredential.getPasswordAsString();

            
            System.out.println("--- Security Check: Attempting login for " + username + " ---");

            if ("admin".equals(username) && "1234".equals(password)) {
                return new CredentialValidationResult("admin", Set.of("admin"));
            }
        }
        return CredentialValidationResult.INVALID_RESULT;
    }
}