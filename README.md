#Prerequisites
The API uses mongo db for persistence. It expects to have a user db with the following collections: accounts, transactions, users.
The users collection should already be populated with some users e.g:
```
{
    "_id" : ObjectId("5f9fafc4d42e4ad2cb7e57b3"),
    "username" : "ionescu"
}
```
# How to run
 1. cd into user-api checked out location
 2. run mvn clean install
 3. run  java -jar target/user-api-0.0.9-SNAPSHOT.jar


