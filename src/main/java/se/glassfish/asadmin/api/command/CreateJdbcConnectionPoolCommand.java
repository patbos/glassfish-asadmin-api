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
import se.glassfish.asadmin.api.Version;

import java.util.Properties;

public class CreateJdbcConnectionPoolCommand extends RemoteCommand<Integer> {
    /*
        Usage: create-jdbc-connection-pool --datasourceclassname classname [--terse=false] [--echo=false] [--interactive=true] [
    --host localhost] [--port 4848|4849] [--secure | -s] [--user admin_user] [--passwordfile file_name] [--restype res_type]
     [--steadypoolsize 8] [--maxpoolsize 32] [--maxwait 60000] [--poolresize 2] [--idletimeout 300] [--isolationlevel isolat
    ion_level] [--isisolationguaranteed] [--isconnectvalidatereq=false] [--validationmethod auto-commit] [--validationtable
    tablename] [--failconnection=false] [--allownoncomponentcallers=false] [--nontransactionalconnections=false] [--descript
    ion text] [--property (name=value)[:name=value]*] jdbc_connection_pool_id
    */
    private String datasourceclassname;
    private String name;
    private int steadypoolsize = 8;
    private int maxpoolsize = 32;
    private int maxwait = 60000;
    private int poolresize = 2;
    private int idletimeout = 300;
    private boolean isconnectvalidatereq = false;
    private boolean failconnection = false;
    private boolean allownoncomponentcallers = false;
    private boolean nontransactionalconnections = false;
    private String description = null;
    private Properties properties = null;
    private JdbcResourceType restype;
    private JdbcIsolationLevel isolationlevel = null;
    private JdbcValidationMethod validationMethod = JdbcValidationMethod.AUTO_COMMIT;
    private String validationtable = null;
    private String validationclassname = null;
    private int validateatmostonceperiod = 0;

    public CreateJdbcConnectionPoolCommand(GlassFishEnvironment environment, String name, String datasourceclassname,
                                           Properties properties) {
        super(environment);
        this.datasourceclassname = datasourceclassname;
        this.name = name;
        this.properties = properties;
    }

    public CreateJdbcConnectionPoolCommand(GlassFishEnvironment environment, String name, String datasourceclassname,
                                           int steadypoolsize, int maxpoolsize, boolean isconnectvalidatereq,
                                           boolean failconnection, Properties properties, JdbcResourceType restype) {
        super(environment);
        this.datasourceclassname = datasourceclassname;
        this.name = name;
        this.steadypoolsize = steadypoolsize;
        this.maxpoolsize = maxpoolsize;
        this.isconnectvalidatereq = isconnectvalidatereq;
        this.failconnection = failconnection;
        this.properties = properties;
        this.restype = restype;
    }

    public CreateJdbcConnectionPoolCommand(GlassFishEnvironment environment, String name, String datasourceclassname,
                                           int steadypoolsize, int maxpoolsize, boolean isconnectvalidatereq,
                                           boolean failconnection, Properties properties, JdbcResourceType restype,
                                           JdbcIsolationLevel isolationlevel) {
        super(environment);
        this.datasourceclassname = datasourceclassname;
        this.name = name;
        this.steadypoolsize = steadypoolsize;
        this.maxpoolsize = maxpoolsize;
        this.isconnectvalidatereq = isconnectvalidatereq;
        this.failconnection = failconnection;
        this.properties = properties;
        this.restype = restype;
        this.isolationlevel = isolationlevel;
    }

    public CreateJdbcConnectionPoolCommand(GlassFishEnvironment environment, String name, String datasourceclassname,
                                           int steadypoolsize, int maxpoolsize, boolean isconnectvalidatereq,
                                           JdbcValidationMethod validationMethod, String validationtable,
                                           String validationclassname,
                                           boolean failconnection, int validateatmostonceperiod, Properties properties, JdbcResourceType restype,
                                           JdbcIsolationLevel isolationlevel) {
        super(environment);
        this.datasourceclassname = datasourceclassname;
        this.name = name;
        this.steadypoolsize = steadypoolsize;
        this.maxpoolsize = maxpoolsize;
        this.isconnectvalidatereq = isconnectvalidatereq;
        this.failconnection = failconnection;
        this.properties = properties;
        this.restype = restype;
        this.isolationlevel = isolationlevel;
        this.validationMethod = validationMethod;
        this.validationtable = validationtable;
        this.validationclassname = validationclassname;
        this.validateatmostonceperiod = validateatmostonceperiod;
    }


    public CreateJdbcConnectionPoolCommand(GlassFishEnvironment environment, String name, String datasourceclassname,
                                           int steadypoolsize, int maxpoolsize, boolean isconnectvalidatereq,
                                           JdbcValidationMethod validationMethod, String validationtable,
                                           String validationclassname,
                                           boolean failconnection, int validateatmostonceperiod, Properties properties, JdbcResourceType restype,
                                           JdbcIsolationLevel isolationlevel, boolean allownoncomponentcallers) {
        super(environment);
        this.datasourceclassname = datasourceclassname;
        this.name = name;
        this.steadypoolsize = steadypoolsize;
        this.maxpoolsize = maxpoolsize;
        this.isconnectvalidatereq = isconnectvalidatereq;
        this.failconnection = failconnection;
        this.properties = properties;
        this.restype = restype;
        this.isolationlevel = isolationlevel;
        this.validationMethod = validationMethod;
        this.validationtable = validationtable;
        this.validationclassname = validationclassname;
        this.validateatmostonceperiod = validateatmostonceperiod;
        this.allownoncomponentcallers = allownoncomponentcallers;
    }

