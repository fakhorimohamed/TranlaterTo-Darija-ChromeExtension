
package org.eclipse.jakarta.hello;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
public class TranslatorApplication extends Application {
	 // Needed to enable Jakarta REST and specify path.
}