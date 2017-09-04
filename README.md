neo4j-ha-example
================

This example project shows how to setup a neo4j embedded database in the HA mode.

Plase note that this example uses neo4j 2.1.2 

普通内嵌：
----------------
Starting an embedded database with configuration settings
#### 1、调用配置文件：
To start Neo4j with configuration settings, a Neo4j properties file can be loaded like this:
```
GraphDatabaseService graphDb = new GraphDatabaseFactory()
    .newEmbeddedDatabaseBuilder( testDirectory.graphDbDir() )
    .loadPropertiesFromFile( pathToConfig + "neo4j.properties" )
    .newGraphDatabase();
```
#### 2、手动配置：
Configuration settings can also be applied programmatically, like so:
```
GraphDatabaseService graphDb = new GraphDatabaseFactory()
    .newEmbeddedDatabaseBuilder( testDirectory.graphDbDir() )
    .setConfig( GraphDatabaseSettings.pagecache_memory, "512M" )
    .setConfig( GraphDatabaseSettings.string_block_size, "60" )
    .setConfig( GraphDatabaseSettings.array_block_size, "300" )
    .newGraphDatabase();
```
#### 3、最简单创建：
```
graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( DB_PATH );
```
HA cluster模式：
----------------

```
protected final static String DB_LOCATION = "target/graph-master";
protected final static String SERVER_ID = "1";
GraphDatabaseBuilder builder = new HighlyAvailableGraphDatabaseFactory().newHighlyAvailableDatabaseBuilder(DB_LOCATION);

builder.setConfig(ClusterSettings.server_id, SERVER_ID);
builder.setConfig(HaSettings.ha_server, "localhost:6001");
builder.setConfig(HaSettings.slave_only, Settings.FALSE);
builder.setConfig(ClusterSettings.cluster_server, "localhost:5001");
builder.setConfig(ClusterSettings.initial_hosts, "localhost:5001,localhost:5002,localhost:5003");

graphDb = builder.newGraphDatabase();
engine = new ExecutionEngine(graphDb);
```
参考文档：
----------------

简单实战文档：

https://www.ibm.com/developerworks/cn/java/j-lo-neo4j/

java api文档：

http://neo4j.com/docs/2.3.1/javadocs/
