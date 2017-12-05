<?php

/**
 * Class to handle all db operations
 * This class will have CRUD methods for database tables
 *
 * @author Ravi Tamada
 * @link URL Tutorial link
 */
class DbHandler {

    private $conn;

    function __construct() {
        require_once dirname(__FILE__) . '/DbConnect.php';
        // opening db connection
        $db = new DbConnect();
        $this->conn = $db->connect();
    }

    public function deleteRow($reg) {

        $stmt = $this->conn->prepare("DELETE FROM students WHERE regno = ?");
        $stmt->bind_param("s",$reg);
        if($stmt->execute()){
            $result = array();
            $result['error'] = false;
            $stmt->close();
            return $result;
        }
        else{
            $stmt->close();
            return NULL;
        }
	}

    public function addRow($name,$reg,$school,$cgpa) {

        $stmt = $this->conn->prepare("INSERT INTO students VALUES(?,?,?,?)");
        $stmt->bind_param("ssss",$name,$reg,$school,$cgpa);
        if($stmt->execute()){
            $result = array();
            $result['error'] = false;
            $stmt->close();
            return $result;
        }
        else{
            $stmt->close();
            return NULL;
        }
    }

    public function getRows() {

        $stmt = $this->conn->prepare("SELECT * FROM students");
        if($stmt->execute()) {
            $res = array();
            $result = $stmt->get_result();
            $res["total"] = $result->num_rows;
	    for($i=0;$i<$result->num_rows;$i++){
	        $res[$i] = $result->fetch_array(MYSQLI_ASSOC);
	    }
	    $res["error"] = false;
            $stmt->close();
            return $res;
        }
        else{
            $stmt->close();
            return NULL;
        }
    }

    public function customQuery($query){

        $stmt = $this->conn->prepare($query);
        if($stmt->execute()) {
            $res = array();
            $result = $stmt->get_result();
            //$fields = mysql_num_fields($stmt);
            $res["total"] = $result->num_rows;
	        for($i=0;$i<$result->num_rows;$i++){
	            $res[$i] = $result->fetch_array(MYSQLI_ASSOC);
	        }
            $res["error"] = false;
                $stmt->close();
                return $res;
            }
            else{
                $stmt->close();
                return NULL;
            }
    
        }

}

?>
