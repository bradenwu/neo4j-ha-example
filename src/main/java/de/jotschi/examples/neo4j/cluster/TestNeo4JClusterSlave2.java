package de.jotschi.examples.neo4j.cluster;

import org.neo4j.cluster.ClusterSettings;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseBuilder;
import org.neo4j.graphdb.factory.HighlyAvailableGraphDatabaseFactory;
import org.neo4j.helpers.Settings;
import org.neo4j.kernel.ha.HaSettings;

public class TestNeo4JClusterSlave2 extends AbstractNeo4jCluster {
    protected final static String DB_LOCATION = "target/graph-slave-2";
    protected final static String SERVER_ID = "3";

    public static void main(String[] args) throws InterruptedException {
        new TestNeo4JClusterSlave2();
    }

    private static enum RelationshipTypes implements RelationshipType {
        PUBLISH
    }

    public TestNeo4JClusterSlave2() throws InterruptedException {
        GraphDatabaseBuilder builder = new HighlyAvailableGraphDatabaseFactory().newHighlyAvailableDatabaseBuilder(DB_LOCATION);

        builder.setConfig(ClusterSettings.server_id, SERVER_ID);
        builder.setConfig(HaSettings.ha_server, "localhost:6003");
        builder.setConfig(ClusterSettings.cluster_server, "localhost:5003");
        builder.setConfig(HaSettings.slave_only, Settings.TRUE);
        builder.setConfig(ClusterSettings.initial_hosts, "localhost:5001,localhost:5002,localhost:5003");

        graphDb = builder.newGraphDatabase();
        engine = new ExecutionEngine(graphDb);
        //Label name = DynamicLabel.label("testnode");

        //while (true) {
//        try (Transaction tx = graphDb.beginTx()) {
//
//            Node node1 = graphDb.createNode();
//            node1.setProperty("name", "赵雷");
//            Node node2 = graphDb.createNode();
//            node2.setProperty("name", "不想长大");
//            node1.createRelationshipTo(node2, RelationshipTypes.PUBLISH);
//            tx.success();
//            System.out.println("Added two nodes and a relationship");
//            executeQuery("MATCH n RETURN count(n) as count");
//            Thread.sleep(1000);
//
//        }
        Label name = DynamicLabel.label("testnode1");
        while (true) {
            try (Transaction tx = graphDb.beginTx()) {
                Node node = graphDb.createNode(name);
                node.setProperty("name", "id_" + Math.random());
                tx.success();
                System.out.println("Added node");
            }
            executeQuery("MATCH n RETURN count(n) as count");
            Thread.sleep(1000);
        }
    }

}
