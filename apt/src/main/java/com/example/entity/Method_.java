package com.example.entity;
import java.util.List;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
/**
 * Created by fuzhipeng on 2016/11/1.
 */

public class Method_ {

    private ExecutableElement executableElement;
    private  TypeMirror returnType;
    private  List<? extends VariableElement> arguments;
    private  String methodName;

    public Method_(ExecutableElement executableElement) {
        this.executableElement = executableElement;
    }

}
