package com.fasttrack.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
public class EstudianteMateriaWsdlConfig extends WsdlConfig {
	@Bean
    public XsdSchema estudianteMateriaSchema() {
        return new SimpleXsdSchema(new ClassPathResource("schemas/estudiante_materia.xsd"));
    }

    @Bean(name = "estudiante_materia")
    public DefaultWsdl11Definition estudianteMateriaWsdl(XsdSchema estudianteMateriaSchema) {
        return createWsdlDefinition(
            "EstudianteMateriaPort",
            "/ws",
            "http://fasttrack.com/application/estudiante_materia",
            estudianteMateriaSchema
        );
    }
}
