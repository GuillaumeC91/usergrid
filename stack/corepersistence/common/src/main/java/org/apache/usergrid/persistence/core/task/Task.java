/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.usergrid.persistence.core.task;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;


/**
 * The task to execute
 */
public abstract class Task<V> extends RecursiveTask<V> {

    @Override
    protected V compute() {
        try {
            return executeTask();
        }
        catch ( Exception e ) {
            exceptionThrown( e );
            throw new RuntimeException( e );
        }
    }



    /**
     * Execute the task
     */
    public abstract V executeTask() throws Exception;

    /**
     * Invoked when this task throws an uncaught exception.
     */
    public abstract void exceptionThrown( final Throwable throwable );

    /**
     * Invoked when we weren't able to run this task by the the thread attempting to schedule the task. If this task
     * MUST be run immediately, you can invoke the call method from within this event to invoke the task in the
     * scheduling thread.  Note that this has performance implications to the user.  If you can drop the request and
     * process later (lazy repair for instance ) do so.
     */
    public abstract void rejected();
}
