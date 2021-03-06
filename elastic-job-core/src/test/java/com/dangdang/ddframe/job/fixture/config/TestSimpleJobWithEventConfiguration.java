/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
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
 * </p>
 */

package com.dangdang.ddframe.job.fixture.config;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobRootConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobTraceEvent.LogLevel;
import com.dangdang.ddframe.job.event.log.JobLogEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobRdbEventConfiguration;
import com.dangdang.ddframe.job.executor.handler.JobProperties;
import com.dangdang.ddframe.job.fixture.ShardingContextsBuilder;
import com.dangdang.ddframe.job.fixture.handler.ThrowJobExceptionHandler;
import com.dangdang.ddframe.job.fixture.job.TestSimpleJob;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class TestSimpleJobWithEventConfiguration implements JobRootConfiguration {
    
    @Override
    public JobTypeConfiguration getTypeConfig() {
        return new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(ShardingContextsBuilder.JOB_NAME, "0/1 * * * * ?", 3)
                .shardingItemParameters("0=A,1=B,2=C").jobParameter("param")
                .failover(true).misfire(false).description("desc")
                .jobProperties(JobProperties.JobPropertiesEnum.JOB_EXCEPTION_HANDLER.getKey(), 
                        ThrowJobExceptionHandler.class.getCanonicalName())
                .jobEventConfiguration(new JobLogEventConfiguration(), new JobRdbEventConfiguration("org.h2.Driver", 
                        "jdbc:h2:mem:job_event_storage", "sa", "", LogLevel.INFO)).build(), TestSimpleJob.class.getCanonicalName());
    }
}
