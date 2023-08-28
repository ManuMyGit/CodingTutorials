# INTRODUCTION
MongoDB is a NoSQL database that stores data in flexible, JSON-like documents. It removes the concept of "rows" of conventional databases introducing the concept of documents. It's designed for ease of development and scalability, making it suitable for a wide range of applications.

Advantages of using MongoDB are:
- Flexible Schema: No fixed schema, allowing easy adaptation to evolve data structures. 
- High Performance: Able to handle large amounts of data and high transaction rates. 
- Scaling: Supports horizontal scaling through sharding for distributed data storage. 
- Rich Query Language: Supports complex queries and aggregation pipelines. Document-Oriented: Data can be stored as self-contained documents, mimicking real-world entities.
- Automatic Failover: Provides fault tolerance through replica sets.

# EXPLORING DATA MODEL AND DOCUMENTS
## MongoDB Document Structure
Documents in MongoDB are JSON-like objects stored in collections. Each document can have a different structure, making it flexible for storing heterogeneous data.

## BSON: Binary JSON
It is important to have an understanding of how MongoDB stores data. MongoDB and many other document-based NoSQL databases use JSON (JavaScript Object Notation) to represent data records as documents.

There are many advantages to using JSON to store data. Some of them being:
- Easiness to read, learn, and its familiarity among developers.
- Flexibility in format, whether sparse, hierarchical, or deeply nested.
- Self-describing, which allows for applications to easily operate with JSON data.
- Allows for the focus on a minimal number of basic types.

JSON supports all the basic data types like string, number, boolean, etc. MongoDB actually stores data records as Binary-encoded JSON (BSON) documents. Like JSON, BSON supports the embedding of documents and arrays within other documents and arrays. BSON allows for additional data types that are not available to JSON.

```json
{
   field1: value1,
   field2: value2,
   field3: value3,
   ...
   fieldN: valueN
}
```

## Data Types and Schemas
MongoDB supports various data types, including strings, numbers, arrays, objects, dates, and more. While MongoDB doesn't enforce strict schemas, having a consistent structure can be beneficial.
- Text
  - String. `{first_name: "Turgon"}`
- Numeric
  - 32-Bit Integer. `{{age: 26}}`
    - Applicable signed range: -2,147,483,648 to 2,147,483,647
    - Applicable unsigned range: 0 to 4,294,967,295
  - 64-Bit Integer
    - Applicable signed range: -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807
    - Applicable unsigned range: 0 to 18,446,744,073,709,551,615
  - Double. `{testScore: 89.6}`
  - Decimal128. `{price : NumberDecimal("5.099")}`
    - This type is used to work with very large numbers with lots of floating point range.
- Date/Time
  - Date.
    - There are three methods to return dates:
      - Date(). `'Tue Sep 28 2021 11:28:52 GMT+0200 (Central European Summer Time)'`
      - new Date(). `ISODate("2021-09-28T09:29:01.924Z")`
      - ISODate(). `ISODate("2021-09-28T09:29:12.151Z")`
  - Timestamp. `{ts: new Timestamp()}`
- Other
  - Object
    - The Object data type in MongoDB is used for storing embedded documents. An embedded document is a series of nested documents in key: value pair format.
```json
{
  "Physics": 88,
  "German": 92,
  "student": {
    "first_name": "Dior",
    "last_name": "Eluchil"
  }
}
```
  - Array
  - Binary Data. `{binaryData: BinData(1, "111010110111100110100010101")})`
    - The Binary data, or BinData, data type does exactly what its name implies and stores binary data for a field's value. BinData is best used when you are storing and searching data, because of its efficiency in representing bit arrays.
  - ObjectId
    - The ObjectId type is specific to MongoDB and it stores the document's unique ID. MongoDB provides an _id field for every document. ObjectId consists of three parts that make up its 12-byte makeup:
      1. 4-byte timestamp value, representing the ObjectId's creation, measured in seconds since the Unix epoch 
      2. 5-byte random value 
      3. 3-byte incrementing counter initialized to a random value
    - In mongosh (MongoDB shell), the creation time of the ObjectId is accessible using the ObjectId.getTimestamp() method. Sorting on an _id field that stores ObjectId data types is a close equivalent to sorting by creation time.
  - Boolean. `{isCorrect: true, isIncorrect: false}`
  - Null
  - Regular Expression. `{exampleregext:/t+/}`
    - The Regular Expression data type in MongoDB allows for the storage of regular expressions as the value of a field. MongoDB uses PCRE (Perl Compatible Regular Expression) as its regular expression language.
  - JavaScript. `{jsCode: "function(){var x; x=1}"}`
    - BSON allows for MongoDB to store JavaScript functions without scope as their own type.
  - Min Key
  - Max Key

## Data modeling
When modeling the data:
1. Choose between embedding related data in a single document or referencing it in separate documents.
2. Design relationships using embedding, referencing, or a combination of both.
3. Consider query patterns, data growth, and scalability when designing your schema.

