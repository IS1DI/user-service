package com.is1di.userservicegradle.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "picture")
public class StorageConstants {
    private String defaultUrlPath;
}
