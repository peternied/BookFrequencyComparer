This project was completed in roughly 6 hours time. It is definately not complete in that it does not fully duplicate the example, but there are alot of requirements questions that cause me to take pause before adding any more details for example:

1) Words by my definition would exist in a dictionary, and to treat a random stream of characters as a word (e.g. abc, xxxxxxxxxxxx) does not seem to honor the true test that we want to accomplish here if we were working with real data.  Or at least this would carry implications as to some of the methodologies used here.

2) I am over counting some works (this is what I mean by not fully duplicate).  With all of my small scale test cases I do not appear to be running into bugs, but that might speak to a broader test approach that is needed or more importantly the ability to get more sample data from basis of comparison.  It is not feasible to try to track down 4000 instances of a word to figure out why you got 4001 if you don't have a crystal clear image of what you are looking for.  I would work with my team for code reviews as well as testers to see if they could give me hints as what I should be looking for.

3) The html files have some non-sense characters within them.  This has me concerned that either the source files were corrupted or that these files have changed in some way since the original data was collected. Example, lacartniiilvisiratracsarbmvtabiledmek is found within the document Julies Vern book.

4) Sometimes there are html specific character defintions that conflict with the raw character interpretation.  For example 'thee&#8212;by' contains a special html control character within it.  Now based on our definition words that contain characters are considered invalid, and this word does statify this criteria, but you could also argue thta this needs to be converted into the browser representation.  This is doable but is non-trival to include so this would need to be vetted with the consumer of the application.

5) I started with the assumption that words only include the ANSI a-zA-Z characters, We could include latin such as à, but I'm not certain what implications the localization of this method would affect that I do not know such as other forms of punctation.  This also applies to the greater unicode character set, we would need a really crisp defintion reviewed with the interested parties 

6) I started with a couple of interfaces, clearly there was some payoff with the different word parsers, but it would also be trival to add support for UrlBook or other book content types even including reading encypted content or other types.


Here is an example output when ExampleCompareBook is run with a report that only spits out the top 10:

Vern				Poe				
Word	Hits	Hyphens		Word	Hits	Hyphens		
the	4966	0		the	716	1		
of	2915	0		of	412	0		
to	1929	22		and	310	0		
and	1868	2		a	201	0		
a	1679	0		to	184	1		
in	1202	1		in	173	0		
was	1019	0		is	115	0		
my	937	0		that	108	0		
it	842	0		or	106	0		
we	795	0		with	103	0		

Lastly, this was completed original code build by Peter Nied, with Eclipse and Git.  There was some usage of Oracle's Javadocs, and hosting is provided by GitHub.