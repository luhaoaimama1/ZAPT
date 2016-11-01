package com.example;

import com.example.utils.LogUtils;
import com.google.auto.service.AutoService;
import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class ViewInjectProcessor extends AbstractProcessor {
    public boolean isFirst = true;
    public int count = 1;

    private static final String SUFFIX = "$$APIINJECTOR";

    private Filer filer;
    private Elements elementUtils;
    private Types typeUtils;
    private Messager messager;
    private Class<? extends Annotation>[] supports = new Class[]{
            ZField.class,
            ZMethod.class,
            ZClass.class
    };

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        filer = env.getFiler();
        elementUtils = env.getElementUtils();
        typeUtils = env.getTypeUtils();
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment env) {
        StringBuilder sb = new StringBuilder();
        for (Class<? extends Annotation> support : supports) {
            //一个类 会声明多次
            for (Element element : env.getElementsAnnotatedWith(support)) {

                sb.append("------------ start:"+support.getSimpleName()+"------------------------\n");
                sb.append("packageName:" + LogUtils.getPackageName(elementUtils,  element) + "\n");

                try {
                    sb.append("kind类型:" + element.getKind() + "\n");
                } catch (Exception e) {
                    messager.printMessage(Diagnostic.Kind.ERROR, "kind类型:" + e.getMessage() + "\n");
                }

                try {
                    sb.append("element.asType():" + element.asType().toString() + "\n");
                } catch (Exception e) {
                    messager.printMessage(Diagnostic.Kind.ERROR, "element.asType():" + e.getMessage() + "\n");
                }

                try {
                    sb.append("element:" + element.toString() + "\n");
                    sb.append("element_getModifiers:" + element.getModifiers().toString() + "\n");
                    if (element.getKind() == ElementKind.FIELD) {
                        sb.append("VariableElement---getEnclosingElement:" +
                                ((VariableElement) element).getEnclosingElement().toString() + "\n");

                        if (VariableElement.class.isAssignableFrom(element.getClass()))
                            sb.append("类型是:VariableElement"+ "\n");
                        else
                            sb.append("类型【不】是:VariableElement"+ "\n");
                        Object fieldValue = ((VariableElement) element).getConstantValue();
                        if(fieldValue!=null)
                            sb.append("fieldValue:"+fieldValue+"\n");
//                        messager.printMessage(Diagnostic.Kind.ERROR, element.toString()+":"+fieldValue+"\n");
                    }

                    if (element.getKind() == ElementKind.CLASS) {
                        TypeMirror superClass = ((TypeElement) element).getSuperclass();
                        sb.append("superClass--->"+superClass+"\n");
                        List<? extends TypeMirror> interfaces = ((TypeElement) element).getInterfaces();
                        for (TypeMirror anInterface : interfaces) {
                            Element inter = typeUtils.asElement(anInterface);
                            sb.append("inter 接口--->"+inter.toString()+"\n");
                        }
                        sb.append("superClass--->"+superClass+"\n");
                        TypeElement a = (TypeElement) typeUtils.asElement(element.asType());
                        sb.append("a--->"+a+"\n");
                    }

                    if (element.getKind() == ElementKind.METHOD) {
                        ExecutableElement method = (ExecutableElement) element;
                        sb.append("methodName:--->"+method.getSimpleName()+"\n");
                        sb.append("method return type:--->"+method.getReturnType()+"\n");
                        List<? extends VariableElement> pars = method.getParameters();
                        for (VariableElement par : pars) {
                            sb.append("parName:"+par.toString()+"\t parType:"+par.asType()+"\n");
                        }
                    }
                } catch (Exception e) {
                    messager.printMessage(Diagnostic.Kind.ERROR, "element:" + e.getMessage() + "\n");
                }

                try {
                    sb.append("EnclosedElements:" + element.getEnclosedElements().toString() + "\n");
                    for (Element element1 : element.getEnclosedElements())
                        sb.append("child:" + element1.toString() + "\t kind->" + element1.getKind() + "\n");
                } catch (Exception e) {
                    messager.printMessage(Diagnostic.Kind.ERROR, "EnclosedElements:" + e.getMessage() + "\n");
                }

                sb.append("-------------end-----------------------\n");
                sb.append("\n");
                sb.append("\n");
            }
        }
        LogUtils.writeLog2(sb.toString(), count);
        count++;
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class support : supports)
            types.add(support.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


}