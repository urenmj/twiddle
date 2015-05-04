- Assumed the REST method signatures were hard requirements, otherwise could have placed data in body of REST request (in variety of formats)

- The requirement to return the status of push() as a String (as opposed to returning the status in the HTTP status code) adds extra complexity for non-happy scenarios - looked to use an ExceptionMapper for this

- Given time constraints here, assumed database size is relatively small, such that loading all elements into memory isn't a problem.
This also affected the post-processing of GCD values. If we had a huge data set, then it would make sense to look at 
dividing-and-conquering (e.g. Hadoop) by allowing auto-partitioning of the data, and calculating gcdSum() for each subset,
then collapsing down to a single value.

- Assumed the JMS queue holds data only until removed by calls to gcd(), but the data insertion history is stored separately in a database.
Note that the insertion into the JMS queue and the database is done inside the same SLSB, to wrap them in a transaction

- Because we don't have guarantees of Java 8, haven't used any lambdas or Streams library processing in the SOAP service, but flagging
it as potentially useful here

- gcd() has been specifically written as non-recursive, so that it doesn't end in a stack overflow. If we wrote in Scala, then tail-recursion
could be implicitly managed by the language instead.

- gcd(), gcdSum() have been written as Command pattern objects, to highlight that they may be better served via a functional language implementation
when available.

- Given the reasonably simple logic for gcd() etc (so mocking not really needed), and time constraints, didn't get around to writing tests for 
the app, but will start looking at Arquillan for more integration-style tests, on top of some use of stuff I'm more familiar with, like Mockito

Enjoy!

Michael Uren  