package com.demo.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Component
public class ConfigProperties {

    @Value("${app.config.max-withdrawal-per-day}")
    private Double maxWithdrawalPerDay;
}
