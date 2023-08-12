# INTRODUCTION

Redis is a fast and lightweight data store that primarily operates in memory, allowing for rapid data retrieval. It offers various data types, each tailored to a specific use case, making it a versatile tool for different scenarios.

## OPERATIONS

### GENERAL
- DEL: this command deletes the key, if it exists.

Pattern:
```shell
DEL key [key...]
```
- DUMP: this command returns a serialized version of the value stored at the specified key.

Pattern:
```shell
DUMP key
```
- EXISTS: this command checks whether the key exists or not.

Pattern:
```shell
EXISTS key [key...]
```
- EXPIRE: sets the expiry of the key after the specified time.

Pattern:
```shell
EXPIRE key seconds
```
- EXPIREAT: sets the expiry of the key after the specified time. Here time is in Unix timestamp format.

Pattern:
```shell
EXPIREAT key timestamp
```
- PEXPIRE: set the expiry of key in milliseconds.

Pattern:
```shell
PEXPIRE key milliseconds
```
- PEXPIREAT: sets the expiry of the key in Unix timestamp specified as milliseconds.

Pattern:
```shell
PEXPIREAT key milliseconds-timestamp
```
- KEYS: finds all keys matching the specified pattern.

Pattern:
```shell
KEYS pattern
```
- MOVE: moves a key to another database.

Pattern:
```shell
MOVE key db
```
- PERSIST: removes the expiration from the key.

Pattern:
```shell
PERSIST key
```
- PTTL: gets the remaining time in keys expiry in milliseconds.

Pattern:
```shell
PTTL key
```
- TTL: gets the remaining time in keys expiry.

Pattern:
```shell
TTL key
```
- : returns a random key from Redis.

Pattern:
```shell
RANDOMKEY
```
- RENAME: changes the key name.

Pattern:
```shell
RENAME key new_key
```
- RENAMENX: renames the key, if a new key doesn't exist.

Pattern:
```shell
RENAMENX key new_key
```
- TYPE: returns the data type of the value stored in the key.

Pattern:
```shell
TYPE key
```
- SELECT: select a database. By default, redis has 0-15 indexes for databases, you can change that number databases NUMBER in redis.conf. Keys can't be shared across databases (although pub/sub is database agnostic).

Pattern:
```shell
SELECT index
```

### STRINGS
- SET: set a key to hold a string value.

Pattern:
```shell
SET key value
```
Example:
```shell
127.0.0.1:6379> SET name Mark
OK
```
- GET: get the value of a key.

Pattern:
```shell
GET key
```
Example:
```shell
127.0.0.1:6379> GET name
"Mark"
```
- GETSET: get the current value and set a new value for a key.

Pattern:
```shell
GETSET key new_value
```
Example:
```shell
127.0.0.1:6379> GETSET name John
"Mark"
127.0.0.1:6379> GET name
"John"
```
- MGET: get the values of multiple keys.

Pattern:
```shell
MGET key1 key2 ...
```
Example:
```shell
127.0.0.1:6379> MGET name surname
1) "John"
2) "Johnson"
```
- SETNX: set a key if it does not exist.

Pattern:
```shell
SETNX key value
```
Example:
```shell
127.0.0.1:6379> SETNX name Arthur
(integer) 0
127.0.0.1:6379> GET name
"John"
```
- SETEX: set a key with an expiration time (TTL).

Pattern:
```shell
SETEX key seconds value
```
Example:
```shell
127.0.0.1:6379> SETEX temporal 10 active
OK
127.0.0.1:6379> GET temporal
"active"
127.0.0.1:6379> GET temporal
(nil)
```
- PSETEX: set a key with an expiration time in milliseconds.

Pattern:
```shell
PSETEX key milliseconds value
```
Example:
```shell
127.0.0.1:6379> PSETEX temporal 10000 active
OK
127.0.0.1:6379> GET temporal
"active"
127.0.0.1:6379> GET temporal
(nil)
```
- SETRANGE: overwrite a part of the string stored at a key.

Pattern:
```shell
SETRANGE key offset value
```
Example:
```shell
127.0.0.1:6379> GET name
"John"
127.0.0.1:6379> SETRANGE name 2 del
(integer) 5
127.0.0.1:6379> GET name
"Jodel"
```
- STRLEN: get the length of the value stored at a key.

