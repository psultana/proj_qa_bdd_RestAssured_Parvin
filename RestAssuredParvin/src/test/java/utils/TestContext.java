package utils;

import io.restassured.response.Response;

public class TestContext {
    private Response lastResponse;
    private String createdId;
    private String sessionCookie;

    public Response getLastResponse() { return lastResponse; }
    public void setLastResponse(Response r) { this.lastResponse = r; }

    public String getCreatedId() { return createdId; }
    public void setCreatedId(String id) { this.createdId = id; }

    public String getSessionCookie() { return sessionCookie; }
    public void setSessionCookie(String cookie) { this.sessionCookie = cookie; }
}
