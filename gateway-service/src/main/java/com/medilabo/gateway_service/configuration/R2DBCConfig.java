//package com.medilabo.gateway_service.configuration;
//
//import io.r2dbc.spi.ConnectionFactories;
//import io.r2dbc.spi.ConnectionFactory;
//import io.r2dbc.spi.ConnectionFactoryOptions;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
//import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
//
//@Configuration
//@EnableR2dbcRepositories
//public class R2DBCConfig extends AbstractR2dbcConfiguration {
//
//    @Bean
//    @Override
//    public ConnectionFactory connectionFactory() {
//        return ConnectionFactories.get(ConnectionFactoryOptions.builder()
//                .option(ConnectionFactoryOptions.DRIVER, "mysql")
//                .option(ConnectionFactoryOptions.HOST, "localhost")
//                .option(ConnectionFactoryOptions.PORT, 3307)
//                .option(ConnectionFactoryOptions.USER, "root")
//                .option(ConnectionFactoryOptions.PASSWORD, "rootroot")
//                .option(ConnectionFactoryOptions.DATABASE, "userdb")
//                .build());
//    }
//}