Pattern:
```shell
STRLEN key
```
Example:
```shell
127.0.0.1:6379> GET name
"Jodel"
127.0.0.1:6379> STRLEN name
(integer) 5
```
- MSET: set multiple keys to multiple values.

Pattern:
```shell
MSET key1 value1 key2 value2 ...
```
Example:
```shell
127.0.0.1:6379> MSET hobbit1 Frodo hobbit2 Sam
OK
127.0.0.1:6379> GET hobbit1
"Frodo"
127.0.0.1:6379> GET hobbit2
"Sam"
```
- MSETNX: set multiple keys to multiple values if none of the keys exist.

Pattern:
```shell
MSETNX key1 value1 key2 value2 ...
```
Example:
```shell
127.0.0.1:6379> MSETNX hobbit2 Samuel hobbit3 Merry hobbit4 Pipin
(integer) 0
127.0.0.1:6379> GET hobbit1
"Frodo"
127.0.0.1:6379> GET hobbit2
"Sam"
127.0.0.1:6379> GET hobbit3
(nil)
127.0.0.1:6379> GET hobbit4
(nil)
127.0.0.1:6379> MSETNX hobbit3 Merry hobbit4 Pipin
(integer) 1
127.0.0.1:6379> GET hobbit1
"Frodo"
127.0.0.1:6379> GET hobbit2
"Sam"
127.0.0.1:6379> GET hobbit3
"Merry"
127.0.0.1:6379> GET hobbit4
"Pipin"
```
- INCR: increment the integer value of a key by 1.

Pattern:
```shell
INCR key
```
Example:
```shell
127.0.0.1:6379> INCR counter
(integer) 1
127.0.0.1:6379> GET counter
"1"
127.0.0.1:6379> INCR counter
(integer) 2
127.0.0.1:6379> GET counter
"2"
```
- INCRBY: increment the integer value of a key by a specified amount.

Pattern:
```shell
INCRBY key increment
```
Example:
```shell
127.0.0.1:6379> INCRBY counter 2
(integer) 4
127.0.0.1:6379> GET counter
"4"
127.0.0.1:6379> INCRBY counter 5
(integer) 9
127.0.0.1:6379> GET counter
"9"
```
- DECR: decrement the integer value of a key by 1.

Pattern:
```shell
DECR key
```
Example:
```shell
127.0.0.1:6379> DECR counter
(integer) 8
127.0.0.1:6379> GET counter
"8"
```
- DECRBY: decrement the integer value of a key by a specified amount.

Pattern:
```shell
DECRBY key decrement
```
Example:
```shell
127.0.0.1:6379> DECRBY counter 2
(integer) 6
127.0.0.1:6379> GET counter
"6"
```
- APPEND: append a value to the end of the string stored at a key.

Pattern:
```shell
APPEND key value
```
Example:
```shell
127.0.0.1:6379> GET name
"Jodel"
127.0.0.1:6379> APPEND name cal
(integer) 8
127.0.0.1:6379> GET name
"Jodelcal"
```
- GETRANGE: get a substring of the string stored at a key.

Pattern:
```shell
GETRANGE key start end
```
Example:
```shell
127.0.0.1:6379> GET name
"Jodelcal"
127.0.0.1:6379> GETRANGE name 1 5
"odelc"
```
- SETBIT: set or clear the bit at a specified offset in the string value stored at a key.

Pattern:
```shell
SETBIT key offset value
```
Example:
```shell
127.0.0.1:6379> GET name
"Jodelcal"
127.0.0.1:6379> SETBIT name 1 0
(integer) 1
127.0.0.1:6379> GET name
"\nodelcal"
```
- GETBIT: get the bit value at a specified offset in the string value stored at a key.

Pattern:
```shell
GETBIT key offset
```
Example:
```shell
127.0.0.1:6379> GETBIT name 1
(integer) 1
```
- BITCOUNT: count the number of set bits (population count) in a string value.

Pattern:
```shell
BITCOUNT key [start end]
```
Example:
```shell
127.0.0.1:6379> BITCOUNT name
(integer) 31
127.0.0.1:6379> BITCOUNT name 5 10
(integer) 11
```
- BITOP: perform bitwise operations (AND, OR, XOR, NOT) between strings and store the result in a destination key.

Pattern:
```shell
BITOP operation destkey key [key ...]
```
Example:
```shell
127.0.0.1:6379> BITOP NOT newkey name
(integer) 8
127.0.0.1:6379> GET newkey
"\xb5\x90\x9b\x9a\x93\x9c\x9e\x93"
```
- BITPOS: find the first bit set or clear in a string value.

