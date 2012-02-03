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

import se.glassfish.asadmin.api.command.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

public class Asadmin {

    private static final String DEFAULT_ADMIN_USERNAME = "admin";
    private static final String DEFAULT_ADMIN_PASSWORD = "adminadmin";
    private static final String DEFAULT_MASTER_PASSWORD = "changeit";
    private static final String DEFAULT_HOSTNAME = "localhost";
    private static final String DEFAULT_TARGET = "server";
    private static final int DEFAULT_PORT = 4848;

    protected GlassFishEnvironment environment;

    public Asadmin(String glassFishHome) {
        environment = new GlassFishEnvironment(DEFAULT_ADMIN_USERNAME, DEFAULT_ADMIN_PASSWORD, DEFAULT_MASTER_PASSWORD,
                glassFishHome, true, DEFAULT_HOSTNAME, DEFAULT_PORT);
    }

    public Asadmin(String glassFishHome, String adminUsername, String adminPassword, String masterAdminPassword) {
        environment = new GlassFishEnvironment(adminUsername, adminPassword, masterAdminPassword, glassFishHome);
    }

    public Asadmin(String adminUsername, String adminPassword, String masterAdminPassword, String glassFishHome,
                   String host, int port) {
        environment = new GlassFishEnvironment(adminUsername, adminPassword, masterAdminPassword, glassFishHome, true,
                host, port);
    }

    public Asadmin(String glassFishHome, int port, boolean useLocalAuth, boolean verbose) {
        environment = new GlassFishEnvironment(glassFishHome, useLocalAuth, port, verbose);
    }

    public Asadmin(String glassFishHome, int port, boolean useLocalAuth, boolean verbose, Version version) {
        environment = new GlassFishEnvironment(glassFishHome, useLocalAuth, port, verbose);
        environment.setVersion(version);
    }

    public Asadmin(String glassFishHome, String host, int port, boolean useLocalAuth, boolean verbose, Version version) {
        environment = new GlassFishEnvironment(glassFishHome, useLocalAuth, port, verbose);
        environment.setVersion(version);
        environment.setHost(host);
    }


    public void createDomain(String domainName) throws CommandException {
        new CreateDomainCommand(environment, domainName, 4848, false, false).execute();
    }

    public void createDomain(String profile, String domainName) throws CommandException {
        new CreateDomainCommand(environment, domainName, profile, 4848).execute();
    }


    public void createDomain(String domainName, int adminPort) throws CommandException {
        new CreateDomainCommand(environment, domainName, adminPort, false, false).execute();
    }

    public void createDomain(String domainName, int adminPort, boolean savemasterpassword, boolean savelogin)
            throws CommandException {
        new CreateDomainCommand(environment, domainName, adminPort, savemasterpassword, savelogin).execute();
    }


    public void createDomain(String name, int adminport, int instanceport, int jms_port, int domain_jmxPort,
                             int orb_listener_port, int http_ssl_port, int orb_ssl_port) throws CommandException {
        new CreateDomainCommand(environment, name, adminport, instanceport, jms_port, domain_jmxPort, orb_listener_port,
                http_ssl_port, orb_ssl_port).execute();
    }

    public void createDomain(String name, int portbase, boolean savemasterpassword,
                             boolean savelogin, String profile) throws CommandException {
        new CreateDomainCommand(environment, name, portbase, savemasterpassword, savelogin, profile).execute();
    }

    public void createDomain(String name, int portbase, boolean savemasterpassword,
                             boolean savelogin, String profile, String template) throws CommandException {
        new CreateDomainCommand(environment, name, portbase, savemasterpassword, savelogin, profile, template).execute();
    }

    public void deleteDomain(String domainName) throws CommandException {
        new DeleteDomainCommand(environment, domainName).execute();
    }

    public void createAuthRealm(String realmname, String classname, Properties properties) throws CommandException {
        new CreateAuthRealmCommand(environment, realmname, classname, properties).execute();
    }

    public void createPasswordAlias(String alias, String password) throws CommandException {
        new CreatePasswordAlias(environment, alias, password).execute();
    }

    public void updatePasswordAlias(String alias, String password) throws CommandException {
        new UpdatePasswordAlias(environment, alias, password).execute();
    }

