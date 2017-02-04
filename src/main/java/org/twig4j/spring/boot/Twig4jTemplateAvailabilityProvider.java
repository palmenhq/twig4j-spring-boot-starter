package org.twig4j.spring.boot;

import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Twig4jTemplateAvailabilityProvider implements TemplateAvailabilityProvider {
    @Override
    public boolean isTemplateAvailable(String view, Environment environment, ClassLoader classLoader, ResourceLoader resourceLoader) {
        org.slf4j.LoggerFactory.getLogger(getClass()).info("Checking if view " + view + " exists");
        try {
            // Check if Twig4jViewResolver is available, if it's not it'll throw an exception
            Class.forName("org.twig4j.spring.Twig4jViewResolver").newInstance();

            String prefix = environment.getProperty("twig4j.prefix", Twig4jProperties.DEFAULT_PREFIX);
            String suffix = environment.getProperty("twig4j.suffix", Twig4jProperties.DEFAULT_SUFFIX);

            Boolean compiledResourceExists = resourceLoader.getResource(prefix + view + suffix).exists();
            Boolean srcResourceExists;
            if (environment.getProperty("twig4j.srcResources") != null) {
                srcResourceExists = Files.exists(Paths.get(environment.getProperty("twig4j.srcResources") + prefix + view + suffix));
            } else {
                srcResourceExists = false;
            }

            org.slf4j.LoggerFactory.getLogger(getClass()).info("View " + prefix + view + suffix + (compiledResourceExists || srcResourceExists ? " exists" : " does not exist"));

            return compiledResourceExists || srcResourceExists;
        } catch (ReflectiveOperationException e) {
            org.slf4j.LoggerFactory.getLogger(getClass()).info("View " + view + " does not exist because Twig4jViewResolver cannot be found");
            return false;
        }
    }
}
