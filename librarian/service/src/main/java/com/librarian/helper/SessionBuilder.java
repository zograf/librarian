package com.librarian.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.objects.ArrayDataProvider;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SessionBuilder {

    Logger logger = LoggerFactory.getLogger(SessionBuilder.class);

    private KieHelper kieHelper;
    
    public SessionBuilder() {
        this.kieHelper = new KieHelper();
    }

    public void addTemplate(String path, DataProvider data) {
        InputStream template = this.getClass().getResourceAsStream(path);

        DataProvider provider = new ArrayDataProvider(new String [][]{
            new String[] {"22", "\"flag_adult\""},
            new String[] {"12", "\"flag_ya\""},
            new String[] {"2", "\"flag_juvenile\""}
        });

        DataProviderCompiler compiler = new DataProviderCompiler();
        String compiled = compiler.compile(provider, template);
        //logger.info(compiled);
        kieHelper.addContent(compiled, ResourceType.DRL);
    }

    public void addRules(String path) {
        InputStream ruleStream = this.getClass().getResourceAsStream(path);
        String text = "";

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ruleStream))) {
            text = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //logger.info(text);
        kieHelper.addContent(text, ResourceType.DRL);
    }

    public KieSession build() {
        return kieHelper.build().newKieSession();
    }
}