    public void createFileUser(String username, String password, String[] groups) throws CommandException {
        new CreateFileUserCommand(environment, username, password, groups, "admin-realm").execute();
    }

    public void startDomain(String domainName) throws CommandException {
        int code = new StartDomainCommand(environment, domainName).execute();
        if (code != 0) {
            throw new CommandException("Could not start domain");
        }
    }

    public void stopDomain(String domainName) throws CommandException {
        new StopDomainCommand(environment, domainName).execute();
    }

    public void restartDomain(String domainName) throws IOException, InterruptedException, CommandException {
        new StopDomainCommand(environment, domainName).execute();
        new StartDomainCommand(environment, domainName).execute();
    }

    public void upgradeDomain(String domainName) throws CommandException {
        int code = new UpgradeDomainCommand(environment, domainName).execute();
        if (code != 0) {
            throw new CommandException("Could not upgrade domain");
        }
    }


    public int createProfilerJvmOptions(String... options) throws IOException, InterruptedException, CommandException {
        return new CreateJvmOptionsCommand(environment, DEFAULT_TARGET, true, options).execute();
    }

    public int createProfiler(String classpath,
                                 String nativelibpath, boolean enabled, String name) throws CommandException {
        return new CreateProfilerCommand(environment, DEFAULT_TARGET, classpath, nativelibpath, enabled, name).execute();
    }

    public int deleteProfiler() throws CommandException {
        return new DeleteProfilerCommand(environment, DEFAULT_TARGET).execute();
    }


    public int createProfiler(String classpath,
                                 String nativelibpath, boolean enabled, String name, String... options) throws
            CommandException, IOException, InterruptedException {
        new CreateProfilerCommand(environment, DEFAULT_TARGET, classpath, nativelibpath, enabled, name).execute();
        /*if (environment.getVersion() == Version.V3) {
            StringBuilder optionsString = new StringBuilder("");
            for (int i = 0; i < options.length; i++) {
                String option = options[i];
                optionsString.append(StringUtil.escape(option, ":"));
                optionsString.append(",");

            }
            set("configs.config.server-config.java-config."+ name + ".jvm-options", optionsString.toString());
            return 0;
        }*/
        return new CreateJvmOptionsCommand(environment, DEFAULT_TARGET, true, options).execute();
    }

    public int createJvmOptions(boolean profiler, String... options) throws IOException, InterruptedException,
            CommandException {
        return new CreateJvmOptionsCommand(environment, DEFAULT_TARGET, profiler, options).execute();
    }


    public int createJvmOptions(String... options) throws IOException, InterruptedException, CommandException {
        return new CreateJvmOptionsCommand(environment, DEFAULT_TARGET, false, options).execute();
    }

    public int createJvmOptions(List<String> options) throws IOException, InterruptedException, CommandException {
        return new CreateJvmOptionsCommand(environment, DEFAULT_TARGET, false, options.toArray(new String[]{})).execute();
    }

    public int createJvmOptions(List<String> options, boolean profiler) throws IOException, InterruptedException, CommandException {
        return new CreateJvmOptionsCommand(environment, DEFAULT_TARGET, profiler, options.toArray(new String[]{})).execute();
    }



    public int deleteJvmOptions(String... options) throws IOException, InterruptedException, CommandException {
        return new DeleteJvmOptionsCommand(environment, DEFAULT_TARGET, options).execute();
    }

    public int createJdbcConnectionPool(String name, String datasourceclassname, Properties properties) throws
            CommandException {
        CreateJdbcConnectionPoolCommand command = new CreateJdbcConnectionPoolCommand(environment, name,
                datasourceclassname, properties);
        return command.execute();
    }

    public int createJdbcConnectionPool(String name, String datasourceclassname, JdbcResourceType type,
                                        boolean validateConnections, Properties properties) throws CommandException {
        CreateJdbcConnectionPoolCommand command = new CreateJdbcConnectionPoolCommand(environment, name,
                datasourceclassname, properties);
        command.setIsconnectvalidatereq(validateConnections);
        command.setRestype(type);
        return command.execute();
    }

