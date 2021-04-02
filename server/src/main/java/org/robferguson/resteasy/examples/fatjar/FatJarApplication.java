package org.robferguson.resteasy.examples.fatjar;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.robferguson.resteasy.examples.fatjar.resource.Employee;
import org.robferguson.resteasy.examples.fatjar.resource.MessageResource;
import org.robferguson.resteasy.examples.fatjar.resource.QueueExample;
import org.robferguson.resteasy.examples.fatjar.resource.StackExample;
import org.robferguson.resteasy.examples.fatjar.resource.StudentMap;
import org.robferguson.resteasy.examples.fatjar.resource.Students;

public class FatJarApplication extends Application {

    public FatJarApplication() {
    }

    /*
     * 
     * public Set<Class<?>> getClasses() { HashSet<Class<?>> set = new
     * HashSet<Class<?>>(); set.add(MessageResource.class); return set; }
     * 
     */

    @Override
    public Set<Object> getSingletons() {
        HashSet<Object> set = new HashSet<Object>();
        set.add(new MessageResource());
        set.add(new Employee());
        set.add(new StudentMap());
        set.add(new Students());
        set.add(new StackExample());
        set.add(new QueueExample());
        return set;
    }
}
