# CacheSimulation
Cache Simulation
Cache.java takes in a pintool readout of memory addresses accessed.
Then by putting in the cache capacity, blocksize, and associativity, cache
read/write and hit/misses are simulated. BlockSet simulates sets and
CacheBlock are the singular blocks inside the set.

InstructionMix.java also takes in a pintool readout of opcodemix.out and calculates
the percentages of computation/memory/logical/branch opCodes and prints them out.
