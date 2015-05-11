- Assumed the REST method signatures were hard requirements, otherwise could have placed data in body of REST request (in variety of formats)

- The requirement to return the status of push() as a String (as opposed to returning the status in the HTTP status code) adds extra complexity 
for non-happy scenarios - looked to use an ExceptionMapper for this

- Given time constraints here, assumed database size is relatively small, such that loading all elements into memory isn't a problem.
This also affected the post-processing of GCD values. If we had a huge data set, then it would make sense to look at 
dividing-and-conquering (e.g. Hadoop) by allowing auto-partitioning of the data, and calculating gcdSum() for each subset,
then collapsing down to a single value.

- Assumed the JMS queue holds data only until removed by calls to gcd(), but the data insertion history is stored separately in a database.
Note that the insertion into the JMS queue and the database is done inside the same SLSB, to wrap them in a transaction

- gcd() has been specifically written as non-recursive, so that it doesn't end in a stack overflow. If we wrote in Scala, then tail-recursion
could be implicitly managed by the language instead.

- As my target platform of JBoss EAP 6.4 supports Java 8, I've used the Java 8 functional interfaces and Streams API to write gcdList() and gcdSum()
to allow for parallel stream processing for larger data sets or more exotic computations than just sum() or gcd(). Might be interesting exploring
how something like JOOQ can push the computation of gcd to the server also. 

- Given the reasonably simple logic for gcd() etc (so mocking not really needed), and time constraints, didn't get around to writing tests for 
the app, but will start looking at Arquillian for more integration-style tests, on top of some use of stuff I'm more familiar with, like Mockito

Enjoy!

Michael Uren  