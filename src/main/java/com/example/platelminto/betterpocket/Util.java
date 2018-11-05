package com.example.platelminto.betterpocket;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {

    public static List<Person> getPeopleList() {

        Person person1 = new Person("H", "L", "t", "s", Drawable.createFromPath("res/drawable/ic_launcher_background.xml"));
        Person person2 = new Person("HADaw", "Lawdad", "awdt", "asds", Drawable.createFromPath("res/drawable/ic_launcher_background.xml"));
        Person person3 = new Person("Hdw3", "Lrg", "tyu", "sawda", Drawable.createFromPath("res/drawable/ic_launcher_background.xml"));
        Person person4 = new Person("Hfsd", "Lfre", "tbgfb", "xss", Drawable.createFromPath("res/drawable/ic_launcher_background.xml"));
        Person person5 = new Person("Hfsd", "Lfre", "tbgfb", "xss", Drawable.createFromPath("res/drawable/ic_launcher_background.xml"));
        Person person6 = new Person("Hfsd", "Lfre", "tbgfb", "xss", Drawable.createFromPath("res/drawable/ic_launcher_background.xml"));
        Person person7 = new Person("Hfsd", "Lfre", "tbgfb", "xss", Drawable.createFromPath("res/drawable/ic_launcher_background.xml"));
        Person person8 = new Person("Hfsd", "Lfre", "tbgfb", "xss", Drawable.createFromPath("res/drawable/ic_launcher_background.xml"));
        Person person9 = new Person("Hfsd", "Lfre", "tbgfb", "xss", Drawable.createFromPath("res/drawable/ic_launcher_background.xml"));
        Person person10 = new Person("Hfsd", "Lfre", "tbgfb", "xss", Drawable.createFromPath("res/drawable/ic_launcher_background.xml"));
        Person person11 = new Person("Hfsd", "Lfre", "tbgfb", "xss", Drawable.createFromPath("res/drawable/ic_launcher_background.xml"));
        Person person12 = new Person("Hfsd", "Lfre", "tbgfb", "xss", Drawable.createFromPath("res/drawable/ic_launcher_background.xml"));
        Person person13 = new Person("Hfsd", "Lfre", "tbgfb", "xss", Drawable.createFromPath("res/drawable/ic_launcher_background.xml"));
        Person person14 = new Person("Hfsd", "Lfre", "tbgfb", "xss", Drawable.createFromPath("res/drawable/ic_launcher_background.xml"));
        Person person15 = new Person("Hfsd", "Lfre", "tbgfb", "xss", Drawable.createFromPath("res/drawable/ic_launcher_background.xml"));

        
        return new ArrayList<>(Arrays.asList(person1, person2, person3, person4, person5, person6, person7, person8, person9, person10, person11, person12, person13, person14, person15));
    }

    public static Person getRandomPerson() {

        return new Person("random", "L", "t", "s", Drawable.createFromPath("res/drawable/ic_launcher_background.xml"));
    }
}
