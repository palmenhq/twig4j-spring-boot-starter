package org.twig4j.spring.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("twig4j")
public class Twig4jProperties {
    public final static Boolean DEFAULT_STRICT_TYPES = true;
    public final static Boolean DEFAULT_STRICT_VARIABLES = true;
    public final static Boolean DEFAULT_DEBUG = false;
    public final static String DEFAULT_PREFIX = "";
    public final static String DEFAULT_SUFFIX = ".twig";

    private Boolean strictTypes = DEFAULT_STRICT_TYPES;
    private Boolean strictVariables = DEFAULT_STRICT_VARIABLES;
    private Boolean debug = DEFAULT_DEBUG;
    private String prefix = DEFAULT_PREFIX;
    private String suffix = DEFAULT_SUFFIX;
    private String srcResources = null;

    public Boolean getStrictTypes() {
        return strictTypes;
    }

    public Twig4jProperties setStrictTypes(Boolean strictTypes) {
        this.strictTypes = strictTypes;
        return this;
    }

    public Boolean getStrictVariables() {
        return strictVariables;
    }

    public Twig4jProperties setStrictVariables(Boolean strictVariables) {
        this.strictVariables = strictVariables;
        return this;
    }

    public Boolean getDebug() {
        return debug;
    }

    public Twig4jProperties setDebug(Boolean debug) {
        this.debug = debug;
        return this;
    }

    public String getPrefix() {
        return prefix;
    }

    public Twig4jProperties setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public String getSuffix() {
        return suffix;
    }

    public Twig4jProperties setSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public String getSrcResources() {
        return srcResources;
    }

    public Twig4jProperties setSrcResources(String srcResources) {
        this.srcResources = srcResources;
        return this;
    }
}