    public int createJdbcConnectionPool(String name, String datasourceclassname, int steadypoolsize, int maxpoolsize,
                                        boolean isconnectvalidatereq, boolean failconnection, Properties properties,
                                        JdbcResourceType restype) throws CommandException {
        CreateJdbcConnectionPoolCommand command = new CreateJdbcConnectionPoolCommand(environment, name,
                datasourceclassname, steadypoolsize, maxpoolsize, isconnectvalidatereq, failconnection, properties,
                restype);
        return command.execute();
    }

    public int createJdbcConnectionPool(String name, String datasourceclassname, int steadypoolsize, int maxpoolsize,
                                        boolean isconnectvalidatereq, boolean failconnection, Properties properties,
                                        JdbcResourceType restype, JdbcIsolationLevel isolationLevel)
            throws CommandException {
        CreateJdbcConnectionPoolCommand command = new CreateJdbcConnectionPoolCommand(environment, name,
                datasourceclassname, steadypoolsize, maxpoolsize, isconnectvalidatereq, failconnection, properties,
                restype, isolationLevel);
        return command.execute();
    }

    public int createJdbcConnectionPool(String name, String datasourceclassname,
                                           int steadypoolsize, int maxpoolsize, boolean isconnectvalidatereq,
                                           JdbcValidationMethod validationMethod, String validationtable,
                                           String validationclassname,
                                           boolean failconnection, int validateatmostonceperiod, Properties properties,
                                           JdbcResourceType restype, JdbcIsolationLevel isolationlevel)
            throws CommandException {
        CreateJdbcConnectionPoolCommand command = new CreateJdbcConnectionPoolCommand(environment, name,
                datasourceclassname, steadypoolsize, maxpoolsize, isconnectvalidatereq, validationMethod,
                validationtable, validationclassname, failconnection, validateatmostonceperiod, properties, restype,
                isolationlevel);
        return command.execute();
    }

    public int createJdbcConnectionPool(String name, String datasourceclassname,
                                        int steadypoolsize, int maxpoolsize, boolean isconnectvalidatereq,
                                        JdbcValidationMethod validationMethod, String validationtable,
                                        String validationclassname,
                                        boolean failconnection, int validateatmostonceperiod, Properties properties,
                                        JdbcResourceType restype, JdbcIsolationLevel isolationlevel, boolean allownoncomponentcallers)
            throws CommandException {
        CreateJdbcConnectionPoolCommand command = new CreateJdbcConnectionPoolCommand(environment, name,
                datasourceclassname, steadypoolsize, maxpoolsize, isconnectvalidatereq, validationMethod,
                validationtable, validationclassname, failconnection, validateatmostonceperiod, properties, restype,
                isolationlevel, allownoncomponentcallers);
        return command.execute();
    }

    public int createJdbcConnectionPool(String name, String datasourceclassname,
                                        int steadypoolsize, int maxpoolsize, boolean isconnectvalidatereq,
                                        JdbcValidationMethod validationMethod, String validationtable,
                                        String validationclassname,
                                        boolean failconnection, int validateatmostonceperiod, Properties properties,
                                        JdbcResourceType restype, JdbcIsolationLevel isolationlevel,
                                        boolean allownoncomponentcallers, boolean nontransactionalconnections)
            throws CommandException {
        CreateJdbcConnectionPoolCommand command = new CreateJdbcConnectionPoolCommand(environment, name,
                datasourceclassname, steadypoolsize, maxpoolsize, isconnectvalidatereq, validationMethod,
                validationtable, validationclassname, failconnection, validateatmostonceperiod, properties, restype,
                isolationlevel, allownoncomponentcallers, nontransactionalconnections);
        return command.execute();
    }


    public int deleteJdbcConnectionPool(String name) throws CommandException {
        return new DeleteJdbcConnectionPoolCommand(environment, name).execute();
    }

    public int deleteJdbcResource(String name) throws CommandException {
        return new DeleteJdbcResourceCommand(environment, name).execute();
    }


    public List<String> getJdbcConnectionPools() throws CommandException {
        return new ListJdbcConnectionPoolsCommand(environment).execute();
    }

    public int deploy(File file, String name, boolean precompilejsp, String[] virtualhost) throws CommandException {
        if (file.exists()) {
            DeployCommand command = new DeployCommand(environment, DEFAULT_TARGET, file, name, precompilejsp, null);
            command.setVirtualservers(virtualhost);
            return command.execute();
        } else {
            throw new CommandException("Could not find file : " + file);
        }
    }


