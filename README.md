# Sockets based implementation of building graph and finding the shortest paths.

### Requirements

* Java 11

### Run

* `java -jar ./build/libs/fat-sockets-graph-1.0-SNAPSHOT.jar`

* Directly by invoking `asundukov.sockets.graph.Application.main()` method from IDE 

### Future possible improvements

* Optimization - Add `Graph` implementation with 
  virtual calculated edges (like a cache) 
  with the shortest paths between already calculated nodes 
  (evict them all when graph topology changed)
  
* Concurrency - Add `Graph` implementation with 
  possibility to work with `transactionId` 
  and `transactionId`-property to edges and/or nodes to make 
  read and write operations working at the same time

(Right now they are out of scope of task)
  
### Sources

There are sime unused classes which reflects developming progress (they were replaced 
by better optimized classes)
