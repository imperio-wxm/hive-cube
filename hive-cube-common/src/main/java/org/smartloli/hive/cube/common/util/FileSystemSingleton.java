/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smartloli.hive.cube.common.util;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartloli.hive.cube.common.client.CommonClientConfigs.Hadoop;

/**
 * HDFS object instance.
 *
 * @author smartloli.
 *
 *         Created by Oct 9, 2016
 *
 */
public class FileSystemSingleton {

	private final static Logger LOG = LoggerFactory.getLogger(FileSystemSingleton.class);

	private static class FileSystemHolder {
		private static FileSystem fs;
		static {
			try {
				Configuration conf = new Configuration();
				conf.setBooleanIfUnset(Hadoop.DFS_SUPPORT_APPEND, true);
				conf.setInt(Hadoop.IO_FILE_BUFFER_SIZE, 4194304);
				conf.set(Hadoop.DFS_POLICY, "NEVER");
				conf.set(Hadoop.DFS_ENABLE, "true");
				String uri = SystemConfig.getProperty("hive.cube.hdfs.uri");
				String user = SystemConfig.getProperty("hive.cube.hadoop.user");
				fs = FileSystem.get(new URI(uri), conf, user);
			} catch (Exception e) {
				LOG.error("Create hadoop file system failure, msg is " + e.getMessage());
			}
		}
	}

	public static FileSystem create() {
		return FileSystemHolder.fs;
	}

}
