package org.twig4j.spring.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.twig4j.core.Environment;
import org.twig4j.core.exception.LoaderException;
import org.twig4j.core.loader.ChainLoader;
import org.twig4j.core.loader.FilesystemLoader;
import org.twig4j.core.loader.Loader;
import org.twig4j.core.loader.ResourceLoader;
import org.twig4j.spring.Twig4jViewResolver;

import java.nio.file.Paths;
import java.util.Arrays;

@Configuration
@EnableConfigurationProperties(Twig4jProperties.class)
public class Twig4jAutoConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    private Twig4jProperties properties;

    @Bean
    public ViewResolver twigViewResolver() {
        ViewResolver resolver = new Twig4jViewResolver(properties.getPrefix(), properties.getSuffix());

        return resolver;
    }

    @Bean
    public Environment twigEnviroment() throws LoaderException {
        FilesystemLoader fsLoader = new FilesystemLoader();
        Loader resourceLoader = new ResourceLoader(this.getClass().getClassLoader());

        if (properties.getSrcResources() != null) {
            fsLoader.addPath(Paths.get(properties.getSrcResources()).toAbsolutePath().toString());
        }

        Environment environment = new Environment(new ChainLoader(Arrays.asList(fsLoader, resourceLoader)));
        environment
            .disableDebug()
            .disableStrictVariables();

        if (properties.getDebug()) {
            environment.enableDebug();
        }

        if (properties.getStrictVariables()) {
            environment.enableStrictVariables();
        }

        return environment;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        super.configureViewResolvers(registry);
        registry.viewResolver(twigViewResolver());
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    public Twig4jAutoConfiguration setProperties(Twig4jProperties properties) {
        this.properties = properties;
        return this;
    }
}
