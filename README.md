# SDA

## Homework 1 - Implement 

On the standart input you will receive a path to a folder in the following format ```D:/DataStructures/books/```, a number ```n```, and n words each on new line.

Your task is to read all ```txt``` files in this folder and search for each word if it is contained in some of the files.
For each word that is found you have to print the word itself and in what file it is found. If the word is not found print only the word.

**Note**: If some of the words is found more than once print all files that the word is found in.
**Note 2**: In the input folder you will have only files, that means that you won't have any subdirectories containing more txt files.

**Hint 1**: Use ``HasMap`` for storing found words. Check in Google for how to use it.
**Hint 2**: Use ``BufferedReader`` to read lines from a file. Check in Google for how to use it.
