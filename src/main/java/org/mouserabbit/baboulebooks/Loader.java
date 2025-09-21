package org.mouserabbit.baboulebooks;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.lang.Exception;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import org.mouserabbit.utilities.helpers.FileScanHelper;
import org.mouserabbit.utilities.log.Timer;

/**
 *
 * @author ytoubhan
 * @category Test
 * @version 1.01
 * 
 */
public class Loader {

    // private static final Logger logger = LogManager.getLogger("HelloWorld");
    private static String version = "Loader, Sep 21 2025 : 1.36";
    private static String _host = "localhost";
    private static int _port = 3306;
    private static String _user = "";
    private static String _password = "";
    private static String _database = "";
    private static String _xmlparameterfile = "";
    private static String _exceldatafile = "";
    private static Connection _dbconn = null;
    private static Logger _logger = null;
    
    
    public static void main(String[] args) throws Exception {

        Statement stmt = null;
        ResultSet rs = null;         

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
        _logger.info("Start now");
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
            // Connect the DB 
            String connectstring = "jdbc:mysql://" +  _host + ":" + _port + "/" + _database + "?" + "user=" + _user + "&password=" + _password;
            _logger.info("Trying to  " + connectstring);
            _dbconn = DriverManager.getConnection(connectstring);
            // Some select 
            stmt = _dbconn.createStatement();  
            if(stmt.execute("SELECT id, email, password,  firstname, lastname FROM users")) {
                rs = stmt.getResultSet();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String email = rs.getString(2);
                    _logger.info("User email  [" + id + "]  " + email );
                }                
            }
            // Exit
            _dbconn.close();
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
            if( rs != null) {
                try {rs.close();}
                catch(SQLException sqle) { _logger.error(sqle);}
            }
            if( stmt != null) {
                try {stmt.close();}
                catch(SQLException sqle) { _logger.error(sqle);}
            }
        }

        // End of job
        _logger.info("Job done in " + tt.getTimerString());
        _logger.info("Exit now");
        System.out.print("\n\n\n");
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
            if (args[loop].equals("-f"))
            {
                if (loop < args.length) {
                    recognized = true;
                    _xmlparameterfile = args[++loop];
                }
            }
            if (args[loop].equals("-d"))
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
    //   Parse an XML parameter file 
    //---------------------------------------------------------------------------------------------------
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
                                System.out.println( "HOST :  " + node.getTextContent());
                                if(_host != "") {_logger.warn("Overriding host with xml file parameter");}
                                _host = node.getTextContent();
                                break;
                    case "port":
                                System.out.println( "PORT :  " + node.getTextContent());
                                _port = Integer.parseInt(node.getTextContent());
                                break;
                    case "user":
                                System.out.println( "USER :  " + node.getTextContent());
                                if(_user != "") {_logger.warn("Overriding user with xml file parameter");}
                                _user = node.getTextContent();
                                break;
                    case "password":
                                System.out.println( "PASSWORD :  " + node.getTextContent());
                                if(_password != "") {_logger.warn("Overriding password with xml file parameter");}
                                _password = node.getTextContent();
                                break;
                    case "database":
                                System.out.println( "DATABASE :  " + node.getTextContent());
                                if(_database != "") {_logger.warn("Overriding database with xml file parameter");}
                                _database = node.getTextContent();
                                break;
                    case "exceldatafile":
                                System.out.println( "DATABASE :  " + node.getTextContent());
                                if(_exceldatafile != "") {_logger.warn("Overriding database with xml file parameter");}
                                _exceldatafile = node.getTextContent();
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
        System.err.println("\nUsage: Loader -u user -p password -d database [ -h host ] [ -port portnumber ] [ -f parameterfile.xml ] -d exceldatafile");
        System.err.println("\t\tuser, is the username to connect to MySQL.");
        System.err.println("\t\tpassword, is the user password to connect to MySQL.");
        System.err.println("\t\tdatabase, is the DB we want to connect.");
        System.err.println("\t\thost, is optional and points to a server: default is localhost.");
        System.err.println("\t\tport is the mysql service port, default is 3306.");
        System.err.println("\t\tf is an axml file containing launch parameters.");
        System.err.println("\t\td is an excel csv file.");
        System.err.println("\t\t");
    }
}