Pattern:
```shell
BITPOS key bit [start] [end]
```
Example:
```shell
127.0.0.1:6379> BITPOS name 1
(integer) 1
127.0.0.1:6379> BITPOS name 0
(integer) 0
127.0.0.1:6379> BITPOS name 1 10
(integer) -1
```

### LISTS
A List represents a collection of string elements sorted according to the order of insertion allowing duplicates.

- LPUSH: add one or more elements to the left (head) of a list.

Pattern:
```shell
LPUSH key value [value...]
```
Example:
```shell
127.0.0.1:6379> LPUSH players Durant LeBron Curry
(integer) 3
127.0.0.1:6379> LRANGE players 0 -1
1) "Curry"
2) "LeBron"
3) "Durant"
```
- RPUSH: add one or more elements to the right (tail) of a list.

Pattern:
```shell
RPUSH key value [value...]
```
Example:
```shell
127.0.0.1:6379> RPUSH players Doncic
(integer) 4
127.0.0.1:6379> LRANGE players 0 -1
1) "Curry"
2) "LeBron"
3) "Durant"
4) "Doncic"
```
- LINDEX: get the element at a specific index in the list.

Pattern:
```shell
LINDEX key index
```
Example:
```shell
127.0.0.1:6379> LRANGE players 0 -1
1) "Curry"
2) "LeBron"
3) "Durant"
4) "Doncic"
127.0.0.1:6379> LINDEX players 2
"Durant"
```
- LRANGE: get a range of elements from the list.

Pattern:
```shell
LRANGE key start stop
```
Example:
```shell
127.0.0.1:6379> LRANGE players 0 -1
1) "Curry"
2) "LeBron"
3) "Durant"
4) "Doncic"
127.0.0.1:6379> LRANGE players 0 1
1) "Curry"
2) "LeBron"
```
- LLEN: get the length (number of elements) of a list.

Pattern:
```shell
LLEN key
```
Example:
```shell
127.0.0.1:6379> LLEN players
(integer) 4
```
- LTRIM: trim a list to contain only the specified range of elements.

Pattern:
```shell
LTRIM key start stop
```
Example:
```shell
127.0.0.1:6379> LRANGE players 0 -1
1) "Curry"
2) "LeBron"
3) "Durant"
4) "Doncic"
127.0.0.1:6379> LTRIM players 0 1
OK
127.0.0.1:6379> LRANGE players 0 -1
1) "Curry"
2) "LeBron"
```
- LREM: removes the first count occurrences of elements equal to element from the list stored at key.
  - count > 0: Remove elements equal to element moving from head to tail. 
  - count < 0: Remove elements equal to element moving from tail to head. 
  - count = 0: Remove all elements equal to element.

Pattern:
```shell
LREM key count value
```
Example:
```shell
127.0.0.1:6379> LREM players 0 Curry
(integer) 1
127.0.0.1:6379> LRANGE players 0 -1
1) "Doncic"
2) "Durant"
3) "LeBron"
```
- LPOP: remove and get the first (left) element from a list.

Pattern:
```shell
LPOP key
```
Example:
```shell
127.0.0.1:6379> LRANGE players 0 -1
1) "Doncic"
2) "Durant"
3) "LeBron"
127.0.0.1:6379> LPOP players
"Doncic"
127.0.0.1:6379> LRANGE players 0 -1
1) "Durant"
2) "LeBron"
```
- RPOP: remove and get the last (right) element from a list.

Pattern:
```shell
RPOP key
```
Example:
```shell
127.0.0.1:6379> LRANGE players 0 -1
1) "Durant"
2) "LeBron"
127.0.0.1:6379> RPOP players
"LeBron"
127.0.0.1:6379> LRANGE players 0 -1
1) "Durant"
```
- BLPOP: the blocking version of LPOP. When BLPOP is called, if at least one of the specified keys contains a non-empty list, an element is popped from the head of the list and returned to the caller together with the key it was popped from. If none of the specified keys exist, BLPOP blocks the connection until another client performs an LPUSH or RPUSH operation against one of the keys. Timeout controls the number of seconds the list gets blocked, with timeout = 0 to block indefinitely.