    public CreateJdbcConnectionPoolCommand(GlassFishEnvironment environment, String name, String datasourceclassname,
                                           int steadypoolsize, int maxpoolsize, boolean isconnectvalidatereq,
                                           JdbcValidationMethod validationMethod, String validationtable,
                                           String validationclassname,
                                           boolean failconnection, int validateatmostonceperiod, Properties properties, JdbcResourceType restype,
                                           JdbcIsolationLevel isolationlevel, boolean allownoncomponentcallers, boolean nontransactionalconnections) {
        super(environment);
        this.datasourceclassname = datasourceclassname;
        this.name = name;
        this.steadypoolsize = steadypoolsize;
        this.maxpoolsize = maxpoolsize;
        this.isconnectvalidatereq = isconnectvalidatereq;
        this.failconnection = failconnection;
        this.properties = properties;
        this.restype = restype;
        this.isolationlevel = isolationlevel;
        this.validationMethod = validationMethod;
        this.validationtable = validationtable;
        this.validationclassname = validationclassname;
        this.validateatmostonceperiod = validateatmostonceperiod;
        this.allownoncomponentcallers = allownoncomponentcallers;
        this.nontransactionalconnections = nontransactionalconnections;
    }



    public String getCommandName() {
        return "create-jdbc-connection-pool";
    }

    public Integer execute() throws CommandException {
        addParam("datasourceclassname", datasourceclassname);
        addParam("steadypoolsize", steadypoolsize);
        addParam("maxpoolsize", maxpoolsize);
        addParam("maxwait", maxwait);
        addParam("poolresize", poolresize);
        addParam("idletimeout", idletimeout);
        addParam("isconnectvalidatereq", isconnectvalidatereq);
        addParam("failconnection", failconnection);
        addParam("allownoncomponentcallers", allownoncomponentcallers);
        addParam("nontransactionalconnections", nontransactionalconnections);
        if (description != null) {
            addParam("description", description);
        }
        if (properties != null) {
            addParam("property", properties);
        }
        if (restype != null) {
            addParam("restype", restype.getName());
        }
        if (isolationlevel != null) {
            addParam("isolationlevel", isolationlevel.getName());
        }
        if (isconnectvalidatereq) {
            addParam("validationmethod", validationMethod.getName());
            if (validationMethod == JdbcValidationMethod.TABLE) {
                addParam("validationtable", validationtable);
            }
            if (environment.getVersion() == Version.V3 || environment.getVersion() == Version.V3_1)  {
                if (validationMethod == JdbcValidationMethod.CUSTOM_VALIDATION) {
                    addParam("validationclassname", validationclassname);
                }
            }
            if (validateatmostonceperiod != 0) {
                addParam("validateatmostonceperiod", validateatmostonceperiod);
            }
        }


        addArg(name);
        return executeCommand().getReturnCode();
    }

    public String getDatasourceclassname() {
        return datasourceclassname;
    }

    public void setDatasourceclassname(String datasourceclassname) {
        this.datasourceclassname = datasourceclassname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSteadypoolsize() {
        return steadypoolsize;
    }

    public void setSteadypoolsize(int steadypoolsize) {
        this.steadypoolsize = steadypoolsize;
    }

    public int getMaxpoolsize() {
        return maxpoolsize;
    }

    public void setMaxpoolsize(int maxpoolsize) {
        this.maxpoolsize = maxpoolsize;
    }

    public int getMaxwait() {
        return maxwait;
    }

    public void setMaxwait(int maxwait) {
        this.maxwait = maxwait;
    }

    public int getPoolresize() {
        return poolresize;
    }

    public void setPoolresize(int poolresize) {
        this.poolresize = poolresize;
    }

    public int getIdletimeout() {
        return idletimeout;
    }

    public void setIdletimeout(int idletimeout) {
        this.idletimeout = idletimeout;
    }

    public boolean isIsconnectvalidatereq() {
        return isconnectvalidatereq;
    }

    public void setIsconnectvalidatereq(boolean isconnectvalidatereq) {
        this.isconnectvalidatereq = isconnectvalidatereq;
    }

    public boolean isFailconnection() {
        return failconnection;
    }

    public void setFailconnection(boolean failconnection) {
        this.failconnection = failconnection;
    }

    public boolean isAllownoncomponentcallers() {
        return allownoncomponentcallers;
    }

    public void setAllownoncomponentcallers(boolean allownoncomponentcallers) {
        this.allownoncomponentcallers = allownoncomponentcallers;
    }

    public boolean isNontransactionalconnections() {
        return nontransactionalconnections;
    }

    public void setNontransactionalconnections(boolean nontransactionalconnections) {
        this.nontransactionalconnections = nontransactionalconnections;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public JdbcResourceType getRestype() {
        return restype;
    }

    public void setRestype(JdbcResourceType restype) {
        this.restype = restype;
    }
}
