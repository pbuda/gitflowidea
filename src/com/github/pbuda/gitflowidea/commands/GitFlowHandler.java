/*
 * Copyright 2012 Piotr Buda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.pbuda.gitflowidea.commands;

import com.intellij.execution.*;
import com.intellij.execution.configurations.*;
import com.intellij.openapi.components.*;
import com.intellij.openapi.diagnostic.*;
import com.intellij.openapi.project.*;
import com.intellij.openapi.vfs.*;
import git4idea.config.*;
import org.jetbrains.annotations.*;

import java.io.*;

/**
 * .
 */
public class GitFlowHandler {
    /**
     * Diagnostics logger.
     */
    private static final Logger LOG = Logger.getInstance(GitFlowHandler.class.getName());

    /**
     * .
     */
    private Project project;

    /**
     * Command to execute.
     */
    private GitFlowCommand command;

    /**
     * Command line interpreter.
     */
    private GeneralCommandLine commandLine;

    /**
     * .
     */
    private File workingDirectory;

    /**
     * .
     */
    private Process process;

    /**
     * .
     *
     * @param project
     * @param directory
     * @param command
     */
    public GitFlowHandler(@NotNull Project project, @NotNull File directory, @NotNull GitFlowCommand command) {
        this.project = project;
        this.workingDirectory = directory;
        this.command = command;

        GitVcsApplicationSettings settings = ServiceManager.getService(GitVcsApplicationSettings.class);

        this.commandLine = new GeneralCommandLine();
        this.commandLine.setWorkDirectory(this.workingDirectory);
        this.commandLine.setExePath(settings.getPathToGit());
        this.commandLine.addParameter("flow");
        this.commandLine.addParameter(command.getCommand());
    }

    /**
     * .
     *
     * @param project
     * @param directory
     * @param command
     */
    public GitFlowHandler(@NotNull Project project, @NotNull VirtualFile directory, @NotNull GitFlowCommand command) {
        this(project, VfsUtil.virtualToIoFile(directory), command);
    }

    public void start() {
        try {
            LOG.info(commandLine.getCommandLineString());
            process = commandLine.createProcess();
        } catch (ExecutionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void addParameter(String param) {
        commandLine.addParameter(param);
    }
}