Pattern:
```shell
BLPOP key [key...] timeout
```
Example:
```shell
127.0.0.1:6379> LRANGE players 0 -1
1) "Curry"
2) "Durant"
3) "LeBron"
127.0.0.1:6379> BLPOP players 10
1) "players"
2) "Curry"
127.0.0.1:6379> BLPOP players 10
1) "players"
2) "Durant"
127.0.0.1:6379> LRANGE players 0 -1
1) "LeBron"
```
- BRPOP: the blocking version of RPOP. When BRPOP is called, if at least one of the specified keys contains a non-empty list, an element is popped from the tail of the list and returned to the caller together with the key it was popped from. If none of the specified keys exist, BRPOP blocks the connection until another client performs an LPUSH or RPUSH operation against one of the keys. Timeout controls the number of seconds the list gets blocked, with timeout = 0 to block indefinitely.

Pattern:
```shell
BRPOP key [key ...] timeout
```
Example:
```shell
127.0.0.1:6379> LRANGE players 0 -1
1) "Doncic"
2) "LeBron"
3) "Durant"
127.0.0.1:6379> BRPOP players 5
1) "players"
2) "Durant"
127.0.0.1:6379> LRANGE players 0 -1
1) "Doncic"
2) "LeBron"
```
- LINSERT: insert an element before or after another element (a.k.a. pivot) in a list.

Pattern:
```shell
LINSERT key BEFORE|AFTER pivot value
```
Example:
```shell
127.0.0.1:6379> LRANGE players 0 -1
1) "Doncic"
2) "LeBron"
127.0.0.1:6379> LINSERT players AFTER Doncic Durant
(integer) 3
127.0.0.1:6379> LRANGE players 0 -1
1) "Doncic"
2) "Durant"
3) "LeBron"
```
- LSET: set the value of an element in a list by its index.

Pattern:
```shell
LSET key index value
```
Example:
```shell
127.0.0.1:6379> LRANGE players 0 -1
1) "Doncic"
2) "Durant"
3) "LeBron"
127.0.0.1:6379> LSET players 0 Curry
OK
127.0.0.1:6379> LRANGE players 0 -1
1) "Curry"
2) "Durant"
3) "LeBron"
```
- LTRIM: trim a list to contain only the specified range of elements.

Pattern:
```shell
LTRIM key start end
```
Example:
```shell
127.0.0.1:6379> LRANGE players 0 -1
1) "Curry"
2) "Durant"
3) "Doncic"
4) "LeBron"
127.0.0.1:6379> LTRIM players 0 1
OK
127.0.0.1:6379> LRANGE players 0 -1
1) "Curry"
2) "Durant"
```
- RPOPLPUSH: remove the last element from one list and push it onto another.

Pattern:
```shell
RPOPLPUSH source destination
```
Example:
```shell
127.0.0.1:6379> LRANGE players 0 -1
1) "Curry"
2) "Durant"
127.0.0.1:6379> RPOPLPUSH players players2
"Durant"
127.0.0.1:6379> LRANGE players2 0 -1
1) "Durant"
127.0.0.1:6379> LRANGE players 0 -1
1) "Curry"
```

### SETS
A Set is a collection of unique and unsorted string elements. Unlike List, Set doesn't allow duplicity and doesn't preserve order.

- SADD: add one or more members to a set.

Pattern:
```shell
SADD key member [member...]
```
Example:
```shell
127.0.0.1:6379> SADD splayers Curry Curry Lebron Briant
(integer) 3
127.0.0.1:6379> SMEMBERS splayers
1) "Briant"
2) "Lebron"
3) "Curry"
```
- SMEMBERS: get all members of a set.

Pattern:
```shell
SMEMBERS key
```
Example:
```shell
127.0.0.1:6379> SADD splayers Curry Curry Lebron Briant
(integer) 3
127.0.0.1:6379> SMEMBERS splayers
1) "Briant"
2) "Lebron"
3) "Curry"
```
- SCARD: get the number of members in a set.

Pattern:
```shell
SCARD key
```
Example:
```shell
127.0.0.1:6379> SCARD splayers
(integer) 3
```
- SISMEMBER: check if a member is present in a set.

Pattern:
```shell
SISMEMBER key member
```
Example:
```shell
127.0.0.1:6379> SISMEMBER splayers Curry
(integer) 1
127.0.0.1:6379> SISMEMBER splayers Doncic
(integer) 0
```
- SREM: remove one or more members from a set.