    public int deploy(File file, String name, boolean precompilejsp, Properties properties) throws CommandException {
        if (file.exists()) {
            return new DeployCommand(environment, DEFAULT_TARGET, file, name, precompilejsp, properties).execute();
        } else {
            throw new CommandException("Could not find file : " + file);
        }
    }

    public int deploy(File file) throws CommandException {
        if (file.exists()) {
            return new DeployCommand(environment, DEFAULT_TARGET, file).execute();
        } else {
            throw new CommandException("Could not find file : " + file);
        }
    }

    public int deployLocal(File file) throws CommandException {
        if (file.exists()) {
            return new DeployCommand(environment, DEFAULT_TARGET, file, null, null, null, false, false, false, true,
                    false, null).execute();
        } else {
            throw new CommandException("Could not find file : " + file);
        }
    }


    public int deploy(File file, String name) throws CommandException {
        if (file.exists()) {
            return new DeployCommand(environment, DEFAULT_TARGET, file, name).execute();
        } else {
            throw new CommandException("Could not find file : " + file);
        }
    }

    public int undeploy(String name) throws CommandException {
        return new UndeployCommand(environment, DEFAULT_TARGET, name).execute();
    }


    public void createJmsConnectionFactory(String jndiname, String description) throws CommandException {
        Properties props = new Properties();
        props.setProperty("Password", "guest");
        props.setProperty("UserName", "guest");
        props.setProperty("AddressList", "guest");
        new CreateJmsResourceCommand(environment, DEFAULT_TARGET, JmsResourceType.javax_jms_ConnectionFactory, jndiname,
                description, props).execute();
    }

    public void createJmsConnectionFactory(String jndiname, JmsResourceType resourceType) throws CommandException {
        new CreateJmsResourceCommand(environment, DEFAULT_TARGET, resourceType, jndiname).execute();
    }


    public void createJmsConnectionFactory(String jndiname, String description, Properties properties) throws
            CommandException {
        new CreateJmsResourceCommand(environment, DEFAULT_TARGET, JmsResourceType.javax_jms_ConnectionFactory, jndiname,
                description, properties).execute();
    }


    public void createJmsQueue(String name, String jndiname, String description) throws CommandException {
        new CreateJmsDestCommand(environment, JmsDestType.QUEUE, name, null).execute();
        Properties queueProps = new Properties();
        queueProps.setProperty("Name", name);
        new CreateJmsResourceCommand(environment, DEFAULT_TARGET, JmsResourceType.javax_jms_Queue, jndiname,
                description, queueProps).execute();
    }

    public void createJmsTopic(String name, String jndiname, String description) throws CommandException {
        new CreateJmsDestCommand(environment, JmsDestType.TOPIC, name, null).execute();
        Properties queueProps = new Properties();
        queueProps.setProperty("Name", name);
        new CreateJmsResourceCommand(environment, DEFAULT_TARGET, JmsResourceType.javax_jms_Topic, jndiname,
                description, queueProps).execute();
    }


    public void createJmsQueue(String name, String jndiname, Properties jmsDestinationProperties, String description,
                               Properties jmsResourceProperties) throws CommandException {
        new CreateJmsDestCommand(environment, JmsDestType.QUEUE, name, jmsDestinationProperties).execute();
        new CreateJmsResourceCommand(environment, DEFAULT_TARGET, JmsResourceType.javax_jms_Queue, jndiname,
                description, jmsResourceProperties);
    }


    public void createJdbcResource(String connectionpoolid, String jndi_name) throws CommandException {
        new CreateJdbcResourceCommand(environment, connectionpoolid, jndi_name).execute();
    }

    public boolean isRestartNeeded(String domainName) throws CommandException {
        if (environment.getVersion() == Version.V2) {
            DomainInfo domain = getDomain(domainName);
            return domain.isRestartRequired();
        } else {
            return new IsRestartRequiredCommand(environment).execute();
        }
    }

    public void set(String name, String value) throws CommandException {
        new SetCommand(environment, name, value).execute();
    }

    public void set(String[] name, String[] value) throws CommandException {
        new SetCommand(environment, name, value).execute();
    }


    public void set(String name, int value) throws CommandException {
        new SetCommand(environment, name, String.valueOf(value)
        ).execute();
    }

