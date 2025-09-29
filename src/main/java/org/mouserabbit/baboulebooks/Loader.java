package org.mouserabbit.baboulebooks;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Exception;
import java.nio.file.*;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import org.mouserabbit.utilities.helpers.FileScanHelper;
import org.mouserabbit.utilities.log.Timer;
import org.mouserabbit.baboulebooks.classes.*;


// import org.mouserabbit.baboulebooks.FileSearch;

/**
 *
 * @author ytoubhan
 * @category Test
 * @version 1.01
 * 
 */
public class Loader {

    // private static final Logger logger = LogManager.getLogger("HelloWorld");
    private static String version = "Loader, Sep 25 2025 : 1.44";
    private static String _host = "localhost";
    private static int _port = 3306;
    private static String _user = "";
    private static String _password = "";
    private static String _database = "";
    private static String _xmlparameterfile = "";
    private static String _exceldatafile = "";
    private static int _maxlines = 0;               // Process only n lines
    private static boolean _zerodata = false;        // Empty tables before insertion ?                            
    private static Connection _dbconn = null;
    private static Logger _logger = null;
    private static final int TOKENNUMBER = 6;
    
    public static void main(String[] args) throws Exception {
        // Want to know the elapsed time
        Timer tt = new Timer();
        // log4j2 parameters:  Uncomment this line in case tracing log4j is required
        // System.setProperty("log4j2.debug", "true");
        // Point to the configuration file. We'll take the name from ENV which is defined in 
        // settings.json
        System.setProperty("log4j2.configurationFile", System.getenv("log4j2.configurationFile"));
        _logger = LogManager.getLogger();
        // Uncomment to check the java classpath
        System.out.println(System.getProperty("java.class.path"));        
        // Start work 
        System.out.print("\n\n\n");
        _logger.info(version);
        String logfile = System.getenv("log4j2.configurationFile");
        _logger.debug("The log4j xml parameter file is set to " + logfile);
        if ( logfile == null ){
            System.out.print("You must pass log4j2.configurationFile environment variable on the command line\n");
            System.out.print("java -Dlog4j2.configurationFile=\"YOURLOCATION\\log4j.xml\" YOURCLASS\n");
            throw new Exception("No log4j parameter file");
        }
        // Now check command line usage
        try {
            ProcessCommandLine(args);
            // Any xml parameter file to be processed ?
            if(_xmlparameterfile != null) analyzeXMLParameterFile();
            checkParameters();
            // Summary of some parameters before run
            _logger.info("Maximum number of lines per file : " + 
                        (_maxlines == 0 ? "No limit" : _maxlines));
            _logger.info("Purge DB tables before run ?  : " + 
                        (_zerodata == true ? "YES" : "NO"));
            // Connect the DB 
            String connectstring = "jdbc:mysql://" +  _host + ":" + _port + "/" + _database + "?" + "user=" + _user + "&password=" + _password;
            _logger.info("Connection : " + connectstring);
            _dbconn = DriverManager.getConnection(connectstring);
            // Set up data objects static properties
            Location.set_dbconn(_dbconn);
            Location.set_logger(_logger);
            Author.set_dbconn(_dbconn);
            Author.set_logger(_logger); 
            Editor.set_dbconn(_dbconn);
            Editor.set_logger(_logger);
            Book.set_dbconn(_dbconn);
            Book.set_logger(_logger);
            // Purge ? 
            if(_zerodata) {
                Book.DeleteAll();
                Location.DeleteAll();
                Author.DeleteAll();
                Editor.DeleteAll();
            }
            // Process input file(s)
            ProcessDataFiles();
            // Some report
            reportRun();
            // Uncomment if you want to use this new utility class ;-)
            // AnotherMethodToScanFiles("data");
        }
        catch (SQLException e) {
            _logger.error("SQL Error : " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } 
        catch (XMLParseException x) {
            _logger.error("An Exception occured  : " + x.getMessage());
            System.exit(1);

        }
        catch (Exception x) {
            _logger.error("An Exception occured  : " + x.getMessage());
            Usage();
            System.exit(1);
        }
        finally {
            if( _dbconn != null) {
                try {_dbconn.close();}
                catch(SQLException sqle) { _logger.error(sqle);}
            }
        }
        // End of job
        _logger.info("Job done in " + tt.getTimerString());
        _logger.info("Exit now");
        System.out.print("\n\n\n");
    }
    //---------------------------------------------------------------------------------------------------
    //   Load data
    //---------------------------------------------------------------------------------------------------
    private static void ProcessDataFiles() throws Exception {
        StringTokenizer linedata; // To separate line elements
        FileScanHelper files = new FileScanHelper(_exceldatafile);
        File oneFile = null;  
        String line;
        while ( files.HasMoreElements()) {
            oneFile = files.NextFile();
            System.out.println("\n");
            _logger.info("Processing :" + oneFile.getAbsolutePath());
            int linecount = 0;      // Number of lines processed
            int loaded = 0;         // Number of valid lines processed
            int formaterrors = 0;   // Number of badly formatted lines
            Location newlocation = null;

            try (Scanner myReader = new Scanner(oneFile)) {
                while (myReader.hasNextLine()) {

                    if(_maxlines != 0 && linecount > _maxlines)
                        break;

                    String location = "";
                    String id = "";
                    String title = "";
                    String lastname = "";
                    String firstname = "";
                    String editor = "";

                    line = myReader.nextLine();
                    if(linecount != 0) {
                        linedata = new StringTokenizer(line, ";");
                        while(linedata.hasMoreElements()) {
                            // Check line structure
                            if(linedata.countTokens() < TOKENNUMBER) {
                                location = linedata.nextToken();
                                id = linedata.nextToken();
                                _logger.warn("\tLine badly formatted for ID " + id);
                                ++formaterrors;
                            }
                            else {
                                // Insert Data : 4 tables
                                location = linedata.nextToken();
                                if(newlocation == null) {   // 1st data line ? 
                                    newlocation = new Location(location);
                                    newlocation.Insert();
                                }
                                else {
                                    String toto = newlocation.get_city();
                                    if ( toto.equals(newlocation.get_city())) {
                                        _logger.info("Existing Location : " + newlocation.get_id() + " : " + newlocation.get_city());
                                    }
                                    else {
                                        newlocation = new Location(location);
                                        newlocation.Insert();
                                    }
                                }
                                id = linedata.nextToken();
                                title = linedata.nextToken();
                                lastname = linedata.nextToken();
                                firstname = linedata.nextToken();
                                editor = linedata.nextToken();
                                
                                Author newauthor = new Author(firstname, lastname);
                                newauthor.Insert();
                                Editor neweditor = new Editor(editor);
                                neweditor.Insert();
                                Book newbook = new Book(title);
                                newbook.set_author(newauthor.get_id());
                                newbook.set_location(newlocation.get_id());
                                newbook.set_editor(neweditor.get_id());
                                newbook.Insert();
                                ++loaded;
                            }
                            break;
                        }
                    }
                    ++linecount;
                }
                _logger.info("\tNumber of data lines in file : " + (linecount-1));
                _logger.info("\tInserted  : " +  loaded + " line(s)");
                _logger.info("\tRejected  " + oneFile.getAbsolutePath() + " : " + formaterrors);
            } 
            catch (FileNotFoundException e) {
                _logger.error(e.getMessage());
            }
        }
    }
    //---------------------------------------------------------------------------------------------------
    //   Some final report
    //---------------------------------------------------------------------------------------------------
    private static void reportRun() {
        System.out.println("\n\n");
        int nbauthors = 0;
        int nbeditors = 0;
        int nblocations = 0;
        int nbbooks = 0;

        try {
            Statement stmt = _dbconn.createStatement();
            stmt.executeQuery("select count(distinct auth_id) from authors ");
            ResultSet rs = stmt.getResultSet();
            while(rs.next()) {
                nbauthors = rs.getInt(1);
                _logger.info(nbauthors + " authors in the table");
            }
            stmt.executeQuery("select count(distinct ed_id) from editors ");
            rs = stmt.getResultSet();
            while(rs.next()) {
                nbeditors = rs.getInt(1);
                _logger.info(nbeditors + " editors in the table");
            }
            stmt.executeQuery("select count(distinct loc_id) from locations ");
            rs = stmt.getResultSet();
            while(rs.next()) {
                nblocations = rs.getInt(1);
                _logger.info(nblocations + " locations in the table");
            }
            stmt.executeQuery("select count(distinct bk_id) from books ");
            rs = stmt.getResultSet();
            while(rs.next()) {
                nbbooks = rs.getInt(1);
                _logger.info(nbbooks + " books in the table");
            }
            System.out.println("\n\n");
        }
        catch(SQLException sqle) {
            // Already inserted, no action
            _logger.error(sqle.getMessage());
        }
    }
    //---------------------------------------------------------------------------------------------------
    //   Analyze command line arguments
    //---------------------------------------------------------------------------------------------------
    private static void ProcessCommandLine(String[] args) throws Exception {
        boolean recognized = false;

        for (int loop = 0; loop < args.length; ++loop) {
            recognized = false;
            if (args[loop].equals("-u"))
            {
                if (loop < args.length) {
                    recognized = true;
                    _user = args[++loop];
                }
            }
            if (args[loop].equals("-p"))
            {
                if (loop < args.length) {
                    recognized = true;
                    _password = args[++loop];
                }
            }
            if (args[loop].equals("-d"))
            {
                if (loop < args.length) {
                    recognized = true;
                    _database = args[++loop];
                }
            }
            if (args[loop].equals("-h"))
            {
                if (loop < args.length) {
                    recognized = true;
                    _host = args[++loop];
                }
            }
            if (args[loop].equals("-port")) {
                if (loop < args.length) {
                    _port = Integer.parseInt(args[++loop]);
                    recognized = true;
                }
            }
            if (args[loop].equals("-l")) {
                if (loop < args.length) {
                    _maxlines = Integer.parseInt(args[++loop]);
                    _logger.warn("Will read at most " + _maxlines + " file(s)");
                    recognized = true;
                }
            }
            if (args[loop].equals("-f"))
            {
                if (loop < args.length) {
                    recognized = true;
                    _xmlparameterfile = args[++loop];
                }
            }
            if (args[loop].equals("-z"))
            {
                recognized = true;
                _zerodata = true;
            }
            if (args[loop].equals("-x"))
            {
                if (loop < args.length) {
                    recognized = true;
                    _exceldatafile = args[++loop];
                }
            }
            // Trap a bad qualifier error
            if (!recognized) {
                if (args[loop].startsWith("-")) {
                    throw new Exception("Unrecognized qualifier : " + args[loop].toString());
                }
            }
        }
    }
    
    //---------------------------------------------------------------------------------------------------
    //   Final parameters control
    //---------------------------------------------------------------------------------------------------
    private static void checkParameters() throws Exception {
       
        if(_user.equals("")) {throw new Exception("Missing user name");}
        if(_password.equals("")) {throw new Exception("Missing password");}
        if(_database.equals("")) {throw new Exception("Missing database name");}
        if(_exceldatafile.equals("")) {throw new Exception("Missing excel data filename : use -d qualifier");}
    }
    //---------------------------------------------------------------------------------------------------
    //   Parse an XML parameter file : sample format here
    //   host, port, maxlines and zerodata are optional
    //---------------------------------------------------------------------------------------------------
    /*
     * 
     <server>
        <host>localhost</host>
        <port>3306</port>
        <user>baboule</user>
        <password>Baboule40</password>
        <database>babouledb</database>
        <exceldatafile>data/*.csv</exceldatafile>
        <maxlines>15</maxlines>
        <zerodata>true</zerodata>
    </server>

     */
    private static void analyzeXMLParameterFile() throws Exception, XMLParseException {
        _logger.info("Processing XML parameter file : " + _xmlparameterfile);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(_xmlparameterfile);

        NodeList nodelist = document.getElementsByTagName("server").item(0).getChildNodes();
        for (int i = 0; i < nodelist.getLength(); i++) {
            Node node = nodelist.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                switch(node.getNodeName()) {
                    case "host":
                                _host = node.getTextContent();
                                break;
                    case "port":
                                _port = Integer.parseInt(node.getTextContent());
                                break;
                    case "user":
                                if(_user != "") {_logger.warn("Overriding user with xml file parameter");}
                                _user = node.getTextContent();
                                break;
                    case "password":
                                if(_password != "") {_logger.warn("Overriding password with xml file parameter");}
                                _password = node.getTextContent();
                                break;
                    case "database":
                                if(_database != "") {_logger.warn("Overriding database with xml file parameter");}
                                _database = node.getTextContent();
                                break;
                    case "exceldatafile":
                                if(_exceldatafile != "") {_logger.warn("Overriding database with xml file parameter");}
                                _exceldatafile = node.getTextContent();
                                break;
                    case "maxlines":
                                _maxlines = Integer.parseInt(node.getTextContent());
                                break;
                    case "zerodata":
                                _zerodata =  Boolean.valueOf(node.getTextContent());
                                break;
                    default:
                            throw new XMLParseException("XML parameter file invalid tag : [ " + node.getNodeName() + " ]");
                }
            }
        }
    }
    //---------------------------------------------------------------------------------------------------
    //   Help user with a short usage message
    //---------------------------------------------------------------------------------------------------
    private static void Usage() {
        System.out.print("\nUsage: Loader -u user -p password -d database -x exceldatafile ");
        System.out.println(" [ -h host ] [ -port portnumber ] [ -f parameterfile.xml ]  [ -l limit ]");
        System.out.println("\t\t-user, is the username to connect to MySQL.");
        System.out.println("\t\t-password, is the user password to connect to MySQL.");
        System.out.println("\t\t-database, is the DB we want to connect.");
        System.out.println("\t\t-host, is optional and points to a server: default is localhost.");
        System.out.println("\t\t-port is optional is the mysql service port, default is 3306.");
        System.out.println("\t\t-f is optional and points to an xml file containing launch parameters.");
        System.out.println("\t\t-x is an excel csv file. Wildcards suported : *.csv");
        System.out.println("\t\t-l maxlines is optional: stop processing input file after maxlines.");
        System.out.println("\t\t-z Empty DB tables before load");
        System.out.println("\t\t");
    }
    //---------------------------------------------------------------------------------------------------
    // Just here to test some modern methods to scan for files
    //---------------------------------------------------------------------------------------------------
    private static void AnotherMethodToScanFiles(String dirspec) throws IOException {
        FileSearch sfbw = new FileSearch();
        Path path = Paths.get(dirspec);
        Path absolutePath = path.toAbsolutePath();          
        try {
            List<String> thelist = sfbw.searchWithWc(absolutePath, "glob:*.{csv}");
            for ( int i = 0; i < thelist.size(); ++i ){
                _logger.info("File Search from AnotherMethodToScanFiles --- Processing : " + thelist.get(i));
            }
        }
        catch(FileNotFoundException fne) {
            _logger.error(fne.getMessage());
        }
    }
}
/*
 *  R E S E R V O I R    C O D E 
 * 
 *             // Some select 
            // stmt = _dbconn.createStatement();  
            // if(stmt.execute("SELECT id, email, password,  firstname, lastname FROM users")) {
            //     rs = stmt.getResultSet();
            //     while (rs.next()) {
            //         int id = rs.getInt(1);
            //         String email = rs.getString(2);
            //         _logger.info("User email  [" + id + "]  " + email );
            //     }                
            // }
            // Exit


            Scanner scanner = new Scanner( System.in );
            String prefix = scanner.nextLine();

 */