# DATABASE OPERATIONS
## General operations
- `show dbs`: show the list of available databases.
- `use databaseName`: switches to that database. If the database is not available, it will be created. However, a database will not be created until a document has been created.
- `db.stats()`: get some basic information about your current database.
- `db`: check your current database.
- `show collections`: show all collections from the current database.
- `db.getCollectionNames()`: show all collections from the current database.
- `db.getCollectionInfos()`: show additional information about the collections in the current database.
- `db.<collection>.count()`: check how many documents a collection contains.
  - `db.system.users.count()`: checks how many documents are in the system.users collection.
- `db.printCollectionStats()`: view basic statistics about the collections in the current database.

## Create
To create a new collection, there are two options: you can create collections either implicitly or explicitly.

As with databases, MongoDB can automatically create collections the first time a document is written to them. This method tells MongoDB to create a new collection by inserting a document into a collection that does not exist yet. The following example will create a collection in the playground database:
```
use playground
db.equipment.insert({name: "slide"})
```

The other option to use to create collections is to explicitly use the `db.createCollection()` method. This allows you to create collections without adding any documents to them. For example, you can create a new collection in the playground database called maintenance.requests by typing:
```
db.createCollection("maintenance")
```

The db.createCollection() method is primarily useful because it allows you to specify various options upon creation. For example, we may want to create a create a capped collection, which is a collection that maintains an upper limit on its allocated size it stores by deleting its oldest document when full.
```
db.createCollection(
    "notifications",
    {
        capped: true,
        size: 10240
    }
)
```

To create a document:
- Use the insertOne(<document>, options) method to insert one document. 
- Use the insertMany([<document1>, <document2>, ...], options) method to insert more than one document.

If the database doesn't exist, insert operations will create it.

## Read
- `db.collectionName.find()`: basic read command to return all data from a collection.
- `db.collectionName.find().pretty()`: same as above, but making the output more readable.
- `db.collectionName.findOne()`: return the 1st element which matches the filters.

### Filtering data
The `db.collectionName.find()` command can be used with arguments to filter the results.
Filter by regex, where the options are:
- i: case insensitivity.
- m: for patterns that include anchors (i.e. ^ for the start, $ for the end), match at the beginning or end of each line for strings with multiline values. Without this option, these anchors match at beginning or end of the string
- x: "extended" capability to ignore all white space characters in the $regex pattern unless escaped or included in a character class.
- s: allows the dot character (i.e. .) to match all characters including newline characters. For an example, see
The special characters for the regex are:
- The ^ symbol can be used for exact text searches, to make sure that the string starts with a certain character.
- The $ symbol can be used for exact text searches, to make sure that the string ends with a certain character.
- The // is equivalent to SQL %.
```
db.collection.find({value:{'$regex' : 'hello', '$options' : 'i'}}) //Contains hello case insensitive, SQL equivalent of %hello%
db.collection.find({name:{'$regex' : '^hello$', '$options' : 'i'}}) //Exact value case insensitive
db.collection.find({name:{'$regex' : '^hello', '$options' : 'i'}}) //Starts with hello case insensitive
db.collection.find({name:{'$regex' : 'hello$', '$options' : 'i'}}) //Ends with hello case insensitive
```
Filter by regex without the regex operator:
```
db.employee.find({name: /te/}).pretty() //Contains te in the name
```
Filter by equality:
```
db.student.find(
  {
_id : ObjectId("60e8792d4655cbf49ff7cb89")
}
  )
```
```
db.students.find({first_name: "Brian", grade_level: 3})
```
Filter by equality using the comparison operator:
```
db.students.find({
    first_name: { $eq: "Brian" }
})
```
Filter by inequality using the comparison operator:
```
db.students.find({
    grade_level: { $ne: null }
})
```
Filter by greater than using the comparison operator:
```
db.students.find({
    grade_level: { $gt: 6 }
})
```
Filter by greater than or equal to using the comparison operator:
```
db.students.find({
    grade_level: { $gte: 6 }
})
```
Filter by less than using the comparison operator:
```
db.students.find({
    dob: { $lt: new Date("January 1, 2010") }
})
```
Filter by less than or equal to using the comparison operator:
```
db.students.find({
    grade_level: { $lte: 6 }
})
```
Filter to match any of a group of values (in this case each value is a regex for all names ending in i or e):
```
db.students.find({
    first_name: {
        $in: [
            /i$/,
            /e$/
        ]
    }
})
```
Filter to match none of a group of values (in this case each value is a regex for all names ending in i or e):
```
db.students.find({
    first_name: {
        $nin: [
            /i$/,
            /e$/
        ]
    }
})
```
Logical AND operator:
```
db.students.find({
    $and: [
        { dob: { $ne: null } },
        { grade_level: { $ne: null } }
    ]
})
```
Logical OR operator:
```
db.students.find({
    $or: [
        { dob: { $eq: null } },
        { grade_level: { $eq: null } }
    ]
})
```
Logical NOT operator:
```
db.students.find({
    dob: {
        $not: { 
            $lt: new Date("January 1, 2010")
        }
    }
})
```
Logical NOR operator:
```
db.students.find({
    $nor: [
        { grade_level: 6 },
        { last_name: /s$/ }
    ]
})
```
Filtering on existence (to check for the existence of a field):
```
db.students.find({
    grade_level: { $exists: true }
})
```
Filtering on type (to check for the type of a field):
```
db.dates.find({
    date: { $type: "date" },
}).pretty()
db.dates.find({
    date: { $type: "string" },
}).pretty()
```
Filtering based on array characteristics to specify required elements:
```
db.teachers.find({
    subjects: {
        $all: [ "composition", "grammar" ]
    }
})
```
Filtering based on array characteristics to specify at least one required elements:
```
db.teachers.find({
    subjects: {
        $elemMatch: {
            $gt: "literature",
            $lt: "vocabulary"
        }
    }
})
```
Filtering based on array characteristics to specify array size:
```
db.teachers.find({
    subjects: { $size: 3 }
})
```
### Mapping data
A function can be used to map the result of a query to produce a mapped outcome. For instance, the following code will add two more values to the outcome:
```
db.dates.find().map(
    function(date_doc) {
        date_doc["is_a_Date_object"] = date_doc.date instanceof Date;
        date_doc["date_storage_value"] = date_doc.date.valueOf();
        return date_doc;
    }
)
```

