package com.googlecode.jsonrpc4j;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Contains the JSON-RPC answer in {@code response}
 * {@code exceptionToRethrow} contains exception, which should be thrown when property {@code rethrowExceptions}
 * is active
 */
public class JsonResponse {
    private JsonNode response;
    private int code;
    private RuntimeException exceptionToRethrow;

    public JsonResponse() {
    }

    public JsonResponse(JsonNode response, int code) {
        this.response = response;
        this.code = code;
    }

    public JsonNode getResponse() {
        if(!response.has(JsonRpcBasicServer.CONTEXT)){
            Object id0 = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getAttribute(JsonRpcServer.X_REQUEST_ID,0);
            if(id0!=null) {
                ObjectNode resNode = (ObjectNode) response;
                ObjectMapper mapper = new ObjectMapper();
                String id = id0.toString();
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put(JsonRpcServer.X_REQUEST_ID, id);
                Object context = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getAttribute(JsonRpcServer.X_REQUEST_CONTEXT, 0);
                if (context != null) {
                    objectNode.put(JsonRpcServer.X_REQUEST_CONTEXT, mapper.valueToTree(context));
                }
                resNode.put(JsonRpcBasicServer.CONTEXT, objectNode);
                response = resNode;
            }
        }
        return response;
    }

    public void setResponse(JsonNode response) {
        this.response = response;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public RuntimeException getExceptionToRethrow() {
        return exceptionToRethrow;
    }

    public void setExceptionToRethrow(RuntimeException exceptionToRethrow) {
        this.exceptionToRethrow = exceptionToRethrow;
    }
}
