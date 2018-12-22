/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aura.cases;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

/**
 * Skeleton for a Flink Batch Job.
 *
 * <p>For a tutorial how to write a Flink batch application, check the
 * tutorials and examples on the <a href="http://flink.apache.org/docs/stable/">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution,
 * change the main class in the POM.xml file to this class (simply search for 'mainClass')
 * and run 'mvn clean package' on the command line.
 */
public class BatchJob {

	public static void main(String[] args) throws Exception {

		final long numSamples = args.length > 0 ? Long.parseLong(args[0]) : 1000000;

		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

		// count how many of the samples would randomly fall into
		// the unit circle
		DataSet<Long> count =
				env.generateSequence(1, numSamples)
						.map(new MapFunction<Long, Long>() {
							@Override
							public Long map(Long aLong) throws Exception {
								double x = Math.random();
								double y = Math.random();
								return (x * x + y * y) < 1 ? 1L : 0L;
							}
						})
						.reduce((x,y) -> x + y);

		long theCount = count.collect().get(0);

		System.out.println("We estimate Pi to be: " + (theCount * 4.0 / numSamples));

	}
}
