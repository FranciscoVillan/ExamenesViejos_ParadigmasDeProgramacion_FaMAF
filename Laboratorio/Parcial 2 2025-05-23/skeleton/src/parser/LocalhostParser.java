package parser;

import feed.Feed;

/* Esta clase implementa el parser de feed de tipo localhost (xml)
 * https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm 
 * */

public class LocalhostParser extends Parser {


    public Feed parseLocalhost(String localhostFeed,String url, String type){
        Feed feed = new Feed(url,type);
        
        /*COMPLETAR EJ3 */
        
        return feed;
    }

}