Pattern:
```shell
SREM key member
```
Example:
```shell
127.0.0.1:6379> SREM splayers Curry
(integer) 1
127.0.0.1:6379> SMEMBERS splayers
1) "Briant"
2) "Lebron"
```
- SUNION: perform set unions between multiple sets.

Pattern:
```shell
SUNION key [key...]
```
- SINTER: perform set intersections between multiple sets.

Pattern:
```shell
SINTER key [key...]
```
- SDIFF: perform set differences between multiple sets.

Pattern:
```shell
SDIFF key [key...]
```
- SUNIONSTORE: it does the same as SUNION, but stores the result in another set.

Pattern:
```shell
SUNIONSTORE destination key [key...]
```
- SINTERSTORE: it does the same as SINTER, but stores the result in another set.

Pattern:
```shell
SINTERSTORE destination key [key...]
```
- SDIFFSTORE: it does the same as SDIFF, but stores the result in another set.

Pattern:
```shell
SDIFFSTORE destination key [key...]
```
- SRANDMEMBER: get one ore more random elements from a set. If no count defined, only 1 element will be returned.

Pattern:
```shell
SRANDMEMBER key [count]
```
Example:
```shell
127.0.0.1:6379> SRANDMEMBER splayers
"Lebron"
127.0.0.1:6379> SRANDMEMBER splayers 1
1) "Briant"
127.0.0.1:6379> SRANDMEMBER splayers 2
1) "Briant"
2) "Lebron"
```
- SPOP: remove and return one or more random members from a set.

Pattern:
```shell
SPOP key [count]
```
Example:
```shell
127.0.0.1:6379> SPOP splayers
"Lebron"
127.0.0.1:6379> SMEMBERS splayers
1) "Briant"
```

### SORTED SETS
Sorted sets in Redis are a powerful data structure that combines the features of sets and sorted lists. They allow you to store a collection of unique elements while assigning a score or rank to each element. This score is used to determine the order of elements in the set, making sorted sets an excellent choice for applications that require ordered data.

- ZADD: add one or more members to a sorted set with their scores. If a specified member is already a member of the sorted set, the score is updated and the element reinserted at the right position to ensure the correct ordering. If key does not exist, a new sorted set with the specified members as sole members is created, like if the sorted set was empty. If the key exists but does not hold a sorted set, an error is returned.  The score values should be the string representation of a double precision floating point number. +inf and -inf values are valid values as well. This command has a few options:
  - XX: Only update elements that already exist. Don't add new elements. 
  - NX: Only add new elements. Don't update already existing elements. The GT, LT and NX options are mutually exclusive. 
  - LT: Only update existing elements if the new score is less than the current score. This flag doesn't prevent adding new elements. 
  - GT: Only update existing elements if the new score is greater than the current score. This flag doesn't prevent adding new elements. 
  - CH: Modify the return value from the number of new elements added, to the total number of elements changed (CH is an abbreviation of changed). Changed elements are new elements added and elements already existing for which the score was updated. So elements specified in the command line having the same score as they had in the past are not counted. Note: normally the return value of ZADD only counts the number of new elements added. 
  - INCR: When this option is specified ZADD acts like ZINCRBY. Only one score-element pair can be specified in this mode.

Pattern:
```shell
ZADD key [NX|LT|GT] [XX] [CH] [INCR] score_member [score_member...]
```
Example:
```shell
127.0.0.1:6379> ZADD leaderboard 100 "Player1" 200 "Player2"
(integer) 2
```
- ZRANGE: get a range of members in ascending order.

Pattern:
```shell
ZRANGE key start end [WITHSCORES]
```
Example:
```shell
127.0.0.1:6379> ZRANGE leaderboard 0 -1
1) "Player1"
2) "Player2"
```
- ZREVRANGE: get a range of members in descending order.

Pattern:
```shell
ZREVRANGE key start end [WITHSCORES]
```
Example:
```shell
127.0.0.1:6379> ZREVRANGE leaderboard 0 -1
1) "Player2"
2) "Player1"
```
- ZRANK: get the rank of a member in ascending order.

Pattern:
```shell
ZRANK key member
```
Example:
```shell
127.0.0.1:6379> ZRANK leaderboard Player2
(integer) 1
127.0.0.1:6379> ZRANK leaderboard Player1
(integer) 0
```
- ZREVRANK: get the rank of a member in descending order.

Pattern:
```shell
ZREVRANK key member
```
Example:
```shell
127.0.0.1:6379> ZREVRANK leaderboard Player1
(integer) 1
127.0.0.1:6379> ZREVRANK leaderboard Player2
(integer) 0
```
- ZSCORE: get the score of a member in a sorted set.

