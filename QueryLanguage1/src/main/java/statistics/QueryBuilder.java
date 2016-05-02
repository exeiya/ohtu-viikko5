
package statistics;

import statistics.matcher.*;
import statistics.matcher.HasAtLeast;
import statistics.matcher.HasFewerThan;
import statistics.matcher.Matcher;
import statistics.matcher.PlaysIn;

public class QueryBuilder {
    
    Matcher matcher;
    
    public QueryBuilder(){
        matcher = new PlaysIn("");
    }
    
    public Matcher build(){
        Matcher matcher = this.matcher;
        this.matcher = new PlaysIn("");
        return matcher;
    }
    
    QueryBuilder playsIn(String team){
        this.matcher = new And(this.build(), new PlaysIn(team));
        return this;
    }
    
    QueryBuilder hasAtLeast(int value, String category){
        this.matcher = new And(this.build(), new HasAtLeast(value, category));
        return this;
    }
    
    QueryBuilder hasFewerThan(int value, String category){
        this.matcher = new And(this.build(),new HasFewerThan(value, category));
        return this;
    }
    
    QueryBuilder oneOf(Matcher...matcher){
        this.matcher = new Or(matcher);
        return this;
    }
    
    QueryBuilder notAny(Matcher...matcher){
        this.matcher = new Not(matcher);
        return this;
    }
    
}
