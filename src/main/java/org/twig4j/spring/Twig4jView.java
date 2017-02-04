package org.twig4j.spring;

import org.springframework.web.servlet.view.AbstractTemplateView;
import org.twig4j.core.Environment;
import org.twig4j.core.template.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class Twig4jView extends AbstractTemplateView {
    protected Environment environment;


    @Override
    protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Context context = new Context();
        context.put("request", request);
        context.put("app", getApplicationContext());

        context.putAll(model);

        response.getWriter().write(environment.render(getUrl(), context));
    }

    public Twig4jView setEnvironment(Environment environment) {
        this.environment = environment;
        return this;
    }
}
