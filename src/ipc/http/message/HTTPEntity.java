package ipc.http.message;

import java.io.InputStream;

/**
 * HTTP messages can optionally carry a content entity (ie data payload).
 * <p>
 * Requests that use entities are referred to as entity enclosing requests. The HTTP
 * specification defines two entity enclosing request methods: POST and PUT. The entity
 * can include the parameters that you entered into a web form, for example.
 * <p>
 * Responses are usually expected to enclose a content entity (eg the page or image you
 * requested). There are exceptions to this rule such as responses to HEAD method and
 * 204 No Content, 304 Not Modified, 205 Reset Content responses.
 * <p>
 * An entity consists of entity-header and, optionally, of entity-body. The entity of an
 * HTTP message can have an arbitrary data format, which is usually specified as a MIME
 * type in a header field.
 * <p>
 * Apache HttpClient distinguishes three kinds of entities, depending on where their
 * content originates:
 * <p>
 * (1) streamed: The content is received from a stream, or generated on the fly. In
 * particular, this category includes entities being received from HTTP responses.
 * Streamed entities are generally not repeatable (ie their content cannot be read more
 * than once).
 * <p>
 * (2) self-contained: The content is in memory or obtained by means that are
 * independent from a connection or other entity. Self-contained entities are generally
 * repeatable (ie their content can be read more than once). This type of entities will
 * be mostly used for entity enclosing HTTP requests.
 * <p>
 * (3) wrapping: The content is obtained from another entity.
 */
public class HTTPEntity {

    private long        fContentLength;
    private String      fContentType;
    private String      fContentEncoding;

    private InputStream fContent;

    public long getContentLength() {
        return fContentLength;
    }

    public HTTPEntity setContentLength(long l) {
        fContentLength = l;
        return this;
    }

    public String getContentType() {
        return fContentType;
    }

    public HTTPEntity setContentType(String contentType) {
        fContentType = contentType;
        return this;
    }

    public String getContentEncoding() {
        return fContentEncoding;
    }

    public HTTPEntity setContentEncoding(String contentEncoding) {
        fContentEncoding = contentEncoding;
        return this;
    }

    public InputStream getContent() {
        return fContent;
    }

    public HTTPEntity setContent(InputStream is) {
        fContent = is;
        return this;
    }
}
