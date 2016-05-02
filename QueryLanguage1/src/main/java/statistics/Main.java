package statistics;

import statistics.matcher.*;

public class Main {

    public static void main(String[] args) {
        Statistics stats = new Statistics(new PlayerReaderImpl("http://nhlstats-2013-14.herokuapp.com/players.txt"));

        /*Matcher m = new And(new HasFewerThan(11, "goals"),
         new HasAtLeast(10, "assists"),
         new PlaysIn("PHI")
         );

         Matcher or = new Or(new HasFewerThan(5, "goals"),
         new PlaysIn("PHI")
         );
        
         Matcher not = new Not(new HasFewerThan(40, "goals"),
         new PlaysIn("WSH")
         );
        
         for (Player player : stats.matches(not)) {
         System.out.println(player);
         }*/
        QueryBuilder query = new QueryBuilder();
        /*
         Matcher m = query.playsIn("NYR")
         .hasAtLeast(10, "goals")
         .hasFewerThan(25, "assists").build();

         for (Player player : stats.matches(m)) {
         System.out.println( player );
         }
         */

        Matcher m1 = query.playsIn("PHI")
                .hasAtLeast(10, "goals")
                .hasFewerThan(20, "assists").build();

        Matcher m2 = query.playsIn("EDM")
                .hasAtLeast(50, "points").build();

        Matcher m = query.oneOf(m1, m2).build();
        
        for (Player player : stats.matches(m)) {
            System.out.println(player);
        }
    }
}
