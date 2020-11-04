package com.company.api.user.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "wiremock")
@Getter
@Setter
@NoArgsConstructor
public class WiremockServiceConfig {
    private String url;
    private String accounts;
    private String transactions;
    private String login;

    public String getAccountsUrl() {
        return this.url + this.accounts;
    }

    public String getTransactionsUrl() {
        return this.url + this.transactions;
    }

    public String getLoginUrl() {
        return this.url + this.login;
    }
}
