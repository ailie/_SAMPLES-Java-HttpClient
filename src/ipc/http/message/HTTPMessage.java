package ipc.http.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RFC 822 HTTP messages get passed between client and server. They consist of (1) a
 * start-line, (2) zero or more header fields, (3) an empty line indicating the end of
 * the header fields, and possibly (4) a message-body. The header fields describe
 * properties of the message (such as the content length, content type and so on). The
 * message-body can contain an entity.
 * <p>
 * There are two kinds of messages: requests and responses. They differ in the format of
 * the first line, but both can have header fields and an optional entity.
 */
public abstract class HTTPMessage {

    /**
     * Name-value pairs, where both name and value are text. The name of a header field
     * is not case sensitive. Multiple values can be assigned to the same name. RFC 2616
     * defines a wide range of header fields for handling various aspects of the HTTP
     * protocol. Other specifications, like RFC 2617 and RFC 2965, define additional
     * headers. Some of the defined headers are for general use, others are meant for
     * exclusive use with either requests or responses, still others are meant for use
     * only with an entity.
     */
    private final Map<String, List<String>> fHeaderFields = new HashMap<String, List<String>>();
    private HTTPEntity                      fEntity;

    public HTTPMessage addHeaderField(String key, String val) {
        if (!fHeaderFields.containsKey(key))
            fHeaderFields.put(key, new ArrayList<String>());
        fHeaderFields.get(key).add(val);
        return this;
    }

    public Map<String, List<String>> getHeaderFields() {
        return fHeaderFields;
    }

    public HTTPEntity getEntity() {
        return fEntity;
    }

    public HTTPMessage setEntity(HTTPEntity entity) {
        fEntity = entity;
        return this;
    }
}
