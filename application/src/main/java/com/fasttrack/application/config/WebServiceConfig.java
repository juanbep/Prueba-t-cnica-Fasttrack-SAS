package com.fasttrack.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@EnableWs
public class WebServiceConfig extends WsConfigurerAdapter {
	
	@Bean
	public XsdSchema estudianteSchema() {
		return new SimpleXsdSchema(new ClassPathResource("schemas/estudiante.xsd"));
	}

	@Bean
	public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean<>(servlet, "/ws/*");
	}
	
	@Bean(name = "estudiante")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema estudianteSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("EstudiantePort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://fasttrack.com/application/estudiantes");
		wsdl11Definition.setSchema(estudianteSchema);
		return wsdl11Definition;
	}
	
	@Bean
    public PayloadValidatingInterceptor validatingInterceptor(){
        PayloadValidatingInterceptor interceptor = new PayloadValidatingInterceptor();
        interceptor.setXsdSchema(estudianteSchema());
        interceptor.setValidateRequest(true);
        interceptor.setValidateResponse(false);
        interceptor.setAddValidationErrorDetail(true);
        interceptor.setFaultStringOrReason("Campos invalidos");
        return interceptor;
    }
	
	 @Override
	    public void addInterceptors(List<EndpointInterceptor> interceptors) {
	        interceptors.add(validatingInterceptor());
	    }
}