### Sorting data
We use the `sort()` function to order the results.
Example of basic sorting data (1 for ascending order, -1 for descending order):
```
db.students.find().sort({
    dob: 1,
    last_name: 1
}).pretty()
```

To sort using embedded documents fields:
```
db.students.find().sort({
    "address.city": 1
}).pretty()
```

## Aggregations
Aggregation is a way of processing a large number of documents in a collection by means of passing them through different stages. The stages make up what is known as a pipeline. The stages in a pipeline can filter, sort, group, reshape and modify documents that pass through the pipeline.

There are what are called single purpose methods like estimatedDocumentCount(),  count(), and distinct() which are appended to a find() query making them quick to use but limited in scope.

How does the MongoDB aggregation pipeline work? Once the input of the query is ready, it goes to the next steps before becoming available:
1. $match stage: filters those documents we need to work with, those that fit our needs.
2. $group stage: does the aggregation job.
3. $sort stage: sorts the resulting documents the way we require (ascending or descending).

The aggregate syntax is (being the pipeline the 1st argument array):
```
db.collectionName.aggregate([
        { $match : { … } },
        { $project : { … } },
        { $group : { … } },
        { $out: … }, 
        { $sort : { … } },
        { $limit: … },
        { $addFields : { … } },
        ], { allowDiskUse : true })
```
- $match: same filters as find() instruction available.
- $project: see the below.
- $group: here we can perform all the aggregation or summary queries that we need, such as finding counts, totals, averages or maximums.
  - $count: calculates the quantity of documents in the given group. 
  - $max: displays the maximum value of a document’s field in the collection. 
  - $min: displays the minimum value of a document’s field in the collection. 
  - $avg: displays the average value of a document’s field in the collection. 
  - $sum: sums up the specified values of all documents in the collection. 
  - $push: adds extra values into the array of the resulting document.
- $out: This is an unusual type of stage because it allows you to carry the results of your aggregation over into a new collection, or into an existing one after dropping it, or even adding them to the existing documents
- $sort: you need the $sort stage to sort your results by the value of a specific field.
- $limit: used to limit the records returned by the result.
- $addFields: it is possible that you need to make some changes to your output in the way of new fields.

```
db.cities.aggregate([
    {
        $match: {
            "continent": { $in: ["North America", "Asia"] }
        }
    },
    {
        $sort: { "population": -1 }
    },
    {
        $group: {
            "_id": {
                "continent": "$continent",
                "country": "$country"
            },
            "first_city": { $first: "$name" },
            "highest_population": { $max: "$population" }
        }
    },
    {
        $match: {
            "highest_population": { $gt: 20.0 }
        }
    },
    {
        $sort: { "highest_population": -1 }
    },
    {
        $project: {
            "_id": 0,
            "location": {
                "country": "$_id.country",
                "continent": "$_id.continent",
            },
            "most_populated_city": {
                "name": "$first_city",
                "population": "$highest_population"
            }
        }
    }
])
```

### Projections
MongoDB Projection is a special feature allowing you to select only the necessary data rather than selecting the whole set of data from the document. For Example, If a Document contains 10 fields and only 5 fields are to be shown the same can be achieved using the Projections. The general sintaxys is `db.DATA_COLLECTION_NAME.find({}, {YOUR_FIELD_KEY: BOOLEAN})`. See the next example, where we're filtering to return all documents whose status is A, but only returning the status and the item, but not the id.
```
db.inventory.find({status: "A"}, {item: 1, status: 1, _id: 0}).pretty()
```

#### $ Operator
This operator is utilized in scenarios where there is a need to limit an array to project only the first elements that matches the condition of the query. In the following example, only 1 element is returned in the grades array:

Syntax: `db.collection.find({}, {"<array>.$": numberOfElements})`

Example:
```
db.students.find({semester: 1, grades: {$gte: 85}}, {"grades.$": 1})
```

Limitations of the $ operator:
- In a single MongoDB projection query, only a single $ operator can be used. 
- It will only be applied to a single condition on the array field 
- The sort() function in the find() is applied before the $ operator and this function may cause the sort order to get distorted. 
- The $ operator can only be utilized at the end of a field path. This restriction helps to mitigate any formatting issues.

