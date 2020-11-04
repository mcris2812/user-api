package com.company.api.user.sync;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Component
@Slf4j
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {

        return (
                httpResponse.getStatusCode().series() == CLIENT_ERROR
                        || httpResponse.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse)
            throws IOException {

        if (httpResponse.getStatusCode()
                .series() == HttpStatus.Series.SERVER_ERROR) {
            log.error("Cannot sync server error {} {} ", httpResponse.getStatusCode(), httpResponse);
        } else if (httpResponse.getStatusCode()
                .series() == HttpStatus.Series.CLIENT_ERROR) {
            log.error("Cannot sync  client error {} {} ", httpResponse.getStatusCode(), httpResponse);
            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.error("Resource not found {} {} ", httpResponse.getStatusCode(), httpResponse);
            }
        }
    }
}
