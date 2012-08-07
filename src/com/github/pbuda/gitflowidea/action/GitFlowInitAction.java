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
import com.intellij.openapi.progress.Task.*;
import com.intellij.openapi.project.*;
import git4idea.actions.*;
import git4idea.repo.*;
import org.jetbrains.annotations.*;

/**
 * .
 */
public class GitFlowInitAction extends AnAction {
    public void actionPerformed(AnActionEvent e) {
        final Project finalProject = e.getProject();
        new Backgroundable(finalProject, "Initializing git flow") {
            @Override
            public void run(@NotNull ProgressIndicator progressIndicator) {
                runInit(finalProject);
                refreshAfterInit(finalProject);
            }
        }.queue();
    }

    private void runInit(Project project) {
        GitFlow gitFlow = new GitFlow();
        gitFlow.initDefault(project, project.getBaseDir());
    }

    private void refreshAfterInit(final Project project) {
        GitInit.refreshAndConfigureVcsMappings(project, project.getBaseDir(), "");
        GitRepositoryManager repositoryManager = project.getComponent(GitRepositoryManager.class);
        for (GitRepository repository : repositoryManager.getRepositories()) {
            repository.getUntrackedFilesHolder().invalidate();
        }
    }
}
