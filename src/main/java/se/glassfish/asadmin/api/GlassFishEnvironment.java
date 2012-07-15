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

public class GlassFishEnvironment {

    private String adminUsername = "admin";
    private String adminPassword = "adminadmin";
    private String masterAdminPassword = "changeit";
    private String glassFishHome;
    private boolean verbose = false;
    private boolean uselocalAuth = false;

    private String host = "localhost";
    private int port = 4848;

    private Version version = Version.V2;

    private CommandExecutor commandExecutor = new CommandLineExecutor();

    public GlassFishEnvironment(String glassFishHome) {
        this.glassFishHome = glassFishHome;
    }

    public GlassFishEnvironment(String glassFishHome, boolean uselocalAuth, int port, boolean verbose) {
        this.glassFishHome = glassFishHome;
        this.uselocalAuth = uselocalAuth;
        this.port = port;
        this.verbose = verbose;
    }
    

    public GlassFishEnvironment(String glassFishHome, boolean uselocalAuth, String host, int port) {
        this.glassFishHome = glassFishHome;
        this.uselocalAuth = uselocalAuth;
        this.host = host;
        this.port = port;
    }

    public GlassFishEnvironment(String adminUsername, String adminPassword, String masterAdminPassword, String glassFishHome, boolean verbose, String host, int port) {
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
        this.masterAdminPassword = masterAdminPassword;
        this.glassFishHome = glassFishHome;
        this.verbose = verbose;
        this.host = host;
        this.port = port;
    }

    public GlassFishEnvironment(String adminUsername, String adminPassword, String masterAdminPassword, String glassFishHome) {
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
        this.masterAdminPassword = masterAdminPassword;
        this.glassFishHome = glassFishHome;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getMasterAdminPassword() {
        return masterAdminPassword;
    }

    public void setMasterAdminPassword(String masterAdminPassword) {
        this.masterAdminPassword = masterAdminPassword;
    }

    public String getGlassFishHome() {
        return glassFishHome;
    }

    public void setGlassFishHome(String glassFishHome) {
        this.glassFishHome = glassFishHome;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public boolean isUselocalAuth() {
        return uselocalAuth;
    }

    public void setUselocalAuth(boolean uselocalAuth) {
        this.uselocalAuth = uselocalAuth;
    }

    public CommandExecutor getCommandExecutor() {
        return commandExecutor;
    }
}
