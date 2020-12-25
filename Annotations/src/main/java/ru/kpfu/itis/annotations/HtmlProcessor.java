package ru.kpfu.itis.annotations;


import com.google.auto.service.AutoService;
import ru.kpfu.itis.annotations.annotation.HtmlForm;
import ru.kpfu.itis.annotations.annotation.HtmlInput;
import ru.kpfu.itis.annotations.html.Form;
import ru.kpfu.itis.annotations.html.HtmlFormBuilder;
import ru.kpfu.itis.annotations.html.Input;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.List;
import java.util.Set;


@AutoService(Processor.class)
@SupportedAnnotationTypes(value = {"ru.kpfu.itis.annotations.annotation.HtmlForm"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class HtmlProcessor extends AbstractProcessor {

    private HtmlFormBuilder htmlFormBuilder;

    public HtmlProcessor() {
        htmlFormBuilder = new HtmlFormBuilder();
    }

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(HtmlForm.class);
        for (Element element : annotatedElements) {
            Form form = new Form(element.getAnnotation(HtmlForm.class).action(),
                    element.getAnnotation(HtmlForm.class).method(), element.getAnnotation(HtmlForm.class).enctype());
            List<? extends Element> fieldElements = element.getEnclosedElements();
            for (Element field :fieldElements) {
                if (field.getAnnotation(HtmlInput.class) != null) {
                    form.getInputs().add(new Input(field.getAnnotation(HtmlInput.class).type(), field.getAnnotation(HtmlInput.class).name(),
                            field.getAnnotation(HtmlInput.class).id(), field.getAnnotation(HtmlInput.class).placeholder()));
                }
            }
            /*String path = HtmlProcessor.class.getProtectionDomain().getCodeSource().getLocation().getPath() + element.getSimpleName() + ".html";*/
            String path = HtmlProcessor.class.getProtectionDomain().getCodeSource().getLocation().getPath().substring(1) + element.getSimpleName().toString() + ".html";
            processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "PATH: " + path);
            htmlFormBuilder.buildHtmlForm(path, form);
        }
        return true;
    }
}