#### $elemMatch Operator
Similar to the $ operator, the $elemMatch operator also limits the contents of an array to the first element that fits the given constraint. Though, there is a minor difference from the $ operator because the $elemMatch projection operator needs an explicit condition argument. Basically this is another way to filter data.

Syntax: `db.collection.find({}, {"<array>.$elemMatch": ($elemMatch operator)})`

Example:
```
db.schools.find({zipcode: "63109"}, {students: {$elemMatch: {school:102}}}) 
```

Limitations of $elemMatch operator are as follows:
- The field to which the $elemMatch projection is applied is returned as the last field of the document, regardless of the order in which the fields are ordered. 
- $elemMatch projection operator is not supported by find() operations on MongoDB views. 
- The $elemMatch operator does not handle $text query expressions.

#### $slice Operator
The $slice operator bounds the number of elements that should be returned as the output of a MongoDB projection query.

Syntax: `db.collection.find({}},{<array Field>:{$slice: <number>}})`

Example:
```
db.posts.find({}, {comments: {$slice: 3}}) 
```

Limitations in $slice operator:
- In a nested array the $slice operator will only return the sliced element and will not return any other item, especially from MongoDB 4.4. 
- The find() action is not supported by the $slice operator on MongoDB views. 
- Due to a constraint imposed by MongoDB, the $slice operator cannot be combined with the $ projection operator. This is because top-level fields are not permitted to contain the $ symbol as part of the field name.

#### $meta Operator
The $meta operator gives us the capability to extract the metadata associated with a document.

Syntax: `{$meta: <metaDataKeyword>}`.

The "metaDataKeyword", represents these values:
- "textScore" Keyword. This keyword effectively finds how precisely a search term is related with the given field in a document. The "textScore" yields the score associated with the provided $text.
- "indexKey" Keyword. Introduced in MongoDB 4.4 as a preferred alternative to the cursor.returnKey() function. For a document that uses a non-text index, this function returns the index key. A key thing to note is that this keyword should not be used in production situations; it should only be used for debugging purposes.

Usage and Limitations of $meta operator in projection:
- The {$meta: "$textScore"} expression is used in a projection query to obtain the text score. The expression can be set up as an inclusion or exclusion in the projection statement. The limitation that it comes with is that if the expression is set to an existing field the projected metadata value will get overwritten by the current value in the field. 
- The $meta expression when paired with sort() function will also sort the "textScore" metadata in descending order. 
- Both the projection and the sort procedures can use a different field name for the expression when $meta: "$textScore" is used. The query system will ignore the sort's field name ().

## Update
To update a document:
```
db.collection.update(
   <query>,
   <update>,
   {
     upsert: <boolean>,
     multi: <boolean>,
     writeConcern: <document>,
     collation: <document>,
     arrayFilters: [ <filterdocument1>, ... ],
     hint:  <document|string>, // Added in MongoDB 4.2
     let: <document> // Added in MongoDB 5.0
   }
)
```

The meaning of the fields are:
- Query: the selection criteria for the update. The same query selectors as in the find() method are available.
- Update: the modifications to apply. Can be one of the following:
  - Update document: contains only update operator expressions.
  - Replacement document: contains only <field1>: <value1> pairs.
- Options:
  - upsert: optional. When true, update() either:
    - Creates a new document if no documents match the query. 
    - Updates a single document that matches the query.
    - If both upsert and multi are true and no documents match the query, the update operation inserts only a single document.
  - multi: optional. If set to true, updates multiple documents that meet the query criteria. If set to false, updates one document. The default value is false.
  - writeConcern: optional. A document expressing the write concern. Omit to use the default write concern w: "majority". Do not explicitly set the write concern for the operation if run in a transaction.
  - collation: optional. Allows users to specify language-specific rules for string comparison, such as rules for lettercase and accent marks.
  - arrayFilters: optional. An array of filter documents that determine which array elements to modify for an update operation on an array field.
  - hint: optional. A document or string that specifies the index to use to support the query predicate.
  - let: optional. Specifies a document with a list of variables. This allows you to improve command readability by separating the variables from the query text.

## Delete
To delete a document:
- To delete multiple documents, use db.collection.deleteMany(<query>). The same query selectors as in the find() method are available.
- To delete a single document, use db.collection.deleteOne(<query>). The same query selectors as in the find() method are available.

To delete a collection, you can use the drop() method on the collection itself.
```
db.collectionName.drop()
```

To delete a database:
```
use databaseName
db.dropDatabase()
```

# OTHER

## Indexes and performance optimization
Much like an encyclopedia, a database is a bountiful store of accessible information. To answer find a specific piece of information in an encyclopedia would require sifting through every page until you come across what you are looking for. This inefficiency is why an encyclopedia has an index which points you to the exact page you need to turn to for the information you are looking for.  A database index similarly points you in the right place for information more efficiently. In MongoDB, an un-indexed query that "searches through the whole book" is called a collection scan.  Indexes can be thought of as shortcuts for accessing your data so that the entire database does not need to be scanned to find what you are looking for. In this article, we are going to introduce indexes in MongoDB, discuss when to use them, and how to manage them.

