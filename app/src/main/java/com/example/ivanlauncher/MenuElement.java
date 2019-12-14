package com.example.ivanlauncher;

import java.util.ArrayList;

public class MenuElement {

    private ArrayList<MenuElement> children;
    private MenuElement parent;
    private int childOnFocus = 0;

    MenuElement(String name, MenuElement parent) {
        this.name = name;
        this.parent = parent;
    }

    MenuElement getParent() {
        return parent != null ? parent : this;
    }

    public MenuElement getChildOnFocus() {
        return children != null ? (childOnFocus >= children.size() ? children.get(children.size() - 1) : children.get(childOnFocus)) : this;
    }

    void focusOnNextChild() {
        if (children != null && childOnFocus < children.size()-1) {
            childOnFocus++;
        }
    }

    void focusPrevChild() {
        if (children != null  && childOnFocus > 0) {
            childOnFocus--;
        }

    }

    public String getName() {
        return name;
    }

    private String name;

    public void setChildren(ArrayList<MenuElement> children) {
        this.children = children;
    }

    public ArrayList<MenuElement> getChildren() {
        return children;
    }

    public String getFocusChildName(){
       return getChildOnFocus().getName();
    }

}