package org.twig4j.spring;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.twig4j.core.Environment;
import org.twig4j.core.exception.LoaderException;

import java.util.Locale;

public class Twig4jViewResolver extends AbstractTemplateViewResolver {
    @Autowired
    protected Environment environment;

    public Twig4jViewResolver() {
        super();
        setViewClass(Twig4jView.class);
    }

    public Twig4jViewResolver(String prefix, String suffix) {
        this();
        setPrefix(prefix);
        setSuffix(suffix);
    }

    @Override
    protected Class<?> requiredViewClass() {
        return Twig4jView.class;
    }

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        try {
            environment.getLoader().getSource(getPrefix() + viewName + getSuffix());

            // If no exception was thrown the view was found
            return super.resolveViewName(viewName, locale);
        } catch (LoaderException e) {
            LoggerFactory.getLogger(getClass()).info(e.getMessage());
            return null;
        }
    }

    @Override
    protected View loadView(String viewName, Locale locale) throws Exception {
        View view = super.loadView(viewName, locale);
        LoggerFactory.getLogger(getClass()).info("Loading view \"" + ((AbstractUrlBasedView)view).getUrl() + "\"");
        return view;
    }

    @Override
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        AbstractUrlBasedView view = super.buildView(viewName);

        ((Twig4jView)view)
            .setEnvironment(environment);

        return view;
    }

    public Twig4jViewResolver setEnvironment(Environment environment) {
        this.environment = environment;
        return this;
    }
}