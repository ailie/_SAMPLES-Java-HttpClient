package ipc.http.message.rsp;

import ipc.http.message.HTTPMessage;

/**
 * This is a message sent by the server back to the client, in response to a request.
 * <p>
 * The first line of this message is the Status-Line. It consists of the protocol
 * version, followed by a numeric status code (that tells about success or failure of
 * the request) and its associated textual phrase.
 */
public class HttpResponse extends HTTPMessage {

    public final String fProtocolVersion;
    public final int    fStatusCode;
    public final String fReasonPhrase;

    public HttpResponse(String protocolVersion, int statusCode, String reasonPhrase) {
        fProtocolVersion = protocolVersion;
        fStatusCode = statusCode;
        fReasonPhrase = reasonPhrase;
    }
}
