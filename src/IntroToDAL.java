// Author:  Wendy-Beth Minton
// Class:   3810 Database
// Lab:     Introduction to Database Connectivity

// THIS IS THE ONLY CLASS THAT SHOULD IMPORT JDBC! 
import java.sql.*;

// This is an example DAL. Notice all the database logic is contained within this class.
public class IntroToDAL
{
    // Notice that the databaseName, user and password are passed into this method. We are in the DAL,
    // and we cannot prompt the user for this information. That should be done in the presentation layer
    Connection getMySQLConnection(String databaseName, String user, String password)
    {
        try
        {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, user, password);
        } 
        catch (SQLException exception)
        {
            System.out.println("Failed to connect to the database" + exception.getMessage());
            return null;
        }
    }
      
    public boolean TryExecutingAQuery(String databaseName, String query, String user, String password)
    {
        try
        {
            /* We need to make a connection to the database, and the 
               DriverManager in JDBC needs to handle a few things:
                    1. What type of database is jdbc connection to?
                        - For us, we are connecting to a mysql database
                    2. Where is the database we are connecting to?
                        - For us, its on our local machine, and we connect to
                          it via port 3206
                        - See the home screen of MySql Workbench for details, as
                          this information is exactly what MySql Workbench
                          uses to connect to mysql!
                        - Also note that the first parameter is a URL telling us
                          where to find the mysql instance.
                    3. What user is connection to the database?
                        - Again, see the homescreen of MySqlWorkbench!
                        - I am currently using root
                    4. What is the password to connect to the database?
                        - I entered my root password here
                        - It is better to prompt for credentials, opposed
                          to hardcoding!
            
               Note that DriverManager.getConnection is overloaded, and you can 
               pass in a single url with all the needed information.
               I felt this was a clearer place to start. 
            
               Also note this is a generic method. It takes the name of any
               database, as well as any credentials you want, and gets you a
               connection. Writing methods that can be reused is always a best
               practice!
             */

            Connection myConnection = getMySQLConnection(databaseName, user, password);
            if(myConnection == null)
            {
                System.out.println("Failed to get a connection, cannot execute query");
                return false;
            }

            /* What are we going to execute, using our connection to the mysql
               database? Let's start with a simple query. We need to send and 
               execute a query, which is exactly what the Statement object is 
               good at. So, lets:
                1. Tell our Connection about our Statement
                2. Let's decide what query we want to run. We shouldn't hardcode
                   a query, we should be using stored procedures, but we are
                   learning, and this is our first lab!
                3. Let's execute our query, using the Statement, that knows how
                   to do that, and also knows all about how to connect to our
                   database
             */
            Statement myStatement = myConnection.createStatement();
            ResultSet myRelation = myStatement.executeQuery(query);

            /* Our Statement executed a query, and whenever you execute a query,
               we know that we get a result set back. MySql Workbench shows us
               this result set. JDBC's Statement object returns us a ResultSet.
            
               A ResultSet is conceptually a 2D array (or perhaps visualizing it
               as a table is more helpful). It looks just like the 
               relation you would expect our query to return. There are a 
               bunch of rows, and each row has a bunch of columns. So let's dive
               into our ResultSet in the Simplest way possible: a while loop!
             */
            
            // Declare an array of objects
            
            // Iterate over the ResultSet, row by row
            while (myRelation.next())
            {
                // For each row, we know there are two columns, RecipeName,
                // and IngredientId. Let's print them out, just to prove
                // we got the data from the database correctly.
                String myRecipeName = myRelation.getString("RecipeName");
                int myIngredientId = myRelation.getInt("IngredientId");
                System.out.println("Tuple Values:" + myRecipeName + "," + myIngredientId);
                
                // But, really you should add an instance of the object to the array of objects
            }
        } 
        /* When working with a database, exceptions can happen. There are lots
           of specific exceptions we could look for, I'm just catching the 
           general purpose one. But think about some of the things that could
           go wrong in our method:

           1. We fail to connect to the database
                - maybe we spelt the databases name wrong
                - maybe our firewall blocked out connection to port 3306
                - maybe we supplied incorrect credentials
                - maybe we don't have permssion to connect to the database
                - maybe mysql is not up and running, and accepting connections
           2. Our query has a syntax error
                - For more complicated queries, and for queries that accept 
                  parameters, this is a very common error. Especially when you
                  have to deal with escaping strings.
           3. We do not have permission to run our query against the database
           4. When we retrieved data from our ResultSet, maybe we spelt a column
              name wrong, or maybe we got the type of a column wrong
        
            When writing applications that interact with a database, you really
            want to be focused on what can go wrong, and making sure you log 
            errors very specifically. It will allow debugging your program as
            you develop it to go much smoother, saving you time. It also is 
            indespensible for programs that exist in the field, as errors happen,
            and fixing bugs is a part of life. Having as much information about
            went wrong wrong, and being able to figure out exactly what went
            wrong, and how to fix it, is a great skill for any programmer who
            works in a system with a lot of moving parts. Like a database!
         */ catch (SQLException exception)
        {
            System.out.println("Epic Fail Executing a Query:" + exception.getMessage());
            return false;
        }
        return true;
    }

    public boolean TryExecutingAStoredProcedure(String databaseName, String user, String password)
    {
        Connection myConnection = getMySQLConnection(databaseName, user, password);
        if(myConnection == null)
        {
            System.out.println("Failed to get a connection, cannot execute stored procedure");
            return false;
        }
        try
        {
            CallableStatement myStoredProcedureCall = myConnection.prepareCall("{Call GetRecipes()}");
            ResultSet myResults = myStoredProcedureCall.executeQuery();

            // Iterate over the ResultSet, row by row
            while (myResults.next())
            {
                String myRecipeName = myResults.getString("RecipeName");
                String myCookbookName = myResults.getString("CookbookName");
                int numServings = myResults.getInt("TotalServings");
                System.out.println("Tuple Values:" + myRecipeName + "," + myCookbookName + "," + numServings);
            }
        } catch (SQLException myException)
        {
            System.out.println("Failed to execute stored procedure:" + myException.getMessage());
            return false;
        }
        return true;
    }

    public boolean TryExecutingAStoredProcedureWithParam(String databaseName, String user, String password, String recipeName, String CookbookName, int numServings, boolean isBook, String website)
    {
        Connection myConnection = getMySQLConnection(databaseName, user, password);
        if(myConnection == null)
        {
            System.out.println("Failed to obstain a valid connection. Stored procedure could not be run");
            return false;
        }
        try
        {
            CallableStatement myStoredProcedureCall = myConnection.prepareCall("{Call InsertNewRecipe(?, ?, ?, ?, ?)}");
            myStoredProcedureCall.setString(1, recipeName);
            myStoredProcedureCall.setString(2,CookbookName);
            myStoredProcedureCall.setInt(3, numServings);
            myStoredProcedureCall.setBoolean(4, isBook);
            myStoredProcedureCall.setString(5, website);
            myStoredProcedureCall.executeQuery();
        } catch (SQLException myException)
        {
            System.out.println("Failed to execute stored procedure:" + myException.getMessage());
            return false;
        }
        return true;
    }

}