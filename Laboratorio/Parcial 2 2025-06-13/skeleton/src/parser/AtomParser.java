package parser;

import feed.Feed;

/* Esta clase implementa el parser de feed de tipo localhost (xml)
 * https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm 
 * */

public class AtomParser extends Parser {


    public Feed parseAtom(String localhostFeed,String url, String type){
        Feed feed = new Feed(url,type);
        
        /*COMPLETAR EJ3 */
        
        return feed;
    }

}
