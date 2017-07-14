#API Description
**Build project**
* gradle build
* gradle run
## Book API
1. **GET** /book/all - Get all books
~~~
curl -X GET \
  http://localhost:8080/book/all \
  -H 'accept: application/json' \
  -H 'authorization: Basic cXJvazpxcm9rLXBhc3N3b3Jk'
~~~
2. **GET** /book/info/{book_id} - Get book by id
~~~
curl -X GET \
  http://localhost:8080/book/info/1 \
  -H 'accept: application/json' \
  -H 'authorization: Basic cXJvazpxcm9rLXBhc3N3b3Jk'
~~~
3. **GET** /book/info/title/{book_title} - Get Book by title
~~~
curl -X GET \
  http://localhost:8080/book/info/title/The%20Catcher%20in%20the%20Rye \
  -H 'accept: application/json' \
  -H 'authorization: Basic cXJvazpxcm9rLXBhc3N3b3Jk'
~~~
4. **GET** /book/info/isbn/{book_isbn} - Get Book by ISBN
~~~
curl -X GET \
  http://localhost:8080/book/info/isbn/9780230035287 \
  -H 'accept: application/json' \
  -H 'authorization: Basic cXJvazpxcm9rLXBhc3N3b3Jk'
~~~
5. **POST** /book/add - Add new book
~~~
curl -X POST \
  http://localhost:8080/book/add \
  -H 'accept: application/json' \
  -H 'authorization: Basic cXJvazpxcm9rLXBhc3N3b3Jk' \
  -H 'content-type: application/json' \
  -d '{
    "title": "Tender Is the Night",
    "genre": "NOVEL",
    "authors": [
        {
            "id": 3
        }
    ],
    "isbn": "9783257211191"
}'
~~~
6. **PUT** /book/update/{book_id} - Update book
~~~
curl -X PUT \
  http://localhost:8080/book/update/5 \
  -H 'accept: application/json' \
  -H 'authorization: Basic cXJvazpxcm9rLXBhc3N3b3Jk' \
  -H 'content-type: application/json' \
  -d '{
    "isbn": "new"
}'
~~~
## Author API
1. **GET** /author/all - Get All authors
~~~
curl -X GET \
  http://localhost:8080/author/all \
  -H 'accept: application/json' \
  -H 'authorization: Basic cXJvazpxcm9rLXBhc3N3b3Jk'
~~~
2. **GET** /author/info/{author_id} - Get Author by id
~~~
curl -X GET \
  http://localhost:8080/author/info/1 \
  -H 'accept: application/json' \
  -H 'authorization: Basic cXJvazpxcm9rLXBhc3N3b3Jk'
~~~
3. **GET** /author/info/firstName/{firstName} - Get Author by firstName
~~~
curl -X GET \
  http://localhost:8080/author/info/firstName/Franz \
  -H 'accept: application/json' \
  -H 'authorization: Basic cXJvazpxcm9rLXBhc3N3b3Jk'
~~~
4. **GET** /author/info/lastName/{lastName} - Get Author by lastName
~~~
curl -X GET \
  http://localhost:8080/author/info/lastName/Christie \
  -H 'accept: application/json' \
  -H 'authorization: Basic cXJvazpxcm9rLXBhc3N3b3Jk'
~~~
5. **POST** /author/add - Add Author
~~~
curl -X POST \
  http://localhost:8080/author/add \
  -H 'accept: application/json' \
  -H 'authorization: Basic cXJvazpxcm9rLXBhc3N3b3Jk' \
  -H 'content-type: application/json' \
  -d '{
	"firstName": "New",
    "lastName": "Data",
    "sex": "FEMALE",
    "birthDate": "1890-09-15"
}'
~~~
6. **PUT** /author/update/{author_id} - update author
~~~
curl -X PUT \
  http://localhost:8080/author/update/5 \
  -H 'accept: application/json' \
  -H 'authorization: Basic cXJvazpxcm9rLXBhc3N3b3Jk' \
  -H 'content-type: application/json' \
  -d '{
	"firstName": "New"
}'
~~~
7. **POST** /author/add/{author_id}/reward - Add Author Reward
~~~
curl -X POST \
  http://localhost:8080/author/add/4/reward \
  -H 'accept: application/json' \
  -H 'authorization: Basic cXJvazpxcm9rLXBhc3N3b3Jk' \
  -H 'content-type: application/json' \
  -d '{
	"year": 2017,
	"title": "New reward"
}'
~~~
8. **GET** /author/info/{author_id}/rewards - Get author rewards
~~~
curl -X GET \
  http://localhost:8080/author/info/1/rewards \
  -H 'accept: application/json' \
  -H 'authorization: Basic cXJvazpxcm9rLXBhc3N3b3Jk'
~~~
9. **GET** /author/info/short/{author_id} - get author short info
~~~
curl -X GET \
  http://localhost:8080/author/info/short/1 \
  -H 'accept: application/json' \
  -H 'authorization: Basic cXJvazpxcm9rLXBhc3N3b3Jk'
~~~

## Developer Notes
Problems, that was faced with:
1. Datasource could not find org.h2.Driver (how ever dependency is present in classpath) (Resolve set scope for h2 dependency to **compile**)
2. Data is not collecting from database (Add **spring.jpa.hibernate.ddl-auto=none** to the application.properties)
3. Default constructor, getters and setters are required for hibernate
4. Entity required to implements Serializable interface
5. @Query annotation with delete sql statement required additional annotations (@Transactional and @Modifying)  
6. Entities with annotations @ManyToMany could only deleted from the object with mappedBy attribute
7. If data need to be updated you should save data in repository or set @Transactional on the method or class. Do not forget to set cascade attribute.
8. Transactional configuration is excessively (add required cascade type for mapping annotation @ManyToMany or @OneToMany and add save method)
9. AuthorShort should be moved to the model package (its not an entity). 