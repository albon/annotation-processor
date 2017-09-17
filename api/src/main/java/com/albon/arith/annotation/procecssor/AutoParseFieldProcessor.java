package com.albon.arith.annotation.procecssor;

import com.google.common.collect.Maps;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.*;
import java.util.Map;
import java.util.Set;


/**
 * @author albon
 *         Date : 17-1-23
 *         Time: 下午3:45
 */
@SupportedAnnotationTypes({ "com.albon.arith.annotation.procecssor.AutoParse" })
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AutoParseFieldProcessor extends AbstractProcessor {

    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Map<String, String> fieldTypeMap = Maps.newHashMap();

        for (Element elem : roundEnv.getElementsAnnotatedWith(AutoParse.class)) {
            AutoParse annotation = elem.getAnnotation(AutoParse.class);
            String message = System.currentTimeMillis() + " - annotation found in " + elem.getSimpleName()
                    + " with key " + annotation.key() + " kind " + elem.getKind() + " class " + elem.getClass()
                    + " asType " + elem.asType() + "\n\tgetEnclosingElement().getSimpleName() "
                    + elem.getEnclosingElement().getSimpleName() + "\n\tgetEnclosingElement().asType() "
                    + elem.getEnclosingElement().asType();
            fieldTypeMap.put(elem.getEnclosingElement().asType().toString() + "#" + elem.getSimpleName(),
                    elem.asType().toString());

            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);
        }

        if (fieldTypeMap.isEmpty()) {
            return true;
        }

        Writer writer = null;
        try {
            JavaFileObject jfo = filer
                    .createSourceFile("com.albon.arith.annotation.service.AutoParseFieldInfo");
            writer = jfo.openWriter();
            writer.write("package com.albon.arith.annotation.service;\n" +
                    "\n" +
                    "import com.google.common.collect.Maps;\n" +
                    "\n" +
                    "import java.util.Map;\n" +
                    "\n" +
                    "/**\n" +
                    " * @author albon\n" +
                    " *         Date : 17-1-24\n" +
                    " *         Time: 下午4:40\n" +
                    " */\n" +
                    "public class AutoParseFieldInfo {\n" +
                    "\n" +
                    "    // key: classpath#fieldName, value: fieldType\n" +
                    "    public static final Map<String, String> FIELD_TYPE_MAP = Maps.newHashMap();\n" +
                    "\n" +
                    "    static {\n");

            for (Map.Entry<String, String> entry : fieldTypeMap.entrySet()) {
                writer.write("        FIELD_TYPE_MAP.put(\"" +
                        entry.getKey() +
                        "\", \"" +
                        entry.getValue() +
                        "\");\n");
            }

            writer.write("    }\n" +
                    "\n" +
                    "    public static void main(String[] args) {\n" +
                    "        System.out.println(FIELD_TYPE_MAP);\n" +
                    "    }\n" +
                    "}\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true; // no further processing of this annotation type
    }
}