    public String get(String attribute) throws CommandException {
        return new GetCommand(environment, attribute).execute();
    }

    public List<DomainInfo> listDomains() throws CommandException {
        return new ListDomainsCommand(environment).execute();
    }

    public boolean existsDomain(String domainName) throws CommandException {
        DomainInfo domain = getDomain(domainName);
        return domain != null;
    }

    public void enableDebug() throws CommandException {
        new SetCommand(environment, DEFAULT_TARGET + "-config.java-config.debug-enabled", "true").execute();
    }

    public boolean isDomainRunning(String domainName) throws CommandException {
        if (environment.getVersion() == Version.V2 || environment.getVersion() == Version.V3_1) {
            DomainInfo domain = getDomain(domainName);
            return domain != null && domain.isRunning();
        } else {
            return new IsRunningCommand(environment).execute();
        }
    }

    public void createJavaMailResource(String mailhost, String mailuser, String fromaddress, String jndi_name)
            throws CommandException {
        new CreateJavaMailResourceCommand(environment, DEFAULT_TARGET, mailhost, mailuser, fromaddress,
                jndi_name).execute();
    }

    public boolean hasJvmOption(String jvmOption) throws CommandException {
        List<String> options = getJvmOptions();
        for (String option : options) {
            if (option.equals(jvmOption)) {
                return true;
            }
        }
        return false;
    }

    public List<String> getJvmOptions() throws CommandException {
        return new ListJvmOptionsCommand(environment).execute();
    }

    public void setMonitorLevel(Monitor monitor, MonitorLevel level) throws CommandException {
        new SetMonitoringLevelCommand(environment, DEFAULT_TARGET, monitor, level).execute();
    }

    public MonitorLevel getMonitorLevel(Monitor monitor) throws CommandException {
        return new GetMonitorLevelCommand(environment, DEFAULT_TARGET, monitor).execute();
    }

    public void enable(String name) throws CommandException {
        new EnableCommand(environment, DEFAULT_TARGET, name).execute();
    }

    public void disable(String name) throws CommandException {
        new DisableCommand(environment, DEFAULT_TARGET, name).execute();
    }

    public void createLifeCycleModule(String classname, String name) throws CommandException {
        new CreateLifecycleModuleCommand(environment, DEFAULT_TARGET, classname, name).execute();
    }


    public void createNodeAgent(String name) throws CommandException {
        new CreateNodeAgentCommand(environment, name).execute();
    }

    public void startNodeAgent(String name) throws CommandException {
        new StartNodeAgentCommand(environment, name).execute();
    }

    public void stopNodeAgent(String name) throws CommandException {
        new StopNodeAgentCommand(environment, name).execute();
    }

    public void createCluster(String name) throws CommandException {
        new CreateClusterCommand(environment, name).execute();
    }

    public void createInstance(String nodeagent, String cluster, String name) throws CommandException {
        new CreateInstanceCommand(environment, nodeagent, cluster, name).execute();
    }

    public void setCustomLogLevel(String loggerName, Level level) throws CommandException {
        if (environment.getVersion() == Version.V2) {
            new SetCustomLogLevel(environment, DEFAULT_TARGET, loggerName, level).execute();
        } else {
            new SetLogLevelCommand(environment, loggerName, level).execute();
        }
    }

    public void setCustomLogLevel(List<String> loggerNames, Level level) throws CommandException {
        if (environment.getVersion() == Version.V2) {
            throw new CommandException("Not implemented");
        } else {
            new SetLogLevelCommand(environment, loggerNames, level).execute();
        }
    }


    //create-cluster
    //create-instance
    //create-connector-resource

    public List<Component> listComponents() throws CommandException {
        return new ListComponentsCommand(environment, DEFAULT_TARGET).execute();
    }

    public String version() throws CommandException {
        return new VersionCommand(environment).execute();
    }

    public boolean pingConnectionPool(String poolname) throws CommandException {
        return new PingConnectionPoolCommand(environment, poolname).execute();
    }


    private DomainInfo getDomain(String domainName) throws CommandException {
        List<DomainInfo> domains = listDomains();

        for (DomainInfo domain : domains) {
            if (domain.getName().endsWith(domainName)) {
                return domain;
            }
        }
        return null;
    }

}
