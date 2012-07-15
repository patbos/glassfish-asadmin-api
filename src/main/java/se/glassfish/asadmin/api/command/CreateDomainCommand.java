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

package se.glassfish.asadmin.api.command;

import se.glassfish.asadmin.api.GlassFishEnvironment;
import se.glassfish.asadmin.api.CommandException;

import java.util.Properties;

public class CreateDomainCommand extends AuthCommand<Integer> {

    private int adminport = -1;
    private int portbase = -1;
    private String profile = null;
    private String domaindir = null;
    private int instanceport = -1;
    private Properties domainProperties = null;
    private boolean savemasterpassword = false;
    private boolean savelogin = false;
    private String template;


    private int jms_port = 7878;
    private int domain_jmxPort = 8686;
    private int orb_listener_port = 3700;
    private int http_ssl_port = 8181;
    private int orb_ssl_port = 3820;
    private String name;

    //s.port=37676:domain.jmxPort=30001:orb.listener.port=30002:orb.ssl.port=30003:orb.mutualauth.port=30004:http.ssl.port=38443 \


    public CreateDomainCommand(GlassFishEnvironment environment, String name, int adminport, boolean savemasterpassword,
                               boolean savelogin) {
        super(environment);
        this.adminport = adminport;
        this.name = name;
        this.savemasterpassword = savemasterpassword;
        this.savelogin = savelogin;
    }

    public CreateDomainCommand(GlassFishEnvironment environment, String name, int portbase, boolean savemasterpassword,
                               boolean savelogin, String profile) {
        super(environment);
        this.portbase = portbase;
        this.name = name;
        this.savemasterpassword = savemasterpassword;
        this.savelogin = savelogin;
        this.profile = profile;
    }

    public CreateDomainCommand(GlassFishEnvironment environment, String name, int portbase, boolean savemasterpassword,
                               boolean savelogin, String profile, String template) {
        super(environment);
        this.portbase = portbase;
        this.name = name;
        this.savemasterpassword = savemasterpassword;
        this.savelogin = savelogin;
        this.profile = profile;
        this.template = template;
    }


    public CreateDomainCommand(GlassFishEnvironment environment, String name, int adminport, int instanceport, int jms_port, int domain_jmxPort, int orb_listener_port, int http_ssl_port, int orb_ssl_port) {
        super(environment);
        this.adminport = adminport;
        this.instanceport = instanceport;
        this.jms_port = jms_port;
        this.domain_jmxPort = domain_jmxPort;
        this.orb_listener_port = orb_listener_port;
        this.http_ssl_port = http_ssl_port;
        this.orb_ssl_port = orb_ssl_port;
        this.name = name;
    }

    public CreateDomainCommand(GlassFishEnvironment environment, String name, String profile, int adminport) {
        super(environment);
        this.name = name;
        this.profile = profile;
        this.adminport = adminport;
    }

    public String getCommandName() {
        return "create-domain";
    }

    public Properties getDomainProperties() {
        return domainProperties;
    }

    public void setDomainProperties(Properties domainProperties) {
        this.domainProperties = domainProperties;
    }

    public int getAdminport() {
        return adminport;
    }

    public void setAdminport(int adminport) {
        this.adminport = adminport;
    }

    public int getPortbase() {
        return portbase;
    }

    public void setPortbase(int portbase) {
        this.portbase = portbase;
    }

    public String getDomaindir() {
        return domaindir;
    }

    public void setDomaindir(String domaindir) {
        this.domaindir = domaindir;
    }

    @Override
    public Integer execute() throws CommandException {
        if (adminport != -1) {
            addParam("adminport", String.valueOf(adminport));
        }
        if (profile != null) {
            addParam("profile", profile);
        }
        if (domaindir != null) {
            addParam("domaindir", domaindir);
        }
        if (portbase != -1) {
            addParam("portbase", portbase);
        }
        if (instanceport != -1 && portbase == -1) {
            addParam("instanceport", instanceport);
        }
        if (template != null) {
            addParam("template", template);
        }

        addParam("savemasterpassword", savemasterpassword);
        addParam("savelogin", savelogin);


        //TODO fix domainproperties

        /*Properties properties = new Properties();
        properties.setProperty("jms.port", String.valueOf(jms_port));
        properties.setProperty("domain.jmxPort", String.valueOf(domain_jmxPort));
        properties.setProperty("orb.listener.port", String.valueOf(orb_listener_port));
        addParam("domainproperties", properties);*/

        if (profile != null) {
            addParam("profile", profile);
        }

        addArg(name);
        CommandResult result = executeCommand();
        int code = result.getReturnCode();

        if (code != 0) {
            if (result.getErrors().size() > 0) {
                throw new CommandException("Error creating domain: " + result.getErrors().get(0));
            } else {
                throw new CommandException("Error creating domain");
            }
        }
        return code;
    }
}