Pattern:
```shell
ZSCORE key member
```
Example:
```shell
127.0.0.1:6379> ZSCORE leaderboard Player2
"200"
```
- ZCOUNT: count the number of members in a score range.

Pattern:
```shell
ZCOUNT key min max
```
Example:
```shell
127.0.0.1:6379> ZCOUNT leaderboard 99 201
(integer) 2
127.0.0.1:6379> ZCOUNT leaderboard 101 201
(integer) 1
```
- ZRANGEBYSCORE: get members within a score range in ascending order.

Pattern:
```shell
ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT offset count]
```
Example:
```shell
127.0.0.1:6379> ZRANGEBYSCORE leaderboard 0 300
1) "Player1"
2) "Player2"
```
- ZREVRANGEBYSCORE: get members within a score range in descending order.

Pattern:
```shell
ZREVRANGEBYSCORE key min max [WITHSCORES] [LIMIT offset count]
```
Example:
```shell
127.0.0.1:6379> ZREVRANGEBYSCORE leaderboard 0 300
(empty list or set)
127.0.0.1:6379> ZREVRANGEBYSCORE leaderboard 300 0
1) "Player2"
2) "Player1"
```
- ZUNIONSTORE: perform set unions on multiple sorted sets and store the result in a new set.

Pattern:
```shell
ZUNIONSTORE destination numkeys key [key...] [WEIGHTS weight [weight ...]] [AGGREGATE <SUM | MIN | MAX>]
```

- ZINTERSTORE: perform set intersections on multiple sorted sets and store the result in a new set.

Pattern:
```shell
ZINTERSTORE destination numkeys key [key...] [WEIGHTS weight [weight ...]] [AGGREGATE <SUM | MIN | MAX>]
```

### HASHES
Redis Hashes provide an efficient way to store and manage key-value pairs within a single Redis key. Hashes are particularly useful when dealing with structured data, such as user profiles or settings. They basically allow to store field-value objects whose id is the key.

- HSET: set the value of a hash field.

Pattern:
```shell
HSET myhash field1 value1
```
Example:
```shell
127.0.0.1:6379> HSET myhash name Cirdan
(integer) 1
```
- HMSET: set multiple hash fields and values.

Pattern:
```shell
HMSET myhash field1 value1 field2 value2
```
Example:
```shell
127.0.0.1:6379> HSET myhash2 name Cirdan age 22
(integer) 2
```
- HGET: get the value of a hash field.

Pattern:
```shell
HGET myhash field1
```
Example:
```shell
127.0.0.1:6379> HGET myhash name
"Cirdan"
```
- HMGET: get the values of multiple hash fields.

Pattern:
```shell
HMGET myhash field1 field2
```
Example:
```shell
127.0.0.1:6379> HMGET myhash2 name age nonexisting
1) "Cirdan"
2) "22"
3) (nil)
```
- HEXISTS: check if a hash field exists.

Pattern:
```shell
HEXISTS myhash field1
```
Example:
```shell
127.0.0.1:6379> HEXISTS myhash2 name
(integer) 1
127.0.0.1:6379> HEXISTS myhash2 other
(integer) 0
```
- HSET: set the value of a hash field (also used for updating).

Pattern:
```shell
HSET myhash field1 new_value
```
Example:
```shell
27.0.0.1:6379> HSET myhash2 age 25
(integer) 0
127.0.0.1:6379> HMGET myhash2 name age
1) "Cirdan"
2) "25"
```
- HDEL: delete one or more hash fields.

Pattern:
```shell
HDEL myhash field1 field2
```
Example:
```shell
127.0.0.1:6379> HMGET myhash name
1) "Cirdan"
127.0.0.1:6379> HDEL myhash name
(integer) 1
127.0.0.1:6379> HMGET myhash name
1) (nil)
```
- HLEN: get the number of fields in a hash.

Pattern:
```shell
HLEN myhash
```
Example:
```shell
127.0.0.1:6379> HLEN myhash2
(integer) 2
```
- HKEYS: get all field names in a hash.

Pattern:
```shell
HKEYS myhash
```
Example:
```shell
127.0.0.1:6379> HKEYS myhash2
1) "name"
2) "age"
```
- HVALS: get all values in a hash.

