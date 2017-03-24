package com.xmli.cassandra;

import info.archinnov.achilles.annotations.CompileTimeConfig;
import info.archinnov.achilles.type.CassandraVersion;
import info.archinnov.achilles.type.strategy.NamingStrategy;

@CompileTimeConfig(cassandraVersion = CassandraVersion.CASSANDRA_3_7, namingStrategy = NamingStrategy.SNAKE_CASE)
public interface AchillesConfig {
}
