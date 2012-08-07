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

package com.github.pbuda.gitflowidea.action;

import com.github.pbuda.gitflowidea.*;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.progress.*;
import com.intellij.openapi.project.*;
import com.intellij.openapi.vfs.*;
import git4idea.actions.*;
import git4idea.repo.*;
import org.jetbrains.annotations.*;

/**
 * .
 */
public class GitFlowInitAction extends AnAction {
    public void actionPerformed(AnActionEvent e) {
        GitFlow gitFlow = new GitFlow();
        Project project = e.getProject();
        VirtualFile baseDir = project.getBaseDir();
        int exitCode = gitFlow.initDefault(project, baseDir);
        if (exitCode != 1) {
            throw new IllegalStateException();
        }
        refreshVcsChanges(e.getProject(), baseDir);
    }

    private void refreshVcsChanges(final Project project, final VirtualFile baseDir) {
        new Task.Backgroundable(project, "Refreshing") {
            public void run(@NotNull ProgressIndicator indicator) {
                GitInit.refreshAndConfigureVcsMappings(project, baseDir, "");
                GitRepositoryManager repositoryManager = project.getComponent(GitRepositoryManager.class);
                for (GitRepository repository : repositoryManager.getRepositories()) {
                    repository.getUntrackedFilesHolder().invalidate();
                }
            }
        }.queue();
    }
}
