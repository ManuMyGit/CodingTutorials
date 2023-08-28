# INTRODUCTION
Document databases, a subset of NoSQL databases, are designed to store and retrieve semi-structured or unstructured data in the form of documents. These documents can be in formats such as JSON, BSON (binary JSON), XML, or others. Each document typically represents a single entity or piece of data and can have varying structures without conforming to a rigid schema.

# DOCUMENT STRUCTURE
A document in a document database consists of key-value pairs, where the keys represent the attributes or fields of the document, and the values can be of various data types including strings, numbers, arrays, or even nested documents. Here's an example JSON document:

```json
{
  "name": "John Doe",
  "age": 30,
  "email": "john@example.com",
  "address": {
    "street": "123 Main St",
    "city": "Anytown",
    "country": "USA"
  }
}
```

Unlike traditional relational databases, document databases offer schema flexibility. This means that documents within the same collection (equivalent to tables in relational databases) can have different structures. New attributes can be added to documents without affecting existing ones, making it suitable for evolving data requirements.

# ADVANTAGES OF DOCUMENT DATABASES
- Flexible Schema: document databases excel in scenarios where data structures may change frequently or where data is naturally hierarchical. The ability to work with varying document structures is a significant advantage. 
- Scalability: document databases support horizontal scalability, meaning they can distribute data across multiple nodes or servers to handle increased load. This architecture supports high availability and fault tolerance. 
- Performance: document databases can provide fast read and write operations due to their ability to store related data together within a single document. Retrieving complete documents in a single operation reduces the need for complex joins or multiple queries. 
- Developer Productivity: the flexibility of document databases makes them developer-friendly. Changes in the application schema can often be accommodated without requiring extensive database modifications. Additionally, working with familiar JSON-like structures simplifies the coding process.

# POPULAR DOCUMENT DATABASE SYSTEMS
## [MongoDB](https://github.com/ManuMyGit/CodingTutorials/tree/main/database/nosql/document/mongodb)
MongoDB is one of the most popular document database systems. It uses JSON-like BSON documents and supports a wide range of programming languages. It's known for its flexibility, scalability, and ease of use.

## Couchbase
Couchbase is an open-source, NoSQL document database that offers both key-value and document data models. It provides features like in-memory caching and supports advanced querying.

## Firestore (by Google)
Firestore is a serverless, scalable document database provided by Google Cloud. It's designed for web and mobile applications and offers real-time synchronization and offline support.

## CouchDB
CouchDB is a distributed document database that focuses on ease of use and data replication. It's designed for scenarios where offline access and data synchronization are critical.