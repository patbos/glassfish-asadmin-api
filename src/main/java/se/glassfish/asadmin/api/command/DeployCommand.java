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

import java.io.IOException;
import java.io.File;
import java.util.Properties;

public class DeployCommand extends RemoteCommand<Integer> {

    /*
    Usage: deploy [--terse=false] [--echo=false] [--interactive=true] [--host localhost] [--port 4848|4849]
    [--secure | -s] [--user admin_user] [--passwordfile file_name] [--virtualservers virtual_servers]
    [--contextroot context_root] [--force=true] [--precompilejsp=false] [--verify=false] [--name component_name]
    [--upload=true] [--retrieve local_dirpath] [--dbvendorname dbvendorname] [--createtables=true|false |
    --dropandcreatetables=true|false] [--uniquetablenames=true|false] [--deploymentplan deployment_plan]
    [--enabled=true] [--generatermistubs=false] [--availabilityenabled=false] [--libraries
    jar_file[(pathseparator)jar_file]*] [--target target(Default server)] [--property
    (name=value)[:name=value]*] filepath
    CLI020 Operand is required.
     */

    private File file;
    private String name = null;

    private String[] virtualservers = null;
    private String contextroot = null;
    private boolean force = false;
    private boolean precompilejsp = false;
    private boolean verify = false;
    private boolean enabled = true;
    private String target;
    private Properties properties;
    private boolean upload = true;

   


    public DeployCommand(GlassFishEnvironment environment, String target, File file) {
        super(environment);
        this.file = file;
        this.target = target;
    }


    public DeployCommand(GlassFishEnvironment environment, String target, File file, String name) {
        super(environment);
        this.file = file;
        this.name = name;
        this.target = target;
    }

    public DeployCommand(GlassFishEnvironment environment, String target, File file, String name, boolean precompilejsp, Properties properties) {
        super(environment);
        this.file = file;
        this.name = name;
        this.precompilejsp = precompilejsp;
        this.properties = properties;
        this.target = target;
    }


    public DeployCommand(GlassFishEnvironment environment, String target, File file, String name, String contextroot, String[] virtualservers, boolean precompilejsp, boolean verify) {
        super(environment);
        this.precompilejsp = precompilejsp;
        this.file = file;
        this.name = name;
        this.virtualservers = virtualservers;
        this.verify = verify;
        this.contextroot = contextroot;
        this.target = target;
    }

    public DeployCommand(GlassFishEnvironment environment, String target, File file, String name, String[] virtualservers, String contextroot, boolean force, boolean precompilejsp, boolean verify, boolean enabled, boolean upload, Properties properties) {
        super(environment);
        this.file = file;
        this.name = name;
        this.virtualservers = virtualservers;
        this.contextroot = contextroot;
        this.force = force;
        this.precompilejsp = precompilejsp;
        this.verify = verify;
        this.enabled = enabled;
        this.properties = properties;
        this.target = target;
        this.upload = upload;
    }

    public String getCommandName() {
        return "deploy";
    }

    public String[] getVirtualservers() {
        return virtualservers;
    }

    public void setVirtualservers(String[] virtualservers) {
        this.virtualservers = virtualservers;
    }

    public Integer execute() throws CommandException {

        if (virtualservers != null) {
            addParam("virtualservers", virtualservers);
        }

        if (force) {
            addParam("force", force);
        }

        if (verify) {
            addParam("verify", verify);
        }

        if (enabled) {
            addParam("enabled", enabled);
        }

        if (!upload) {
            addParam("upload", upload);
        }


        if (properties != null) {
            addParam("property", properties);
        }

        if (contextroot != null) {
            addParam("contextroot", contextroot);
        }

        if (precompilejsp) {
            addParam("precompilejsp", precompilejsp);
        }

        if (name != null) {
            addParam("name", name);
        }

        if (target != null) {
            addParam("target", target);
        }


        try {
            addArg(file.getCanonicalPath());

            int code = executeCommand().getReturnCode();
            if (code != 0) {
                if (getErrors().size() > 0) {
                    throw new CommandException("Error deploying domain: " + getErrors().get(0));
                } else {
                    throw new CommandException("Error deploying domain");
                }
            }
            return code;
        } catch (IOException e) {
            throw new CommandException(e);
        }
    }

}
