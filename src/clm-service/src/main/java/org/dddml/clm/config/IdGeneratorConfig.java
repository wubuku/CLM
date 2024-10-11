package org.dddml.clm.config;

import org.dddml.clm.domain.tag.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.dddml.clm.specialization.IdGenerator;
import org.dddml.clm.tag.TagIdGenerator;

@Configuration
public class IdGeneratorConfig {

    @Bean
    public IdGenerator<String, TagCommand.CreateTag, TagState> tagIdGenerator() {
        return new TagIdGenerator();
    }
}
