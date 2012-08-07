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

package com.github.pbuda.gitflowidea;

import com.github.pbuda.gitflowidea.commands.*;
import com.intellij.openapi.project.*;
import com.intellij.openapi.vfs.*;

/**
 * .
 */
public class GitFlow {
    public int initDefault(Project project, VirtualFile directory) {
        GitFlowHandler handler = new GitFlowHandler(project, directory, GitFlowCommand.INIT);
        handler.addParameter("-d");
        return run(handler);
    }

    private int run(GitFlowHandler handler) {
        handler.start();
        return 1;
    }
}
