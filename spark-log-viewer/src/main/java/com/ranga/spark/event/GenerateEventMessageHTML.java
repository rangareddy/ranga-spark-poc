package com.ranga.spark.event;/* rangareddy.avula created on 11/05/20 */

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GenerateEventMessageHTML {

    static Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);

    static {
        // Where do we load the templates from:
        cfg.setClassForTemplateLoading(GenerateEventMessageHTML.class, "/");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    public static void generateHTML(String outputFile, String applicationName, List<Map<String, String>> eventMessages) {

        Map<String, Object> input = new HashMap<>();
        input.put("title", ""+applicationName);
        input.put("eventMessages", eventMessages);

        try {

          /*
            Writer consoleWriter = new OutputStreamWriter(System.out);
            template.process(input, consoleWriter);
            */

            // For the sake of example, also write output into a file:
            Template template = cfg.getTemplate("event_log_exceptions.ftl");
            String htmlFileName = outputFile+".html";
            System.out.println(htmlFileName);

            Writer fileWriter = new FileWriter(new File(htmlFileName));
            try {
                template.process(input, fileWriter);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            finally {
                fileWriter.close();
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}