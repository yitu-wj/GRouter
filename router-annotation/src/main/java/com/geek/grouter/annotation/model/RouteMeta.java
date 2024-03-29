package com.geek.grouter.annotation.model;


import com.geek.grouter.annotation.Route;

import javax.lang.model.element.Element;

public class RouteMeta {
    public enum Type{
        ACTIVITY,
        ISERVICE
    }
    private Type type;
    private Element element;
    private Class<?> destination;
    private String path;
    private String group;
    public static RouteMeta build(Type type,Class<?> destination,String path,String group){
        return new RouteMeta(type,null,destination,path,group);
    }

    public RouteMeta(){

    }
    public RouteMeta(Type type, Route route, Element element) {
        this(type,element,null,route.path(),route.group());
    }
    public RouteMeta(Type type,Element element,Class<?> destination,String path,String group){
        this.type=type;
        this.destination=destination;
        this.element=element;
        this.path=path;
        this.group=group;
    }

    public Type getType() {
        return type;
    }

    public RouteMeta setType(Type type) {
        this.type = type;
        return this;
    }

    public Element getElement() {
        return element;
    }

    public RouteMeta setElement(Element element) {
        this.element = element;
        return this;
    }

    public Class<?> getDestination() {
        return destination;
    }

    public RouteMeta setDestination(Class<?> destination) {
        this.destination = destination;
        return this;
    }

    public String getPath() {
        return path;
    }

    public RouteMeta setPath(String path) {
        this.path = path;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public RouteMeta setGroup(String group) {
        this.group = group;
        return this;
    }
}
