package com.pavliuchenko.messageprovider.config.data;

import com.pavliuchenko.messageprovider.domain.security.Role;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.ConverterBuilder;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

import java.util.List;

@Configuration
public class CustomR2dbcConfig extends AbstractR2dbcConfiguration {

    private final ConnectionFactory connectionFactory;

    public CustomR2dbcConfig(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public ConnectionFactory connectionFactory() {
        return connectionFactory;
    }

    @Override
    protected List<Object> getCustomConverters() {
        return List.copyOf(ConverterBuilder
                .writing(Role.class, Integer.class, Role::getValue)
                .andReading(Role::findByValue)
                .getConverters());
    }
}