Keeping with our encyclopedia analogy, one might think to have an index line for every word in the book. If it is always faster to use an index, then this seems beneficial. However, as you can imagine, the more lines of words in an index, the larger the book becomes. At some point, the size of the book needed to accommodate indexing every word becomes ineffective. There are many words such as "the" or "because" that are less useful to search for than "hippopotamus".  This is similar to an index in MongoDB and databases in general. While yes, it is faster to have an index for any data a query might use, there simply is data that doesn't need indexing. Like the size of a book, adding too many indexes to a database also takes up space and has a detrimental effect on write operations on the database if not kept in check.  Indexes are an incredibly useful way to optimize access to specific data that is often used as selection criteria in queries. Knowing when to use them is important, so making sure that you are adding them on database fields that are often being queried will keep your reads efficient without negatively impacting database size and write efficiency.

To create an index (the second parameter specifies the order, ascending = 1 and descending = -1):
```
db.collectionName.createIndex({"field_name": 1})
```
To show indexes:
```
db.collectionName.getIndexes()
```
To drop an index:
```
db.collectionName.dropIndex({"field_name": 1})
```

## Transactions and data consistency
Transactions are a way to group together and isolate multiple statements for processing as a single operation. Instead of executing each command individually as it is sent to the server, in a transaction, commands are bundled together and executed in a separate context than other requests.

Isolation is an important part of transactions. Within a transaction, the executed statements can only affect the environment within the transaction itself. From inside the transaction, statements can modify data and the results are immediately visible. From the outside, no changes are made until the transaction is committed, at which time all of the actions within the transaction become visible at once.

These features help databases achieve ACID compliance by providing atomicity and isolation. These together help the database maintain consistency. Furthermore, changes in transactions are not returned as successful until they are committed to non-volatile storage, which provides durability.

MongoDB has support for multi-document ACID transactions and distributed multi-document ACID transactions. By nature, the document model allows related data to be stored together in a single document. The document model, combined with atomic document updates, takes away the need for transactions in a majority of use cases. However, there are cases where multi-document, multi-collection MongoDB transactions are your best choice.

To create a transaction:
```
var session = db.getMongo().startSession()
session.startTransaction(
    { "readConcern": { "level": "snapshot" },
      "writeConcern": { "w": "majority }
     }
)
```
To eventually commit the transaction:
```
session.commitTransaction()
```
To abort the transaction:
```
session.abortTransaction()
```

## Connectivity via URIs
Connecting to your database server is usually one of the first tasks you need to accomplish when designing and configuring database-backed applications. While there are many methods of providing the address, listening port, credentials, and other details to applications, connection URIs, sometimes called connection strings or connection URLs, are one of the most powerful and flexible ways of specifying complex configuration in a compact format.

### Percent encoding values:
Characters you should percent-encode include:
- `:`: %3A
- `/`: %2F 
- `?`: %3F 
- `#`: %23
- `[`: %5B
- `]`: %5D
- `@`: %40

So if your password is `pe@ce&lo\/3` you'll want to specify it within the connection URI as `pe%40ce&lo\%2F3`.

### URI structure:
```
mongodb://[username:password@]host[:port][,...hostN[:port]][/[database][?parameter_list]]
```
- mongodb://: the schema identifier used to identify the string as a MongoDB connection URI.
- auth credentials: an optional component of the URI that can be used to specify the user and password to connect as. 
  - username: an optional username. If included, it should start after the second slash (/) and continue until a colon (:). Must be accompanied by a password if included. 
  - password: an optional password. If included, it begins after a colon (:) and continues until the at sign (@). Must be accompanied by a username if included.
- host specifier: a required component used to specify one or more hostnames and ports to connect to. 
  - host: an IP address, DNS name, or locally resolvable name of the server to connect to. The host continues until a colon (:) (if a port is included), until a comma (,) if more than one host is specified, or else until a slash (/). At least one host must be provided. 
  - port: an optional port specification to indicate which port MongoDB is listening to on the host. The port begins with a colon (:) and continues until a comma (,) if another host is provided or until the slash (/) if not.
- default authentication database: the name of the database to authenticate to if a more specific authSource is not provided in the parameter list. If no database is specified here or with authSource, MongoDB will attempt to authenticate to the standard admin database.
- parameter list: an optional list of additional parameters that can affect the connection behavior. The parameter list begins with a question mark (?). If no default authentication database is provided, you must begin the parameter list with both the slash and question mark (/?) after the last host definition. 
  - parameter pairs: the parameter list is composed of key-value pairs. The key and value within each pair are separated by an equal sign (=) and each pair is separated from the next by an ampersand (&).