Pattern:
```shell
HVALS myhash
```
Example:
```shell
127.0.0.1:6379> HVALS myhash2
1) "Cirdan"
2) "25"
```
- HINCRBY: increment the integer value of a hash field by a specified amount.

Pattern:
```shell
HINCRBY myhash field1 10
```
Example:
```shell
127.0.0.1:6379> HVALS myhash2
1) "Cirdan"
2) "25"
127.0.0.1:6379> HINCRBY myhash2 age 7
(integer) 32
127.0.0.1:6379> HVALS myhash2
1) "Cirdan"
2) "32"
```

### GEOSPATIAL
Redis Geospatial data capabilities allow you to manage and query data with geographical information, making it ideal for location-based services, geofencing, and more.

- GEOADD: you can add one or more members with their longitude and latitude.

Pattern:
```shell
GEOADD key longitude latitude member [longitude latitude member]
```
Examples:
```shell
127.0.0.1:6379> GEOADD locations -73.99171 40.738868 "Times Square" -118.243683 34.052235 "Los Angeles"
(integer) 2
```
- GEODIST: calculate the distance between two members.

Pattern:
```shell
GEODIST key member1 member2 [m|km|ft|mi]
```
Examples:
```shell
127.0.0.1:6379> GEODIST locations "Times Square" "Los Angeles" m
"3937870.1010"
```
- GEORADIUS: retrieve members within a specified radius of a given location.

Pattern:
```shell
GEORADIUS key longitude latitude radious m|km|ft|mi [WITHCOORD] [WITHDIST] [WITHHASH] [COUNT count]
```
Examples:
```shell
127.0.0.1:6379> GEORADIUS locations -73.985130 40.758896 10 km
1) "Times Square"
```
- GEORADIUSBYMEMBER: retrieve members within a specified radius of a given location.

Pattern:
```shell
GEORAGEORADIUSBYMEMBERDIUS key longitude latitude radious m|km|ft|mi [WITHCOORD] [WITHDIST] [WITHHASH] [COUNT count]
```
- GEOHASH: get the Geohash representation of a member's location.

Pattern:
```shell
GEOHASH key member [member...]
```
Examples:
```shell
127.0.0.1:6379> GEOHASH locations "Times Square"
1) "dr5ru22ch50"
```

## CONFIGURATIONS

### TRANSACTIONS
Redis transactions allow you to group multiple commands into a single atomic operation, ensuring that all commands either execute completely or not at all. This helps maintain data integrity in complex operations.

To start a transaction, use the MULTI command. This indicates the beginning of a transaction block.
```shell
MULTI
```

Within a transaction block, you can queue multiple commands. These commands are not executed immediately; instead, they are queued for execution once you issue the EXEC command.
```shell
MULTI
SET key1 "value1"
SET key2 "value2"
```

When you queue commands within a transaction, Redis responds with QUEUED for each command to indicate that the command has been successfully queued.

To execute the queued commands within a transaction, use the EXEC command. Redis will execute the commands in the order they were queued.
```shell
EXEC
```

If you decide not to execute a transaction, you can discard it using the DISCARD command.
```shell
DISCARD
```

Redis transactions follow an "all-or-nothing" principle. If any command within a transaction fails, the entire transaction is rolled back, and none of the commands are executed. For example, if a command within a transaction block fails:
```shell
MULTI
SET key1 "value1"
INCR key2  # This command will fail since key2 does not exist
EXEC
```

The result of EXEC will be a list containing a single nil value, indicating that the entire transaction failed.

### PUBLISHER - SUBSCRIBER
Redis Pub/Sub provides a simple yet effective way to implement real-time communication between different components of an application or between different applications altogether.

#### Publishing messages
To publish messages to a channel, connect to the Redis server and use the PUBLISH command.
```shell
PUBLISH channel_name message
```

#### Subscribing
```shell
SUBSCRIBE channel [channel...]
```

The Redis Pub/Sub implementation supports pattern matching. Clients may subscribe to glob-style patterns to receive all the messages sent to channel names matching a given pattern.
```shell
PSUBSCRIBE pattern [pattern...]
```

#### Unsubscribing
```shell
UNSUBSCRIBE channel [channel...]
```

The Redis Pub/Sub implementation supports pattern matching. Clients may unsubscribe from glob-style patterns to stop receiving all the messages sent to channel names matching a given pattern.
```shell
PUNSUBSCRIBE pattern [pattern...]
```

