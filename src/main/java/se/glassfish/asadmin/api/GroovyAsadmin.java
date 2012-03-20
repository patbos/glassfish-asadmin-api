/**
 * Java API for management of GlassFish servers.
 * Copyright (C) 2010 Patrik Boström
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package se.glassfish.asadmin.api;

import groovy.lang.GroovyObject;
import groovy.lang.GroovyObjectSupport;
import groovy.lang.MetaClass;


public class GroovyAsadmin extends Asadmin implements GroovyObject {

    private MetaClass metaClass;

    public GroovyAsadmin(String glassFishHome) {
        super(glassFishHome);
    }

    public GroovyAsadmin(String glassFishHome, String adminUsername, String adminPassword, String masterAdminPassword) {
        super(glassFishHome, adminUsername, adminPassword, masterAdminPassword);
    }

    public GroovyAsadmin(String adminUsername, String adminPassword, String masterAdminPassword, String glassFishHome, String host, int port) {
        super(adminUsername, adminPassword, masterAdminPassword, glassFishHome, host, port);
    }

    public GroovyAsadmin(String glassFishHome, int port, boolean useLocalAuth, boolean verbose) {
        super(glassFishHome, port, useLocalAuth, verbose);
    }

    public GroovyAsadmin(String glassFishHome, int port, boolean useLocalAuth, boolean verbose, Version version) {
        super(glassFishHome, port, useLocalAuth, verbose, version);
    }

    public GroovyAsadmin(String glassFishHome, String host, int port, boolean useLocalAuth, boolean verbose, Version version) {
        super(glassFishHome, host, port, useLocalAuth, verbose, version);
    }

    public void setProperty(String property, Object newValue) {
        System.out.println("Setting: " + property);
    }

    public Object getProperty(String property) {
        return new Property(this, property);
    }

    public Object invokeMethod(String s, Object o) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public MetaClass getMetaClass() {
        return metaClass;
    }

    public void setMetaClass(MetaClass metaClass) {
        this.metaClass = metaClass;
    }

    class Property extends GroovyObjectSupport {

        String name;
        Property parent;
        GroovyAsadmin asadmin;

        Property(GroovyAsadmin asadmin, String name) {
            this.name = name;
            this.asadmin = asadmin;
        }

        Property(GroovyAsadmin asadmin, Property property, String name) {
            this.name = name;
            this.parent = property;
            this.asadmin = asadmin;
        }

        @Override
        public Object getProperty(String property) {
            return new Property(asadmin, this, property);
        }

        @Override
        public void setProperty(String property, Object newValue) {
            if (parent != null) {
                String s = parent.resolveProperty();
                System.out.println("Setting: " + s + "." + name + "." + property);
                try {
                    asadmin.set(s + "." + name + "." + property, newValue.toString());
                } catch (CommandException e) {
                    throw new IllegalArgumentException(e.getMessage());
                }
            } else {
                System.out.println("Setting: " + name + "." + property);
                try {
                    asadmin.set(name + "." + property, newValue.toString());
                } catch (CommandException e) {
                    throw new IllegalArgumentException(e.getMessage());
                }
            }
        }

        public String resolveProperty() {
            if (parent != null) {
                return parent.resolveProperty() + "." + name;
            } else {
                return name;
            }
        }

        public Object getValue() throws CommandException {
            String name = toString();
            return asadmin.get(name);
        }
        
        @Override
        public String toString() {
            if (parent != null) {
                return parent.resolveProperty() + "." + name;
            } else {
                return name;
            }
        }
    }

}
