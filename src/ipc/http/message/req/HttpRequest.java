package ipc.http.message.req;

import java.net.URI;
import java.net.URISyntaxException;

import ipc.http.message.HTTPMessage;

/**
 * This gets sent from a client to a server. All HTTP requests have (1) a Request-Line
 * consisting a method name, (2) a request URI and (3) a HTTP protocol version.
 * <p>
 * Method is an operation requested from the server. HTTP defines a set of operations,
 * the most frequent being GET and POST. Other protocols based on HTTP can define
 * additional methods.
 */
public abstract class HttpRequest extends HTTPMessage {

    public static enum Method {
        GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS
    }

    final private Method fMethod;

    /**
     * This identifies the resource upon which to apply the request. It consists of a
     * protocol scheme, host name, optional port, resource path, optional query, and
     * optional fragment.
     */
    final private URI    fURI;

    final private String fHTTPProtocolVersion;

    public HttpRequest(Method method, String stringURI) throws URISyntaxException {
        this(method, new URI(stringURI), "HTTP/1.1");
    }

    public HttpRequest(Method method, URI uri, String httpProtocolVersion) {
        fMethod = method;
        fURI = uri;
        fHTTPProtocolVersion = httpProtocolVersion;
    }

    public Method getMethod() {
        return fMethod;
    }

    public URI getURI() {
        return fURI;
    }

    public String getHTTPProtocolVersion() {
        return fHTTPProtocolVersion;
    }
}
