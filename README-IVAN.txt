
These are some notes about what I needed to do to get this fork up and running. Have in mind
that I normally use Gradle and haven't ever used Maven; there may be better ways to solve
some of the problems I have encountered.


- The build includes signing, which I had to disable in pom.xml (maven-gpg-plugin).

- Other than that, the build fails for me for JDK 8, 11, and 13. I focused on getting 11 up and running.

- For 11 (and 13, I think), the problem was in org.openjsse.sun.security.util.CurveDB, which was
  trying to access some internal sun.security.util.CurveDB methods. I managed to rewrite the code
  to use public methods instead.

- At some point JavaDoc generation started failing. I must have changed something, but I couldn't
  correlate my changes to the error messages. I didn't care about the JavaDoc so I didn't pursue this.

- The project should now build with: $ mvn -P jdk.version.11 clean install

- To run anything with provider, you need to specify: --add-opens java.base/jdk.internal.misc=ALL-UNNAMED

- To use the new provider, first activate it with: Security.addProvider(new org.openjsse.net.ssl.OpenJSSE()). You
  could also try to install it at the top location, but for me that didn't work. I got a strange ASN.1-related
  error. After this, you can get an SSLContext from this provider with SSLContext.getInstance("TLS", "OpenJSSE").


Some further tweaks:

- In IDEA, import pom.xml and apply profile 11 to the Maven configuration.

- Even though IDEA would use JDK11 to run Maven, in my case there was an underlying JAVA_HOME that
  was pointing to JDK8, and that caused JavaDoc generation to fail. Set JAVA_HOME for Maven to
  the correct JDK.

- Setting profile to JDK 11 didn't help with IDEA, which continued to use Java 8 as the default language
  level. So I changed the default in pom.xml.
