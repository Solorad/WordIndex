WordIndex
=========

Takes text file and build suffix tree for frequently quering word index.
If word hasn't found - return null.
Has case sensitive and insesnsitive methods for index search.

WORKS ONLY WITH UTF-8 ENCODING.
 
Has jUnit tests, that compare results with results from dummy word count.

There are 2 WordIndex realisation and was discovered that concurrency classes are good here only on very big file.
To run test just print in terminal from  WI/ directory command 'mvn clean install' and tests will be executed.


@author Morenkov E.V.
