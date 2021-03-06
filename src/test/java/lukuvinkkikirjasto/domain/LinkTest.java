
package lukuvinkkikirjasto.domain;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class LinkTest {
    
    Note link;
    
    @Before
    public void setUp() {
        this.link = new Link("header", "url", 0, "link info");
    }
    
    @Test
    public void constructorIsWorking() {
        assertEquals("header", link.getHeader());
        assertEquals("url", link.getUrl());
    }
    
    @Test
    public void tagsCanBeAdded() {
        link.addTag("tag");
        ArrayList<String> tags = new ArrayList<>();
        tags.add("tag");
        
        assertEquals(tags, link.getTags());
    }
}
