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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommandLineExecutor implements CommandExecutor {

    private static final int PROCESS_LOOP_SLEEP_MILLIS = 100;


    public ExecutorResult execute(String commandLine, boolean verbose) throws ExecutorException {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            String[] command =  commandLine.split(" ");
            processBuilder.command(command);
            Process process = processBuilder.start();
            BufferedReader outReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            List<String> output = new ArrayList<String>();
            List<String> errors = new ArrayList<String>();


            int exit;
            do {
                try {
                    exit = process.exitValue();
                    if (verbose)
                        System.out.println("exit: " + exit);
                    break;
                } catch (IllegalThreadStateException e) {
                    Thread.sleep(PROCESS_LOOP_SLEEP_MILLIS);
                } finally {
                    while (outReader.ready()) {
                        String line = outReader.readLine();
                        if (verbose)
                            System.out.println("out: " + line);
                        output.add(line);
                    }
                    while (errReader.ready()) {
                        String line = errReader.readLine();
                        if (verbose)
                            System.out.println("error: " + line);
                        errors.add(line);
                    }
                }
            } while (true);

            return new ExecutorResult(output, errors, exit);
        } catch (IOException e) {
            throw new ExecutorException(e);
        } catch (InterruptedException e) {
            throw new ExecutorException(e);
        }

    }

}
