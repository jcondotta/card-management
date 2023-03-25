package com.blitzar.cards.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

@Configuration
public class MessageSourceConfiguration {

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("i18n/messages/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setDefaultLocale(Locale.UK);
        return messageSource;
    }

    @Bean("exceptionMessageSource")
    public MessageSource exceptionMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("i18n/exceptions/exceptions");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setDefaultLocale(Locale.UK);
        return messageSource;
    }
}
