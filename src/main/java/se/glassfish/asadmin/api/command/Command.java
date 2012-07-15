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

import se.glassfish.asadmin.api.*;

import java.io.File;
import java.util.*;

public abstract class Command<E> {


    protected GlassFishEnvironment environment;

    private Map<String, Object> params = new HashMap<String, Object>();
    private List<String> arguments = new ArrayList<String>();

    public Command(GlassFishEnvironment environment) {
        this.environment = environment;
    }

    public void addParam(String name, String value) {
        params.put("--" + name, value);
    }

    public void addQuotedParam(String name, String value) {
        params.put("--" + name, "\"" + value + "\"");
    }


    public void addParam(String name, int value) {
        params.put("--" + name, String.valueOf(value));
    }

    public void addParam(String name, boolean value) {
        params.put("--" + name + "=", value);
    }

    public void addParam(String name, Properties value) {
        params.put("--" + name, value);
    }

    public void addParam(String name, Object value) {
        params.put("--" + name, value);
    }


    public void addParam(String name, String[] values) {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (String value : values) {

            builder.append(value);

            if (i < values.length - 1) {
                builder.append(":");
            }
            i++;

        }
        params.put("--" + name, builder.toString());
    }


    public void addArg(String name) {
        arguments.add(name);
    }

    protected String escape(String value, String chars) {
        return escape(value, chars, "\\\\");
    }

    protected String escape(String value, String chars, String escapeSequence) {
        String escaped = value;
        if (escaped == null) {
            return "";
        }
        for (char ch : chars.toCharArray()) {
            escaped = escaped.replaceAll(String.valueOf(ch), escapeSequence + ch);
        }
        return escaped;
    }


    protected CommandResult executeCommand() throws CommandException {
        try {
            List<String> commandLine = new ArrayList<String>();
            StringBuilder asadmin = new StringBuilder();
            asadmin.append(environment.getGlassFishHome());
            asadmin.append(File.separator);
            asadmin.append("bin");
            asadmin.append(File.separator);
            if (System.getProperty("os.name").contains("indows")) {
                asadmin.append("asadmin.bat");
            } else {
                asadmin.append("asadmin");
            }
            commandLine.add(asadmin.toString());
            if (environment.getVersion() == Version.V2 || getCommandName().equals("create-domain") || getCommandName().equals("deploy") ||
                    getCommandName().equals("create-jdbc-connection-pool") || getCommandName().equals("create-jdbc-resource") ||
                    getCommandName().equals("create-jvm-options") ||
                    getCommandName().equals("create-profiler") || getCommandName().equals("create-jms-resource") ||
    				getCommandName().equals("create-jmsdest")) {
                commandLine.add(getCommandName());
            }

            if (environment.getVersion() == Version.V3 || environment.getVersion() == Version.V3_1) {
                params.remove("--target");
            }

            Set<String> keys = params.keySet();
            for (String key : keys) {
                Object value = params.get(key);
                if (value == null) {
                    throw new CommandException("Param " + key + " has a null value!");
                }
                if (key.endsWith("=")) {
                    commandLine.add(key + value);
                } else {
                    
                    commandLine.add(key);
                    //commandString.append(" ");
                    if (value instanceof String) {
                        commandLine.add(value.toString());
                    } else {
                        Properties properties = (Properties) value;
                        Set propKeys = properties.keySet();
                        StringBuilder builder = new StringBuilder("");
                        int i = 0;
                        for (Object propKey : propKeys) {
                            String propValue = properties.getProperty(propKey.toString());
                            builder.append(escape(propKey.toString(), "=;:"));
                            builder.append("=");
                            builder.append(escape(propValue, "=;:"));
                            if (i < propKeys.size() - 1) {
                                builder.append(":");
                            }
                            i++;
                        }
                        commandLine.add(builder.toString());
                    }
                }
            }
            if ((environment.getVersion() == Version.V3 || environment.getVersion() == Version.V3_1) && !getCommandName().equals("create-domain") &&
                    !getCommandName().equals("create-jdbc-connection-pool") &&
                    !getCommandName().equals("create-jdbc-resource") && !getCommandName().equals("deploy") && !getCommandName().equals("create-jvm-options")
                    && !getCommandName().equals("create-profiler") && !getCommandName().equals("create-jms-resource")
					&& !getCommandName().equals("create-jmsdest")) {
                commandLine.add(getCommandName());
            }

            for (String argument : arguments) {
                commandLine.add(argument);
            }

            StringBuilder buffer = new StringBuilder();
            for (String argument : commandLine) {
                buffer.append(argument).append(" ");
            }

            if (environment.isVerbose()) {
                System.out.println("command: " + buffer.toString());
            }

            ExecutorResult result = environment.getCommandExecutor().execute(buffer.toString() , environment.isVerbose());


            //passwordFile.delete();
            CommandException exception = null;

            for (String error : result.getErrors()) {
                if (error.startsWith("CLI")) {
                    exception = new CommandException(error);
                }
            }

            if (exception != null) {
                throw exception;
            }

            return new CommandResult(result.getExitCode(), result.getOutput(), result.getErrors());
        } catch (ExecutorException e) {
            throw new CommandException(e);
        }
    }

    public abstract E execute() throws CommandException;

    public abstract String getCommandName();

}