#### Scoping
Pub/Sub has no relation to the key space. It was made to not interfere with it on any level, including database numbers.  Publishing on db 10, will be heard by a subscriber on db 1. If you need scoping of some kind, prefix the channels with the name of the environment (test, staging, production...).

#### Count
The meaning of the subscription count with pattern matching in subscribe, unsubscribe, psubscribe and punsubscribe message types, the last argument is the count of subscriptions still active. This number is the total number of channels and patterns the client is still subscribed to. So the client will exit the Pub/Sub state only when this count drops to zero as a result of unsubscribing from all the channels and patterns.

### SCRIPTS
Redis scripting allows you to define and execute custom Lua scripts on the Redis server. This provides a way to combine multiple commands into a single atomic operation, reducing network round trips and enabling complex operations.

Redis Lua scripts are written in the [Lua](https://www.lua.org/manual/5.4/) programming language. You can create and store scripts directly on the Redis server using the SCRIPT LOAD command.

#### Scripting benefits
- Atomicity: redis Lua scripts are atomic, ensuring that the script's execution is isolated from other operations. This guarantees consistent and reliable behavior, especially in scenarios where multiple commands need to be executed together.
- Reduced Network Traffic: by executing a single Lua script on the server, you can reduce the network traffic between the client and the server, leading to better performance and lower latency.
- Complex Operations: lua scripting allows you to perform complex operations that may not be achievable with a single Redis command. This can include conditional logic, iteration, and more.

#### Script caching
After loading a script with SCRIPT LOAD, you receive a SHA-1 hash representing the script. This hash can be used to execute the script multiple times without sending the entire script content, thus improving performance.

#### Using scripts:
- SCRIPT LOAD: load a new script.

Pattern:
```shell
SCRIPT LOAD script
```
Example:
```shell
127.0.0.1:6379> SCRIPT LOAD "redis.call('set', KEYS[1], ARGV[1]); return redis.call('get', KEYS[1])"
"5afd00504d9d21a8fc37cd1b4400872d2e69296a"
```
- EVALSHA: executes the script.

Pattern:
```shell
EVALSHA hash
```
Example:
```shell
127.0.0.1:6379> EVALSHA 5afd00504d9d21a8fc37cd1b4400872d2e69296a 1 example_key "Another example value"
"Another example value"
127.0.0.1:6379> get example_key
"Another example value"
```
- SCRIPT EXISTS: lets you verify whether a script with a given SHA1 identifier exists.

Pattern:
```shell
SCRIPT EXISTS hash
```
Example:
```shell
127.0.0.1:6379> SCRIPT EXISTS 5afd00504d9d21a8fc37cd1b4400872d2e69296a
1) (integer) 1
```
- SCRIPT DEBUG: can be used to enable/disable debugging mode for Lua scripts. When debugging is enabled, EVAL commands use the Lua debugger built into Redis. The debugging mode comes with two options. It can be run asynchronously using SCRIPT DEBUG YES. This has debugging operate on a separate session where it does not block Redis operations. With this option, changes are rolled back when the script finishes. Alternatively, debugging mode can be run synchronously using SCRIPT DEBUG SYNC

Pattern:
```shell
SCRIPT DEBUG YES|NO
```
Example:
```shell
127.0.0.1:6379> SCRIPT DEBUG YES
OK
127.0.0.1:6379> EVAL "redis.call('set', KEYS[1], ARGV[1])" 1 another_example_key "A value"
* Stopped at 1, stop reason = step over
-> 1   redis.call('set', KEYS[1], ARGV[1])
```
- SCRIPT KILL: provides the only way to interrupt a running script, other than restarting the server.

Pattern:
```shell
SCRIPT KILL
```
- SCRIPT FLUSH:

- Pattern: clears Redis’ script cache.
```shell
SCRIPT KILL
```

## CONNECTIVITY AND SECURITY
- Basic Redis connectivity: redis-cli -h hostname -p port
- Testing connectivity: PING (Redis replies PONG)
- Authentication: to enable password authentication you just need to use the following instruction:
```shell
requirepass your_password_here
```
- Client authentication: if Redis server requires password, use the following instruction to authenticate:
```shell
AUTH your_password_here
```
- Restrict Redis access: restrict Redis to bind only to necessary network interfaces to limit exposure to the public internet. Use the bind directive in the Redis configuration file to specify the IP addresses you want Redis to listen on. For example:
```shell
bind 127.0.0.1
```