Additional parameters can be found [here](https://www.mongodb.com/docs/manual/reference/connection-string/#connection-string-options). The most common ones are:
- replicaSet: Specifies the name of the replica set, if the mongod is a member of a replica set. Set the replicaSet connection option to ensure consistent behavior across drivers..
- tls: enables or disables TLS/SSL for the connection:
  - true: Initiate the connection with TLS/SSL. Default for SRV Connection Format. 
  - false: Initiate the connection without TLS/SSL. Default for Standard Connection String Format.
- ssl: aboolean to enable or disables TLS/SSL for the connection:
  - true: Initiate the connection with TLS/SSL. Default for SRV Connection Format. 
  - false: Initiate the connection without TLS/SSL. Default for Standard Connection String Format.
- connectTimeoutMS: the time in milliseconds to attempt a connection before timing out. The default is 10,000 milliseconds, but specific drivers might have a different default.
- socketTimeoutMS: the time in milliseconds to attempt a send or receive on a socket before the attempt times out. The default is never to timeout.
- maxPoolSize: the maximum number of connections in the connection pool. The default value is 100.
- minPoolSize: the minimum number of connections in the connection pool. The default value is 0.
- maxIdleTimeMS: the maximum number of milliseconds that a connection can remain idle in the pool before being removed and closed.
- waitQueueTimeoutMS: the maximum time in milliseconds that a thread can wait for a connection to become available.
- w: corresponds to the write concern w Option. Values are:
  - majority: requests acknowledgment that write operations have been durably committed to the calculated majority of the data-bearing voting members (i.e. primary and secondaries with members[n].votes greater than 0). { w: "majority" } is the default write concern for most MongoDB deployments.
  - <number>: requests acknowledgment that the write operation has propagated to the specified number of mongodb instances.
- readConcernLevel: the level of isolation. Can accept one of the following values:
  - local
  - majority
  - linearizable
  - available

## Replication and High Availability
- Replication Concepts: replica sets provide redundancy and high availability by maintaining multiple copies of data. 
- Setting Up a Replica Set: create a replica set with multiple MongoDB instances. 
- Failover and Read Preference: replica sets automatically promote a new primary if the current primary fails. Configure read preferences for distributing read operations.

## Sharding and Horizontal Scaling
- Sharding Principles: sharding distributes data across multiple servers for horizontal scalability.
- Creating a Sharded Cluster: set up a sharded cluster by configuring shard servers and shard key.
- Balancing Data and Managing Chunks: MongoDB automatically balances data distribution among shards.

# SECURITY AND USER AUTHENTICATION
## User management
To create, modify, and delete users within MongoDB and configure authentication, the core methods you need are:
- `db.createUser`: create a new MongoDB user account
```
db.createUser({
    user: "myUser",
    pwd: "myPassword",
    roles: []
})
```
This option will ask the user to type the password:
```
db.createUser({
    user: "myUser",
    pwd: passwordPrompt(),
    roles: []
})
```
- `db.updateUser`: update the details of a user account. The object that you include in the command with the change information can contain many different fields.
  - customData: any arbitrary data to be associated with the user account. 
  - roles: the roles that the user is granted. It is often better to use the db.grantRolesToUser() and db.revokeRolesFromUser() methods to control role membership. 
  - pwd: the user's password. Using the db.ChangeUserPassword() method is usually easier if that is the only field that needs to be updated. 
  - authenticationRestrictions: specifies restrictions for the account that can limit the IP addresses users can connect from or to. The value of this key is an object or array that defines clientSource and or serverAddress, which contain arrays specifying the valid IP addresses or ranges. 
  - mechanisms: the specific authentication mechanisms to be used for credentials. Can be set to either one or both of SCRAM-SHA-1 or SCRAM-SHA-256, but can only be changed to a subset of the current mechanisms if a new password isn't currently being supplied. 
  - passwordDigestor: specifies which component processes the user's password. Can be either server (the default) or client.
```
db.updateUser("myUser", {
    authenticationRestrictions: [ {
        clientSource: ["127.0.0.1", "::1"],
        serverAddress: ["127.0.0.1", "::1"]
    } ]
})
```
- `db.changeUserPassword`: change the password used by a user account.
```
db.changeUserPassword("myUser", "myPassword")
db.changeUserPassword("myUser", passwordPrompt())
```
- `db.dropUser`: delete a MongoDB user account.
```
db.dropUser("myUser")
```
- `db.getUsers`: get users. This command offers several options. like showCredentials and filtering.
```
db.getUsers({
    showCredentials: true,
    showPrivileges: true,
    showAuthenticationRestrictions: true,
    filter: {
        "roles.role": "root"
    }
})
```
To get a specific uer:
```
db.getUser("myUser")
```

Additionally, the following database command is useful for finding information about users on the system:
- `db.runCommand('usersInfo')`: show information about one or more MongoDB user accounts

## Authorization and privileges
Within MongoDB, you'll need an account that has at least the userAdmin role so that role-based authorization policies can be set. Roles that include the userAdmin role, listed from most narrowly focused to the broadest level of privileges are:
- userAdmin 
- dbOwner 
- userAdminAnyDatabase 
- root

Authorization and privilege management in MongoDB is implemented using Role-Based Access Control (RBAC). In this system, different levels of access are associated with individual roles. To give a user permission to perform an action, you grant them membership to a role that has the appropriate privileges.

Privileges can be assigned to the following resources:
- Collection.
- Database.
- Cluster.

Before MongoDB can use authorization to manage user privileges, you must enable the functionality on your server or cluster. To do so, you must log in to your server with root or other administrative privileges. Modify the MongoDB server's configuration by opening the /etc/mongod.conf file in a text editor as an administrator. The MongoDB configuration file uses the YAML serialization format to define the configuration. Uncomment or add a security: section key to the file. Beneath this key, indent a line using spaces (tabs are not permitted in YAML) and set authorization to enabled:
```yaml
...
security:
  authorization: enabled
...
```

## Manage privileges and roles.
- `db.getRoles()`: to view all of the roles available on the system.
```
db.getRoles({
    rolesInfo: 1,
    showPrivileges: true,
    showBuiltinRoles: true
})
```
- `db.getRole()`: to get information about a specific user role.
```
db.getRole(
    "root",
    {
        showPrivileges: true,
        showBuiltinRoles: true
    }
)
```
- `db.grantRolesToUser()`: to specify additional roles that you want to add to a user.
```
db.grantRolesToUser(
    "myUser",
    [
        "read",
        "backup"
    ]
)
```
To assign roles to different databases in the same instruction:
```
db.grantRolesToUser(
    "myUser",
    [
        { role: "read", db: "sales"},
        { role: "readWrite", db: "callLogs"}
    ]
)
```
- `db.revokeRolesFromUser()`: to revoke roles from a user.
```yaml
db.revokeRolesFromUser(
    "myUser",
    [
        "read",
        "backup"
    ]
)
```
To revoke roles from different databases in the same instruction:
```
db.revokeRolesFromUser(
    "sally",
    [
        { role: "read", db: "sales"},
        { role: "readWrite", db: "callLogs"}
    ]
)
```

### Custom roles
There are times when the system's built-in roles do not adequately match up with the types of permissions you need to assign. In these cases, you can create your own custom roles.

The `db.createRole()` method allows you to define a new role that you can assign privileges and other roles to. You can then grant your new role to users to give them the specific privileges you defined. The basic syntax of the db.createRole() method involves passing a document that defines the role's characteristics. The document can have the following fields:
- role: The name you want to give the role.
- privileges: An array containing the set of loose privileges you want to assign to the role. Each privilege is defined in a nested document that defines a resource document (specifying which resources this privilege applies to) as well as an array of actions that are being granted.
- roles: An array of additional roles that this role should inherit from. The new role will acquire all of the privileges granted to any of the roles listed here.
- authenticationRestrictions: An array that specifies any restrictions on authentication for the role. This allows you to deny a role's privileges if the user has not authenticated in a way the role approves of.

The first three fields are required for every new role created. As an example, let's create a role called salesMonitor that provides read-only access to the sales database:
```
db.createRole({
    role: "salesMonitor",
    privileges: [],
    roles: [
        {
            role: "read",
            db: "sales"
        }
    ],
})
```

We could express a similar (but more limited) role using the privileges field instead of the read role by typing:
```
db.createRole({
    role: "salesMonitor",
    privileges: [
        {
            resource: { db: "sales", collection: "" },
            actions: [ "find" ]
        }
    ],
    roles: []
})
```

You can delete unnecessary roles with the `db.dropRole("roleName")` method.


## Actions available in MongoDB
Here is the list of actions available in MongoDB as well as their function:

| Action  | Scope  | Description                                                                                   |
|---------|--------|-----------------------------------------------------------------------------------------------|
|find | database or collection | Allows read operations on a database                                                          |
|insert | database or collection | Allows write operations on a database                                                         |
|remove | database or collection | Allows delete operations on a database                                                        |
|update | database or collection | Allows replacement operations on a database                                                   |
|bypassDocumentValidation | database or collection | Lets the user to ignore data validation policies for documents.                               |
|useUUID | cluster | Lets the user use UUID values to find for documents                                           |
|changeCustomData | database | The user can modify custom data associated with any user in the database                      |
|changeOwnCustomData | database | Allows the user to change the custom data associated with their own user                      |
|changeOwnPassword | database | Lets a user change their own account password                                                 |
|changePassword | database | Lets a user change the password for any user in the database                                  |
|createCollection | database or collection | Allows the user to create collections in the database                                         |
|createIndex | database or collection | Lets the user create indexes for the database                                                 |
|createRole | database | Allows the user to create custom roles in the database                                        |
|createUser | database | Lets the user define new user accounts                                                        |
|dropCollection | database or collection | Allows the user to delete collections                                                         |
|dropRole | database | Lets the user delete roles                                                                    |
|dropUser | database | Lets the user delete users                                                                    |
|enableProfiler | database | Allows the user to enable performance profiling                                               |
|grantRole | database | Lets the user grant roles associated with the database to any user on the system              |
|killCursors | collection | Lets a user kill their own cursors in versions of MongoDB prior to 4.2                        |
|killAnyCursor | collection | Lets a user kill other users' cursors                                                         |
|revokeRole | database | Allows the user to remove roles from any user in the system                                   |
|setAuthenticationRestriction | database | Allows the user to set authentication requirements for users and roles                        |
|unlock | cluster | Lets the user reduce the number of write locks on the cluster                                 |
|viewRole | database | Allows viewing details about roles in the database                                            |
|viewUser | database | Allows viewing details about users in the database                                            |
|authSchemaUpgrade | cluster | Allows user to upgrade the authentication mechanisms between MongoDB versions                 |
|cleanupOrphaned | cluster | Lets the user clean up orphaned documents in MongoDB versions prior to 4.4                    |
|cpuProfiler | cluster | Allows the user to enable CPU profiling                                                       |
|inprog | cluster | Lets the user view information on other users' in progress or queued operations               |
|invalidateUserCache | cluster | Lets the user manually flush user details from the cache                                      |
|killop | cluster | Allows the user to kill other users' operations                                               |
|planCacheRead | database or collection | Lets the user view information about cached query plans                                       |
|planCacheWrite | database or collection | Allows the user to delete cached query plans                                                  |
|changeStream | collection, database, or cluster | Allows the user to access real time change data from non-system collections                   |
|appendOpLogNote | cluster | Lets the user add notes to the oplog                                                          |
|replSetConfigure | cluster | Allows configuring replica sets                                                               |
|replSetGetConfig | cluster | Lets the user view the current replica set configuration                                      |
|replSetGetStatus | cluster | Lets the user find the current status of the replica set                                      |
|replSetStateChange | cluster | Allows the user to manage the state of cluster replica sets                                   |
|addShard | cluster | Lets the user add a shard replica to a sharded cluster                                        |
|clearJumboFlag | database or collection | Lets the user clean up oversized chunks in a shard                                            |
|enableSharding | cluster, database, or collection | Allows user to enable sharding on clusters and database or manage sharding on a cluster level |
|refineCollectionShardKey | database or collection | Lets the user add additional fields to an existing shard key                                  |
|flushRouterConfig | cluster | Lets the user mark a cached routing table as obsolete                                         |
|getShardVersion | database | Internal command                                                                              |
|listShards | cluster | Lets user see a list of configured shards for the cluster                                     |
|moveChunk | database or collection | Allows a user to move a chunk to a new shard                                                  |
|removeShard | cluster | Lets the user drain chunks from a shard and then remove the shard from the cluster            |
|shardingState | cluster | Allows the user to view whether the MongoDB server is part of a sharded cluster               |
|splitChunk | database or collection | Allows the user to merge or split chunks in a shard                                           |
|splitVector | database or collection | Internal command                                                                              |
|applicationMessage | cluster | Lets the user add custom messages to the audit log                                            |
|collMod | database or collection | Lets the user modify the options associated with a collection                                 |
|compact | database or collection | Allows the user to defragment data and indexes in a collection                                |
|connPoolSync | cluster | Internal command                                                                              |
|convertToCapped | database or collection | Lets the user convert a collection into a capped collection with a set maximum size           |
|dropConnections | cluster | Lets the user remove outgoing connections from MongoDB to a specified host                    |
|dropDatabase | database | Lets the user delete the current database                                                     |
|dropIndex | database or collection | Lets the user delete an index                                                                 |
|forceUUID | cluster | Lets users define collections using globally unique UUIDs                                     |
|fsync | cluster | Allows the user to flush all pending writes to storage and lock the cluster for writes        |
|getDefaultRWConcern | cluster | Lets the user view the default read and write consistency and isolation settings              |
|getParameter | cluster | Lets the user query for the value of a parameter                                              |
|hostInfo | cluster | Allows the user to see information about the server running the MongoDB instance              |
|logRotate | cluster | Lets the user trigger log rotation                                                            |
|reIndex | database or collection | Lets the user rebuild the indexes for a collection                                            |
|renameCollectionSameDB | database | Lets the user rename a collection in the current database                                     |
|setDefaultRWConcern | cluster | Lets the user specify the default read and write consistency and isolation settings           |
|setParameter | cluster | Allows the user to define the value for parameters                                            |
|shutdown | cluster | Lets the user shutdown the MongoDB instance                                                   |
|impersonate | cluster | Lets the user kill sessions associated with other users and roles                             |
|listSessions | cluster | Lets the user list all sessions by all users                                                  |
|killAnySession | cluster | Lets the user kill all sessions for a specific user or a pattern                              |
|checkFreeMonitoringStatus | cluster | Allows the user to see the status of cloud monitoring functionality                         |
|setFreeMonitoring | cluster | Lets the user enable or disable cloud monitoring functionality                                |
|collStats | database or collection | Allows the user to view statistics about collections                                          |
|connPoolStats | cluster | Lets the user see the status of outgoing connections from MongoDB instances                   |
|dbHash | database or collection | Lets the user query for hashed values of collections in a database                            |
|dbStats | database | Allows the user to view storage statistics                                                    |
|getCmdLineOpts | cluster | Lets the user see the command line options used to start the MongoDB instance                 |
|getLog | cluster | Lets the user see the most recent MongoDB events                                              |
|listDatabases | cluster | Lets the user see the list of all databases                                                   |
|listCollections | database | Allows the user to see a list of collections and views in a database                          |
|listIndexes | database or collection | Lets the user see what indexes are associated with a specific collection                      |
|netstat | cluster | Internal command                                                                              |
|serverStatus | cluster | Lets the user view information about the database's current state                             |
|validate | database or collection | Allows the user to check a collections data and indexes for correctness                       |
|top | cluster | Lets the user see usage statistics for collections                                            |

## Roles available in MongoDB
[Here](https://www.prisma.io/dataguide/mongodb/authorization-and-privileges) a the list of actions available in MongoDB as well as their function: