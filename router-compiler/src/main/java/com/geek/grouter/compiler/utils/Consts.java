package com.geek.grouter.compiler.utils;


import com.squareup.javapoet.ClassName;



public class Consts {


    public static final ClassName ROUTER = ClassName.get("com.geek.grouter.core", "GRouter");

    public static final String ARGUMENTS_NAME = "moduleName";
    public static final String ANN_TYPE_ROUTE = "com.geek.grouter.annotation.Route";
    public static final String ANN_TYPE_Extra = "com.geek.grouter.annotation.Extra";


    public static final String IROUTE_GROUP = "com.geek.grouter.core.template.IRouteGroup";
    public static final String IROUTE_ROOT = "com.geek.grouter.core.template.IRouteRoot";
    public static final String IEXTRA = "com.geek.grouter.core.template.IExtra";

    public static final String METHOD_LOAD_INTO = "loadInto";
    public static final String METHOD_LOAD_EXTRA = "loadExtra";

    public static final String ACTIVITY = "android.app.Activity";
    public static final String ISERVICE = "com.geek.grouter.core.template.IService";


    private static final String LANG = "java.lang";
    public static final String BYTE = LANG + ".Byte";
    public static final String SHORT = LANG + ".Short";
    public static final String INTEGER = LANG + ".Integer";
    public static final String LONG = LANG + ".Long";
    public static final String FLOAT = LANG + ".Float";
    public static final String DOUBEL = LANG + ".Double";
    public static final String BOOLEAN = LANG + ".Boolean";
    public static final String STRING = LANG + ".String";
    public static final String ARRAY = "ARRAY";

    public static final String BYTEARRAY = "byte[]";
    public static final String SHORTARRAY = "short[]";
    public static final String BOOLEANARRAY = "boolean[]";
    public static final String CHARARRAY = "char[]";
    public static final String DOUBLEARRAY = "double[]";
    public static final String FLOATARRAY = "float[]";
    public static final String INTARRAY = "int[]";
    public static final String LONGARRAY = "long[]";
    public static final String STRINGARRAY = "java.lang.String[]";

    public static final String ARRAYLIST = "java.util.ArrayList";
    public static final String LIST = "java.util.List";


    public static final String PARCELABLE = "android.os.Parcelable";


    public static final String SEPARATOR = "$$";
    public static final String PROJECT = "GRouter";
    public static final String NAME_OF_ROOT = PROJECT + SEPARATOR + "Root" + SEPARATOR;
    public static final String NAME_OF_GROUP = PROJECT + SEPARATOR + "Group" + SEPARATOR;
    public static final String NAME_OF_EXTRA = SEPARATOR + "Extra";

    public static final String PACKAGE_OF_GENERATE_FILE = "com.geek.grouter.routes";

}