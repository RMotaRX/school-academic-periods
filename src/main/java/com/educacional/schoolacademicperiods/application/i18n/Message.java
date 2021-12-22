package com.educacional.schoolacademicperiods.application.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

@Component
public class Message {
    private MessageSourceAccessor accessor;

    public Message(MessageSource messageSource) {
        this.accessor = new MessageSourceAccessor(messageSource, LocaleContextHolder.getLocale());
    }

    public String get(String code) {
        return accessor.getMessage(code, LocaleContextHolder.getLocale());
    }

    public String get(MessageProperties messageProp) {
        return accessor.getMessage(messageProp.toString(), LocaleContextHolder.getLocale());
    }

    public String get(String code, String[] args) {
        return accessor.getMessage(code, args);
    }

    public String get(MessageSourceResolvable resolvable) {
        return accessor.getMessage(resolvable, LocaleContextHolder.getLocale());
    }
}
