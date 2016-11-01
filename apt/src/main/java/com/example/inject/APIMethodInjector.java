//package com.example.inject;
//
//import org.gemini.httpengine.annotation.GET;
//import org.gemini.httpengine.annotation.Name;
//import org.gemini.httpengine.annotation.POST;
//import org.gemini.httpengine.annotation.Path;
//import org.gemini.httpengine.annotation.TaskId;
//
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.lang.model.element.ExecutableElement;
//import javax.lang.model.element.VariableElement;
//import javax.lang.model.type.DeclaredType;
//import javax.lang.model.type.TypeKind;
//import javax.lang.model.type.TypeMirror;
//
///**
// * Created by geminiwen on 15/5/21.
// */
//public class APIMethodInjector {
//
//
//    private static final String RESPONSE_LISETENER = "org.gemini.httpengine.library.OnResponseListener";
//
//    private ExecutableElement executableElement;
//    private final TypeMirror returnType;
//    private final List<? extends VariableElement> arguments;
//    private final String methodName;
//
//    private String taskId;
//    private String httpMethod;
//    private String url;
//
//    public APIMethodInjector(ExecutableElement executableElement) {
//        String methodName = executableElement.getSimpleName().toString();
//        TypeMirror returnType = executableElement.getReturnType();
//        List<? extends VariableElement> arguments = executableElement.getParameters();
//
//        this.returnType = returnType;
//        this.arguments = arguments;
//        this.methodName = methodName;
//
//        //真正Path的值
//        this.url = executableElement.getAnnotation(Path.class).value();
//        if (executableElement.getAnnotation(TaskId.class) != null) {
//            this.taskId = executableElement.getAnnotation(TaskId.class).value();
//        }
//
//        if (executableElement.getAnnotation(POST.class) != null) {
//            this.httpMethod = "POST";
//        } else if (executableElement.getAnnotation(GET.class) != null) {
//            this.httpMethod = "GET";
//        }
//
//    }
//
//
//    public String brewJava() throws Exception{
//        StringBuilder sb = new StringBuilder(" public ");
//
//        //build return type
//        TypeKind returnTypeKind = returnType.getKind();
//
//        String responseListenerName = null;
//        Map<String, String> parameterNameMap = new LinkedHashMap<>();
//
//        switch (returnTypeKind) {
//            case VOID: {
//                sb.append("void ");
//                break;
//            }
//            default: {
//                throw new Exception("other types are not supported");
//            }
//        }
//
//        sb.append(methodName + "(");
//        boolean isFirst = true;
//
//        for (VariableElement variableElement : arguments) {
//            DeclaredType type = (DeclaredType)variableElement.asType();
//            String typeName = type.asElement().toString();
//            String variableName = variableElement.getSimpleName().toString();
//
//            if (typeName.equals(RESPONSE_LISETENER)) {
//                responseListenerName = variableName;
//            } else {
//                Name nameAnnotation = variableElement.getAnnotation(Name.class);
//                String parameterName = variableName;
//                if (nameAnnotation != null) {
//                    parameterName = nameAnnotation.value();
//                }
//                parameterNameMap.put(parameterName, variableName);
//            }
//            if (!isFirst) {
//                sb.append(", ");
//            }
//
//            sb.append(typeName);
//            sb.append(" " + variableName);
//
//            if (isFirst) {
//                isFirst = false;
//            }
//        }
//
//        sb.append(") {\n");
//        sb.append(buildFunctionBody(parameterNameMap, responseListenerName));
//        sb.append("}\n");
//
//        return sb.toString();
//    }
//
//    private String buildFunctionBody(Map<String, String> parameters, String responseListenerName) {
//        StringBuilder sb = new StringBuilder();
//        for (Map.Entry<String, String> parameter: parameters.entrySet()) {
//            String name = parameter.getKey();
//
//            sb.append(" final String FIELD_" + name.toUpperCase() + " = \"");
//            sb.append(name);
//            sb.append("\";\n");
//        }
//
//        sb.append("GMHttpParameters httpParameter = new GMHttpParameters();\n");
//        for (Map.Entry<String, String> parameter: parameters.entrySet()) {
//            String parameterName = parameter.getKey();
//            String variableName = parameter.getValue();
//
//            sb.append("httpParameter.setParameter(FIELD_" + parameterName.toUpperCase() + ", ");
//            sb.append(variableName + ");\n");
//        }
//
//        sb.append("GMHttpRequest.Builder builder = new GMHttpRequest.Builder();\n");
//        sb.append("builder.setHttpParameters(httpParameter);\n");
//        if (this.taskId != null) {
//            sb.append("builder.setTaskId(\"" + this.taskId + "\");\n");
//        }
//        sb.append("builder.setUrl(\"" + this.url + "\");\n");
//
//        if (this.httpMethod != null) {
//            sb.append("builder.setMethod(\"" + this.httpMethod + "\");\n");
//        }
//
//        if (responseListenerName != null) {
//            sb.append("builder.setOnResponseListener(" + responseListenerName + ");\n");
//        }
//
//        sb.append("GMHttpService service = GMHttpService.getInstance();\n");
//        sb.append("service.executeHttpMethod(builder.build());\n");
//
//        sb.append("\n");
//        return sb.toString();
//    }
//
//
//}
