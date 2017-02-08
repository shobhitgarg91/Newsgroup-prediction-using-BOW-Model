This program requires two java files, TextMining.java and Node.java

To run the code, perform the following steps:
1. Compile the java code.
This can be done through the following command: 
javac TextMining.java Node.java

2. Execute the java program TextMining.java and give the path to the newsgroup as a command line argument followed the path to the stopwords text file.
Eg: 
java TextMining /home/adjunct/tmh/pub/newsgroups/20_newsgroups StopWords.txt


The program will run and will produce the confusion matrix for the bag of words model, it will also give the accuracy and the time taken for classification.

For example,
PRINTING CONFUSION MATRIX:

talk.religion.misc              170     35      4       14      21      2       0       0       0       1       1       0      00       0       0       0       0       0       52
talk.politics.misc              47      196     21      17      6       4       2       0       2       0       0       0      30       1       0       0       0       0       1
talk.politics.mideast           3       12      278     1       3       0       0       0       0       0       0       0      10       2       0       0       0       0       0
talk.politics.guns              18      39      9       227     0       0       1       0       2       1       0       0      10       0       0       0       0       1       1
soc.religion.christian          10      1       12      2       267     0       2       0       0       0       0       0      00       0       0       0       0       0       6
sci.space                       0       6       9       1       3       256     7       1       3       0       1       1      21       2       1       1       0       2       3
sci.med                         2       6       4       4       3       4       265     2       1       0       1       0      00       1       0       1       0       5       1
sci.electronics                 1       1       2       1       1       9       11      200     10      0       0       1      11       6       3       7       15      2       18      1
sci.crypt                       1       7       4       3       1       0       1       1       271     0       0       0      10       3       0       0       1       6       0
rec.sport.hockey                3       10      3       0       1       0       0       0       0       272     7       0      21       0       0       0       0       0       1
rec.sport.baseball              2       7       4       2       3       0       2       0       2       7       266     1      20       0       0       1       0       1       0
rec.motorcycles                 0       4       8       1       1       1       7       1       0       0       1       255    18       1       0       0       0       0       2       0
rec.autos                       2       15      9       1       4       3       3       10      2       0       0       8      231      5       1       3       0       1       2       0
misc.forsale                    2       5       11      0       6       5       4       12      7       2       4       1      17       151     7       9       30      9       17      1
comp.windows.x                  2       2       1       0       5       6       3       1       3       0       1       0      10       242     3       3       1       26      0
comp.sys.mac.hardware           1       0       3       0       1       3       0       4       6       0       1       0      24       17      211     19      7       21      0
comp.sys.ibm.pc.hardware        1       0       0       0       4       3       3       8       7       0       2       1      315      12      20      183     11      27      0
comp.os.ms-windows.misc         1       1       1       0       3       3       1       3       4       0       0       1      74       24      5       25      178     39      0
comp.graphics                   2       2       6       0       9       5       4       4       6       1       4       0      02       24      0       8       3       220     0
alt.atheism                     91      5       10      0       19      1       0       0       2       1       1       0      10       0       0       0       0       1       168

Accuracy: 75.11666%
Time taken in millis: 101 seconds
